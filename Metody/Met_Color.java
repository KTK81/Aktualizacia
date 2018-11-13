package Metody;

import java.io.IOException;
import java.util.ArrayList;

//vycucnutie farby z nazvu produktu, pripadne z ineho zdroja
public class Met_Color {
    public static String zistiFarbu (String nazov, String vyrobca) throws IOException {
        String farba = "Farba: ";
        String subor = "null";
        if (vyrobca.equals("AUTRONIC"))
            subor = "Zoznam_farba_autronic.csv";
        if (vyrobca.equals("DREVONA"))
            subor = "Zoznam_farba_drevona.csv";
        if (vyrobca.equals("NELLYS"))
            subor = "Zoznam_farba_nellys.csv";
        if (vyrobca.equals("Tempo-Kondela"))
            subor = "Zoznam_farba_tempo.csv";

        ArrayList<Produkt> temp=Premenne.complexReplace(Premenne.cestaZoznam+subor);
        for (int i=0; i<temp.size();i++) {
            if (nazov.contains(temp.get(i).getKod())) {
                farba += temp.get(i).getMOC();
                break;
            }
        }

        return farba;
    }
}