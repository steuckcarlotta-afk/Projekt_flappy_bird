package Logik;

import modell.Saeule;
import modell.SpielModell;
import modell.Vogel;
import speicherung.HighscoreSpeicherung;

import java.awt.*;

public class SpielLogik {
    private Vogel vogel;
    private Saeule saeule;
    private SpielModell spielModell;
    private HighscoreSpeicherung highscoreSpeicherung;

    public SpielLogik(Vogel vogel, Saeule saeule, SpielModell spielModell, HighscoreSpeicherung highscoreSpeicherung) {
        this.vogel = vogel;
        this.saeule = saeule;
        this.spielModell = spielModell;
        this.highscoreSpeicherung = highscoreSpeicherung;

        spielModell.setHighscore(highscoreSpeicherung.laden());
    }

}