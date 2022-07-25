import java.awt.*;

public class Paddle {
    private Rectangle rectangle;

    public Paddle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setX(int x, int leftEdge, int rightEdge) {

        int adjustedRightEdge = rightEdge - rectangle.width;

        int adjustedIncomingX = x - (rectangle.width / 2);

        if (adjustedIncomingX < leftEdge) {
            rectangle.x = leftEdge;
        } else if (adjustedIncomingX > adjustedRightEdge) {
            rectangle.x = adjustedRightEdge;
        } else {
            rectangle.x = adjustedIncomingX;
        }
    }
}
