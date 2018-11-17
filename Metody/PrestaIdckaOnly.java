package Metody;

import java.io.*;
import java.util.ArrayList;

public class PrestaIdckaOnly {
    public static ArrayList<Produkt> filePresta() throws IOException {
        ArrayList<Produkt> prestaIDlist = new ArrayList<>();
        PrintWriter zapisPresta = new PrintWriter(Premenne.cesta+"PrestaIDckaOnly.csv", "UTF-8");
        zapisPresta.println("id_product;reference");

// zisti posledny stiahnuty subor s udajmi zo stranky a nacita ho
        String nazovSuboruSKL=("request_sql");
        File pomocnyNazov= Met_ZistiDatum.lastFileModified(Premenne.prestaIDDownload, nazovSuboruSKL);
        String suborMeno=pomocnyNazov.getPath();
        System.out.println("Vytvaram PrestaId zo suboru "+suborMeno);
        FileInputStream fis = new FileInputStream(suborMeno);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
        String hladany = reader.readLine();

        while (hladany != null) {
            hladany = reader.readLine();
            if (hladany != null) {
                int bodkociarka = hladany.indexOf(";");
                int bodkociarka2 = hladany.indexOf(";",bodkociarka+1);
                String idPresta = hladany.substring(1, bodkociarka-1);
                String kod = hladany.substring(bodkociarka+2, bodkociarka2-1);

                    prestaIDlist.add(new Produkt(idPresta, kod));
                    zapisPresta.println(idPresta+";"+kod);
                }
        }

        zapisPresta.close();
        reader.close();
        System.out.println("Hotovo PrestaID");
        return prestaIDlist;
    }
}
