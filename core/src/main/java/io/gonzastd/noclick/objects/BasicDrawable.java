package io.gonzastd.noclick.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class BasicDrawable {
    protected float spriteWidth;
    protected float spriteHeight;

    private final Rectangle bounds;
    private final Vector2 position;

    private final float boundsOffsetX;
    private final float boundsOffsetY;

    // Textura compartida de 1x1 blanca para debug
    private static Texture debugPixel;

    public BasicDrawable(float startX, float startY, final float spriteWidth, final float spriteHeight, final float realWidth, final float realHeight) {
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.position = new Vector2(startX, startY);

        this.boundsOffsetX = (spriteWidth - realWidth) / 2f;
        this.boundsOffsetY = 0f; // sprite base = bounds base

        this.bounds = new Rectangle(
            startX + boundsOffsetX,
            startY + boundsOffsetY,
            realWidth,
            realHeight
        );


        if (debugPixel == null) {
            Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            pixmap.setColor(Color.RED);
            pixmap.fill();
            debugPixel = new Texture(pixmap);
            pixmap.dispose();
        }
    }

    public Rectangle getBounds() {
        return this.bounds;
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
        this.bounds.setPosition(x + boundsOffsetX, y + boundsOffsetY);
    }

    public Vector2 getPosition() {
        return this.position;
    }

    /** Dibuja el contorno de los bounds con una línea blanca de 1px. */
    public void drawBounds(SpriteBatch batch) {
        float x = this.bounds.x;
        float y = this.bounds.y;
        float w = this.bounds.width;
        float h = this.bounds.height;
        float t = 1f; // grosor de la línea

        batch.setColor(Color.WHITE);
        batch.draw(debugPixel, x,         y,         w, t); // abajo
        batch.draw(debugPixel, x,         y + h - t, w, t); // arriba
        batch.draw(debugPixel, x,         y,         t, h); // izquierda
        batch.draw(debugPixel, x + w - t, y,         t, h); // derecha
    }

    public abstract void draw(SpriteBatch batch);
    public abstract void dispose();
}
