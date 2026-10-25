package io.gonzastd.noclick.levels.level1;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import io.gonzastd.noclick.levels.Level;
import io.gonzastd.noclick.noclick.Constants;
import io.gonzastd.noclick.noclick.Utilities;
import io.gonzastd.noclick.objects.global.Player;
import io.gonzastd.noclick.objects.attributes.Facing;
import io.gonzastd.noclick.objects.types.collidable.Map;
import io.gonzastd.noclick.objects.types.text.Text;

import java.util.HashSet;
import java.util.Set;

public class Level1 extends Level {
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
    protected void initializeLevel() {
        int parkOffsetX = Constants.TILE_SIZE * 8; // You can't go to the park that has 8 meters long.
        Map drawableMap = new Map(super.getMapRenderer(),
            - ((float) parkOffsetX / 2),
            Player.REAL_HEIGHT - 1,
            Level.VIRTUAL_WIDTH,
            Level.VIRTUAL_HEIGHT,
            Level.VIRTUAL_WIDTH - Player.REAL_WIDTH - parkOffsetX,
            Level.VIRTUAL_HEIGHT - Player.REAL_HEIGHT * 2
        );
        // TODO: Cleanup this mess.
        super.addCollidable(drawableMap);
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
        super.addCollidables(this.cars);
    }

    @Override
    protected void handleLevelInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)){
            for (MapObject object : this.carLayer.getObjects()) {
                RectangleMapObject rectMapObject = ((RectangleMapObject) object);
                if (this.player.getBounds().overlaps(rectMapObject.getRectangle())){ // TODO: Handle collisions other way.
                    for(StaticCar car : this.cars){
                        if (car.getParkingSlotId() == (int) rectMapObject.getProperties().get("id")){
                            Text plateMessage = new Text(car.getPlate(), new BitmapFont(), 3f);

                            plateMessage.setScale(3f);
                            plateMessage.setBackground(Color.WHITE, 20f);
                            plateMessage.setTextColor(Color.BLACK);
                            plateMessage.centerPosition(this.getScreenWidth(), this.getScreenHeight());
                            float x = plateMessage.getPosition().x;
                            plateMessage.setPosition(x, this.getScreenHeight() - plateMessage.getBoxHeight());

                            this.addTemporary(plateMessage);
                        }
                    }
                }
            }
        }
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
