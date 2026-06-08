package ansicht;

import javax.swing.*;

public class SpielFenster extends JFrame {

    public SpielFenster(){
        setTitle("Flappy Bird");

        //fenster wird geschlossen & programm beendet, wenn man auf exit drückt
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //fenstergröße nicht veränderbar
        setResizable(false);

        add(new SpielPanel());

        pack();
        //fenster wird in der mitte zentriert
        setLocationRelativeTo(null);

        setVisible(true);
}

}
