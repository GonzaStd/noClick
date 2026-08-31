package io.gonzastd.noclick.levels;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import io.gonzastd.noclick.objects.StaticCar;
import io.gonzastd.noclick.objects.attributes.Facing;

class Level1 extends Level {
    private final MapLayer carLayer;
    private static final Color[] CAR_COLORS = {
        Color.RED,
        Color.BLUE,
        Color.GREEN,
        Color.YELLOW,
        Color.WHITE
    };
    private Array<StaticCar> cars;

    public Level1() {
        super("map/level-one.tmx");
        TiledMap map = super.getMap();
        this.carLayer = map.getLayers().get("cars");
        this.cars = new Array<StaticCar>();
    }

    @Override
    public void initialize() {
        for (MapObject object : carLayer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                String facingStr = object.getProperties().get("facing").toString();
                Facing facing = Facing.fromString(facingStr);
                this.cars.add(
                    new StaticCar(rect.x, rect.y, facing, this.genColor())
                );
            }
        }
        super.addDrawables(this.cars);

        super.camera.setToOrtho(false, 480, 480);
        Vector2 center = new Vector2();
        new Rectangle(0, 0, 480, 480).getCenter(center);
        camera.position.set(center.x, center.y, 0);
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

    private Color genColor(){
        int index = MathUtils.random(0, CAR_COLORS.length - 1);
        return CAR_COLORS[index];
    }

    @Override
    public void resize(int width, int height) {

    }
}

