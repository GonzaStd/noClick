package io.gonzastd.noclick.objects.types.text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import io.gonzastd.noclick.levels.Level;
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

    public Text(String text, BitmapFont font, float textScale) {
        this.text = text;
        this.font = font;
        if (textScale == 0) {
            throw new IllegalArgumentException("Text scale cannot be 0");
        }
        this.font.getData().setScale(textScale);
        this.layout.setText(this.font, this.text);
    }

    public Text(String text, BitmapFont font, float textScale, float timeLeft) {
        this.text = text;
        this.font = font;
        if (textScale == 0) {
            throw new IllegalArgumentException("Text scale cannot be 0.");
        }
        this.font.getData().setScale(textScale);
        this.layout.setText(this.font, this.text);
        this.setTimeLeft(timeLeft);
    }

    public float getBoxWidth() {
        return this.boxWidth;
    }

    public float getBoxHeight() {
        return this.boxHeight;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public Vector2 getPosition() {
        return this.position.cpy();
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
        this.isPositionDefined = true;

    }

    public void centerPosition() {
        float xWorldCenter = Level.ORIGINAL_VIRTUAL_WIDTH / 2f;
        float yWorldCenter = Level.ORIGINAL_VIRTUAL_HEIGHT / 2f;
        float x = xWorldCenter - (this.boxWidth / 2f);
        float y = yWorldCenter + this.boxHeight;
        this.setPosition(
            x,
            y
        );

        this.isCentered = true;
    }


    public void createBackground(Color backgroundColor, float padding) {
        if (this.isCentered) {
            throw new IllegalStateException(
                "Set the background before centering the message."
            );
        }

        this.backgroundColor = backgroundColor;
        this.padding = padding;

        this.boxWidth = this.layout.width + this.padding * 2f;
        this.boxHeight = this.layout.height + this.padding * 2f;
        this.hasBackground = true;
    }

    private void drawBackground(Matrix4 projectionMatrix) {
        float boxX = this.position.x - this.padding;
        float boxY = this.position.y - this.layout.height - this.padding;
        this.shapeRenderer.setProjectionMatrix(projectionMatrix);
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.shapeRenderer.setColor(this.backgroundColor);
        this.shapeRenderer.rect(boxX, boxY, this.boxWidth, this.boxHeight);
        this.shapeRenderer.end();
    }


    public void draw(SpriteBatch batch) {
        if (!this.isPositionDefined) {
            throw new IllegalStateException(
                "Message position was not defined. Use centerPosition() or setPosition()."
            );
        }

        Matrix4 worldProjection = batch.getProjectionMatrix().cpy();
        Matrix4 uiProjection = new Matrix4().setToOrtho2D(
            0, 0, Level.ORIGINAL_VIRTUAL_WIDTH, Level.ORIGINAL_VIRTUAL_HEIGHT
        );

        batch.end();
        if (this.hasBackground) {
            this.drawBackground(uiProjection);
        }

        batch.setProjectionMatrix(uiProjection);
        batch.begin();
        this.font.setColor(this.textColor);
        this.font.draw(batch, this.text, this.position.x, this.position.y);
        batch.setProjectionMatrix(worldProjection);
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
