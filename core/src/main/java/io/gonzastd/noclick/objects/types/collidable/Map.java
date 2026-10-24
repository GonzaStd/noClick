package io.gonzastd.noclick.objects.types.collidable;

import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Map extends Collidable {
    private final OrthogonalTiledMapRenderer mapRenderer;

    public Map(OrthogonalTiledMapRenderer mapRenderer, float startX, float startY, float spriteWidth, float spriteHeight, float realWidth, float realHeight) {
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
