package io.gonzastd.noclick.objects.types;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public interface Drawable extends Disposable {
    void draw(SpriteBatch batch);
    void setPosition(float x, float y);
    Vector2 getPosition();
}
