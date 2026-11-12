package io.gonzastd.noclick.noclick;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import io.gonzastd.noclick.levels.Level;

public class GameScreen implements Screen {

    private final Level currentLevel;
    private boolean isMusicPausedByApp = false;

    public GameScreen(Level level) {
        this.currentLevel = level;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(null); // Clean last input processor
        this.currentLevel.initialize();
    }

    @Override
    public void render(float delta) {
        this.currentLevel.update(delta);
        this.currentLevel.render();
    }

    @Override
    public void resize(int width, int height) {
        this.currentLevel.resize(width, height);
    }

    @Override
    public void pause() {
        if (this.currentLevel.backgroundMusic != null && this.currentLevel.backgroundMusic.isPlaying()) {
            this.currentLevel.backgroundMusic.pause();
            this.isMusicPausedByApp = true;
        }
    }

    @Override
    public void resume() {
        if (this.currentLevel.backgroundMusic != null && this.isMusicPausedByApp) {
            this.currentLevel.backgroundMusic.play();
            this.isMusicPausedByApp = false;
        }
    }

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        this.currentLevel.dispose();
    }
}
