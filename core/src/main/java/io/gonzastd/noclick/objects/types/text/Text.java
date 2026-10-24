package io.gonzastd.noclick.objects.types.text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import io.gonzastd.noclick.objects.types.Drawable;
import io.gonzastd.noclick.objects.types.Timerizable;

public class Text implements Drawable, Disposable, Timerizable {
    private boolean temporary;
    private float timeLeft;

    private final Vector2 position = new Vector2();
    private boolean isPositionDefined = false;
    private boolean isCentered = false;

    private final String text;
    private final BitmapFont font;
    private final GlyphLayout layout = new GlyphLayout();
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private Color textColor = Color.WHITE.cpy();

    private boolean hasBackground = false;
    private Color backgroundColor;
    private float padding;
    private float boxWidth;
    private float boxHeight;

    public Text(String text, BitmapFont font) {
        this.text = text;
        this.font = font;
        this.layout.setText(this.font, this.text);
    }

    public Text(String text, BitmapFont font, float timeLeft) {
        this.text = text;
        this.font = font;
        this.layout.setText(this.font, this.text);
        this.setTimeLeft(timeLeft);
    }

    private void updateBoxSize() {
        this.layout.setText(this.font, this.text);
        this.boxWidth = this.layout.width + this.padding * 2f;
        this.boxHeight = this.layout.height + this.padding * 2f;
    }

    public float getBoxWidth() {
        return this.boxWidth;
    }

    public float getBoxHeight() {
        return this.boxHeight;
    }

    public void setBackground(Color backgroundColor, float padding) {
        if (this.isCentered) {
            throw new IllegalStateException(
                "Set the background before centering the message."
            );
        }

        this.backgroundColor = backgroundColor;
        this.padding = padding;
        this.hasBackground = true;

        this.updateBoxSize();
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public void setScale(float multiplier) {
        if (this.isCentered) {
            throw new IllegalStateException(
                "Set the scale before centering the message."
            );
        }

        this.font.getData().setScale(multiplier);
        this.updateBoxSize();
    }

    public Vector2 getPosition() {
        return this.position.cpy();
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
        this.isPositionDefined = true;
    }

    public void centerPosition() {
        this.updateBoxSize();

        float x = (Gdx.graphics.getWidth() - this.boxWidth) / 2f;
        float y = (Gdx.graphics.getHeight() - this.boxHeight) / 2f;

        this.setPosition(
            x + this.padding,
            y + this.padding + this.layout.height
        );

        this.isCentered = true;
    }


    private void createBackground(OrthographicCamera tempCamera){
        if (!this.hasBackground) {
            throw new IllegalStateException("Message background was not defined. Use setBackground().");
        }
        this.boxWidth = this.layout.width + this.padding * 2f;
        this.boxHeight = this.layout.height + this.padding * 2f;

        float boxX = this.position.x - this.padding;
        float boxY = this.position.y - this.layout.height - this.padding;

        this.shapeRenderer.setProjectionMatrix(tempCamera.combined);
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.shapeRenderer.setColor(this.backgroundColor);
        this.shapeRenderer.rect(boxX, boxY, this.boxWidth, this.boxHeight);
        this.shapeRenderer.end();
    }


    public void draw(SpriteBatch batch) {
        if (!this.isPositionDefined){
            throw new IllegalStateException(
                "Message position was not defined. Use centerPosition() or setPosition()."
            );
        }

        OrthographicCamera tempCamera = new OrthographicCamera();
        tempCamera.setToOrtho(
            false,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight()
        );

        if (this.hasBackground) {
            batch.end();
            this.createBackground(tempCamera);
            batch.begin();
            batch.setProjectionMatrix(tempCamera.combined);
        } else {
            batch.setProjectionMatrix(tempCamera.combined);
        }
        this.font.setColor(this.textColor);
        this.font.draw(batch, this.text, this.position.x, this.position.y);
    }

    public void dispose() {
        this.shapeRenderer.dispose();
        this.font.dispose();
    }

    // Temporary logic
    @Override
    public boolean isTemporary() {
        return this.temporary;
    }

    @Override
    public float getTimeLeft() {
        if (this.temporary){
            return this.timeLeft;
        } else {
            throw new IllegalCallerException("Time left can only be accessed when text is temporary. " +
                "Please set your time left before using this method, setting it in constructor or using setTimeLeft().");
        }
    }

    @Override
    public void setTimeLeft(float timeLeft) {
        this.temporary = true;
        this.timeLeft = timeLeft;
    }

    @Override
    public void updateTimeLeft(float deltaTime) {
        this.timeLeft -= deltaTime;
    }
}
