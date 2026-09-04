package io.gonzastd.noclick.objects.types.movable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.gonzastd.noclick.objects.BasicDrawable;

public abstract class MovableEntity extends BasicDrawable {
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
        final int horizontalSpacing,
        final int verticalSpacing,
        final int framesPerMove,
        final float moveFrameDuration
    ) {
        super(
            startX,
            startY,
            spriteWidth,
            spriteHeight
        );
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
        moveDownAnim  = createMoveAnimation(MovableDirection.DOWN);
        moveUpAnim    = createMoveAnimation(MovableDirection.UP);
        moveLeftAnim  = createMoveAnimation(MovableDirection.LEFT);
        moveRightAnim = createMoveAnimation(MovableDirection.RIGHT);
    }

    protected Animation<TextureRegion> createMoveAnimation(MovableDirection direction) {
        TextureRegion[] frames = new TextureRegion[this.framesPerMove];
        int row = direction.getSpriteRow();

        for (int column = 0; column < this.framesPerMove; column++) {
            int x = column * this.horizontalStride;
            int y = row * this.verticalStride;
            frames[column] = new TextureRegion(this.spriteSheet, x, y, this.spriteWidth, this.spriteHeight);
        }

        Animation<TextureRegion> animation = new Animation<>(moveFrameDuration, frames);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        return animation;
    }

    public void update(float delta) {
        this.position.x += this.velocity.x * delta;
        this.position.y += this.velocity.y * delta;

        this.stateTime += delta;

        this.currentFrame = this.getCurrentFrame();
    }

    protected TextureRegion getCurrentFrame() {
        Animation<TextureRegion> animation = switch (direction) {
            case DOWN  -> this.moveDownAnim;
            case UP    -> this.moveUpAnim;
            case LEFT  -> this.moveLeftAnim;
            case RIGHT -> this.moveRightAnim;
        };

        if (state == MovableState.IDLE) {
            return animation.getKeyFrame(0, false);
        } else {
            return animation.getKeyFrame(this.stateTime, true);
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(this.getCurrentFrame(), this.position.x, this.position.y);
    }

    public void move(float dx, float dy) {
        if (dx == 0 && dy == 0) {
            this.stop();
            return;
        }

        this.state = MovableState.MOVING;

        this.velocity.set(dx, dy).nor().scl(speed); // set distance, normalize and scale

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

    public Vector2 getPosition() { return position; }
    public MovableState getState() { return state; }
    public MovableDirection getDirection() { return direction; }

    @Override
    public void dispose() {
        this.spriteSheet.dispose();
    }

}
