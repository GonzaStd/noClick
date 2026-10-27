package io.gonzastd.noclick.noclick;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;

public class CustomScaling extends Scaling {
    private float sourceWidth;
    private float sourceHeight;
    private float targetWidth;
    private float targetHeight;

    public int getScale() {
        int scale;
        int scaleWidth = (int) Math.floor(this.targetWidth / this.sourceWidth);
        int scaleHeight = (int) Math.floor(this.targetHeight / this.sourceHeight);
        scale = Math.min(scaleWidth, scaleHeight);
        if (scale <= 1) {
            scale = 1;
        }
        return scale;
    }

    @Override
    public Vector2 apply(
        float sourceWidth,
        float sourceHeight,
        float targetWidth,
        float targetHeight
    ) {
        this.sourceWidth = sourceWidth;
        this.sourceHeight = sourceHeight;
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;

        int scale = this.getScale();
        return new Vector2(
            this.sourceWidth * scale,
            this.sourceHeight * scale
        );
    }
}
