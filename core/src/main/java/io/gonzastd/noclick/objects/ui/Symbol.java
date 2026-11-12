package io.gonzastd.noclick.objects.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.gonzastd.noclick.assets.ui.UiTextureRegionsTable;
import io.gonzastd.noclick.objects.types.Timerizable;

public class Symbol extends Timerizable {
    Texture texture;
    TextureRegion textureRegion;
    Sprite sprite;
    Vector2 position;

    public Symbol(UiTextureRegionsTable uiTextureRegion, float x, float y) {
        this.texture = new Texture(uiTextureRegion.getTexturePath());
        this.textureRegion = uiTextureRegion.createTextureRegion(this.texture);
        this.sprite = new Sprite(this.textureRegion);
        this.sprite.setPosition(x, y);
    }
    @Override
    public void draw(SpriteBatch batch) {
        this.sprite.draw(batch);
    }

    @Override
    public void setPosition(float x, float y) {
        this.position = new Vector2(x, y);
    }

    @Override
    public Vector2 getPosition() {
        return this.position.cpy();
    }

    @Override
    public void dispose() {
        this.texture.dispose();
    }
}
