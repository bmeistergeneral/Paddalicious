import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Paddalicious extends JPanel implements MouseMotionListener, ActionListener {

    Paddle paddle;
    int gameWidth;
    int gameHeight;

    private Timer timer;
    private int timerDuration = 6;

    public Paddalicious(int gameWidth, int gameHeight) {
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;

        int paddleWidth = 130;
        int paddleHeight = 20;
        int margin = 40;

        Rectangle paddleRectangle = new Rectangle((gameWidth / 2) - (paddleWidth / 2), (gameHeight - paddleHeight - margin), paddleWidth, paddleHeight);

        paddle = new Paddle(paddleRectangle);

        addMouseMotionListener(this);

        timer = new Timer(timerDuration, this);
        timer.start();
    }

    public void paint(Graphics g) {
        // Background
        g.setColor(Color.GREEN);
        g.fillRect(0,0,800,600);

        // Paddle
        g.setColor(Color.red);
        Rectangle tempPaddleRect = paddle.getRectangle();
        g.fillRect(tempPaddleRect.x, tempPaddleRect.y, tempPaddleRect.width, tempPaddleRect.height);
    }

    public void mouseMoved(MouseEvent e) {
        paddle.setX(e.getX(), 0, gameWidth);
    }

    public void mouseDragged(MouseEvent e) {
        // do nothing
    }

    public void actionPerformed(ActionEvent e) {
        timer.start();
        repaint();
    }
}
