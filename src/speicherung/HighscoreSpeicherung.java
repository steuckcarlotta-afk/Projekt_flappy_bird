package speicherung; // hier highscore speichern und highscore laden

public class HighscoreSpeicherung {
    private int highscore =0; // hier wird highscore gespeichert . Am Anfang ist highscore 0
    public int laden(){ // aktueller highcore zurückgeben
        return highscore;
    }
    public void speichern (int highscore){
        this.highscore = highscore; // neuen highscore speichern
    }
}
