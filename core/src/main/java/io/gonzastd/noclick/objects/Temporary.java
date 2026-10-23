package io.gonzastd.noclick.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public abstract class Temporary implements SimpleDrawable, Disposable {
    private float time;

    public Temporary(float time) {
        this.time = time;
    }

    public float getTime() {
        return this.time;
    }

    public void update(float delta) {
        this.time -= delta;
    }

    public abstract void draw(SpriteBatch batch);
    public abstract void dispose();
}
