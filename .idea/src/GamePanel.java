import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    final int WIDTH = 800;
    final int HEIGHT = 600;

    Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(new Color(240,128,128));
        this.setDoubleBuffered(true);
    }

    public void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread != null) {
            update();
            repaint();

            try {
                Thread.sleep(16); // ca. 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        // Hier kommt später Bewegung rein
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Hintergrund
        g.setColor(new Color(240,128,128));
        g.fillRect(0, 0, WIDTH, HEIGHT);


        // Boden
        g.setColor(new Color(255,182,193));
        g.fillRect(0, 500, WIDTH, 100);
    }
}