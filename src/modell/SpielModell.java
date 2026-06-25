package modell;

import speicherung.HighscoreSpeicherung;

public class SpielModell {

    private int punkte = 0;
    private int highscore = 0;
    private boolean gameOver = false;
    private HighscoreSpeicherung speicherung = new HighscoreSpeicherung();

    public SpielModell() {
        highscore = speicherung.laden(); // ← beim Start laden
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
        speicherung.speichern(highscore); // ← sofort in Datei speichern
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