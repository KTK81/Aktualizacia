package Metody;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Met_Marcus {
//veci pre Marcus Trade
    public static ArrayList<Produkt> produkty () throws IOException {
        ArrayList<Produkt>dodavatelia = new ArrayList<>();
        FileInputStream suborOriginal = new FileInputStream(Premenne.cestaZoznam+"oberon skladove karty.csv");
        BufferedReader nacitajOriginalSubor = new BufferedReader(new InputStreamReader(suborOriginal, "UTF-8"));
        String riadokAkcia = nacitajOriginalSubor.readLine();
        int pocitadlo = 0;
        while (riadokAkcia != null) {
            pocitadlo+=1;
            riadokAkcia = nacitajOriginalSubor.readLine();
            if (riadokAkcia != null) {
//                String priceAkciaString=null;
                int bodkociarka = riadokAkcia.indexOf(";");
                int bodkociarka2 = riadokAkcia.indexOf(";", bodkociarka+1);
                int bodkociarka3 = riadokAkcia.indexOf(";", bodkociarka2+1);
                int bodkociarka4 = riadokAkcia.indexOf(";", bodkociarka3+1);
                int bodkociarka5 = riadokAkcia.indexOf(";", bodkociarka4+1);
                int bodkociarka6 = riadokAkcia.indexOf(";", bodkociarka5+1);
                int bodkociarka7 = riadokAkcia.indexOf(";", bodkociarka6+1);
                String karta = riadokAkcia.substring(0, bodkociarka);
                String nazov = riadokAkcia.substring(bodkociarka2+1, bodkociarka3);
                String dodavatel = riadokAkcia.substring(bodkociarka3+1, bodkociarka4);
                String mnozstvo = riadokAkcia.substring(bodkociarka4+1, bodkociarka5-1);
                String typ_ks = riadokAkcia.substring(bodkociarka5+1, bodkociarka6);
                String objednane = riadokAkcia.substring(bodkociarka6+1, bodkociarka7-4);
                int objednaneInt = Integer.parseInt(objednane);
                if (mnozstvo.contains(",")) {
                    mnozstvo = mnozstvo.replace(",", ".");
                }
                mnozstvo.trim();

                if (!(objednaneInt==0))
                    System.out.println("karta:"+karta+";nazov:"+nazov+";dodavatel:"+dodavatel+";mnozstvo"+mnozstvo+" "+typ_ks+";objednane:"+objednaneInt);


//                if (moznost.equals("kategoria")) {
//                    dodavatelia.add(new Produkt(karta, nazov));
//                }
            }
        }
        suborOriginal.close();
        return dodavatelia;
    }

}
