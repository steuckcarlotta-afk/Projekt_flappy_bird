package ansicht;

import modell.Vogel;

import javax.swing.*;
import java.awt.*;

public class SpielPanel extends JPanel{
    private Vogel vogel;
    private Timer timer;

    public SpielPanel(){
        //Größe Fenster
        setPreferredSize(new Dimension(500, 700));
        setBackground(Color.CYAN);

        vogel = new Vogel(100, 200, 30);
        //alle 16millisek
        timer = new Timer(16, e -> {

            vogel.setGeschwindigkeitY(
                    vogel.getGeschwindigkeitY() + 0.5 //gravität wird hergestellt
            );
            vogel.setY(
                    vogel.getY() + (int) vogel.getGeschwindigkeitY()
            );
            repaint();
        });

        timer.start();
    }


    @Override

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.YELLOW);
        g.fillOval(
                vogel.getX(),
                vogel.getY(),
                vogel.getGroesse(),//breite
                vogel.getGroesse()//höhe
        );
    }
}
