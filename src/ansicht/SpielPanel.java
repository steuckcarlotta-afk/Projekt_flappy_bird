package ansicht;

import modell.Saeule;
import modell.SpielModell;
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
    private SpielModell spielModell;


    public SpielPanel() {
        //Größe Fenster
        setPreferredSize(new Dimension(500, 700)); // Größe des Spielfelds
        setFocusable(true); // Aktiviert Tastatureingaben

        //konstruktoren
        hintergrundBild = new ImageIcon("src/Bilder/hintergrund.png").getImage();
        vogelBild = new ImageIcon("src/Bilder/vogel.gif").getImage();
        vogel = new Vogel(100, 200, 100); //startposition vogel
        spielModell = new SpielModell();

        saeulen = new ArrayList<>(); //arraylist, für mehrere säulen
        saeulen.add(new Saeule(350, 80, 250, 180));
        saeulen.add(new Saeule(650, 80, 200, 180));
        saeulen.add(new Saeule(950, 80, 300, 180));

        //alle 16millisek
        timer = new Timer(16, e -> {
            if (spielModell.isGameOver()){
                repaint();
                return;
            }

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
                    spielModell.setPunkte(
                            spielModell.getPunkte()+1
                    );
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
                    if (spielModell.getPunkte() > spielModell.getHighscore()) {
                        spielModell.setHighscore(
                                spielModell.getPunkte()
                        );
                    }
                    spielModell.setGameOver(true);
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
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (spielModell.isGameOver()) {
                        neustarten();
                    } else {
                        vogel.setGeschwindigkeitY(-8);  // Negative Geschwindigkeit bewegt den Vogel nach oben
                    }
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

        g.setFont(new Font("Arial", Font.BOLD, 50));

        g.setColor(new Color(255, 230, 240));

        String punkteText = String.valueOf(spielModell.getPunkte());

        g.drawString(
                punkteText,
                getWidth() / 2 - 15,
                60
        );
        if (spielModell.isGameOver()) {

            // Hintergrund
            g.setColor(new Color(245, 220, 230));
            g.fillRoundRect(80, 180, 340, 260, 15, 15);

            // Rahmen
            g.setColor(new Color(120, 90, 120));
            g.drawRoundRect(80, 180, 340, 260, 15, 15);

            g.setFont(new Font("Arial", Font.BOLD, 38));
            g.setColor(new Color(255, 120, 170));
            g.drawString("GAME OVER", 120, 240);

            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.setColor(Color.BLACK);
            g.drawString("Score", 140, 310);
            g.drawString(String.valueOf(spielModell.getPunkte()), 290, 310);

            // Highscore
            g.drawString("Highscore", 110, 360);
            g.drawString(String.valueOf(spielModell.getHighscore()), 290, 360);

            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.drawString("Leertaste zum Neustart", 110, 420);
        }
    }
        private void neustarten() {
            vogel = new Vogel(100, 200, 100);

            saeulen.clear();

            saeulen.add(new Saeule(350, 80, 250, 180));
            saeulen.add(new Saeule(650, 80, 200, 180));
            saeulen.add(new Saeule(950, 80, 300, 180));

            spielModell.setPunkte(0);
            spielModell.setGameOver(false);
        }
    }
