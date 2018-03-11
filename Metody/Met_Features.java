package Metody;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

//vycucnutie Vlastnosti vyrobkov
public class Met_Features {

    //v subore featureKomplet.csv je sql vycuc vyrobkov a ich Vlastnosti z PrestaShopu. Vysledkom tejto metody je Arraylist, v ktorom
    //jednotlive vyrobky maju priradene ID Vlastnosti, nazov Vlastnosti /Farba, Hlbka, Vyska/ a jej hodnotu, bud text alebo cislo.
    // V hlavnom Classe teda zadam len kod vyrobku, nazov Vlastnosti a viem si zistit jej hodnotu
    public static ArrayList<Produkt> nacitajFeatureKomplet () throws IOException {
        ArrayList<Produkt> feat = new ArrayList<>();
        ArrayList<Produkt> featValueList = new ArrayList<>();
//cez metodu priradID() vytvorim arraylist so zoznamom jednotlivych Vlastnosti, ich ID a hodnotu
        featValueList = priradID();
        FileInputStream fin = new FileInputStream(Premenne.cesta+"featureKomplet.csv");
        BufferedReader citac = new BufferedReader(new InputStreamReader(fin, "UTF-8"));
        String riadok = citac.readLine();
        boolean pomocny = false;
//vycucnem jednotlive vyrobky a ich Vlastnost vo forme IDcka
        while (riadok != null) {
            riadok = citac.readLine();
            if (riadok != null) {
                int bodkociarka = riadok.indexOf(";");
                int bodkociarka2 = riadok.indexOf(";",bodkociarka+1);
                int bodkociarka3 = riadok.indexOf(";",bodkociarka2+1);
                int bodkociarka4 = riadok.indexOf(";",bodkociarka3+1);
                String prestaID = riadok.substring(1, bodkociarka-1);
                String productCode = riadok.substring(bodkociarka+2,bodkociarka2-1);
                String featName = riadok.substring(bodkociarka2+2,bodkociarka3-1);
                String featValue = riadok.substring(bodkociarka3+2,bodkociarka4-1);
//porovnam jednotlive vyrobky a ich Vlastnost vo forme IDcka s IDckami Vlastnosti zo suboru, kde je ich zoznam, s pripradenymi nasimi hodnotami
                for (int i=0;i<featValueList.size();i++) {
                    if (featValueList.get(i).getKod().equals(featValue)) {
//ak sa zhodne IDcko Vlastnosti zo zoznamu s hladanym IDckom, tak danej Vlastnosti priradim nasu hodnotu
//napr. farba hneda uz nebude mat IDcko 258, ale bude mat text "hneda"
                        pomocny = true;
                        featValue=featValueList.get(i).getMOC();
                        break;
                    }
                }
                if (!pomocny)
                    featValue = "ID"+featValue;
//ale ak dane IDcko nie je v nasom zozname, treba ho doplnit, no a zatial mu priradim povodne IDcko, s textom ID pred danou hodnotou, aby bolo jasne, ze sa nenaslo
//keby som mu len priradil povodne IDcko, mohlo by sa stat, ze pri ciselnych Vlastnostiach, hlbka, vyska, by sa to nemuselo ani vsimnut


                feat.add(new Produkt(productCode, featName, featValue));
            }
        }
        citac.close();
        return feat;
    }


//v subore feature_zoznam.csv je zoznam vlastnosti z Prestashopu. Ten ich vycucava vo forme IDciek, kazda Vlastnost ma svoje IDcko
    //no a v tomto subore je ku kazdemu IDcku priradena nasa hodnota, nazov, cislo. Tato metoda to vycucne do Arraylistu
    public static ArrayList<Produkt> priradID () throws IOException {
        ArrayList<Produkt> feat = new ArrayList<>();

                FileInputStream fin = new FileInputStream(Premenne.cestaZoznam+"feature_zoznam.csv");
                BufferedReader citac = new BufferedReader(new InputStreamReader(fin, "UTF-8"));
                String riadok = citac.readLine();
                while (riadok != null) {
                    riadok = citac.readLine();
                    if (riadok != null) {
                        int bodkociarka = riadok.indexOf(";");
                        int bodkociarka2 = riadok.indexOf(";",bodkociarka+1);
//                        int bodkociarka3 = riadok.indexOf(";",bodkociarka2+1);
                        String nameFeat = riadok.substring(0, bodkociarka);
                        String IDFeat = riadok.substring(bodkociarka+1,bodkociarka2);
                        String valueFeat = riadok.substring(bodkociarka2+1);
//                        System.out.println(IDFeat+";"+valueFeat);
                        feat.add(new Produkt(IDFeat, valueFeat));
                    }
                }
                citac.close();
        return feat;
    }
}
