package io.gonzastd.noclick.levels.level1;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import io.gonzastd.noclick.assets.ui.UiTextureRegionsTable;
import io.gonzastd.noclick.levels.Level;
import io.gonzastd.noclick.noclick.Constants;
import io.gonzastd.noclick.noclick.Utilities;
import io.gonzastd.noclick.objects.global.Player;
import io.gonzastd.noclick.objects.attributes.Facing;
import io.gonzastd.noclick.objects.types.collidable.Map;
import io.gonzastd.noclick.objects.ui.Symbol;

import java.util.HashSet;
import java.util.Set;

public class Level1 extends Level {
    private Sound popSound;
    private final MapLayer parkingSlotsLayer;
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
        this.parkingSlotsLayer = map.getLayers().get("parkingSlots");
        this.cars = new Array<>();
    }

    @Override
    protected void initializeLevel() {
        this.setBackgroundMusicPath("music/day_in_the_park.mp3");
        this.popSound = Gdx.audio.newSound(Gdx.files.internal("sounds/pop.ogg"));
        this.addSound(this.popSound);
        int parkOffsetX = Constants.TILE_SIZE_IN_PIXELS * 8; // You can't go to the park that is 8 tiles long.
        Map drawableMap = new Map(super.getMapRenderer(),
            - ((float) parkOffsetX / 2),
            Player.BOX_HEIGHT - 1,
            Level.ORIGINAL_VIRTUAL_WIDTH,
            Level.ORIGINAL_VIRTUAL_HEIGHT,
            Level.ORIGINAL_VIRTUAL_WIDTH - Player.BOX_WIDTH - parkOffsetX,
            Level.ORIGINAL_VIRTUAL_HEIGHT - Player.BOX_HEIGHT * 2
        );
        super.addCollidable(drawableMap);

        MapObjects parkingSlots = this.parkingSlotsLayer.getObjects();
        Set<String> plates = new HashSet<>();
        for (MapObject parkingSlot : parkingSlots) {
            if (parkingSlot instanceof RectangleMapObject parkingSlotRectObj) { // A little check to avoid bad implementation on Tiled.
                Rectangle parkingSlotRect = parkingSlotRectObj.getRectangle();
                String facingStr = parkingSlot.getProperties().get("facing").toString();
                Facing facing = Facing.fromString(facingStr);
                String newPlate;
                Color newColor;

                do { newPlate = this.genPlate(); } // Solves Birth problem
                while (!plates.add(newPlate)); // Create new plate while adding plate to set fails (because this value already exists).
                newColor = this.genColor();
                int parkingSlotId = (int) parkingSlotRectObj.getProperties().get("id");

                this.cars.add(
                    new StaticCar(
                        parkingSlotRect.x + (float) Constants.TILE_SIZE_IN_PIXELS, // TODO: Change parkingSlotRectangle size to car's size on Tiled.
                        parkingSlotRect.y,
                        facing,
                        newColor,
                        newPlate,
                        parkingSlotId
                    )
                );
            }
        }
        super.addCollidables(this.cars);
    }

    @Override
    protected void handleLevelInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)){
            for (MapObject carRegion : this.parkingSlotsLayer.getObjects()) {
                RectangleMapObject rectangleCarRegion = ((RectangleMapObject) carRegion);
                if (this.player.getBounds().overlaps(rectangleCarRegion.getRectangle())){ // TODO: Handle collisions other way.
                    for(StaticCar car : this.cars){
                        if (car.getParkingSlotId() == (int) rectangleCarRegion.getProperties().get("id")){
                            this.popSound.play();
                            this.addTemporary(car.makePlateMessage());
                            Symbol lastSeenSymbol = getLastSeenSymbol(car);
                            this.addTemporary(lastSeenSymbol);
                        }
                    }
                }
            }
        }
    }

    private static Symbol getLastSeenSymbol(StaticCar car) {
        float HeightOfPlatePortionOfCar = (float) StaticCar.BOX_HEIGHT / 4;
        float symbolDistanceX = 14f;

        float symbolPositionX = 0;
        float symbolPositionY = car.getPosition().y + (HeightOfPlatePortionOfCar);
        if (car.getFacing() == Facing.LEFT){
            symbolPositionX = car.getPosition().x - symbolDistanceX;
        } else if (car.getFacing() == Facing.RIGHT){
            symbolPositionX = car.getPosition().x + symbolDistanceX + StaticCar.BOX_WIDTH;
        }
        Symbol newSymbol = new Symbol(UiTextureRegionsTable.EYE, symbolPositionX, symbolPositionY);
        newSymbol.setTimeLeft(2f);
        return newSymbol;
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
