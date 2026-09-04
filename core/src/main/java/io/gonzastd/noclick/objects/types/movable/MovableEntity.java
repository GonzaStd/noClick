package io.gonzastd.noclick.objects.types.movable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.gonzastd.noclick.objects.BasicDrawable;

public abstract class MovableEntity extends BasicDrawable {
    private final int HORIZONTAL_STRIDE;
    private final int VERTICAL_STRIDE;
    private final int FRAMES_PER_MOVE;
    private final float MOVE_FRAME_DURATION;

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
        final float SPRITE_WIDTH,
        final float SPRITE_HEIGHT,
        final int HORIZONTAL_SPACING,
        final int VERTICAL_SPACING,
        final int FRAMES_PER_MOVE,
        final float MOVE_FRAME_DURATION
    ) {
        super(
            startX,
            startY,
            SPRITE_WIDTH,
            SPRITE_HEIGHT
        );
        this.HORIZONTAL_STRIDE = ( (int) this.SPRITE_WIDTH) + HORIZONTAL_SPACING;
        this.VERTICAL_STRIDE =  ( (int) this.SPRITE_HEIGHT) + VERTICAL_SPACING;
        this.FRAMES_PER_MOVE = FRAMES_PER_MOVE;
        this.MOVE_FRAME_DURATION = MOVE_FRAME_DURATION;

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
        TextureRegion[] frames = new TextureRegion[this.FRAMES_PER_MOVE];
        int row = direction.getSpriteRow();

        for (int column = 0; column < this.FRAMES_PER_MOVE; column++) {
            int x = column * this.HORIZONTAL_STRIDE;
            int y = row * this.VERTICAL_STRIDE;
            frames[column] = new TextureRegion(this.spriteSheet, x, y, this.SPRITE_WIDTH, this.SPRITE_HEIGHT);
        }

        Animation<TextureRegion> animation = new Animation<>(MOVE_FRAME_DURATION, frames);
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
