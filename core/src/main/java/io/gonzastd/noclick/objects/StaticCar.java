package io.gonzastd.noclick.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import io.gonzastd.noclick.objects.attributes.Facing;
import io.gonzastd.noclick.objects.types.StaticEntity;

public class StaticCar extends StaticEntity {
    protected static final int SPRITE_WIDTH = 48;
    protected static final int SPRITE_HEIGHT = 32;

    private static final String SPRITE_RIGHT_BODY = "sprites/car/car_right_body_grayscale.png";
    private static final String SPRITE_RIGHT_DETAILS = "sprites/car/car_right_details.png";

    private final Rectangle bounds;
    private final Sprite bodySprite;
    private final Sprite detailsSprite;
    private final Texture bodyTexture;
    private final Texture detailsTexture;

    public StaticCar(
        float startX,
        float startY,
        Facing facing,
        Color color
    ) {
        super(
            startX,
            startY,
            StaticCar.SPRITE_WIDTH,
            StaticCar.SPRITE_HEIGHT
        );

        if (facing != Facing.RIGHT && facing != Facing.LEFT) {
            throw new IllegalArgumentException("StaticCar only supports horizontal facings for the moment");
        }

        Color colorTint = new Color(color);

        this.bodyTexture = new Texture(SPRITE_RIGHT_BODY);
        this.bodySprite = new Sprite(this.bodyTexture);
        this.bodySprite.setPosition(startX, startY);
        this.bodySprite.setColor(colorTint);

        this.detailsTexture = new Texture(SPRITE_RIGHT_DETAILS);
        this.detailsSprite = new Sprite(this.detailsTexture);
        this.detailsSprite.setPosition(startX, startY);


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
