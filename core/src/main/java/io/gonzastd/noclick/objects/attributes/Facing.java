package io.gonzastd.noclick.objects.attributes;

public enum Facing {
    UP, RIGHT, DOWN, LEFT;

    public String nameLowerCase() {
        return this.name().toLowerCase();
    }

    public static Facing fromString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Facing value cannot be null");
        }
        return valueOf(value.toUpperCase());
    }
}
