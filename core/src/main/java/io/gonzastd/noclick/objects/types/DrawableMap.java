package io.gonzastd.noclick.objects.types;

import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import io.gonzastd.noclick.objects.BasicDrawable;

public class DrawableMap extends BasicDrawable {
    private final OrthogonalTiledMapRenderer mapRenderer;

    public DrawableMap(OrthogonalTiledMapRenderer mapRenderer, float startX, float startY, float spriteWidth, float spriteHeight, float realWidth, float realHeight) {
        super(startX, startY, spriteWidth, spriteHeight, realWidth, realHeight);
        this.mapRenderer = mapRenderer;
    }

    @Override
    public void draw(com.badlogic.gdx.graphics.g2d.SpriteBatch batch) {
        this.mapRenderer.render();
    }

    @Override
    public void dispose() {
        this.mapRenderer.dispose();
    }
}
