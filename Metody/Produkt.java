package Metody;
import java.math.BigDecimal;
import java.util.ArrayList;

// zakladny objekt v tomto programe

public class Produkt {
    public String skupina, kod, dostupnost, MOC, VOC, nazov, nazovNovy, zasoby, aktivita;
    public String prestaID, vyrobca, popis, URL, IMGURL, navod, vahaString;
    public String feature, staryUdaj, shortText, operacia, objem, farba, rozmer, sirka, hlbka, vyska, dlzka;
    public int MOCint, zasobyint, vaha;
    public double MOCdouble;
    public BigDecimal MOCBigDecimal;

    //Produkt_dodavatel
    public Produkt(String prestaID, String kod, String nazov, String aktivita, String VOC, int MOCint, int zasobyint, String dostupnost, String skupina) {
        this.skupina = skupina;
        this.kod = kod;
        this.dostupnost = dostupnost;
        this.MOCint = MOCint;
        this.VOC = VOC;
        this.nazov = nazov;
        this.zasobyint = zasobyint;
        this.aktivita = aktivita;
        this.prestaID = prestaID;
    }

    //cenaOFF
    public Produkt(String kod, String MOC) {
        this.kod = kod;
        this.MOC = MOC;
    }

    //cenaOFF
    public Produkt(String kod, double MOCdouble) {
        this.kod = kod;
        this.MOCdouble = MOCdouble;
    }

    //cenaOFF
    public Produkt(String kod, BigDecimal MOCBigDecimal) {
        this.kod = kod;
        this.MOCBigDecimal = MOCBigDecimal;
    }

    //PrestaID, kategorie
    public Produkt(String skupina, String kod, String dostupnost) {
        this.skupina = skupina;
        this.kod = kod;
        this.dostupnost = dostupnost;
    }

    //Rozmery
    public Produkt(String sirka, String hlbka, String vyska, String dlzka) {
        this.sirka = sirka;
        this.hlbka = hlbka;
        this.vyska = vyska;
        this.dlzka = dlzka;
    }

    //statistik
    public Produkt(String skupina, String kod, int zasobyint, int MOCint, String nazov) {
        this.skupina = skupina;
        this.kod = kod;
        this.MOCint = MOCint;
        this.nazov = nazov;
        this.zasobyint = zasobyint;
    }

    //na zapis prestaIDciek do arraylistu. 8x String
    public Produkt(String prestaID, String kod, String nazov, String aktivita, String MOC, String vyrobca, String skupina, String dostupnost) {
        this.kod = kod;
        this.prestaID = prestaID;
        this.nazov = nazov;
        this.aktivita = aktivita;
        this.MOC = MOC;
        this.vyrobca = vyrobca;
        this.skupina = skupina; //kategoria
        this.dostupnost = dostupnost;
    }
    //na zapis vysledku do arraylistu. 11x String
    public Produkt(String prestaID, String kod, String nazov, String skupina, String vyrobca, String MOC, String dostupnost, String aktivita, String feature, String operacia, String oldData) {
        this.prestaID = prestaID;
        this.kod = kod;
        this.nazov = nazov;
        this.skupina = skupina; //kategoria
        this.vyrobca = vyrobca;
        this.MOC = MOC;
        this.dostupnost = dostupnost;
        this.aktivita = aktivita;
        this.feature = feature;
        this.operacia = operacia;
        this.staryUdaj = oldData;
    }

    //  16xString, Drevona, Tempo, Autronic
    public Produkt(String prestaID, String skupina, String kod, String dostupnost, String zasoby, String MOC, String VOC, String nazov, String nazovNovy, String vyrobca,
                   String aktivita, String popis, String URL, String IMGURL, String navod, String vahaString, String objem, String farba, String rozmer,
                   String sirka, String hlbka, String vyska, String dlzka) {
        this.prestaID = prestaID;
        this.skupina = skupina;
        this.kod = kod;
        this.dostupnost = dostupnost;
        this.zasoby = zasoby;
        this.MOC = MOC;
        this.VOC = VOC;
        this.nazov = nazov;
        this.nazovNovy = nazovNovy;
        this.vyrobca = vyrobca;
        this.aktivita = aktivita;
        this.popis = popis;
        this.URL = URL;
        this.IMGURL = IMGURL;
        this.navod = navod;
        this.vahaString = vahaString;
        this.objem = objem;
        this.farba = farba;
        this.rozmer = rozmer;
        this.sirka = sirka;
        this.hlbka = hlbka;
        this.vyska = vyska;
        this.dlzka = dlzka;
    }
    //sluzi na zapis vysledkov Porovnania do Arraylistu, z ktoreho sa to potom vypise do .xml suboru = vacsia kontrola nad konecnym .xml suborom, lahsie sa robia zmeny
    // String option = nech nie je 20 roznych metod, je tu len jedna a option povie, co treba zapisat /ine je, ked sa meni cena, ine ked dostupnost/
    //String pomocnyString = niekedy je potrebne este dalsie clenenie, pomocou tohoto to viem poriesit
    //String staryUdaj = stary udaj... ;) ked sa nieco meni, tak tu je stary udaj toho, co sa zmenilo
    public ArrayList<Produkt> getVysledokInput (String option, String pomocnyString, String staryUdaj) {
        ArrayList<Produkt> vysledok = new ArrayList<>();
        if (option.equals("dostupnost"))
            vysledok.add(new Produkt(this.prestaID, this.kod, this.nazov, this.skupina, this.vyrobca, "", this.dostupnost, this.aktivita, "Dostupnosť: "+pomocnyString, "Zmena dostupnosti", staryUdaj));
        if (option.equals("vyhodene"))
            vysledok.add(new Produkt(this.prestaID, this.kod, this.nazov, this.skupina, this.vyrobca, "", "", "0", "", "Vyhodene produkty", ""));
        if (option.equals("aktivovanie"))
            vysledok.add(new Produkt(this.prestaID, this.kod, this.nazov, this.skupina, this.vyrobca, this.MOC, "viac ako mesiac", "1", "Dostupnosť: "+pomocnyString, "Zmena aktivity", staryUdaj));
        if (option.equals("cena"))
            vysledok.add(new Produkt(this.prestaID, this.kod, this.nazov, this.skupina, this.vyrobca, this.MOC, "viac ako mesiac", "1", "Dostupnosť: "+pomocnyString, "Zmena ceny", staryUdaj));
        if (option.equals("cenaOFF"))
            vysledok.add(new Produkt(this.prestaID, this.kod, this.nazov, this.skupina, this.vyrobca, this.MOC, "viac ako mesiac", "1", "Dostupnosť: "+pomocnyString, "Zmena OFF ceny", staryUdaj));
        return vysledok;
    }

//    public String getSumarMOC() {
//        return this.prestaID + ";" + this.kod + ";" + this.nazov + ";" + this.skupina + ";" + this.vyrobca + ";" + this.MOC + ";<p><span style=\"color: #000000\"><strong>Dostupnosť: </strong></span> ";
//    }

    public String getVysledokOutput() {
        return this.prestaID + ";" + this.kod + ";" + this.vyrobca + ";"+this.MOC+";<p><span style=\"color: #000000\"><strong>Dostupnosť: </strong></span> "
                +this.dostupnost+";"+this.aktivita+";"+this.feature+";"+this.operacia+";"+this.staryUdaj+ ";" + this.nazov + ";" + this.skupina;
    }
//šírka	hĺbka	výška	výška sedu	rozkladanie	materiál	nosnosť	čalúnenie
    public String getVysledokNove() {
        return this.kod + ";" + this.nazov+";"+this.nazovNovy+";"+this.popis+";"+this.skupina+";"+this.getIMGURL()+";"+this.vyrobca+";"+this.VOC+
                ";"+this.MOC+";"+this.vahaString+";"+
                "<p><span style=\"color: #000000\"><strong>Dostupnosť: viac ako mesiac </strong></span> ;1;Dostupnosť: viac ako mesiac, "+this.farba+
                ", "+this.sirka+", "+this.hlbka+", "+this.vyska+";"+this.farba+";"+this.sirka+";"+this.hlbka+";"+this.vyska+";"+this.dlzka;    }

    public String getPrestaID() {
        return this.prestaID;
    }

    public String getAktivita() {
        return this.aktivita;
    }

    public String getVyrobca() {
        return this.vyrobca;
    }

    public String getSkupina() {
        return this.skupina;
    }

    public String getKod() {
        return this.kod;
    }

    public String getDostupnost() {
        return this.dostupnost;
    }

    public String getMOC() {
        return this.MOC;
    }

    public String getZasoby() {
        return this.zasoby;
    }

    public String getNazov() {
        return this.nazov;
    }

    public String getNazovNovy() {
        return this.nazovNovy;
    }

    public String getPopis() {
        return this.popis;
    }

    public String getURL() {
        return this.URL;
    }

    public String getIMGURL() {
        return this.IMGURL;
    }

    public String getNavod() {
        return this.navod;
    }

    public int getVaha() {
        return this.vaha;
    }

    public String getVahaString() {
        return this.vahaString;
    }

    public String getObjem() {
        return this.objem;
    }

    public String getFarba() {
        return this.farba;
    }

    public int getZasobyint() {
        return zasobyint;
    }

    public void setZasobyint(int zasobyint) {
        this.zasobyint = zasobyint;
    }

    public int getMOCint() {
        return this.MOCint;
    }



    double getMOCdouble() {
        return this.MOCdouble;
    }



    public BigDecimal getMOCBigDecimal() {
        return this.MOCBigDecimal;
    }



    //    public int getVOCint() {return this.VOCint;};
    public String getVOC() {
        return this.VOC;
    }


    public void setMOC(String MOC) {
        this.MOC = MOC;
    }

    public String getShortText() {
        return shortText;
    }

    public String getFeature() {
        return feature;
    }

    public String getStaryUdaj() {
        return staryUdaj;
    }

    public String getOperacia() {
        return operacia;
    }

    public String getSirka() {
        return sirka;
    }

    public String getHlbka() {
        return hlbka;
    }

    public String getVyska() {
        return vyska;
    }

    public String getDlzka() {
        return dlzka;
    }
}
