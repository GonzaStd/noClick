package io.gonzastd.noclick.objects.types;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.gonzastd.noclick.objects.BasicDrawable;

public class StaticEntity extends BasicDrawable {

    public StaticEntity(float startX, float startY, float spriteWidth, float spriteHeight) {
        super(startX, startY, spriteWidth, spriteHeight);
    }

    @Override
    public void draw(SpriteBatch batch) {

    }

    @Override
    public void dispose() {

    }
}
