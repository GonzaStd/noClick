package io.gonzastd.noclick.noclick;

import com.badlogic.gdx.Game;
import io.gonzastd.noclick.levels.LevelFactory;

public class NoClickGame extends Game {

    @Override
    public void create() {
        setScreen(new GameScreen(LevelFactory.PARKING_1.create()));
    }
}
