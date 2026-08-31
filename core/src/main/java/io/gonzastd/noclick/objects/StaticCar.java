package io.gonzastd.noclick.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import io.gonzastd.noclick.objects.attributes.Facing;

public class StaticCar implements Drawable {
    private static final String SPRITE_RIGHT = "sprites/car/car_right.png";
    private final Rectangle bounds;
    private final Sprite sprite;
    private final Texture texture;

    public StaticCar(float x, float y, Facing facing, Color color) {
        if (facing != Facing.RIGHT && facing != Facing.LEFT) {
            throw new IllegalArgumentException("StaticCar only supports horizontal facings for the moment");
        }
        Vector2 position = new Vector2(x, y);
        Color colorTint = new Color(color);

        this.texture = new Texture(SPRITE_RIGHT);
        this.sprite = new Sprite(this.texture);
        this.sprite.setPosition(position.x, position.y);
        this.sprite.setColor(colorTint);


        if (facing == Facing.LEFT) {
            this.sprite.flip(true, false);
        }

        this.bounds = new Rectangle(
            position.x,
            position.y,
            this.sprite.getWidth(),
            this.sprite.getHeight());

    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
