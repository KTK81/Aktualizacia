/**
 * Created by jano on 22.02.2017.
 */
import Metody.Produkt;

public class Produkt_dodavatel extends Produkt {
    private int pohyb;
    private int sucet;
    private int koeficient;
    private String sumar;

    public Produkt_dodavatel(String prestaID, String kod, String nazov, String aktivita, String VOC, int MOCint, int zasobyInt, String dostupnost,
                             String skupina) {
        super(prestaID, kod, nazov, aktivita, VOC, MOCint, zasobyInt, dostupnost, skupina);
    }

    public Produkt_dodavatel(String prestaID, String kod, String nazov, String aktivita, String VOC, int MOCint, int zasobyInt, String dostupnost,
                             String skupina, int pohyb, int sucet, int koeficient, String sumar) {
        super(prestaID, kod, nazov, aktivita,VOC,MOCint,zasobyInt,dostupnost,skupina);
        this.pohyb = pohyb;
        this.sucet = sucet;
        this.koeficient = koeficient;
        this.sumar = sumar;
    }


    public String getSumar() {
        return sumar;
    }
    public int getSucet() {
        return sucet;
    }
    public int getKoeficient() {
        return koeficient;
    }
    public int getPohyb() {        return pohyb;    }


    public String getCSV() {
        return this.prestaID+";"+this.kod+";"+this.nazov+";"+this.aktivita+";"+this.VOC+";"+this.MOCint+";"+this.zasobyint+";"+this.dostupnost+";"+
                this.skupina+";"+this.pohyb+";"+this.sucet+";"+this.koeficient+";"+this.sumar;
    }


    public void setSucet(int sucet) {
        this.sucet = sucet;
    }
    public void setPohyb(int pohyb) {
        this.pohyb = pohyb;
    }
    public void setKoeficient(int koeficient) {
        this.koeficient = koeficient;
    }
    public void setSumar(String sumar) {  this.sumar = sumar;
    }
}
