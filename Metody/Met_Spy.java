package Metody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

//spying :) zbehnem na konkkurencny eshop a vytiahnem udaje, ktore chcem. napriklad ich cenu za dany produkt.
public class Met_Spy {
    //popis produktu. Takze hladat, vycucnut URL na produkt, vojst do produktu a v nom najst popis
    public static String description(String code, String wwwStranka) throws IOException {
        String wwwAdresa = null, popisProduktu = "popis", hrubaURL = null, hrubaURLhladanie = null;
        String kodNaHladanie = code.replaceAll("\\s", "+");
        if (wwwStranka.equals("hejnabytok")) {
            wwwAdresa = "https://www.hejnabytok.sk/vyhledavani?controller=search&orderby=position&orderway=desc&search_query_cat=0&search_query="+kodNaHladanie+"&submit_search=";
        }
        //najprv NAJDEM produkt, takze stranka s vysledkom hladania, Odtial si stiahnem URL na dany produkt
        try {
            URL hladajProdukt = new URL(wwwAdresa);
//            System.out.println("hladaj produkt:"+hladajProdukt);
            BufferedReader vysledokHladania = new BufferedReader(new InputStreamReader(hladajProdukt.openStream()));
            String riadokZdrojovehoKodu = null;
            while ((riadokZdrojovehoKodu = vysledokHladania.readLine()) != null)    //načíta URL source kód, kým sú riadky, tak ide
            {
//                System.out.println("naslo produkt");
                    if (riadokZdrojovehoKodu.contains("a class=\"product_img_link\" href")) {
                        int zaciatok = riadokZdrojovehoKodu.indexOf("a class=\"product_img_link\" href");
                        int koniec = riadokZdrojovehoKodu.lastIndexOf("product-flags");
                        hrubaURLhladanie = riadokZdrojovehoKodu.substring(zaciatok, koniec);
//                        System.out.println("hrubaURLhladanie:"+hrubaURLhladanie);
                        int zaciatokURL = hrubaURLhladanie.indexOf(" href=");
//                        System.out.println("zaciatokURL:"+zaciatokURL);
                        int koniecURL = hrubaURLhladanie.indexOf("?search_query=");
//                        System.out.println("koniecURL"+koniecURL);
                        hrubaURL = hrubaURLhladanie.substring(zaciatokURL+7, koniecURL);
//                        System.out.println("hruba url:"+hrubaURL);
                        break;
                    }
            }
            //idem na stranku produktu a vycucnem potrebny udaj
            URL produktURL = new URL(hrubaURL);
            BufferedReader nasloProdukt = new BufferedReader(new InputStreamReader(produktURL.openStream()));
            String riadokKodu = null;
            while ((riadokKodu = nasloProdukt.readLine()) != null)    //načíta URL source kód, kým sú riadky, tak ide
            {
                try {
                if (riadokKodu.contains("itemprop=\"description")) {
//                    int zaciatok = riadokKodu.indexOf("itemprop=\"description");
//                    int koniec = riadokKodu.lastIndexOf("class=\"buttons_bottom_block\"");
                    int zaciatok = riadokKodu.indexOf("<div class=\"rte\">");
                    int koniec = riadokKodu.lastIndexOf("table-data-sheet");
                    popisProduktu = riadokKodu.substring(zaciatok + 17, koniec - 97);
                    //niektore vysledky mi vyhodilo na konci "<br /> <br />", tak ich jednoducho nahradim medzerou
                    if (popisProduktu.contains("<br /> <br />"))
                        popisProduktu = popisProduktu.replaceAll("<br /> <br />", "");
                    if (popisProduktu.contains("<br />"))
                        popisProduktu = popisProduktu.replaceAll("<br />", "");
//                    break;
                }
                if (riadokKodu.contains("Výška v cm:")) {
                    int zaciatokVyskyKodu = riadokKodu.indexOf("Výška v cm:");
                    String vycucVyskaVyrobku = riadokKodu.substring(zaciatokVyskyKodu, zaciatokVyskyKodu+50);
                    System.out.println("vyska:"+vycucVyskaVyrobku);

                   }
                }
                catch (StringIndexOutOfBoundsException siobe ) {
                    System.out.println("zle cosi");
                }

            }
        }
        catch (java.io.IOException e) {
            System.out.println("zla www");
        }
        System.out.println(popisProduktu);
        return popisProduktu;
    }
}
