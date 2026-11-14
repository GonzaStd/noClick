package io.gonzastd.noclick.objects.global;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.gonzastd.noclick.objects.types.collidable.Collidable;
import io.gonzastd.noclick.objects.types.collidable.movable.MovableEntity;

public class Player extends MovableEntity {
    public static final int SPRITE_WIDTH = 16;
    public static final int SPRITE_HEIGHT = 16;

    public static final int BOX_WIDTH = 12;
    public static final int BOX_HEIGHT = 7;

    public static final int HORIZONTAL_SPRITESHEET_SPACING = 0; // Not currently used.
    public static final int VERTICAL_SPRITESHEET_SPACING = 0;

    public static final int FRAMES_PER_MOVE = 4;
    public static final float MOVE_FRAME_DURATION = 0.15f;

    public Player(float startX, float startY) {
        super(
            startX,
            startY,
            60f,
            "sprites/character_base_16x16.png",
            (float) Player.SPRITE_WIDTH,
            (float) Player.SPRITE_HEIGHT,
            Player.BOX_WIDTH,
            Player.BOX_HEIGHT,
            Player.HORIZONTAL_SPRITESHEET_SPACING,
            Player.VERTICAL_SPRITESHEET_SPACING,
            Player.FRAMES_PER_MOVE,
            Player.MOVE_FRAME_DURATION
        );
    }

    public boolean hasObjectCloseRadius(Collidable object, float radiusDistance) {
        Vector2 playerBoxCenter = new Vector2();
        Vector2 objectBoxCenter = new Vector2();
        this.getBounds().getCenter(playerBoxCenter);
        object.getBounds().getCenter(objectBoxCenter);
        return playerBoxCenter.dst(objectBoxCenter) <= radiusDistance;
        // Answers: is object inside the provided radius of distance?
    }

    public boolean hasObjectCloseInAxis(Collidable object, float radiusDistance, Character axisChar) {
        Vector2 playerBoxCenter = new Vector2();
        Vector2 objectBoxCenter = new Vector2();
        boolean isClose;
        boolean isBetweenTheObject;

        Rectangle playerBounds = this.getBounds();
        Rectangle objectBounds = object.getBounds();
        playerBounds.getCenter(playerBoxCenter);
        objectBounds.getCenter(objectBoxCenter);

        switch (axisChar) {
            case 'x' :
                boolean isCloseInX = Math.abs(playerBoxCenter.x - objectBoxCenter.x) <= radiusDistance;
                isBetweenTheObject = Math.abs(playerBoxCenter.y - objectBoxCenter.y) <= objectBounds.getHeight()/2;
                isClose = isCloseInX && isBetweenTheObject;
                break;
            case 'y':
                boolean isCloseInY = Math.abs(playerBoxCenter.y - objectBoxCenter.y) <= radiusDistance;
                isBetweenTheObject = Math.abs(playerBoxCenter.x - objectBoxCenter.x) <= objectBounds.getWidth()/2;
                isClose = isCloseInY && isBetweenTheObject;
                break;
            default:
                throw new IllegalArgumentException("Character \"" + axisChar + "is not an axis. Use \"x\" or \"y\".");
        }

        return isClose;
        // Answers: is object inside the provided radius of distance in the axisChar axis?
    }

    public void handleInput() {
        float dx = 0;
        float dy = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) dy += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) dy -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) dx += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) dx -= 1;

        this.move(dx, dy);
    }
}
