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
        writerSubor.println("PrestaID;Kod;Nasa cena;Spravna cena;Rozdiel;Vyrobca;Stav");

         for (int i = 0; i < prestaNase.size(); i++) {
//             if (prestaNase.get(i).getAktivita().contains("1")) {
             if ((prestaNase.get(i).getAktivita().contains("1")) && (prestaNase.get(i).getVyrobca().contains("AUTRONIC"))) {
//             && (prestaNase.get(i).getKod().equals("BC-12481 WAL"))) {
                String prestID = prestaNase.get(i).getPrestaID();
                String vyrobca = prestaNase.get(i).getVyrobca();
                String kod = prestaNase.get(i).getKod();
                String kategoria = prestaNase.get(i).getSkupina();
                String cenaNasa = prestaNase.get(i).getMOC();
                Double cenaNasaDouble = Double.parseDouble(cenaNasa);
                Double spravnaCena = najdiCenu(kod);
                Double rozdiel = spravnaCena-cenaNasaDouble;
                String stav = null;
//                System.out.print("PrestaID:"+prestID+";Kod:"+kod+";Nasa cena:"+cenaNasaDouble+";Spravna cena:"+spravnaCena+";Rozdiel:"+rozdiel+";Vyrobca:"+vyrobca);
                if (cenaNasaDouble.equals(spravnaCena)) {
                    stav = "rovnake ceny";
                }
                else {
                    stav = "INE ceny";
                }
                 writerSubor.println(prestID+";"+kod+";"+cenaNasaDouble+";"+spravnaCena+";"+rozdiel+";"+vyrobca+";"+kategoria+";"+stav);
                Thread.sleep(500);
            }
        }
        writerSubor.close();
    }

    public static Double najdiCenu(String kod) throws IOException {
        Double cenaDouble = 7777.7;
        try {
//            if (kod.equals("BC-12481 WAL")) {
                String kodNaHladanie = kod.replaceAll("\\s", "+");
//                System.out.println(kodNaHladanie);
//                URL hladanie = new URL("https://www.lacnyeshop.sk/search-engine.htm?slovo=" + kodNaHladanie + "&search_submit=&hledatjak=2");
                URL hladanie = new URL("https://www.lacnyeshop.sk/lacnyeshop/e-search?q="+kodNaHladanie+"&qm=2");
//                System.out.println(hladanie);
                BufferedReader in = new BufferedReader(new InputStreamReader(hladanie.openStream()));
                String riadokArtium = null;
                Boolean naslo = false;
                String cena = null;
                while ((riadokArtium = in.readLine()) != null)    //načíta URL source kód, kým sú riadky, tak ide
                {
//                    System.out.println(riadokArtium);
                    if (riadokArtium.contains(kod))
                        naslo = true;
                    if (naslo && riadokArtium.contains("product_price_text")) {
                        int zaciatok = riadokArtium.indexOf("product_price_text");
                        int koniec = riadokArtium.lastIndexOf("span");
                        cena = riadokArtium.substring(zaciatok + 20, koniec - 9);
                        cena = cena.replaceAll(",", ".");
                        cenaDouble = Double.parseDouble(cena);
//                        System.out.println(hladanie);
                        break;
                    }
                }
//            }
        }
        catch (java.io.IOException e) {
            System.out.println("zla www");
        }
        return cenaDouble;
    }

}

