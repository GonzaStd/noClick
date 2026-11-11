package io.gonzastd.noclick.objects.types;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Timerizable implements Drawable {
    private boolean temporary;
    private float timeLeft;

    public abstract void draw(SpriteBatch batch);

    public abstract void dispose();

    public boolean isTemporary() {
        return this.temporary;
    }

    public float getTimeLeft() {
        if (this.temporary){
            return this.timeLeft;
        } else {
            throw new IllegalCallerException("Time left can only be accessed when object is temporary. " +
                "Please set your time left before using this method, setting it in constructor or using setTimeLeft().");
        }
    }

    public void setTimeLeft(float timeLeft) {
        this.temporary = true;
        this.timeLeft = timeLeft;
    }

    public void updateTimeLeft(float deltaTime) {
        this.timeLeft -= deltaTime;
    }

    public boolean isExpired(){
        return (this.getTimeLeft() <= 0f && this.isTemporary());
    }
}
