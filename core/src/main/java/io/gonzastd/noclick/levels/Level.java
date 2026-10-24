package io.gonzastd.noclick.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import io.gonzastd.noclick.noclick.DoubleScaling;
import io.gonzastd.noclick.objects.BasicDrawable;
import io.gonzastd.noclick.objects.SimpleDrawable;
import io.gonzastd.noclick.objects.Temporary;
import io.gonzastd.noclick.objects.types.movable.MovableEntity;

abstract public class Level implements Disposable {
    protected static final float VIRTUAL_WIDTH = 480f;
    protected static final float VIRTUAL_HEIGHT = 480f;

    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    protected final OrthographicCamera camera;
    private final ScalingViewport viewport;
    protected SpriteBatch batch;
    private final Array<SimpleDrawable> drawables;

    public Level(String mapPath){
        this.map = new TmxMapLoader().load(mapPath);
        this.mapRenderer = new OrthogonalTiledMapRenderer(this.map);
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.viewport = new ScalingViewport(
            new DoubleScaling(),
            VIRTUAL_WIDTH,
            VIRTUAL_HEIGHT,
            this.camera
        );
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
        for (int i = 0; i < this.drawables.size; i++) {
            SimpleDrawable drawable = this.drawables.get(i);
            drawable.draw(this.batch);
        }

        this.batch.end();
    }

    public abstract void handleInput();

    abstract public void updateLevel(float delta);

    public void update(float delta) {
        this.updateLevel(delta);
        for (int i = 0; i < this.drawables.size; i++) {
            SimpleDrawable drawable = this.drawables.get(i);
            if (drawable instanceof Temporary temporary) {
                temporary.update(Gdx.graphics.getDeltaTime());

                if (temporary.getTime() <= 0) {
                    drawable.dispose();
                    this.drawables.removeIndex(i);
                    i--;
                }
            }
        }

    }

    public void resize(int width, int height) {
        this.viewport.update(width, height, true);
        this.camera.update();
    }

    protected float getPixelsPerUnit() {
        return (float) this.viewport.getScreenWidth()
            / this.viewport.getWorldWidth();
    }

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
