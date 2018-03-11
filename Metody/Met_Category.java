package Metody;

import java.io.IOException;
import java.util.ArrayList;
import java.io.IOException;
import java.util.ArrayList;

//priradi nasu kategoriu zo zoznamu, na zaklade originalnej kategorie, alebo inych udajov
public class Met_Category {
    public static String zistiKategoriu(String kategoriaOriginal, String vyrobca, String pomocne, String popis) throws IOException {
        String kategoria = "Original : "+kategoriaOriginal;
        String name = pomocne;
        String subor = null;
        if (vyrobca.equals("DREVONA"))
            subor = "Zoznam_kategoria_drevona.csv";
        if (vyrobca.equals("Tempo-Kondela"))
            subor = "Zoznam_kategoria_tempo.csv";
        if (vyrobca.equals("AUTRONIC"))
            subor = "Zoznam_kategoria_autronic.csv";
        
        ArrayList<Produkt> temp = Premenne.complexReplace(Premenne.cestaZoznam + subor);

//hladam kategoriu v povodnej kategorii
        if (vyrobca.equals("DREVONA")) {
            for (int i = 0; i < temp.size(); i++) {
                if (kategoriaOriginal.contains(temp.get(i).getKod())) {
                    kategoria = temp.get(i).getMOC();
                    break;
                }
            }
        }

//hladam kategoriu v povodnom nazve
        if (vyrobca.equals("Tempo-Kondela")) {
            for (int i = 0; i < temp.size(); i++) {
                if (name.contains(temp.get(i).getKod())) {
                    kategoria = temp.get(i).getMOC();
                    if (kategoriaOriginal.contains("Kuchyňa a Jedáleň > Stoličky > Drevené"))
                        kategoria = "Drevené stoličky, Stoličky, Kuchyňa";
                    if (kategoriaOriginal.contains("Kuchyňa a Jedáleň > Stoličky > Kovové"))
                        kategoria = "Kovové stoličky, Stoličky, Kuchyňa";
                    if (kategoriaOriginal.contains("Jednolôžka"))
                        kategoria = "Jednolôžkové postele, Postele, Spálňa";
                    if (kategoriaOriginal.contains("Kancelária > Regály"))
                        kategoria = "Skrinky a regály, Kancelária";
                    if (kategoriaOriginal.contains("Obývacia izba > Konferenčné stolíky"))
                        kategoria = "Konferenčné stolíky, Stolíky, Obývačka";
                    if (kategoriaOriginal.contains("Obývacia izba > TV stolíky"))
                        kategoria = "TV stolíky, Stolíky, Obývačka";

                    //postele su specificke, podla sirky postele sa urci kategoria
                    if (kategoria.equals("Jednolôžkové postele, Postele, Spálňa")) {
                        if ((name.contains("140"))||(name.contains("160"))||(name.contains("180")))
                            kategoria = "Manželské postele, Postele, Spálňa";
                    }
                    //jedalenske stolicky, drevene vs kovove, tiez specificky treba riesit
                    if (kategoria.contains("Stoličky")) {
                        if (popis.contains("kov"))
                            kategoria = "Kovové stoličky, Stoličky, Kuchyňa";
                    }

                    break;
                }
            }
        }

        return kategoria;
    }
}

