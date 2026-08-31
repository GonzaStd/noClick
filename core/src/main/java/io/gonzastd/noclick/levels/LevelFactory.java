package io.gonzastd.noclick.levels;
import java.util.function.Supplier;

public enum LevelFactory {
    PARKING_1(() -> new Level1());

    private final Supplier<Level> supplier;

    LevelFactory(Supplier<Level> supplier) {
        this.supplier = supplier;
    }

    public Level create() {
        return supplier.get();
    }
}
