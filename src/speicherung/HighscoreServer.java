package speicherung;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HighscoreServer {
    public static void main(String[] args) throws IOException {

        //higscore wird beim start aus datei gelesen
        HighscoreSpeicherung speicherung = new HighscoreSpeicherung();
        int highscore = speicherung.laden();

        //server öffnet port 12345 und wartet auf verbindung
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Server läuft!");

        //server läuft dauerhaft, nimmt anfragen an
        while (true) {
            //wartet bis client sich verbindet
            Socket socket = serverSocket.accept();

            //lesen
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //schreiben
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            //score vom client empfangen
            int score = Integer.parseInt(in.readLine());

            if (score > highscore) {
                highscore = score;
                speicherung.speichern(highscore); //in datei speichern
            }

            //aktueller higscore wird zurückgeschickt
            out.println(highscore);
            //verbindung wird geschlossen
            socket.close();
        }
    }
}

