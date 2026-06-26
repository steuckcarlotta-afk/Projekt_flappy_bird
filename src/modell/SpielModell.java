package modell;

import speicherung.HighscoreSpeicherung;
import speicherung.HighscoreClient;

public class SpielModell {

    private int punkte = 0;
    private int highscore = 0;
    private boolean gameOver = false;
    private HighscoreSpeicherung speicherung = new HighscoreSpeicherung();

    public SpielModell() {
        highscore = speicherung.laden(); // ← beim Start laden
    }

    public void setHighscore(int highscore) {
        this.highscore = HighscoreClient.sendeScore(highscore); // Schickt zum Server, bekommt echten Highscore zurück
        speicherung.speichern(this.highscore); // speichert dann den vom Server zurückgegebenen Wert
    }
    public int getPunkte() {
        return punkte;
    }

    public void setPunkte(int punkte) {
        this.punkte = punkte;
    }

    public int getHighscore() {
        return highscore;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}