package io.gonzastd.noclick.assets.ui;

public enum UiSpriteSheets {
    CONTEXT_ICONS("context_icons.png");

    final String relativePath;
    UiSpriteSheets(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getRelativePath() {
        return this.relativePath;
    }
}
