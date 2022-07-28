import java.awt.*;
public class Brick {
    private Rectangle rectangle;
    private int lives;

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Brick(Rectangle rectangle) {
        this.rectangle = rectangle;
        this.lives = 1;
    }

    public Brick(Rectangle rectangle, int lives) {
        this.rectangle = rectangle;
        this.lives = lives;
    }

    public boolean isDestroyed() {
        return lives == 0;
    }

    public boolean brickWasHit() {
        if (lives > 0) {
            lives -= 1;
        }

        return isDestroyed();
    }

    public Color getColor() {
        if (lives == 1) {
            return Color.green;
        } else if (lives == 2) {
            return Color.orange;
        } else {
            return Color.red;
        }
    }
}
