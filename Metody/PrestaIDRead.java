package Metody;

import java.io.*;
import java.util.ArrayList;

public class PrestaIDRead {
    public static ArrayList<Produkt> filePresta() throws IOException {
        ArrayList<Produkt> prestaIDlist = new ArrayList<>();
        ArrayList<Produkt> categ = new ArrayList<>();
        ArrayList<Produkt> feature = new ArrayList<>();
        ArrayList<String> obrazy = new ArrayList<>();
        String catMeno = null;
        PrintWriter zapisPresta = new PrintWriter(Premenne.cesta+"PrestaID.csv", "UTF-8");
        zapisPresta.println("id_product;reference;product_name;active;CAST(ps_product.price*1.2 as decimal (38,2));" +
                "vyrobca_name;id_category_default;description_short;farba;sirka;hlbka;vyska;vyska sedu;rozkladanie;material;nosnost;calunenie;dlzka;vaha");
        PrintWriter zapisVysledku = new PrintWriter(Premenne.cesta + "pomocnySuborVysledok.csv", "UTF-8");
        zapisVysledku.println("id;kod;nazov;kategoria;kategoria zmenena");


// nacitanie nazvu kategorii
        FileInputStream fin = new FileInputStream(Premenne.cestaZoznam+"kategorie_zoznam.csv");
        BufferedReader citac = new BufferedReader(new InputStreamReader(fin, "UTF-8"));
        String riadok = citac.readLine();
        while (riadok != null) {
            riadok = citac.readLine();
            if (riadok != null) {
                int bodkociarka = riadok.indexOf(";");
                int bodkociarka2 = riadok.indexOf(";",bodkociarka+1);
                int bodkociarka3 = riadok.indexOf(";",bodkociarka2+1);
                String catID = riadok.substring(0, bodkociarka);
                String catNazov = riadok.substring(bodkociarka+1,bodkociarka2-1);
                String catCele = riadok.substring(bodkociarka2+1,bodkociarka3);
                categ.add(new Produkt(catID, catNazov, catCele));
            }
        }
        citac.close();

// nacitanie Tempo obrazy - mame v ponuke, nie su vo feede
        FileInputStream inputObrazy = new FileInputStream(Premenne.cestaZoznam+"tempo_obrazyOFF.txt");
        BufferedReader readerObrazy = new BufferedReader(new InputStreamReader(inputObrazy, "UTF-8"));
        String lineObrazy = readerObrazy.readLine();
        while (lineObrazy != null) {
            lineObrazy = readerObrazy.readLine();
            if (lineObrazy != null) {
                obrazy.add(new String(lineObrazy));
            }
        }
        readerObrazy.close();

// zisti posledny stiahnuty subor s udajmi zo stranky a nacita ho
        String nazovSuboruSKL=("request_sql");
        File pomocnyNazov= Met_ZistiDatum.lastFileModified(Premenne.prestaIDDownload, nazovSuboruSKL);
        String suborMeno=pomocnyNazov.getPath();
        System.out.println("Vytvaram PrestaId zo suboru "+suborMeno);
        FileInputStream fis = new FileInputStream(suborMeno);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
        String hladany = reader.readLine();
        feature=Met_Features.nacitajFeatureKomplet();

        while (hladany != null) {
            String farba = null, calunenie= null, hlbka= null, material= null, nosnost= null, sirka= null, vaha= null, vyska= null, sed = null,
                    rozkladanie = null, dlzka = null;
            hladany = reader.readLine();
            if (hladany != null) {
                catMeno = "";
                int bodkociarka = hladany.indexOf(";");
                int bodkociarka2 = hladany.indexOf(";",bodkociarka+1);
                int bodkociarka3 = hladany.indexOf(";",bodkociarka2+1);
                int bodkociarka4 = hladany.indexOf(";",bodkociarka3+1);
                int bodkociarka5 = hladany.indexOf(";",bodkociarka4+1);
                int bodkociarka6 = hladany.indexOf(";",bodkociarka5+1);
                int bodkociarka7 = hladany.indexOf(";",bodkociarka6+1);
                int bodkociarka8 = hladany.indexOf(";",bodkociarka7+1);
                String idPresta = hladany.substring(1, bodkociarka-1);
                String kod = hladany.substring(bodkociarka+2, bodkociarka2-1);
                String name = hladany.substring(bodkociarka2+2, bodkociarka3-1);
                String aktivny = hladany.substring(bodkociarka3+2, bodkociarka4-1);
                String MOC = hladany.substring(bodkociarka4+2, bodkociarka5-1);
                String vyrobca = hladany.substring(bodkociarka5+2, bodkociarka6-1);
                String kategoriaRequest = hladany.substring(bodkociarka6+2, bodkociarka7-1);



                for (int i=0; i < categ.size(); i++) {
                    if (kategoriaRequest.equals(categ.get(i).getSkupina())) {
                        String kategoria=categ.get(i).getDostupnost();
                        catMeno=Met_CatZostavy.pridajZostavu(idPresta, kod, name, kategoria,name);
                        if (!(kategoria.equals(catMeno))) {
                            zapisVysledku.println(idPresta+";"+kod+";"+name+";"+kategoria+";"+catMeno);
                        }
                        break;
                    }
                }

                for (int i=0; i < obrazy.size(); i++) {
                    if (kod.equals(obrazy.get(i))) {
                        aktivny="0";
                        break;
                    }
                }

                String dostupnost = hladany.substring(bodkociarka7+2, bodkociarka8-1);
                String dostupnostOriginal = dostupnost;
                if (dostupnost.contains("Dostupnosť: ")) {
                    dostupnost = (dostupnost.substring(12));
                    dostupnost = dostupnost.trim();
                    if (dostupnost.contains("dne"))
                        dostupnost = Premenne.feature4tyzdne;
                }

//features, Vlastnosti produktov
                for (int k=0; k<feature.size();k++) {
                    if (feature.get(k).getSkupina().equals(kod)) {
                        if (feature.get(k).getKod().equals("Farba")) {
                            farba = feature.get(k).getDostupnost();
                        }
                        if (feature.get(k).getKod().equals("Šírka")) {
                            sirka = feature.get(k).getDostupnost();
                        }
                        if (feature.get(k).getKod().equals("Hĺbka")) {
                            hlbka = feature.get(k).getDostupnost();
                        }
                        if (feature.get(k).getKod().equals("Výška")) {
                            vyska = feature.get(k).getDostupnost();
                        }
                        if (feature.get(k).getKod().equals("Výška sedu")) {
                            sed = feature.get(k).getDostupnost();
                        }
                        if (feature.get(k).getKod().equals("Rozkladanie")) {
                            rozkladanie = feature.get(k).getDostupnost();
                        }
                        if (feature.get(k).getKod().equals("Materiál")) {
                            material = feature.get(k).getDostupnost();
                        }
                        if (feature.get(k).getKod().equals("Nosnosť")) {
                            nosnost = feature.get(k).getDostupnost();
                        }
                        if (feature.get(k).getKod().equals("Čalúnenie")) {
                            calunenie = feature.get(k).getDostupnost();
                        }
                        if (feature.get(k).getKod().equals("Dĺžka")) {
                            dlzka = feature.get(k).getDostupnost();
                        }
                        if (feature.get(k).getKod().equals("Váha")) {
                            vaha = feature.get(k).getDostupnost();
                        }
                    }
                }

                if (vyrobca.contains("AUTRONIC") || vyrobca.contains("Tempo-Kondela") || vyrobca.contains("Drevona")
                        || vyrobca.contains("Drevona")|| vyrobca.contains("NELLYS")|| vyrobca.contains("SAMESKIN")) {

                    prestaIDlist.add(new Produkt(idPresta, kod, name, aktivny, MOC, vyrobca, catMeno, dostupnost));
                    zapisPresta.println(idPresta+";"+kod+";"+name+";"+aktivny+";"+MOC+";"+vyrobca+";"+catMeno+";"+dostupnostOriginal
                            +";"+farba+";"+sirka+";"+hlbka+";"+vyska+";"+sed+";"+rozkladanie+";"+material+";"+nosnost+";"+calunenie+";"+dlzka+";"+vaha);
                }
            }

        }

        zapisVysledku.close();
        zapisPresta.close();
        reader.close();
        System.out.println("Hotovo PrestaID");
        return prestaIDlist;

    }
}
