package io.gonzastd.noclick.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.gonzastd.noclick.objects.types.movable.MovableEntity;

public class Player extends MovableEntity {
    protected static final int SPRITE_WIDTH = 16;
    protected static final int SPRITE_HEIGHT = 16;

    protected static final int HORIZONTAL_SPACING = 0;
    protected static final int VERTICAL_SPACING = 0;

    protected static final int FRAMES_PER_MOVE = 4;
    protected static final float MOVE_FRAME_DURATION = 0.15f;

    public Player(float startX, float startY) {
        super(
            startX,
            startY,
            100f,
            "sprites/character_base_16x16.png",
            (float) Player.SPRITE_WIDTH,
            (float) Player.SPRITE_HEIGHT,
            Player.HORIZONTAL_SPACING,
            Player.VERTICAL_SPACING,
            Player.FRAMES_PER_MOVE,
            Player.MOVE_FRAME_DURATION
        );
    }
    public void handleInput() {
        float dx = 0;
        float dy = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) dy += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) dy -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) dx += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) dx -= 1;

        move(dx, dy);
    }
}
