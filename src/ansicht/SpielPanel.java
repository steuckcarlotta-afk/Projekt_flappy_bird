package ansicht;

import Logik.SpielLogik;
import modell.Saeule;
import modell.SpielModell;
import modell.Vogel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;


public class SpielPanel extends JPanel {
    private Vogel vogel;
    private Image vogelBild;
    private Timer timer;
    private ArrayList<Saeule> saeulen;
    private Image saeuleUnten;
    private Image saeuleOben;
    private boolean spielGestartet = false;

    private Image hintergrundBild;
    private SpielModell spielModell;
    private Random random = new Random();
    private SpielLogik spielLogik;


    public SpielPanel() {
        //Größe Fenster
        setPreferredSize(new Dimension(500, 700)); // Größe des Spielfelds
        setFocusable(true); // Aktiviert Tastatureingaben

        //konstruktoren
        saeuleOben= new ImageIcon("src/Bilder/saeuleOben.png").getImage();
        saeuleUnten= new ImageIcon("src/Bilder/saeuleUnten.png").getImage();
        hintergrundBild = new ImageIcon("src/Bilder/hintergrund.png").getImage();
        vogelBild = new ImageIcon("src/Bilder/vogel.png").getImage();
        vogel = new Vogel(100, 200, 100); //startposition vogel
        spielModell = new SpielModell();

        saeulen = new ArrayList<>(); //arraylist, für mehrere säulen
        saeulen.add(new Saeule(350, 80, 200, 180));
        saeulen.add(new Saeule(650, 80, 250, 180));
        saeulen.add(new Saeule(950, 80, 220, 180));

        spielLogik = new SpielLogik(vogel, saeulen,spielModell, 500, 700);

        //alle 16millisek
        timer = new Timer(16, e -> {
            if (!spielGestartet) {
                repaint();
                return;
            }

            if (spielModell.isGameOver()){
                repaint();
                return;
            }
            spielLogik.update();

            repaint();
        });

        addKeyListener(new KeyAdapter() {  // Tastatursteuerung
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (!spielGestartet) {
                        spielGestartet = true;
                    } else if (spielModell.isGameOver()) {
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

        if (!spielGestartet) {
            g.drawImage(
                    vogelBild,
                    340,
                    120,
                    120,
                    120,
                    this
            );
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 45));
            g.setColor(new Color(255, 120, 170));
            g.drawString("FLAPPY BIRD", 80, 230);


            g.setColor(new Color(255, 230, 240));
            g.fillRoundRect(150, 400, 200, 70, 20, 20);

            g.setColor(new Color(255, 230, 240));
            g.drawRoundRect(150, 400, 200, 70, 20, 20);

            g.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
            g.setColor(Color.BLACK);
            g.drawString("START", 195, 445);

            g.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
            g.drawString("Leertaste zum Starten", 150, 510);

            return;
        }

        for (Saeule saeule : saeulen) {
            // Obere Säule – Bild wird von oben nach unten gestreckt bis zur Lücke
            g.drawImage(
                    saeuleOben,
                    saeule.getX(),                  // x
                    0,                              // y (beginnt ganz oben)
                    85,                             // breite
                    saeule.getLueckeY(),            // höhe (bis zur Lücke)
                    this
            );

            // Untere Säule – beginnt nach der Lücke, geht bis zum Boden
            int untereY = saeule.getLueckeY() + saeule.getLueckeHoehe();
            g.drawImage(
                    saeuleUnten,
                    saeule.getX(),                  // x
                    untereY,                        // y (nach der Lücke)
                    85,                             // breite
                    getHeight() - untereY,          // höhe (bis zum Boden)
                    this
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

        g.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
        g.setColor(new Color(255, 230, 240));

        String punkteText = String.valueOf(spielModell.getPunkte());

        g.drawString(
                punkteText,
                getWidth() / 2 - 15,
                60
        );
        if (spielModell.isGameOver()) {

            // Hintergrund bei GameOver
            g.setColor(new Color(255, 230, 240));
            g.fillRoundRect(80, 180, 340, 260, 15, 15);

            // Rahmen
            g.setColor(new Color(255, 230, 240));
            g.drawRoundRect(80, 180, 340, 260, 15, 15);

            g.setFont(new Font("Comic Sans MS", Font.BOLD, 38));
            g.setColor(new Color(255, 120, 170));
            g.drawString("GAME OVER", 135, 240);

            g.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
            g.setColor(Color.BLACK);
            g.drawString("Score", 135, 310);
            g.drawString(String.valueOf(spielModell.getPunkte()), 290, 310);

            // Highscore
            g.drawString("Highscore", 135, 360);
            g.drawString(String.valueOf(spielModell.getHighscore()), 290, 360);

            g.setFont(new Font("Comic Sans MS", Font.BOLD, 19));
            g.drawString("Leertaste zum Neustart", 135, 420);
        }
    }
    private void neustarten() {
        vogel.reset(100, 200);

        saeulen.clear();
        saeulen.add(new Saeule(350, 80, 200, 180));
        saeulen.add(new Saeule(650, 80, 250, 180));
        saeulen.add(new Saeule(950, 80, 220, 180));

        spielModell.setPunkte(0);
        spielModell.setGameOver(false);


        spielLogik = new SpielLogik(vogel, saeulen, spielModell, 500, 700);
    }
}