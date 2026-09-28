package io.gonzastd.noclick.levels;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import io.gonzastd.noclick.noclick.Constants;
import io.gonzastd.noclick.noclick.Utilities;
import io.gonzastd.noclick.objects.Player;
import io.gonzastd.noclick.objects.StaticCar;
import io.gonzastd.noclick.objects.attributes.Facing;
import io.gonzastd.noclick.objects.types.DrawableMap;
import java.util.HashSet;
import java.util.Set;

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
    private Player player;

    public Level1() {
        super("map/level-one.tmx");
        TiledMap map = super.getMap();
        this.carLayer = map.getLayers().get("cars");
        this.cars = new Array<>();
    }

    @Override
    public void initialize() {
        int parkOffsetX = Constants.TILE_SIZE * 8; // You can't go to the park that has 8 meters long.
        DrawableMap drawableMap = new DrawableMap(super.getMapRenderer(),
            - ((float) parkOffsetX / 2),
            Player.REAL_HEIGHT - 1,
            Level.VIRTUAL_WIDTH,
            Level.VIRTUAL_HEIGHT,
            Level.VIRTUAL_WIDTH - Player.REAL_WIDTH - parkOffsetX,
            Level.VIRTUAL_HEIGHT - Player.REAL_HEIGHT * 2
        );
        super.addDrawable(drawableMap);
        final int TILE_SIZE = Constants.TILE_SIZE;
        Set<String> plates = new HashSet<>();
        for (MapObject object : this.carLayer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectMapObject = ((RectangleMapObject) object);
                Rectangle rect = rectMapObject.getRectangle();
                String facingStr = object.getProperties().get("facing").toString();
                Facing facing = Facing.fromString(facingStr);

                String newPlate;
                do {
                    newPlate = this.genPlate();
                }
                while (!plates.add(newPlate)); // this means, create new plate while adding plate to set fails.
                // it avoids collision (we might miss one plate if it already existed. Birth problem)

                float carDrawOffsetX = TILE_SIZE;
                this.cars.add(
                    new StaticCar(
                        (float)(rect.x + carDrawOffsetX),
                        rect.y,
                        facing,
                        this.genColor(),
                        newPlate,
                        (int) rectMapObject.getProperties().get("id")
                    )
                    // The rectangle from the car map object does not start where the sprite starts.
                    // It has a margin of 1 tile at left and 1 tile at right.
                );
            }
        }
        super.addDrawables(this.cars);
        this.player = new Player(Level.VIRTUAL_WIDTH / 2f, Level.VIRTUAL_HEIGHT / 2f);
        super.addDrawable(this.player);
    }

    @Override
    public void handleInput() {
        this.player.handleInput();
    }

    @Override
    public void update(float delta) {
        this.handleInput();
        this.updateMovableEntities(delta);

        this.camera.position.set(this.player.getPosition().x, this.player.getPosition().y, 0);
        this.camera.update();
    }

    @Override
    protected void setCameraPosition() {
        this.camera.position.set(super.getViewportWidth() / 2f, super.getViewportHeight() / 2f, 0);
    }

    private Color genColor(){
        int index = MathUtils.random(0, CAR_COLORS.length - 1);
        return CAR_COLORS[index];
    }

    private String genPlate() {
        Utilities u = new Utilities();
        StringBuilder plate = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            plate.append(u.genRandomLetter());
        }
        plate.append(u.genRandomDigits(3));
        return plate.toString();
    }
}
