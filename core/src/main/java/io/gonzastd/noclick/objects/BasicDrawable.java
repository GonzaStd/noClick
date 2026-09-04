package io.gonzastd.noclick.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class BasicDrawable {
    protected final float SPRITE_WIDTH;
    protected final float SPRITE_HEIGHT;

    protected Rectangle bounds;
    protected Vector2 position;

    public BasicDrawable(float startX, float startY, final float SPRITE_WIDTH, final float SPRITE_HEIGHT) {
        this.SPRITE_WIDTH = SPRITE_WIDTH;
        this.SPRITE_HEIGHT = SPRITE_HEIGHT;
        this.position = new Vector2(startX, startY);
        this.bounds = new Rectangle(
            this.position.x,
            this.position.y,
            this.SPRITE_WIDTH,
            this.SPRITE_HEIGHT
        );
    }


    public void updateBounds() {
        bounds.setPosition(position.x, position.y);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Vector2 getPosition() {
        return position;
    }

    public abstract void draw(SpriteBatch batch);
    public abstract void dispose();
}
