package io.gonzastd.noclick.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public interface SimpleDrawable extends Disposable {
    void draw(SpriteBatch batch);
}
