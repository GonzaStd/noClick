package io.gonzastd.noclick.objects.attributes;

public enum Facing {
    UP, RIGHT, DOWN, LEFT;

    public String nameLowerCase() {
        return name().toLowerCase();
    }
}
