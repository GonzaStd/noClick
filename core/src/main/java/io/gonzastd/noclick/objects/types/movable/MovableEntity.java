package io.gonzastd.noclick.objects.types.movable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import io.gonzastd.noclick.objects.BasicDrawable;
import io.gonzastd.noclick.objects.types.DrawableMap;

public abstract class MovableEntity extends BasicDrawable {
    Rectangle bounds;
    private final int horizontalStride;
    private final int verticalStride;
    private final int framesPerMove;
    private final float moveFrameDuration;

    private Vector2 velocity;
    private float speed;

    private MovableState state;
    private MovableDirection direction;

    private float stateTime;
    private Animation<TextureRegion> moveDownAnim;
    private Animation<TextureRegion> moveUpAnim;
    private Animation<TextureRegion> moveLeftAnim;
    private Animation<TextureRegion> moveRightAnim;

    protected TextureRegion currentFrame;
    protected Texture spriteSheet;

    public MovableEntity(
        float startX,
        float startY,
        float speed,
        String spritePath,
        final float spriteWidth,
        final float spriteHeight,
        final float realWidth,
        final float realHeight,
        final int horizontalSpacing,
        final int verticalSpacing,
        final int framesPerMove,
        final float moveFrameDuration
    ) {
        super(
            startX,
            startY,
            spriteWidth,
            spriteHeight,
            realWidth,
            realHeight
        );
        this.bounds = this.getBounds();
        this.horizontalStride = ( (int) this.spriteWidth) + horizontalSpacing;
        this.verticalStride =  ( (int) this.spriteHeight) + verticalSpacing;
        this.framesPerMove = framesPerMove;
        this.moveFrameDuration = moveFrameDuration;

        this.velocity = new Vector2(0, 0);
        this.speed = speed;
        this.state = MovableState.IDLE;
        this.direction = MovableDirection.DOWN;
        this.stateTime = 0f;
        this.spriteSheet = new Texture(spritePath);
        this.loadAnimations();
    }

    protected void loadAnimations() {
        this.moveDownAnim = this.createMoveAnimation(MovableDirection.DOWN);
        this.moveUpAnim = this.createMoveAnimation(MovableDirection.UP);
        this.moveLeftAnim = this.createMoveAnimation(MovableDirection.LEFT);
        this.moveRightAnim = this.createMoveAnimation(MovableDirection.RIGHT);
    }

    protected Animation<TextureRegion> createMoveAnimation(MovableDirection direction) {
        TextureRegion[] frames = new TextureRegion[this.framesPerMove];
        int row = direction.getSpriteRow();

        for (int column = 0; column < this.framesPerMove; column++) {
            int x = column * this.horizontalStride;
            int y = row * this.verticalStride;
            frames[column] = new TextureRegion(this.spriteSheet, x, y, (int) this.spriteWidth, (int) this.spriteHeight);
        }

        Animation<TextureRegion> animation = new Animation<>(this.moveFrameDuration, frames);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        return animation;
    }

    public void update(float delta, Array<? extends BasicDrawable> collidables) {
        Rectangle bounds = this.getBounds();
        float traveledDistanceX = this.velocity.x * delta;
        float traveledDistanceY = this.velocity.y * delta;

        float currentX = this.getPosition().x;
        float currentY = this.getPosition().y;

        // --- Eje X ---
        boolean blockedX = false;
        if (traveledDistanceX != 0) {
            this.setPosition(currentX + traveledDistanceX, currentY);
            for (int i = 0; i < collidables.size; i++) {
                BasicDrawable other = collidables.get(i);
                if (other == this) continue;
                if (other instanceof DrawableMap && !bounds.overlaps(other.getBounds())) {
                    blockedX = true;
                    break;
                }
                if (bounds.overlaps(other.getBounds()) && !(other instanceof DrawableMap)) {
                    blockedX = true;
                    break;
                }
            }
            if (blockedX) {
                this.setPosition(currentX, currentY); // reverse X
            }
        }

        // --- Eje Y ---
        boolean blockedY = false;
        if (traveledDistanceY != 0) {
            this.setPosition(this.getPosition().x, currentY + traveledDistanceY);
            for (int i = 0; i < collidables.size; i++) {
                BasicDrawable other = collidables.get(i);
                if (other == this) continue;
                if (other instanceof DrawableMap && !bounds.overlaps(other.getBounds())) {
                    blockedY = true;
                    break;
                }
                if (bounds.overlaps(other.getBounds()) && !(other instanceof DrawableMap)) {
                    blockedY = true;
                    break;
                }
            }
            if (blockedY) {
                this.setPosition(this.getPosition().x, currentY); // reverse Y
            }
        }

        this.stateTime += delta;
        this.currentFrame = this.getCurrentFrame();
    }

    protected TextureRegion getCurrentFrame() {
        Animation<TextureRegion> animation = switch (this.direction) {
            case DOWN  -> this.moveDownAnim;
            case UP    -> this.moveUpAnim;
            case LEFT  -> this.moveLeftAnim;
            case RIGHT -> this.moveRightAnim;
        };

        if (this.state == MovableState.IDLE) {
            return animation.getKeyFrame(0, false);
        } else {
            return animation.getKeyFrame(this.stateTime, true);
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(this.getCurrentFrame(), this.getPosition().x, this.getPosition().y);
    }

    public void move(float dx, float dy) {
        if (dx == 0 && dy == 0) {
            this.stop();
            return;
        }

        this.state = MovableState.MOVING;

        this.velocity.set(dx, dy).nor().scl(this.speed); // set distance, normalize and scale

        if (Math.abs(dx) > Math.abs(dy)) {
            this.direction = dx > 0 ? MovableDirection.RIGHT : MovableDirection.LEFT;
        } else {
            this.direction = dy > 0 ? MovableDirection.UP : MovableDirection.DOWN;
        }
    }

    public void stop() {
        this.state = MovableState.IDLE;
        this.velocity.set(0, 0);
    }

    public MovableState getState() { return this.state; }
    public MovableDirection getDirection() { return this.direction; }

    @Override
    public void dispose() {
        this.spriteSheet.dispose();
    }

}
