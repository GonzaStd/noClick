package io.gonzastd.noclick.objects.types;

import com.badlogic.gdx.utils.Disposable;

public interface Timerizable extends Drawable, Disposable {
    boolean isTemporary();
    float getTimeLeft();
    void setTimeLeft(float timeLeft);
    void updateTimeLeft(float deltaTime);

    default boolean isExpired(){
        return (this.getTimeLeft() <= 0f && this.isTemporary());
    }


}
