package io.gonzastd.noclick.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Player implements BasicDrawable {
    private Vector2 position;
    private Texture sheet;
    private TextureRegion currentFrame;

    public Player(float x, float y) {
        this.position = new Vector2(x, y);
        this.sheet = new Texture("sprites/character_base_16x16.png");
        this.currentFrame = new TextureRegion(this.sheet, 0, 0, 16, 16);
    }


    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(this.currentFrame, this.position.x, this.position.y);
    }

    @Override
    public void dispose() {
        this.sheet.dispose();
    }
}
