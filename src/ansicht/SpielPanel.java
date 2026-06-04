package ansicht;

import modell.Saeule;
import modell.Vogel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SpielPanel extends JPanel {
    private Vogel vogel;
    private Timer timer;
    private Saeule saeule;

    public SpielPanel() {
        //Größe Fenster
        setPreferredSize(new Dimension(500, 700)); // Größe des Spielfelds
        setBackground(Color.CYAN);
        setFocusable(true); // Aktiviert Tastatureingaben


        vogel = new Vogel(100, 200, 30);// Startposition des Vogels
        saeule= new Saeule(350,80,250,180);
        //alle 16millisek
        timer = new Timer(16, e -> {

            vogel.setGeschwindigkeitY(
                    vogel.getGeschwindigkeitY() + 0.5 //gravität wird hergestellt
            );
            vogel.setY(
                    vogel.getY() + (int) vogel.getGeschwindigkeitY()
            );
            saeule.setX(saeule.getX()-3);
            repaint();

            //wenn säule ausm bild verschwindet soll sie wieder kommen
            if (saeule.getX() + saeule.getBreite() < 0) {

                saeule.setX(getWidth());

            }
        });



        addKeyListener(new KeyAdapter() {  // Tastatursteuerung
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {  // Vogel springt bei Leertaste
                    vogel.setGeschwindigkeitY(-8);  // Negative Geschwindigkeit bewegt den Vogel nach oben
                }
            }
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
        if (vogel.getY() + vogel.getGroesse() >= getHeight()) { // Prüft, ob der Vogel den Boden berührt
            vogel.setY(getHeight() - vogel.getGroesse()); // Setzt den Vogel exakt auf den Boden
            vogel.setGeschwindigkeitY(0); // Stoppt die Fallbewegung
        g.setColor(Color.GREEN);
        }
        g.fillRect(
//obere säule
                saeule.getX(),

                0,

                saeule.getBreite(),

                saeule.getLueckeY()

        );



        g.fillRect(
//untere säule
                saeule.getX(),

                saeule.getLueckeY() + saeule.getLueckeHoehe(),

                saeule.getBreite(),

                getHeight()

        );
    }

}
