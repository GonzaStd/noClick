package io.gonzastd.noclick.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class BasicDrawable {

    protected float spriteWidth;
    protected float spriteHeight;
    protected Rectangle bounds;
    protected Vector2 position;

    public BasicDrawable(float startX, float startY, float width, float height) {
        this.spriteWidth = width;
        this.spriteHeight = height;
        this.position = new Vector2(startX, startY);
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
