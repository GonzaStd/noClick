package io.gonzastd.noclick.levels;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.gonzastd.noclick.objects.BasicDrawable;
import io.gonzastd.noclick.objects.SimpleDrawable;
import io.gonzastd.noclick.objects.Temporary;
import io.gonzastd.noclick.objects.types.movable.MovableEntity;

abstract public class Level implements Disposable {
    protected static final float VIRTUAL_WIDTH = 480f;
    protected static final float VIRTUAL_HEIGHT = 480f;

    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private float viewportWidth, viewportHeight;
    protected final OrthographicCamera camera;
    private final FitViewport viewport;
    protected SpriteBatch batch;
    private final Array<SimpleDrawable> drawables;

    public Level(String mapPath){
        this.map = new TmxMapLoader().load(mapPath);
        this.mapRenderer = new OrthogonalTiledMapRenderer(this.map);
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(480, 480, this.camera);
        this.drawables = new Array<>();
    }

    protected <T extends SimpleDrawable> void addDrawable(T drawable) {
        if (drawable != null) {
            this.drawables.add(drawable);
        }
    }

    protected void addDrawables(Array<? extends SimpleDrawable> drawables) {
        if (drawables != null) {
            for (int i = 0; i < drawables.size; i++) {
                SimpleDrawable drawable = drawables.get(i);
                if (drawable != null) {
                    this.drawables.add(drawable);
                }
            }
        }
    }

    protected OrthogonalTiledMapRenderer getMapRenderer() {
        return this.mapRenderer;
    }

    protected TiledMap getMap() {
        return this.map;
    }

    protected void updateMovableEntities(float delta) {
        Array<BasicDrawable> collidables = new Array<>();
        for (int i = 0; i < this.drawables.size; i++) {
            SimpleDrawable drawable = this.drawables.get(i);
            if (drawable instanceof BasicDrawable) {
                collidables.add((BasicDrawable) drawable);
            }
            if (drawable instanceof MovableEntity movableEntity) {
                movableEntity.update(delta, collidables);
            }
        }
    }

    abstract public void initialize();

    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.15f, 1f);

        this.mapRenderer.setView(this.camera);

        this.batch.setProjectionMatrix(this.camera.combined);
        this.batch.begin();
        if (this.drawables != null) {
            for (int i = 0; i < this.drawables.size; i++) {
                SimpleDrawable drawable = this.drawables.get(i);
                if (drawable != null) {
                    if (drawable instanceof Temporary && ((Temporary) drawable).timer() <= 0) {
                        drawable.dispose();
                        this.drawables.removeIndex(i);
                    } else {
                        if (
                            drawable instanceof BasicDrawable ||
                                (drawable instanceof Temporary && ((Temporary) drawable).timer() > 0)
                        ) {
                            drawable.draw(this.batch);
                        }
                        //basicDrawable.drawBounds(this.batch); // only used for debugging
                    }
                }
            }
        }
        this.batch.end();

    }

    public abstract void handleInput();

    abstract public void update(float delta);

    public void resize(int width, int height){
        float virtualAspect = VIRTUAL_WIDTH / VIRTUAL_HEIGHT;
        float screenAspect = (float) width / (float) height;

        if (screenAspect > virtualAspect) {
            this.viewportHeight = VIRTUAL_HEIGHT;
            this.viewportWidth = VIRTUAL_HEIGHT * screenAspect;
        } else {
            this.viewportWidth = VIRTUAL_WIDTH;
            this.viewportHeight = VIRTUAL_WIDTH / screenAspect;
        }

        this.camera.setToOrtho(false, this.viewportWidth, this.viewportHeight);
        this.viewport.update(width, height);
        this.setCameraPosition();
        this.camera.update();
    }

    public float getViewportWidth() {
        return this.viewportWidth;
    }

    public float getViewportHeight() {
        return this.viewportHeight;
    }

    abstract protected void setCameraPosition();

    private void disposeArray(Array<? extends Disposable> disposables){
        if (disposables != null){
            for (int i = 0; i < disposables.size; i++) {
                Disposable disposable = disposables.get(i);
                if (disposable != null) {
                    disposable.dispose();
                }
            }
        }
    }

    @Override
    public void dispose() {
        this.map.dispose();
        this.disposeArray(this.drawables);
        this.batch.dispose();
    }
}
