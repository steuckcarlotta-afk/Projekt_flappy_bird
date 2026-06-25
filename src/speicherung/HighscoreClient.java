package speicherung;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HighscoreClient {

    //adresse und port von server
    private static final String HOST="localhost";
    private static final int PORT=12345;

    //score wird an server geschickt und highscore kommt zurück
    public static int sendeScore(int score) {
        try {
            //verbindung wird zum server hergestellt
            Socket socket = new Socket("localhost", 12345);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Score hinschicken
            out.println(score);
            // Highscore zurückbekommen
            int highscore = Integer.parseInt(in.readLine());

            //verbindung schließen
            socket.close();
            return highscore;

        } catch (IOException e) {
            return score; // falls Server nicht läuft, einfach weiterspielen
        }
    }
}

