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
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    protected final OrthographicCamera camera;
    protected SpriteBatch batch;
    private Array<Drawable> drawables;

    public Level(String mapPath){
        this.map = new TmxMapLoader().load(mapPath);
        this.mapRenderer = new OrthogonalTiledMapRenderer(this.map);
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.drawables = new Array<Drawable>();
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

    abstract public void resize(int width, int height);

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
