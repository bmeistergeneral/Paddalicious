import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class Paddalicious extends JPanel implements MouseMotionListener, ActionListener {

    private Paddle paddle;
    private Ball ball;
    private ArrayList<Brick> bricks;
    private int gameWidth;
    private int gameHeight;

    private Timer timer;
    private int timerDuration = 6;

    public Paddalicious(int gameWidth, int gameHeight) {
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;

        int paddleWidth = 130;
        int paddleHeight = 20;
        int paddleMargin = 40;

        Rectangle paddleRectangle = new Rectangle((gameWidth / 2) - (paddleWidth / 2), (gameHeight - paddleHeight - paddleMargin), paddleWidth, paddleHeight);

        // create the paddle and ball
        paddle = new Paddle(paddleRectangle);
        ball = new Ball(400, 240, 20);

        // create the bricks
        bricks = new ArrayList<Brick>(32);

        int brickMargin = 4;
        int rows = 4;
        int columns = 8;
        int brickWidth = (gameWidth / columns) - (brickMargin);
        int brickHeight = 30;

        for (int j = 1; j<=rows; j++) {
            for (int i = 1; i<=columns; i++) {
                int tempX = (i * brickMargin) + ((i - 1) * brickWidth) - (brickMargin / 2);
                int tempY = (j * brickMargin) + ((j - 1) * brickHeight);

                Rectangle tempRect = new Rectangle(tempX, tempY, brickWidth, brickHeight);

                int tempLives;

                if (j == 1) {
                    tempLives = 3;
                } else if (j == 2) {
                    tempLives = 2;
                } else {
                    tempLives = 1;
                }

                Brick tempBrick = new Brick(tempRect, tempLives);
                bricks.add(tempBrick);
            }
        }

        addMouseMotionListener(this);

        timer = new Timer(timerDuration, this);
        timer.start();
    }

    public void paint(Graphics g) {
        // Background
        g.setColor(Color.white);
        g.fillRect(0,0,800,600);

        // Paddle
        g.setColor(Color.red);
        Rectangle tempPaddleRect = paddle.getRectangle();
        g.fillRect(tempPaddleRect.x, tempPaddleRect.y, tempPaddleRect.width, tempPaddleRect.height);

        // Ball
        g.setColor(Color.BLUE);
        g.fillOval(ball.getX(),ball.getY(), ball.getDiameter(), ball.getDiameter());

        // Bricks
        for (Brick brick: bricks) {
            g.setColor(brick.getColor());
            Rectangle tempBrickRect = brick.getRectangle();
            g.fillRect(tempBrickRect.x, tempBrickRect.y, tempBrickRect.width, tempBrickRect.height);
        }

        // for debugging
//        drawGrid(g);
    }

    private void drawGrid(Graphics g) {
        for (int i = 0; i < gameWidth; i++) {

            if (i % 100 == 0) {
                g.setColor(Color.black);
                g.drawLine(i, 0, i, gameHeight);
            }
        }
    }

    public void mouseMoved(MouseEvent e) {
        paddle.setX(e.getX(), 0, gameWidth);
    }

    public void mouseDragged(MouseEvent e) {
        // do nothing
    }

    private void checkForWallBounces() {

        // left wall
        if (ball.getX() <= 0) {
            ball.bounceOffVerticalSurface();
        }

        // right wall
        if (ball.getX() + ball.getDiameter() >= gameWidth) {
            ball.bounceOffVerticalSurface();
        }

        // ceiling
        if (ball.getY() <= 0) {
            ball.bounceOffHorizontalSurface();
        }

        // pit
        if (ball.getY() >= gameHeight) {
            // you dead!
        }
    }

    private void checkForPaddleBounces() {
        if (ball.getY() + ball.getDiameter() >= paddle.getRectangle().getMinY()) {
            if (ball.getX() >= paddle.getRectangle().getMinX() && ball.getX() <= paddle.getRectangle().getMaxX()) {
                ball.bounceOffHorizontalSurface();
                ball.changeAngle((-10 + (int)(Math.random() * ((10 - -10) + 1))));
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        timer.start();
        ball.update(timerDuration);
        checkForWallBounces();
        checkForPaddleBounces();

        repaint();
    }
}
