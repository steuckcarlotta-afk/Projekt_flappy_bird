package speicherung;

import java.io.*;

    public class HighscoreSpeicherung {
        //in der datei wird der highscore gespeichert
        private static final String DATEI = "highscore.txt";

        //highscore wird aus datei gelesen
        public int laden() {
            try (BufferedReader reader = new BufferedReader(new FileReader(DATEI))) {
                //erste zeile der datei lesen, als zahl zurückgeben
                return Integer.parseInt(reader.readLine().trim());
            } catch (Exception e) {
                return 0; // Datei existiert noch nicht
            }
        }
        //highscore wird in die datei geschrieben
        public void speichern(int highscore) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(DATEI))) {
                writer.println(highscore);
            } catch (IOException e) {
                System.out.println("Speichern fehlgeschlagen: " + e.getMessage());
            }
        }
    }

