package io.gonzastd.noclick.objects.types.movable;

public enum MovableDirection {
    DOWN(0, 0),
    UP(1, 90),
    LEFT(3, 180),
    RIGHT(2, 270);

    private final int spriteRow;
    private final int degrees;

    MovableDirection(int spriteRow, int degrees) {
        this.spriteRow = spriteRow;
        this.degrees = degrees;
    }

    public int getSpriteRow() {
        return spriteRow;
    }

    public int getDegrees() {
        return degrees;
    }
}

