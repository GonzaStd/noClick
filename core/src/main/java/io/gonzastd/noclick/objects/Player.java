package io.gonzastd.noclick.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.gonzastd.noclick.objects.types.movable.MovableEntity;

public class Player extends MovableEntity implements BasicDrawable {

    public Player(float startX, float startY) {
        super(
            startX,
            startY,
            100f,
            "sprites/character_base_16x16.png"
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
