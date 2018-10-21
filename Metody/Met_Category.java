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
            ArrayList<Produkt> zoznamKategoria = Premenne.complexReplace(Premenne.cestaZoznam + suborCategory);
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

            //OK, prebehol som zoznamy, teraz par indivudualne riesenych kategorii

            //sedacky na mieru BORN nemaju v Tempe vlastnu podkategoriu, tak si ich musim oddelit z hlavnej kategorie
            if ((kategoriaOriginal.contains("Sedačky na mieru"))&&(meno.contains("BORN"))) {
                kategoria = "BORN, Sedačky na mieru, Sedacie súpravy, Obývačka";
            }

            //jedalenske stolicky, drevene vs kovove, tiez specificky treba riesit, ale lavice su vzdy lavice samostatne
            if ((kategoria.contains("Stoličky"))&& (!(kategoria.contains("Lavice")))){
                if (popis.contains("kov"))
                    kategoria = "Kovové stoličky, Stoličky, Kuchyňa";
            }

            //postele su specificke, podla sirky postele sa urci kategoria
            if (kategoria.equals("Jednolôžkové postele, Postele, Spálňa")) {
                if ((name.contains("140"))||(name.contains("160"))||(name.contains("180")))
                    kategoria = "Manželské postele, Postele, Spálňa";
            }


            }

        return kategoria;
    }
}

