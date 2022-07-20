public class Ball {
    private double x;
    private double y;
    private int diameter;
    private int angle = 290;
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

    public void increaseSpeed(double speedIncrease) {
        speed += speedIncrease;
    }

    public void changeAngle(int degrees) {
        angle += degrees;
    }

    public void bounceOffHorizontalSurface() {
        double dy = speed * Math.sin(Math.toRadians(angle));

        dy *= -1;

        double newAngle = Math.asin(dy / speed);

        angle = (int) Math.toDegrees(newAngle);
    }

    public void bounceOffVerticalSurface() {
        double dx = speed * Math.cos(Math.toRadians(angle));

        dx *= -1;

        double newAngle = Math.acos(dx / speed);

        angle = (int) Math.toDegrees(newAngle);
    }

    public void update(double time) {
        double dx = time * (speed * Math.cos(Math.toRadians(angle)));
        double dy = time * (speed * Math.sin(Math.toRadians(angle)));

        x += dx;
        y += dy;
    }
}
