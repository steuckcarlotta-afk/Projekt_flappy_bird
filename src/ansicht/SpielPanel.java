package ansicht;

import modell.Saeule;
import modell.Vogel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class SpielPanel extends JPanel {
    private Vogel vogel;
    private Image vogelBild;
    private Timer timer;
    private ArrayList<Saeule> saeulen;
    private int punkte = 0;
    private Image hintergrundBild;

    public SpielPanel() {
        //Größe Fenster
        setPreferredSize(new Dimension(500, 700)); // Größe des Spielfelds
        setFocusable(true); // Aktiviert Tastatureingaben

        //konstruktoren
        hintergrundBild = new ImageIcon("src/Bilder/hintergrund.png").getImage();
        vogelBild = new ImageIcon("src/Bilder/vogel.gif").getImage();
        vogel = new Vogel(100, 200, 100); //startposition vogel

        saeulen = new ArrayList<>(); //arraylist, für mehrere säulen
        saeulen.add(new Saeule(350, 80, 250, 180));
        saeulen.add(new Saeule(650, 80, 200, 180));
        saeulen.add(new Saeule(950, 80, 300, 180));

        //alle 16millisek
        timer = new Timer(16, e -> {

            vogel.setGeschwindigkeitY(
                    vogel.getGeschwindigkeitY() + 0.5 //gravität wird hergestellt(geschwindigkeit nach unten wird immer größer)
            );

            //Vogel bewegen
            vogel.setY(
                    vogel.getY() + (int) vogel.getGeschwindigkeitY()
            );

            if (vogel.getY() + vogel.getGroesse() >= getHeight()) { // Prüft, ob der Vogel den Boden berührt
                vogel.setY(getHeight() - vogel.getGroesse()); // Setzt den Vogel exakt auf den Boden
                vogel.setGeschwindigkeitY(0); // Stoppt die Fallbewegung
            }

            Rectangle vogelRechteck = new Rectangle(
                    vogel.getX() + 25,
                    vogel.getY() + 25,
                    vogel.getGroesse() - 50,
                    vogel.getGroesse() - 50
            );

            for (Saeule saeule : saeulen) {

                //säule bewegen
                saeule.setX(saeule.getX() - 3);

                if (saeule.getX() + saeule.getBreite() < vogel.getX() && !saeule.isPunktGegeben()) {
                    punkte++;
                    saeule.setPunktGegeben(true);
                }

                Rectangle obereSaeule = new Rectangle(
                        saeule.getX(),
                        0,
                        saeule.getBreite(),
                        saeule.getLueckeY()
                );

                Rectangle untereSaeule = new Rectangle(
                        saeule.getX(),
                        saeule.getLueckeY() + saeule.getLueckeHoehe(),
                        saeule.getBreite(),
                        getHeight() - (saeule.getLueckeY() + saeule.getLueckeHoehe())
                );

                if (vogelRechteck.intersects(obereSaeule)
                        || vogelRechteck.intersects(untereSaeule)) {
                    timer.stop();
                    JOptionPane.showMessageDialog(null, "Game Over!");
                }

                //wenn säule ausm bild verschwindet soll sie wieder kommen
                if (saeule.getX() + saeule.getBreite() < 0) {
                    saeule.setX(getWidth() + 600);
                    saeule.setPunktGegeben(false);
                }
            }

            repaint();
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
        super.paintComponent(g);//damit keine alten bilder stehen bleiben

        g.drawImage(
                hintergrundBild,
                0,
                0,
                getWidth(),
                getHeight(),
                this
        );

        for (Saeule saeule : saeulen) {
            // Pinke Säulen zeichnen
            g.setColor(new Color(255, 105, 180));

            // obere Säule
            g.fillRect(
                    saeule.getX(),
                    0,
                    saeule.getBreite(),
                    saeule.getLueckeY()
            );

            // untere Säule
            g.fillRect(
                    saeule.getX(),
                    saeule.getLueckeY() + saeule.getLueckeHoehe(),
                    saeule.getBreite(),
                    getHeight() - (saeule.getLueckeY() + saeule.getLueckeHoehe())
            );

            // Dunkler Rand
            g.setColor(new Color(150, 20, 90));

            g.drawRect(
                    saeule.getX(),
                    0,
                    saeule.getBreite(),
                    saeule.getLueckeY()
            );

            g.drawRect(
                    saeule.getX(),
                    saeule.getLueckeY() + saeule.getLueckeHoehe(),
                    saeule.getBreite(),
                    getHeight() - (saeule.getLueckeY() + saeule.getLueckeHoehe())
            );
        }

        g.drawImage(
                vogelBild,
                vogel.getX(),
                vogel.getY(),
                vogel.getGroesse(),
                vogel.getGroesse(),
                this
        );

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Punkte:" + punkte, 20, 40);
    }
}