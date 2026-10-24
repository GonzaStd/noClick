package io.gonzastd.noclick.levels;
import io.gonzastd.noclick.levels.level1.Level1;

import java.util.function.Supplier;

public enum LevelFactory {
    PARKING_1(() -> new Level1());

    private final Supplier<Level> supplier;

    LevelFactory(Supplier<Level> supplier) {
        this.supplier = supplier;
    }

    public Level create() {
        return this.supplier.get();
    }
}
