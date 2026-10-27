package io.gonzastd.noclick.levels;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import io.gonzastd.noclick.noclick.CustomScaling;
import io.gonzastd.noclick.objects.types.Timerizable;
import io.gonzastd.noclick.objects.types.collidable.Collidable;
import io.gonzastd.noclick.objects.global.Player;
import io.gonzastd.noclick.objects.types.Drawable;
import io.gonzastd.noclick.objects.types.collidable.movable.MovableEntity;

abstract public class Level implements Disposable {
    public static final float ORIGINAL_VIRTUAL_WIDTH = 480f;
    public static final float ORIGINAL_VIRTUAL_HEIGHT = 480f;

    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    protected final OrthographicCamera camera;
    private final ScalingViewport viewport;
    protected SpriteBatch batch;
    private final Array<Drawable> drawables;
    private Array<Collidable> collidables;
    private Array<Timerizable> temporaries;

    protected final Player player = new Player(Level.ORIGINAL_VIRTUAL_WIDTH / 2f, Level.ORIGINAL_VIRTUAL_HEIGHT / 2f);

    public Level(String mapPath){
        this.map = new TmxMapLoader().load(mapPath);
        this.mapRenderer = new OrthogonalTiledMapRenderer(this.map);
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.viewport = new ScalingViewport(
            new CustomScaling(),
            ORIGINAL_VIRTUAL_WIDTH,
            ORIGINAL_VIRTUAL_HEIGHT,
            this.camera
        );
        this.drawables = new Array<>();
        this.collidables = new Array<>();
        this.temporaries = new Array<>();
    }

    protected <T extends Drawable> void addDrawable(T drawable) {
        if (drawable != null) {
            this.drawables.add(drawable);
        }
    }

    protected void addDrawables(Array<? extends Drawable> drawables) {
        if (drawables != null) {
            for (int i = 0; i < drawables.size; i++) {
                Drawable drawable = drawables.get(i);
                if (drawable != null) {
                    this.addDrawable(drawable);
                }
            }
        }
    }

    protected <T extends Collidable> void addCollidable(T drawable) {
        if (drawable != null) {
            this.collidables.add(drawable);
            this.drawables.add(drawable);
        }
    }

    protected void addCollidables(Array<? extends Collidable> drawables) {
        if (drawables != null) {
            for (int i = 0; i < drawables.size; i++) {
                Collidable drawable = drawables.get(i);
                if (drawable != null) {
                    this.addCollidable(drawable);
                }
            }
        }
    }

    protected <T extends Timerizable> void addTemporary(T temporary) {
        if (temporary.isTemporary()) {
            this.temporaries.add(temporary);
            this.drawables.add(temporary);
        } else {
            throw new IllegalCallerException("You should only add a possibleTemporary that in fact IS temporary.");
        }
    }

    protected void addTemporaries(Array<? extends Timerizable> temporaries) {
        if (temporaries != null) {
            for (int i = 0; i < temporaries.size; i++) {
                Timerizable temporary = temporaries.get(i);
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
        for (int i = 0; i < this.collidables.size; i++) {
            Collidable collidable = this.collidables.get(i);
            if (collidable instanceof MovableEntity) {
                MovableEntity movableEntity = (MovableEntity) collidable;
                movableEntity.update(delta, this.collidables);
            }
        }
    }

    abstract protected void initializeLevel();

    public void initialize(){
        this.initializeLevel();
        this.addCollidable(this.player);
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
        if (this.temporaries.size > 0){
            for (Timerizable temporary : this.temporaries) {
                temporary.updateTimeLeft(delta);

                if (temporary.getTimeLeft() <= 0) {
                    temporary.dispose();
                    this.drawables.removeValue(temporary, true);
                    this.temporaries.removeValue(temporary, true);
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

    public float getScreenWidth() {
        return this.viewport.getScreenWidth();
    }

    public float getScreenHeight() {
        return this.viewport.getScreenHeight();
    }
}
