package Metody;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Met_AutAction {
    //nacita zoznam akciovych produktov, kody vyrobkov
    public static ArrayList<Produkt> produkty () throws IOException {
        BigDecimal priceAkcia;
        ArrayList<Produkt>autroAkcia = new ArrayList<>();
        //nacitanie akciovych produktov
        FileInputStream finAkcia = new FileInputStream(Premenne.cestaZoznam+"autronic_akcia.csv");
        BufferedReader citacAkcia = new BufferedReader(new InputStreamReader(finAkcia, "UTF-8"));
        String riadokAkcia = citacAkcia.readLine();
        while (riadokAkcia != null) {
            riadokAkcia = citacAkcia.readLine();
            if (riadokAkcia != null) {
                int bodkociarka = riadokAkcia.indexOf(";");
                int bodkociarka2 = riadokAkcia.indexOf(";", bodkociarka+1);
                int bodkociarka3 = riadokAkcia.indexOf(";", bodkociarka2+1);
                int bodkociarka4 = riadokAkcia.indexOf(";", bodkociarka3+1);
                String codeAkcia = riadokAkcia.substring(bodkociarka+1, bodkociarka2);
                String priceAkciaString = ((riadokAkcia.substring(bodkociarka3+1, bodkociarka4)).replace(",",".")).trim();
                priceAkcia = new BigDecimal(priceAkciaString);
                priceAkcia = priceAkcia.setScale(2);
                autroAkcia.add (new Produkt(codeAkcia,priceAkcia));
            }
        }
        citacAkcia.close();
        return autroAkcia;
    }

    public static ArrayList<Produkt> ceny (ArrayList<Produkt> autroAkcia) throws IOException {
        //nacita zoznam akciovych produktov, ceny vyrobkov
        BigDecimal price = null;
        ArrayList<Produkt>autroPrice = new ArrayList<>();
        FileInputStream fin = new FileInputStream(Premenne.cestaZoznam+"autronic_cennik.txt");
        BufferedReader citac = new BufferedReader(new InputStreamReader(fin, "UTF-8"));
        String riadok = citac.readLine();
        while (riadok != null) {
            riadok = citac.readLine();
            if (riadok != null) {
                int bodkociarka = riadok.indexOf(";");
                String codePrice = riadok.substring(0, bodkociarka);
                String priceTemp = ((riadok.substring(bodkociarka+1)).replace(",",".")).trim();
                price = new BigDecimal(priceTemp);

                // pozrie na akciove produkty a ak najde, priradi do "price" akciovu cenu, tak ju potom nevyhodi ako rozdielnu, pri konecnom porovnavani
                for (int j=0; j<autroAkcia.size();j++) {
                    if (autroAkcia.get(j).getKod().equals(codePrice))
                        price = autroAkcia.get(j).getMOCBigDecimal();
                }
                autroPrice.add (new Produkt(codePrice,price));
            }
        }
        citac.close();
        return autroPrice;
    }




}
