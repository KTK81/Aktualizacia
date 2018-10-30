import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import Metody.Produkt;
import Metody.Premenne;
import Metody.PrestaIDRead;
/**
 * Created by jano on 13.03.2017.
 */
public class PorovnanieCien {
    public static void spusti() throws IOException, InterruptedException {
        ArrayList<Produkt> prestaNase = new ArrayList<>();
        prestaNase = PrestaIDRead.filePresta();
        PrintWriter writerSubor = new PrintWriter(Premenne.cesta + "porovnanieCien.csv", "UTF-8");
        writerSubor.println("Kod;Nasa cena;Spravna cena;Poznamka");

         for (int i = 0; i < prestaNase.size(); i++) {
             if ((prestaNase.get(i).getAktivita().contains("1")) && (prestaNase.get(i).getVyrobca().contains("AUTRONIC"))) {
//            if (prestaNase.get(i).getAktivita().contains("1")) {
                String prestID = prestaNase.get(i).getPrestaID();
                String vyrobca = prestaNase.get(i).getVyrobca();
                String kod = prestaNase.get(i).getKod();
                String cenaNasa = prestaNase.get(i).getMOC();
                Double cenaNasaDouble = Double.parseDouble(cenaNasa);
                Double spravnaCena = najdiCenu(kod);

                System.out.print("PrestaID:"+prestID+";Kod:"+kod+";Nasa cena:"+cenaNasaDouble+";Spravna cena:"+spravnaCena+";Vyrobca:"+vyrobca);
                if (cenaNasaDouble.equals(spravnaCena)) {
                    System.out.println(";rovnake ceny");
                    writerSubor.println(kod+";"+cenaNasaDouble+";"+spravnaCena+";rovnaka cena");
                }
                else {
                    System.out.println(";INE ceny");
                    writerSubor.println(kod+";"+cenaNasaDouble+";"+spravnaCena+";INA cena");
                }
                Thread.sleep(500);
            }
        }
        writerSubor.close();

    }


    public static Double najdiCenu(String kod) throws IOException {
        Double cenaDouble = null;
        try {
            String kodNaHladanie = kod.replaceAll("\\s", "+");
            URL hladanie = new URL("https://www.lacnyeshop.sk/search-engine.htm?slovo=" + kodNaHladanie + "&search_submit=&hledatjak=2");
            BufferedReader in = new BufferedReader(new InputStreamReader(hladanie.openStream()));
            String riadokArtium = null;
            Boolean naslo = false;
            String cena = null;
            while ((riadokArtium = in.readLine()) != null)    //načíta URL source kód, kým sú riadky, tak ide
            {
                if (riadokArtium.contains(kod))
                    naslo = true;
                if (naslo && riadokArtium.contains("product_price_text")) {
                    int zaciatok = riadokArtium.indexOf("product_price_text");
                    int koniec = riadokArtium.lastIndexOf("span");
                    cena = riadokArtium.substring(zaciatok + 20, koniec - 9);
                    cena = cena.replaceAll(",", ".");
                    cenaDouble = Double.parseDouble(cena);
                }
            }
        }
        catch (java.io.IOException e) {
            System.out.println("zla www");
        }
        return cenaDouble;
    }

}

