package io.gonzastd.noclick.levels;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import io.gonzastd.noclick.objects.Drawable;

abstract public class Level implements Disposable {
    private static final float VIRTUAL_WIDTH = 480f;
    private static final float VIRTUAL_HEIGHT = 480f;

    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private float viewportWidth, viewportHeight;
    protected final OrthographicCamera camera;
    protected SpriteBatch batch;
    private final Array<Drawable> drawables;

    public Level(String mapPath){
        this.map = new TmxMapLoader().load(mapPath);
        this.mapRenderer = new OrthogonalTiledMapRenderer(this.map);
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.drawables = new Array<>();
    }

    protected void addDrawable(Drawable Drawable) {
        if (Drawable != null) {
            this.drawables.add(Drawable);
        }
    }

    protected void addDrawables(Array<? extends Drawable> drawables) {
        if (drawables != null) {
            for (int i = 0; i < drawables.size; i++) {
                Drawable drawable = drawables.get(i);
                if (drawable != null) {
                    this.drawables.add(drawable);
                }
            }
        }
    }

    protected OrthogonalTiledMapRenderer getMapRenderer() {
        return mapRenderer;
    }

    protected TiledMap getMap() {
        return map;
    }

    abstract public void initialize();

    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.15f, 1f);

        mapRenderer.setView(this.camera);
        mapRenderer.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        if (this.drawables != null) {
            for (int i = 0; i < this.drawables.size; i++) {
                Drawable drawable = this.drawables.get(i);
                if (drawable != null) {
                    drawable.draw(this.batch);
                }
            }
        }
        batch.end();

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
        this.setCameraPosition();
        this.camera.update();
    }

    public float getViewportWidth() {
        return viewportWidth;
    }

    public float getViewportHeight() {
        return viewportHeight;
    }

    abstract protected void setCameraPosition();

    @Override
    public void dispose() {
        this.mapRenderer.dispose();
        this.map.dispose();
        if (this.drawables != null) {
            for (int i = 0; i < this.drawables.size; i++) {
                Drawable drawable = this.drawables.get(i);
                if (drawable != null) {
                    drawable.dispose();
                }
            }
        }
        this.batch.dispose();
    }
}
