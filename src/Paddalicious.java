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
    private int score;

    private enum GameState {
        PLAYING,
        DEAD,
        WON
    }

    private GameState gameState = GameState.PLAYING;

    private int gameWidth;
    private int gameHeight;
    private double ballSpeedIncreaseAmount = 0.025;

    private Timer timer;
    private int timerDuration = 6;

    public Paddalicious(int gameWidth, int gameHeight) {
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;

        int paddleWidth = 130;
        int paddleHeight = 20;
        int paddleMargin = 40;
        score = 0;

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

        for (int j = 1; j <= rows; j++) {
            for (int i = 1; i <= columns; i++) {
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
        g.fillRect(0, 0, 800, 600);

        g.setColor(Color.black);
        g.drawString("Score: " + score, 2, gameHeight - 26);

        // Paddle
        g.setColor(Color.red);
        Rectangle tempPaddleRect = paddle.getRectangle();
        g.fillRect(tempPaddleRect.x, tempPaddleRect.y, tempPaddleRect.width, tempPaddleRect.height);

        // Ball
        switch (gameState) {
            case PLAYING:
                g.setColor(Color.BLUE);
                g.fillOval(ball.getX(), ball.getY(), ball.getDiameter(), ball.getDiameter());
                break;
            case DEAD:
                g.setColor(Color.black);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
                g.drawString("You Dead!", gameWidth / 2, gameHeight / 2);
                break;
            case WON:
                g.setColor(Color.black);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
                g.drawString("You Da Boss!", gameWidth / 2, gameHeight / 2);
                break;
        }

        // Bricks
        for (Brick brick : bricks) {
            if (!brick.isDestroyed()) {
                g.setColor(brick.getColor());
                Rectangle tempBrickRect = brick.getRectangle();
                g.fillRect(tempBrickRect.x, tempBrickRect.y, tempBrickRect.width, tempBrickRect.height);
            }
        }

    }


    //  private void drawGrid(Graphics g) {
    // for (int i = 0; i < gameWidth; i++) {

    // if(i %100==0)


    // g.setColor(Color.black);
    // g.drawLine(i, 0, i, gameHeight);



    public void mouseMoved(MouseEvent e) {
        paddle.setX(e.getX(), 0, gameWidth);
    }

    public void mouseDragged(MouseEvent e) {
        // do nothing
    }

    private void checkForWallCollisions() {

        // left wall
        if (ball.getX() <= 0) {
            ball.bounceOffTheRight(0);
        }

        // right wall
        if (ball.getX() + ball.getDiameter() >= gameWidth) {
            ball.bounceOffTheLeft(gameWidth);
        }

        // ceiling
        if (ball.getY() <= 0) {
            ball.bounceOffTheBottom(0);
        }

        // pit
        if (ball.getY() >= gameHeight) {
            // you dead!
            gameState = GameState.DEAD;
            timer.stop();
        }
    }

    private void checkForPaddleCollisions() {

        int maxSpinDegrees = 25;
        double halfOfPaddleWidth = (paddle.getRectangle().getWidth() / 2);

        if (ball.getRectangle().intersects(paddle.getRectangle())) {

            double bottomMiddleOfBall = ball.getX() + (ball.getDiameter() / 2);

            double middleOfPaddle = paddle.getRectangle().getX() + halfOfPaddleWidth;

            double difference = bottomMiddleOfBall - middleOfPaddle;

            if (difference > 0 && difference > halfOfPaddleWidth) {
                difference = halfOfPaddleWidth;
            }

            if (difference < 0 && difference < -halfOfPaddleWidth) {
                difference = -halfOfPaddleWidth;
            }

            double factor = difference / halfOfPaddleWidth;

            int paddleSpinInDegrees = (int) (factor * maxSpinDegrees);

            ball.bounceOffTopWithSpin(paddle.getRectangle().getMinY(), paddleSpinInDegrees);

            score += 10;
        }
    }

    private void processBrickHit(Brick brick) {

        boolean wasDestroyed = brick.brickWasHit();

        if (wasDestroyed) {
            ball.increaseSpeed(ballSpeedIncreaseAmount);
        }
    }

    private void checkForBrickCollisions() {

        int midXOfBall = ball.getX() + (ball.getDiameter() / 2);
        int midYOfBall = ball.getY() + (ball.getDiameter() / 2);

        Point topOfBall     = new Point(midXOfBall, (int) ball.getRectangle().getMinY());
        Point bottomOfBall  = new Point(midXOfBall, (int) ball.getRectangle().getMaxY());
        Point leftOfBall    = new Point((int) ball.getRectangle().getMinX(), midYOfBall);
        Point rightOfBall   = new Point((int) ball.getRectangle().getMaxX(), midYOfBall);

        for (Brick brick : bricks) {
            if (!brick.isDestroyed()) {
                if (brick.getRectangle().intersects(ball.getRectangle())) {

                    // ball approaching the brick from the top
                    if (brick.getRectangle().contains(bottomOfBall)) {
                        ball.bounceOffTheTop(brick.getRectangle().getMinY());
                        processBrickHit(brick);
                        score += 100;
                    }

                    // ball approaching the brick from the left
                    if (brick.getRectangle().contains(rightOfBall)) {
                        ball.bounceOffTheLeft(brick.getRectangle().getMinX());
                        processBrickHit(brick);
                        score += 100;
                    }

                    // ball approaching the brick from the right
                    if (brick.getRectangle().contains(leftOfBall)) {
                        ball.bounceOffTheRight(brick.getRectangle().getMaxX());
                        processBrickHit(brick);
                        score += 100;
                    }

                    // ball approaching the brick from the bottom
                    if (brick.getRectangle().contains(topOfBall)) {
                        ball.bounceOffTheBottom(brick.getRectangle().getMaxY());
                        processBrickHit(brick);
                        score += 100;
                    }
                }
            }
        }
    }

    private boolean didWin() {

        int numOfBricksLeft = bricks.size();

        for (Brick brick : bricks) {
            if (brick.isDestroyed()) {
                numOfBricksLeft -= 1;
            }
        }

        return numOfBricksLeft < 1;
    }

    public void actionPerformed(ActionEvent e) {

        if (gameState == GameState.PLAYING) {
            timer.start();
            ball.update(timerDuration);
            checkForWallCollisions();
            checkForPaddleCollisions();
            checkForBrickCollisions();

            if (didWin()) {
                gameState = GameState.WON;
            }
        }

        repaint();
    }
}
