package Metody;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Met_Price {

    public static String zistiCenu(String priceOriginal, String code) throws IOException {
        String price = priceOriginal;
        ArrayList<Produkt> prestaIDlist;
        prestaIDlist = Premenne.prestaIDPremenne;

//nacitanie cenyOff - sucasnu cenu nahradi cenou nastavenou v subore cenaOff = cenou, aka by mala byt podla feedu
        FileInputStream fin = new FileInputStream(Premenne.cestaZoznam+"cena_off.txt");
        BufferedReader citac = new BufferedReader(new InputStreamReader(fin, "UTF-8"));
        String riadok = citac.readLine();
        while (riadok != null) {
            riadok = citac.readLine();
            if (riadok != null) {
                int bodkociarka = riadok.indexOf(";");
                int bodkociarka2 = riadok.indexOf(";",bodkociarka+1);
                String codeOff = riadok.substring(0, bodkociarka);
                String priceFeed = riadok.substring(bodkociarka+1, bodkociarka2);
                String priceActual = riadok.substring(bodkociarka2+1);
                if (codeOff.equals(code)) {
                    if ((price.contains(priceFeed))||(price.equals("0.00")))
                        price = priceActual;
                    else
                        price = "66666";
                }
            }
        }
        citac.close();
//rovno v tejto sekcii osetrim aj to, ked je cena vo Feede 0.00 - pozriem na staru cenu do PrestaID a nahradim ju touto starou cenou
        if (price.equals("0.00")) {
            for (int i=0; i<prestaIDlist.size(); i++) {
                if (prestaIDlist.get(i).getKod().equals(code))
                    price=prestaIDlist.get(i).getMOC();
            }
        }
        return price;
    }
}
