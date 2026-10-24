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
import io.gonzastd.noclick.objects.types.collisionable.CollisionalDrawable;
import io.gonzastd.noclick.objects.global.Player;
import io.gonzastd.noclick.objects.types.Drawable;
import io.gonzastd.noclick.objects.types.Temporary;
import io.gonzastd.noclick.objects.types.collisionable.movable.MovableEntity;

abstract public class Level implements Disposable {
    protected static final float VIRTUAL_WIDTH = 480f;
    protected static final float VIRTUAL_HEIGHT = 480f;

    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    protected final OrthographicCamera camera;
    private final ScalingViewport viewport;
    protected SpriteBatch batch;
    private final Array<Drawable> drawables;
    private Array<CollisionalDrawable> basicDrawables;
    private Array<Temporary> temporaries;

    protected final Player player = new Player(Level.VIRTUAL_WIDTH / 2f, Level.VIRTUAL_HEIGHT / 2f);

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
        this.basicDrawables = new Array<>();
        this.temporaries = new Array<>();
    }
    protected <T extends CollisionalDrawable> void addBasicDrawable(T drawable) {
        if (drawable != null) {
            this.basicDrawables.add(drawable);
            this.drawables.add(drawable);
        }
    }

    protected void addBasicDrawables(Array<? extends CollisionalDrawable> drawables) {
        if (drawables != null) {
            for (int i = 0; i < drawables.size; i++) {
                CollisionalDrawable drawable = drawables.get(i);
                if (drawable != null) {
                    this.addBasicDrawable(drawable);
                }
            }
        }
    }

    protected <T extends Temporary> void addTemporary(T temporary) {
        if (temporary != null) {
            this.temporaries.add(temporary);
            this.drawables.add(temporary);
        }
    }

    protected void addTemporaries(Array<? extends Temporary> temporaries) {
        if (temporaries != null) {
            for (int i = 0; i < temporaries.size; i++) {
                Temporary temporary = temporaries.get(i);
                if (temporary != null) {
                    this.addTemporary(temporary);
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

    private void updateMovableEntities(float delta) {
        for (int i = 0; i < this.basicDrawables.size; i++) {
            CollisionalDrawable collidable = this.basicDrawables.get(i);
            if (collidable instanceof MovableEntity) {
                MovableEntity movableEntity = (MovableEntity) collidable;
                movableEntity.update(delta, this.basicDrawables);
            }
        }
    }

    abstract protected void initializeLevel();

    public void initialize(){
        this.initializeLevel();
        this.addBasicDrawable(this.player);
    }

    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.15f, 1f);

        this.mapRenderer.setView(this.camera);

        this.batch.setProjectionMatrix(this.camera.combined);
        this.batch.begin();
        for (int i = 0; i < this.drawables.size; i++) {
            Drawable drawable = this.drawables.get(i);
            drawable.draw(this.batch);
        }

        this.batch.end();
    }

    protected abstract void handleLevelInput();

    public void update(float delta) {
        this.player.handleInput();
        this.handleLevelInput();
        this.updateMovableEntities(delta);

        float pixelsPerUnit = this.getPixelsPerUnit();

        float cameraX = Math.round(
            this.player.getPosition().x * pixelsPerUnit
        ) / pixelsPerUnit;

        float cameraY = Math.round(
            this.player.getPosition().y * pixelsPerUnit
        ) / pixelsPerUnit;

        this.camera.position.set(cameraX, cameraY, 0);
        this.camera.update();
        for (int i = 0; i < this.drawables.size; i++) {
            Drawable drawable = this.drawables.get(i);
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
