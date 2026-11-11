package io.gonzastd.noclick.noclick;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuScreen implements Screen {

    private final NoClickGame game;
    private final Stage stage;
    private final BitmapFont titleFont;
    private final BitmapFont menuFont;
    private final TextButton.TextButtonStyle buttonStyle;

    public MenuScreen(NoClickGame game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());

        this.titleFont = new BitmapFont();
        this.titleFont.getData().setScale(3f);

        this.menuFont = new BitmapFont();
        this.menuFont.getData().setScale(2f);

        this.buttonStyle = new TextButton.TextButtonStyle();
        this.buttonStyle.font = this.menuFont;
        this.buttonStyle.fontColor = Color.WHITE;
        this.buttonStyle.overFontColor = Color.GREEN;
        this.buttonStyle.downFontColor = Color.LIGHT_GRAY;
        this.buttonStyle.disabledFontColor = Color.DARK_GRAY;

        Table table = new Table();
        table.setFillParent(true);
        table.center();

        Label.LabelStyle titleStyle = new Label.LabelStyle(this.titleFont, Color.GREEN);
        table.add(new Label("noClick", titleStyle)).padBottom(32).row();

        // Each option is added with an explicit call.
        this.addButtons(table, new MenuOption[]{
                MenuOption.NEW_GAME, MenuOption.CONTINUE_LAST_LEVEL, MenuOption.OPTIONS, MenuOption.GLOSSARY, MenuOption.CREDITS, MenuOption.EXIT_GAME
            }
        );

        this.stage.addActor(table);
    }

    // Creates the button, adds it to the table, and attaches the listener
    // that decides what to do based on the button's text.
    private void addButton(Table table, MenuOption option) {
        TextButton button = new TextButton(option.getLabel(), this.buttonStyle);
        button.setDisabled(false);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuScreen.this.handleClick(option);
            }
        });


        table.add(button).width(320).height(48).left().pad(4).row();
    }

    private void addButtons(Table table, MenuOption[] options) {
        for (MenuOption option : options) {
            this.addButton(table, option);
        }
    }

    // A single place where each option's behavior is decided.
    private void handleClick(MenuOption option) {
        switch (option) {
            case MenuOption.NEW_GAME: this.game.startLevel(1); break;
            case MenuOption.CONTINUE_LAST_LEVEL: this.game.continueLastLevel(); break;
            case MenuOption.EXIT_GAME: Gdx.app.exit(); break;
            default: break;
        }
        // OPTIONS / GLOSSARY / CREDITS are disabled, so they never reach here.
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.04f, 0.05f, 0.08f, 1f);
        this.stage.act(delta);
        this.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        this.stage.dispose();
        this.titleFont.dispose();
        this.menuFont.dispose();
    }
}
