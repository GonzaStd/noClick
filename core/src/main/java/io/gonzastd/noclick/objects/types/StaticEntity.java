package io.gonzastd.noclick.objects.types;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.gonzastd.noclick.objects.BasicDrawable;

public class StaticEntity extends BasicDrawable {

    public StaticEntity(float startX, float startY, float SPRITE_WIDTH, float SPRITE_HEIGHT) {
        super(startX, startY, SPRITE_WIDTH, SPRITE_HEIGHT);
    }

    @Override
    public void draw(SpriteBatch batch) {

    }

    @Override
    public void dispose() {

    }
}
