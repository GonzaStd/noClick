package io.gonzastd.noclick.noclick;
import io.gonzastd.noclick.levels.Level;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {
    private final Level currentLevel;

    public GameScreen(Level level) {
        this.currentLevel = level;
    }

    @Override
    public void show() {
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
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        currentLevel.dispose();
    }
}
