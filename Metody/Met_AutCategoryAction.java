package Metody;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Met_AutCategoryAction {
    //nacita zoznam akciovych produktov, kody vyrobkov plus akciove ceny a z
    public static String akciovaCategory (String kod, String categoryPovodna) throws IOException {
        String akciaCategory = null;
        BigDecimal priceAkcia;
        ArrayList<Produkt>autroAkcia = new ArrayList<>();
        //nacitanie akciovych produktov
        FileInputStream finAkcia = new FileInputStream(Premenne.cestaZoznam+"autronic_akcia.csv");
        BufferedReader citacAkcia = new BufferedReader(new InputStreamReader(finAkcia, "UTF-8"));
        String riadokAkcia = citacAkcia.readLine();
        while (riadokAkcia != null) {
            riadokAkcia = citacAkcia.readLine();
            if (riadokAkcia != null) {
                String priceAkciaString=null;

                int bodkociarka = riadokAkcia.indexOf(";");
                int bodkociarka2 = riadokAkcia.indexOf(";", bodkociarka+1);
                int bodkociarka3 = riadokAkcia.indexOf(";", bodkociarka2+1);
                int bodkociarka4 = riadokAkcia.indexOf(";", bodkociarka3+1);
                String codeAkcia = riadokAkcia.substring(bodkociarka+1, bodkociarka2);
//                priceAkciaString = ((riadokAkcia.substring(bodkociarka3 + 1, bodkociarka4)).replace(",", "."));
                priceAkciaString = ((riadokAkcia.substring(bodkociarka3 + 1, bodkociarka4)));
                if (priceAkciaString.contains(",")) {
                    priceAkciaString = priceAkciaString.replace(",", ".");
                }
                priceAkciaString.trim();
//                System.out.print("kod:"+codeAkcia);
//                System.out.println(";cena:"+priceAkciaString);

                priceAkcia = new BigDecimal(priceAkciaString);
                priceAkcia = priceAkcia.setScale(2);
                autroAkcia.add (new Produkt(codeAkcia,priceAkcia));
            }
        }
        citacAkcia.close();
        return akciaCategory;
    }
}
