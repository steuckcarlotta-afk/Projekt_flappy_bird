package modell;

public class Saeule {

        private int x;
        private int breite;
        private int lueckeY;
        private int lueckeHoehe;
        private int gschwindigkeit=3;
        private boolean punktGegeben=false;

        //geschwindigkeit nicht in konstruktor, weil konstruktur nur einmal ausgeführt wird
        public Saeule(int x, int breite, int lueckeY, int lueckeHoehe) {
            this.x = x;
            this.breite = breite;
            this.lueckeY = lueckeY;
            this.lueckeHoehe = lueckeHoehe;

        }

        public int getX() {
            return x;
        }

        public int getBreite() {
            return breite;
        }

        public int getLueckeY() {
            return lueckeY;
        }

        public int getLueckeHoehe() {
            return lueckeHoehe;
        }

        public int getGschwindigkeit() {
        return gschwindigkeit;}

    //säule soll sich bewegen
        public void setX(int x){
            this.x=x;
            }

        public boolean isPunktGegeben() {
        return punktGegeben;
        }

        public void setPunktGegeben(boolean punktGegeben) {
        this.punktGegeben = punktGegeben;
    }

    public void setLueckeY(int lueckeY) {
        this.lueckeY = lueckeY;
    }
}

