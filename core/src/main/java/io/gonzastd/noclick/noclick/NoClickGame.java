package io.gonzastd.noclick.noclick;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import io.gonzastd.noclick.levels.LevelFactory;

public class NoClickGame extends Game {
    private int lastLevelNumber = 0;
    private Screen lastScreen = null;
    @Override
    public void create() {
        this.setScreen(new MenuScreen(this));
    }

    public void startLevel(int levelNumber) {
        int levelIndex = levelNumber - 1;
        LevelFactory[] levels = LevelFactory.values();
        LevelFactory level;

        if (Utilities.isInClosedInterval(levelIndex, 0, levels.length - 1))
            level = levels[levelIndex];
        else {
            throw new IllegalArgumentException("Invalid level index: " + levelIndex
                + " (level number " + levelNumber + ")");
        }

        this.lastLevelNumber = levelNumber;
        if (this.lastScreen != null) { // If this is not the first screen, dispose the last one.
            this.lastScreen.dispose();
        }
        this.lastScreen = new GameScreen(level.create());
        this.setScreen(this.lastScreen);
    }

    public void continueLastLevel() {
        if (this.lastLevelNumber > 0) {
            this.startLevel(this.lastLevelNumber);
        }
    }

    public void returnToMenu() {
        if (this.getScreen() instanceof GameScreen) {
            if (this.lastScreen != null) {
                this.setScreen(new MenuScreen(this));
                this.lastScreen.dispose();
                this.lastScreen = null;
            } else {
                throw new IllegalStateException("lastScreen shouldn't be null if we are in game screen.");
            }
        }

    }


    @Override
    public void render() {
        super.render(); // Game.render() calls screen.render()

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            this.returnToMenu();
        }
    }
}
