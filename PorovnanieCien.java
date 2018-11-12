import Metody.Met_Price;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
        writerSubor.println("PrestaID;Kod;Nasa cena;Spravna cena;Rozdiel;Spravna cena bez sedaka;Vyrobca;Stav");

         for (int i = 0; i < prestaNase.size(); i++) {
//             if (prestaNase.get(i).getAktivita().contains("1")) {
             if ((prestaNase.get(i).getAktivita().contains("1")) && (prestaNase.get(i).getVyrobca().contains("AUTRONIC"))) {
//             && (prestaNase.get(i).getKod().equals("BC-12481 WAL"))) {
                String prestID = prestaNase.get(i).getPrestaID();
                String vyrobca = prestaNase.get(i).getVyrobca();
                String kod = prestaNase.get(i).getKod();
                String kategoria = prestaNase.get(i).getSkupina();
                String cenaNasa = prestaNase.get(i).getMOC();
                BigDecimal cenaNasaBezSedaka = new BigDecimal(cenaNasa);
                //ak su sedaky na stolicke. Tak musim spocitat cenu za stolicku a cenu sedaku, aby som mal konecnu cenu
                 if (vyrobca.contains("AUTRONIC"))
                     cenaNasa = Met_Price.cenaAutronicSedaky(cenaNasa, kod);

                BigDecimal cenaNasaBigDecimal = new BigDecimal(cenaNasa);//bud so sedakom, alebo ak nema sedak, tak povodna
                BigDecimal spravnaCena = najdiCenu(kod); //cena u konkurencie, mala by byt ta spravna cena
                BigDecimal rozdiel = cenaNasaBigDecimal.subtract(spravnaCena); //rozdiel medzi nasou a spravnou. plusova hodnota, ak mam znizit, minusova, ak mam zvysit
                BigDecimal rozdielBezSedaka = cenaNasaBezSedaka.subtract((rozdiel)); //ak ma stolicka sedak, tak musim pozriet na cenu predtym, ako som
                 // k tej cene pripocital sedak a z tejto sumy musim odobrat rozdiel a ten vysledok, to je suma, za ktoru by som ju mal predavat, bez sedaka
                 //Ked k tejto vyslednej sume pripocitam cenu za sedak, bude taka, ako ma byt
                 //Kedze do Presty viem cez import CSV-cka zadat len tu cenu bez sedaku, tak ta ma musi zaujimat, tu chcem zmenit
                String stav = null;
//                System.out.print("PrestaID:"+prestID+";Kod:"+kod+";Nasa cena:"+cenaNasaDouble+";Spravna cena:"+spravnaCena+";Rozdiel:"+rozdiel+";Vyrobca:"+vyrobca);

                if (cenaNasaBigDecimal.equals(spravnaCena)) {
                    stav = "rovnake ceny";
                }
                else {
                    stav = "INE ceny";
                }
                 System.out.println(kod+";"+cenaNasaBigDecimal+";"+spravnaCena+";"+rozdiel+";"+rozdielBezSedaka+";"+stav);
                 writerSubor.println(prestID+";"+kod+";"+cenaNasaBigDecimal+";"+spravnaCena+";"+rozdiel+";"+rozdielBezSedaka+";"+vyrobca+";"+kategoria+";"+stav);
                Thread.sleep(500);
            }
        }
        writerSubor.close();
    }

    public static BigDecimal najdiCenu(String kod) throws IOException {
        BigDecimal cenaDouble = new BigDecimal(77777.00);
        try {
                String kodNaHladanie = kod.replaceAll("\\s", "+");
                URL hladanie = new URL("https://www.lacnyeshop.sk/lacnyeshop/e-search?q="+kodNaHladanie+"&qm=2");
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
                        cenaDouble = new BigDecimal(cena);
                        break;
                    }
                }
        }
        catch (java.io.IOException e) {
            System.out.println("zla www");
        }
        return cenaDouble;
    }

}

