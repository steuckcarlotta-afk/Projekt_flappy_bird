package Logik;

import modell.Saeule;
import modell.SpielModell;
import modell.Vogel;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class SpielLogik {
    private Vogel vogel;
    private ArrayList<Saeule> saeulen;
    private SpielModell spielModell;
    private Random random = new Random();
    private int panelBreite;
    private int panelHoehe;

    public SpielLogik(Vogel vogel, ArrayList<Saeule> saeulen, SpielModell spielModell, int panelBreite, int panelHoehe) {
        this.vogel = vogel;
        this.saeulen = saeulen;
        this.spielModell = spielModell;
        this.panelBreite = panelBreite;
        this.panelHoehe = panelHoehe;
    }

    public void update() {
        // Gravitation
        vogel.setGeschwindigkeitY(
                vogel.getGeschwindigkeitY() + 0.5
        );
        vogel.setY(
                vogel.getY() + (int) vogel.getGeschwindigkeitY()
        );

        // Boden-Kollision
        if (vogel.getY() + vogel.getGroesse() >= panelHoehe) {
            vogel.setY(panelHoehe - vogel.getGroesse());
            if (spielModell.getPunkte() > spielModell.getHighscore()) {
                spielModell.setHighscore(spielModell.getPunkte());
            }
            spielModell.setGameOver(true);
        }
        //Decken kollision
        if (vogel.getY() <= 0) {
            spielModell.setGameOver(true);
        }

        // Vogel-Rechteck für Kollision
        Rectangle vogelRechteck = new Rectangle(
                vogel.getX() + 25,
                vogel.getY() + 25,
                vogel.getGroesse() - 50,
                vogel.getGroesse() - 50
        );

        for (Saeule saeule : saeulen) {
            // Säule bewegen
            saeule.setX(saeule.getX() - 3);

            // Punkt vergeben
            if (saeule.getX() + saeule.getBreite() < vogel.getX() && !saeule.isPunktGegeben()) {
                spielModell.setPunkte(spielModell.getPunkte() + 1);
                saeule.setPunktGegeben(true);
            }

            // Kollisions-Rechtecke Säulen
            Rectangle obereSaeule = new Rectangle(
                    saeule.getX(),
                    0,
                    85,
                    saeule.getLueckeY()
            );
            Rectangle untereSaeule = new Rectangle(
                    saeule.getX(),
                    saeule.getLueckeY() + saeule.getLueckeHoehe(),
                    85,
                    panelHoehe - (saeule.getLueckeY() + saeule.getLueckeHoehe())
            );

            // Kollision mit Säule
            if (vogelRechteck.intersects(obereSaeule)
                    || vogelRechteck.intersects(untereSaeule)) {
                if (spielModell.getPunkte() > spielModell.getHighscore()) {
                    spielModell.setHighscore(spielModell.getPunkte());
                }
                spielModell.setGameOver(true);
            }

            // Säule nochmal
            if (saeule.getX() + saeule.getBreite() < 0) {
                saeule.setX(panelBreite + 300);
                saeule.setLueckeY(random.nextInt(300) + 150);
                saeule.setPunktGegeben(false);
            }
        }
    }
}