package modell;

public class Vogel {
    private int x;
    private int y;
    private int groesse;
    private double geschwindigkeitY;
//x und y: wo ist der vogel
    public Vogel(int x, int y, int groesse) {
        this.x = x;
        this.y = y;
        this.groesse = groesse;
    }
    //neu: statt bei neustart neuen vogel, wird er wieder neu hergestellt
    public void reset(int x, int y) {
        this.x = x;
        this.y = y;
        this.geschwindigkeitY = 0;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getGroesse() {
        return groesse;
    }
    public double getGeschwindigkeitY() {
        return geschwindigkeitY;
    }
    public void setGeschwindigkeitY(double geschwindigkeit) {
        this.geschwindigkeitY = geschwindigkeit; }
    public void setY(int y) {
        this.y = y;
    }
}
