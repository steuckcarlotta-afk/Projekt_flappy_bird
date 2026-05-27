package ansicht;

import javax.swing.*;

public class SpielFenster extends JFrame {

    public SpielFenster(){
        setTitle("Flappy Bird");

        //fenster wird geschlossen, wenn man auf exit drückt
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);

        add(new SpielPanel());

        pack();

        setLocationRelativeTo(null);

        setVisible(true);
}

}
