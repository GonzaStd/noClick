package io.gonzastd.noclick.objects.types.collidable.static_;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.gonzastd.noclick.objects.types.collidable.CollidableDrawable;
import io.gonzastd.noclick.objects.attributes.Facing;

public class StaticEntity extends CollidableDrawable {
    protected Facing facing;

    public StaticEntity(
        float startX,
        float startY,
        float spriteWidth,
        float spriteHeight,
        float realWidth,
        float realHeight,
        Facing facing
    ) {
        super(
            startX,
            startY,
            spriteWidth,
            spriteHeight,
            realWidth,
            realHeight
        );
        this.facing = facing;

    }

    public Facing getFacing() {
        return this.facing;
    }

    protected void checkAndApplyFacingFlip(Sprite... sprites) {
        if (this.facing == Facing.LEFT) {
            for (Sprite sprite : sprites) {
                sprite.flip(true, false);
            }
        }
    }

    protected void syncSpritePosition(Sprite... sprites) {
        for (Sprite sprite : sprites) {
            sprite.setPosition(this.getPosition().x, this.getPosition().y);
        }
    }


    @Override
    public void draw(SpriteBatch batch) {

    }

    @Override
    public void dispose() {

    }
}
