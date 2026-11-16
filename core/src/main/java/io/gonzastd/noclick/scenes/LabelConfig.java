package io.gonzastd.noclick.scenes;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class LabelConfig {
    private final Table table;
    private final Label.LabelStyle style;

    public LabelConfig(Table table, Label.LabelStyle style) {
        this.table = table;
        this.style = style;
    }

    public Table getTable() {
        return this.table;
    }

    public Label.LabelStyle getStyle() {
        return this.style;
    }
}
