package Metody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
//                if (riadokKodu.contains("Výška v cm:")) {
//                    int zaciatokVyskyKodu = riadokKodu.indexOf("Výška v cm:");
//                    String vycucVyskaVyrobku = riadokKodu.substring(zaciatokVyskyKodu, zaciatokVyskyKodu+50);
//                    System.out.println("vyska:"+vycucVyskaVyrobku);
//
//                   }
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

//som na stranke, je tam zoznam produktov, tak si vycucnem zoznam vsetkych produktov v danej kategorii
    public static ArrayList<Produkt> zoznamProduktov (String oblast, String wwwStranka) throws IOException, InterruptedException {
        ArrayList<Produkt> zoznamVysledok = new ArrayList<>();
        String URLAdresa = null, URLProduktu = null, pocetTyzden = null, category = null, name=null, price=null;
        PrintWriter writerSubor = new PrintWriter("C:\\Users\\jano\\Disk Google\\JAVA\\subory\\spySubor.csv", "UTF-8");
        writerSubor.println("category;name;price;count");

        if (wwwStranka.equals("Alza")) {
            String alzaURL = "https://www.alza.sk";

            if (oblast.equals("LEGO")) {
                for (int i = 2; i < 4; i++) {
                    boolean dacoNaslo = false;
                    URLAdresa = "https://www.alza.sk/hracky/lego/18851136-p" + i + ".htm";
                    URL hladajProdukt = new URL(URLAdresa);
                    System.out.println("Aktualna URL :" + hladajProdukt);
                    BufferedReader vysledokHladania = new BufferedReader(new InputStreamReader(hladajProdukt.openStream()));
                    String riadokZdrojovehoKodu = null;
                    while ((riadokZdrojovehoKodu = vysledokHladania.readLine()) != null)    //načíta URL source kód, kým sú riadky, tak ide
                    {
                        if (riadokZdrojovehoKodu.contains("name browsinglink")) {
                            dacoNaslo = true;
                            int zaciatok = riadokZdrojovehoKodu.indexOf("href=");
                            int koniec = riadokZdrojovehoKodu.lastIndexOf(".htm");
                            URLProduktu = alzaURL+riadokZdrojovehoKodu.substring(zaciatok + 6, koniec+4);
//                            System.out.println("URL Produktu : " + URLProduktu);
                            zoznamVysledok.add(new Produkt(URLProduktu, "test"));
                        }
                    }
                    Thread.sleep(1000);
                    if (dacoNaslo==false)
                        break;
                }

                for (int j=0; j<zoznamVysledok.size(); j++) {
//                    String URLAdresaProduktu = zoznamVysledok.get(j).getKod();
                    URL hladajProdukt = new URL(zoznamVysledok.get(j).getKod());
//                    System.out.println("stranka produktu :" + hladajProdukt);
                    BufferedReader vysledokHladania = new BufferedReader(new InputStreamReader(hladajProdukt.openStream()));
                    String riadokZdrojovehoKodu = null;
                    while ((riadokZdrojovehoKodu = vysledokHladania.readLine()) != null)    //načíta URL source kód, kým sú riadky, tak ide
                    {
                        try {
                            if (riadokZdrojovehoKodu.contains("data-msgs=")) {
                                int zaciatok = riadokZdrojovehoKodu.indexOf("Tento týždeň");
                                int koniec = riadokZdrojovehoKodu.indexOf("Tento tovar si práve prezerá");
                                pocetTyzden = riadokZdrojovehoKodu.substring(zaciatok, koniec-1);
//                                System.out.println("hned zranka "+pocetTyzden);
                            }
                        if (riadokZdrojovehoKodu.contains("'name':")) {
                            int koniec = riadokZdrojovehoKodu.indexOf("',");
                            name = riadokZdrojovehoKodu.substring(9, koniec);
                        }
                        if (riadokZdrojovehoKodu.contains("'category':")) {
                            int koniec = riadokZdrojovehoKodu.indexOf("',");
                            category = riadokZdrojovehoKodu.substring(13, koniec);
                        }
                        if (riadokZdrojovehoKodu.contains("'price':")) {
                            int koniec = riadokZdrojovehoKodu.indexOf("})");
                            price = riadokZdrojovehoKodu.substring(10, koniec-1);
                        }
                        }
                        catch (StringIndexOutOfBoundsException siobe ) {
                            System.out.println("zle cosi");
                        }
                    }

                    System.out.println(category + ";" + name + ";" + price + ";" + pocetTyzden);
                    writerSubor.println(category+";"+ name+";"+price+";"+ pocetTyzden);
                    Thread.sleep(1000);
                }
            }
        }
        writerSubor.close();
        return zoznamVysledok;
    }


}
