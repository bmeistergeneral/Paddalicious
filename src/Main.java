import javax.swing.JFrame;

public class Main {

    public static void main (String[] args) {

        int gameWidth = 800;
        int gameHeight = 600;

        JFrame jframe = new JFrame();
        Paddalicious game = new Paddalicious(gameWidth, gameHeight);

        jframe.setBounds(0, 0, gameWidth, gameHeight);
        jframe.setTitle("Paddalicious");
        jframe.add(game);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);
    }
}