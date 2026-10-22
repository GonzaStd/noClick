package io.gonzastd.noclick.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public abstract class Temporary implements SimpleDrawable, Disposable {
    private float time;

    public Temporary(float time) {
        this.time = time;
    }

    public float timer(){
        this.time -= Gdx.graphics.getDeltaTime();
        return this.time;
    }

    public abstract void draw(SpriteBatch batch);
    public abstract void dispose();
}
