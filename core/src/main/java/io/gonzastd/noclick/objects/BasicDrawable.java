package io.gonzastd.noclick.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class BasicDrawable {
    protected float spriteWidth;
    protected float spriteHeight;

    protected Rectangle bounds;
    private Vector2 position;

    public BasicDrawable(float startX, float startY, final float spriteWidth, final float spriteHeight) {
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.position = new Vector2(startX, startY);
        this.bounds = new Rectangle(
            this.position.x,
            this.position.y,
            this.spriteWidth,
            this.spriteHeight
        );
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
        this.bounds.setPosition(x, y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public abstract void draw(SpriteBatch batch);
    public abstract void dispose();
}
