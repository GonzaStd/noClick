package io.gonzastd.noclick.assets.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static io.gonzastd.noclick.assets.ui.UiSpriteSheets.CONTEXT_ICONS;

public enum UiTextureRegionsTable {
    EYE(CONTEXT_ICONS.getRelativePath(), 0, 0, 14, 14),
    USE_HAND(CONTEXT_ICONS.getRelativePath(), 0, 14, 14, 14),
    INTERACT_HAND(CONTEXT_ICONS.getRelativePath(), 0, 28, 14, 14);

    boolean isRegion = false;
    final String texturePath;
    int x;
    int y;
    int width;
    int height;

    final String basePath = "ui/";
    UiTextureRegionsTable(String relativePath) {
        this.texturePath = this.basePath + relativePath;
    }

    UiTextureRegionsTable(String relativePath, int x, int y, int width, int height) {
        this.isRegion = true;
        this.texturePath = this.basePath + relativePath;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public String getTexturePath() {
        return this.texturePath;
    }

    public TextureRegion createTextureRegion(Texture texture) {
        TextureRegion region;
        if  (this.isRegion) {
            region = new TextureRegion(texture,  this.x, this.y, this.width, this.height);
        } else {
            region = new TextureRegion(texture);
        }
        return region;
    }
}
