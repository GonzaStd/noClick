package io.gonzastd.noclick.levels.labels;

import com.badlogic.gdx.Input;

public class InteractText extends TextLabel {
    private final String keyName;
    private final String message;
    public InteractText(final int keycode, final String message) {
        this.keyName = Input.Keys.toString(keycode);
        this.message = message;
    }

    @Override
    public String getText() {
        return "(" + this.keyName + ") " + this.message;
    }
}
