package io.gonzastd.noclick.levels.labels;

import com.badlogic.gdx.Input;

public class ObjectiveText extends TextLabel {
    int number;
    String message;

    public ObjectiveText(ObjectiveText lastObjective, String message) {
        if (lastObjective == null) {
            this.number = 1;
        } else {
            this.number = lastObjective.number + 1;
        }
        this.message = message;
    }

    @Override
    public String getText() {
        return " " + this.number + ") " + this.message;
    }
}
