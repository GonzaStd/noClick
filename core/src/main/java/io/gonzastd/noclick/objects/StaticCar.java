package io.gonzastd.noclick.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import io.gonzastd.noclick.objects.attributes.Facing;

public class StaticCar implements BasicDrawable {
    private static final String SPRITE_RIGHT_BODY = "sprites/car/car_right_body_grayscale.png";
    private static final String SPRITE_RIGHT_DETAILS = "sprites/car/car_right_details.png";

    private final Rectangle bounds;
    private final Sprite bodySprite;
    private final Sprite detailsSprite;
    private final Texture bodyTexture;
    private final Texture detailsTexture;

    public StaticCar(float x, float y, Facing facing, Color color) {
        if (facing != Facing.RIGHT && facing != Facing.LEFT) {
            throw new IllegalArgumentException("StaticCar only supports horizontal facings for the moment");
        }
        Vector2 position = new Vector2(x, y);
        Color colorTint = new Color(color);

        this.bodyTexture = new Texture(SPRITE_RIGHT_BODY);
        this.bodySprite = new Sprite(this.bodyTexture);
        this.bodySprite.setPosition(x, y);
        this.bodySprite.setColor(colorTint);

        this.detailsTexture = new Texture(SPRITE_RIGHT_DETAILS);
        this.detailsSprite = new Sprite(this.detailsTexture);
        this.detailsSprite.setPosition(x, y);


        if (facing == Facing.LEFT) {
            this.bodySprite.flip(true, false);
            this.detailsSprite.flip(true, false);
        }

        this.bounds = new Rectangle(
            position.x,
            position.y,
            this.bodySprite.getWidth(),
            this.bodySprite.getHeight());

    }

    public void draw(SpriteBatch batch) {
        bodySprite.draw(batch);
        detailsSprite.draw(batch);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public void dispose() {
        bodyTexture.dispose();
        detailsTexture.dispose();
    }
}
