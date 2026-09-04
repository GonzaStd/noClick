package io.gonzastd.noclick.objects.types;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.gonzastd.noclick.objects.BasicDrawable;
import io.gonzastd.noclick.objects.attributes.Facing;

public class StaticEntity extends BasicDrawable {
    protected Facing facing;

    public StaticEntity(
        float startX,
        float startY,
        float spriteWidth,
        float spriteHeight,
        Facing facing
    ) {
        super(
            startX,
            startY,
            spriteWidth,
            spriteHeight
        );
        this.facing = facing;

    }

    public Facing getFacing() {
        return facing;
    }

    protected void checkAndApplyFacingFlip(Sprite... sprites) {
        if (facing == Facing.LEFT) {
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
