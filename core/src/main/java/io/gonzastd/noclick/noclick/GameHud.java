package io.gonzastd.noclick.noclick;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import io.gonzastd.noclick.levels.Level;
import io.gonzastd.noclick.levels.labels.InteractText;
import io.gonzastd.noclick.levels.labels.ObjectiveText;
import io.gonzastd.noclick.levels.labels.TextLabel;
import io.gonzastd.noclick.scenes.LabelConfig;

import java.util.*;

public class GameHud implements Disposable {
    private final Drawable defaultLabelBackground;
    private final Texture blackBackgroundTexture;
    private final BitmapFont objectivesFont;
    private final BitmapFont interactionsFont;
    private final Label.LabelStyle objectivesStyle;
    private final Label.LabelStyle interactionsStyle;

    private final OrthographicCamera camera;
    private final ScalingViewport viewport;
    private final SpriteBatch batch;
    private final Stage stage;
    private final Map<Class<? extends TextLabel>, LabelConfig> labelTypeConfig;

    public GameHud() {
        Pixmap blackPixel = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        blackPixel.setColor(0f, 0f, 0f, 0.8f);
        blackPixel.fill();
        this.blackBackgroundTexture = new Texture(blackPixel);
        blackPixel.dispose();

        this.defaultLabelBackground = new TextureRegionDrawable(
            new TextureRegion(this.blackBackgroundTexture)
        );

        this.objectivesFont = new BitmapFont();
        this.interactionsFont = new BitmapFont();
        this.objectivesStyle = new Label.LabelStyle(this.objectivesFont, Color.ORANGE);
        this.interactionsStyle = new Label.LabelStyle(this.interactionsFont, Color.WHITE);

        this.camera = new OrthographicCamera();
        this.viewport = new ScalingViewport(
            new CustomScaling(),
            Level.ORIGINAL_VIRTUAL_WIDTH,
            Level.ORIGINAL_VIRTUAL_HEIGHT,
            this.camera
        );
        this.batch = new SpriteBatch();
        this.labelTypeConfig = new HashMap<>();
        this.stage = new Stage(this.viewport, this.batch);


        Table rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.top().left();

        Table objectivesTable = new Table();
        objectivesTable.top();
        objectivesTable.defaults().expandX().padTop(10).padLeft(10).left().row();
        LabelConfig objectivesConfig = new LabelConfig(objectivesTable, this.objectivesStyle);
        this.labelTypeConfig.put(ObjectiveText.class, objectivesConfig);

        Table interactionsTable = new Table();
        interactionsTable.top();
        interactionsTable.defaults().expandX().padLeft(10).left();
        LabelConfig interactionsConfig = new LabelConfig(interactionsTable, this.interactionsStyle);
        this.labelTypeConfig.put(InteractText.class, interactionsConfig);
        List<LabelConfig> labelConfigs = this.labelTypeConfig.values().stream().toList();

        for (int i=0; i<labelConfigs.size(); i++) {
            LabelConfig labelConfig = labelConfigs.get(i);
            labelConfig.getStyle().background = this.defaultLabelBackground;
            if (i<labelConfigs.size() - 1) {
                rootTable.add(labelConfig.getTable()).left().row();
            } else {
                rootTable.add(labelConfig.getTable()).left();
            }
        }
        this.stage.addActor(rootTable);

    }

    public <T extends TextLabel> void updateLabelsTable(Class<T> labelClass, Level currentLevel) {
        LabelConfig labelConfig = this.labelTypeConfig.get(labelClass);
        Table table = labelConfig.getTable();
        if (table.hasChildren()) {
            table.clearChildren();
        }

        List<? extends TextLabel> textLabels = currentLevel.getTextLabels(labelClass);
        Label.LabelStyle labelsStyle = labelConfig.getStyle();

        for (TextLabel textLabel: textLabels ) {
            Label label = new Label(textLabel.getText(), labelsStyle);
            label.setFontScale(0.8f); // TODO: Allow manual modification for each label type, outside of this function.
            table.add(label); // for modifiers use table.defaults() at the constructor of this class.
        }
    }

    public void updateViewport(int screenWidth, int screenHeight, boolean centerCamera) {
        this.viewport.update(screenWidth, screenHeight, centerCamera);
    }

    public void draw() {
        this.stage.draw();
    }

    @Override
    public void dispose() {
        this.stage.dispose();
        this.batch.dispose();
        this.objectivesFont.dispose();
        this.interactionsFont.dispose();
        this.blackBackgroundTexture.dispose();
    }


}
