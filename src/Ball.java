import java.awt.*;

public class Ball {
    private double x;
    private double y;
    private int diameter;
    private int angle = 30;
    private double speed = 0.3;

    public Ball(int x, int y, int diameter) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
    }

    public int getX() {
        return (int) x;
    }
    public int getY() {
        return (int) y;
    }
    public int getDiameter() {
        return diameter;
    }
    public Rectangle getRectangle() {
        return new Rectangle(getX(), getY(), diameter, diameter);
    }

    public void increaseSpeed(double speedIncrease) {
        speed += speedIncrease;
    }

    private void cleanUpAngle() {

        if (angle < 0) {
            angle += 360;
        }

        if (angle > 360) {
            angle = angle % 360;
        }
    }

    public void changeAngle(int degrees) {
        angle += degrees;
        cleanUpAngle();
    }

    public void bounceOffTheLeft(double atX) {
        bounceOffVerticalSurface(atX - diameter - 1);
    }

    public void bounceOffTheRight(double atX) {
        bounceOffVerticalSurface(atX + 1);
    }

    public void bounceOffTheTop(double atY) {
        bounceOffHorizontalSurface(atY - diameter - 1);
    }

    public void bounceOffTheBottom(double atY) {
        bounceOffHorizontalSurface(atY + 1);
    }

    private void bounceOffHorizontalSurface(double atY) {
        bounceOffSurface(false, 0, atY);
    }

    private void bounceOffVerticalSurface(double atX) {
        bounceOffSurface(true, atX, 0);
    }

    private void bounceOffSurface(boolean vertical, double atX, double atY) {

        double dx = calculateXComponentOfPath(angle, speed);
        double dy = calculateYComponentOfPath(angle, speed);

        if (vertical) {
            dx *= -1;
            x = atX;
        } else {
            dy *= -1;
            y = atY;
        }

        angle = calculateAngleBasedOnComponents(dx, dy);
        cleanUpAngle();
    }

    private double calculateXComponentOfPath(int angleInDegrees, double magnitude) {
        double angleInRadians = Math.toRadians(angleInDegrees - 90);

        return magnitude * Math.cos(angleInRadians);
    }

    private double calculateYComponentOfPath(int angleInDegrees, double magnitude) {
        double angleInRadians = Math.toRadians(angleInDegrees - 90);

        return magnitude * Math.sin(angleInRadians);
    }

    private int calculateAngleBasedOnComponents(double xComponent, double yComponent) {
        double angleInRadians = Math.atan2(yComponent, xComponent);

        return (int) Math.toDegrees(angleInRadians) + 90;
    }

    public void update(double time) {
        double dx = time * calculateXComponentOfPath(angle, speed);
        double dy = time * calculateYComponentOfPath(angle, speed);

        x += dx;
        y += dy;
    }
}
