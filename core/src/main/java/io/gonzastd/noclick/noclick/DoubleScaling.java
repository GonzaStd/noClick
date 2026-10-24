package io.gonzastd.noclick.noclick;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;

public class DoubleScaling extends Scaling {

    private static final float SCALE = 2f;

    @Override
    public Vector2 apply(
        float sourceWidth,
        float sourceHeight,
        float targetWidth,
        float targetHeight
    ) {
        return new Vector2(
            sourceWidth * SCALE,
            sourceHeight * SCALE
        );
    }
}
