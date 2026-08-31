package io.gonzastd.noclick.levels;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import io.gonzastd.noclick.noclick.Constants;
import io.gonzastd.noclick.objects.Player;
import io.gonzastd.noclick.objects.StaticCar;
import io.gonzastd.noclick.objects.attributes.Facing;

class Level1 extends Level {
    private final MapLayer carLayer;
    private static final Color[] CAR_COLORS = {
        Color.RED,
        Color.BLUE,
        Color.GREEN,
        Color.YELLOW,
        Color.DARK_GRAY,
        Color.LIGHT_GRAY
    };
    private final Array<StaticCar> cars;

    public Level1() {
        super("map/level-one.tmx");
        TiledMap map = super.getMap();
        this.carLayer = map.getLayers().get("cars");
        this.cars = new Array<>();
    }

    @Override
    public void initialize() {
        for (MapObject object : carLayer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                String facingStr = object.getProperties().get("facing").toString();
                Facing facing = Facing.fromString(facingStr);
                this.cars.add(
                    new StaticCar(rect.x + Constants.TILE_SIZE, rect.y, facing, this.genColor())
                    // The rectangle from the car map object does not start where the sprite starts.
                    // It has a margin of 1 tile at left and 1 tile at right.
                );
            }
        }
        super.addDrawables(this.cars);
        super.addDrawable(new Player(Level.VIRTUAL_WIDTH / 2f, Level.VIRTUAL_HEIGHT / 2f));
    }

    @Override
    public void handleInput() {
        // TODO: process WASD, E to interact, etc.
    }

    @Override
    public void update(float delta) {
        this.handleInput();
        camera.update();
    }

    @Override
    protected void setCameraPosition() {
        this.camera.position.set(super.getViewportWidth() / 2f, super.getViewportHeight() / 2f, 0);
    }

    private Color genColor(){
        int index = MathUtils.random(0, CAR_COLORS.length - 1);
        return CAR_COLORS[index];
    }

}

