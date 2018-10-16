package Metody;

import java.io.IOException;
import java.util.ArrayList;
import java.io.IOException;
import java.util.ArrayList;

//priradi nasu kategoriu zo zoznamu, na zaklade originalnej kategorie, alebo inych udajov
public class Met_Category {
    public static String zistiKategoriu(String kategoriaOriginal, String vyrobca, String meno, String popis) throws IOException {
        //Pred povodnu kategoriu pridam text Original: a tym padom, ak ju na konci k nicomu nepriparuje, hned vidim, ktore su nesparovane
        String kategoria = "Original : "+kategoriaOriginal;
        String name = meno;
        String suborMeno = null, suborCategory = null;

        //podla vyrobcu si urcim, ktore subory sa budu nacitavat - cesta je rovnaka, len nazov suboru si urcujem
        //na nacitanie pouzivam metodu Premenne.complexReplace - kde nacitam dva stlpce a cey get.Kod a get.MOC ich nacitam
        if (vyrobca.equals("DREVONA"))
            suborMeno = "Zoznam_kategoria_drevona.csv";
        if (vyrobca.equals("Tempo-Kondela")) {
            suborMeno = "Zoznam_kategoria_tempo podla nazvu.csv";
            suborCategory = "Zoznam_kategoria_tempo podla kategorie.csv";
        }
        if (vyrobca.equals("AUTRONIC"))
            suborMeno = "Zoznam_kategoria_autronic.csv";
        
        ArrayList<Produkt> zoznamMeno = Premenne.complexReplace(Premenne.cestaZoznam + suborMeno);
        ArrayList<Produkt> zoznamKategoria = Premenne.complexReplace(Premenne.cestaZoznam + suborCategory);

//hladam kategoriu v povodnej kategorii
        if (vyrobca.equals("DREVONA")) {
            for (int i = 0; i < zoznamMeno.size(); i++) {
                if (kategoriaOriginal.contains(zoznamMeno.get(i).getKod())) {
                    kategoria = zoznamMeno.get(i).getMOC();
                    break;
                }
            }
        }

//hladam kategoriu v povodnom NAZVE vyrobku
        if (vyrobca.equals("Tempo-Kondela")) {
            for (int i = 0; i < zoznamMeno.size(); i++) {
                if (name.contains(zoznamMeno.get(i).getKod())) {
                    kategoria = zoznamMeno.get(i).getMOC();
                    break;
                }
            }

            for (int i = 0; i < zoznamKategoria.size(); i++) {
                if (kategoriaOriginal.contains(zoznamKategoria.get(i).getKod())) {
                    kategoria = zoznamKategoria.get(i).getMOC();
                    break;
                }
            }

//
//            //KUCHYNA
//            if (kategoriaOriginal.contains("Kuchyňa a Jedáleň > Stoličky > Drevené"))
//                kategoria = "Drevené stoličky, Stoličky, Kuchyňa";
//            if (kategoriaOriginal.contains("Kuchyňa a Jedáleň > Stoličky > Kovové"))
//                kategoria = "Kovové stoličky, Stoličky, Kuchyňa";
//            if (kategoriaOriginal.contains("Jedálenské sety"))
//                kategoria = "Jedálenské sety, Kuchyňa";
//            if (kategoriaOriginal.contains("Kuchyňa a Jedáleň > Kuchynské linky > Komplety"))
//                kategoria = "Kompletné linky, Kuchynské linky, Kuchyňa";
//            if (kategoriaOriginal.contains("Kuchyňa a Jedáleň > Lavice do kuchyne"))
//                kategoria = "Lavice do kuchyne, Stoličky, Kuchyňa";

            //jedalenske stolicky, drevene vs kovove, tiez specificky treba riesit, ale lavice su vzdy lavice
            if ((kategoria.contains("Stoličky"))&& (!(kategoria.contains("Lavice")))){
                if (popis.contains("kov"))
                    kategoria = "Kovové stoličky, Stoličky, Kuchyňa";
            }
//
//            //Kuchynske ZOSTAVY
//            if (kategoriaOriginal.contains("Prado-biela lesk HG/korpus sivá"))
//                kategoria = "Prado-biela lesk HG/korpus sivá, Zostavy na mieru, Kuchyňa";
//            if (kategoriaOriginal.contains("Prado-krém lesk HG/sivá lesk HG/sivá"))
//                kategoria = "Prado-krém lesk HG/sivá lesk HG/sivá, Zostavy na mieru, Kuchyňa";
//            if (kategoriaOriginal.contains("Prado-krémová lesk HG/korpus sivá"))
//                kategoria = "Prado-krémová lesk HG/korpus sivá, Zostavy na mieru, Kuchyňa";
//            if (kategoriaOriginal.contains("Kuchyne na mieru > Vega"))
//                kategoria = "Vega, Zostavy na mieru, Kuchyňa";
//
//            //SPALNA
//            if (kategoriaOriginal.contains("Kompletné spálne"))
//                kategoria = "Kompletné spálne, Spálňa";
//            if (kategoriaOriginal.contains("Jednolôžka"))
//                kategoria = "Jednolôžkové postele, Postele, Spálňa";
//            if (kategoriaOriginal.contains("Kancelária > Regály"))
//                kategoria = "Skrinky a regály, Kancelária";

            //postele su specificke, podla sirky postele sa urci kategoria
            if (kategoria.equals("Jednolôžkové postele, Postele, Spálňa")) {
                if ((name.contains("140"))||(name.contains("160"))||(name.contains("180")))
                    kategoria = "Manželské postele, Postele, Spálňa";
            }
//
//            //OBYVACKA
//            if (kategoriaOriginal.contains("Obývacia izba > Konferenčné stolíky"))
//                kategoria = "Konferenčné stolíky, Stolíky, Obývačka";
//            if (kategoriaOriginal.contains("Obývacia izba > TV stolíky"))
//                kategoria = "TV stolíky, Stolíky, Obývačka";
//            if (kategoriaOriginal.contains("Rohové sedačky"))
//                kategoria = "Rohové súpravy, Sedacie súpravy, Obývačka";
//            if (kategoriaOriginal.contains("Obývačka > Sedačky a sedacie súpravy > Sedačky v tvare U"))
//                kategoria = "Sedačky v tvare U, Sedacie súpravy, Obývačka";
//            if (kategoriaOriginal.contains("Obývačka > Pohovky"))
//                kategoria = "Pohovky, Sedacie súpravy, Obývačka";
//
//
//            //PREDSIEN
//            if (kategoriaOriginal.contains("Predsieň > Lavice a taburety"))
//                kategoria = "Lavice, Predsieň";



//                    break;
            }
//        }

        return kategoria;
    }
}

