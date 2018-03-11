package Metody;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Premenne {
    public static String prestaIDDownload = "C:\\Users\\jano\\Downloads\\";
    public static String cesta = "C:\\Users\\jano\\Disk Google\\JAVA\\subory\\";
//    public static String cestaXML = "C:\\Users\\Jan\\Disk Google\\JAVA\\subory\\XML\\";
    public static String cestaZoznam = "C:\\Users\\jano\\Disk Google\\JAVA\\subory\\zoznamy\\";
    public static String featureSKL = "1 - 3 dni (skladom)";
    public static String feature2tyzdne = "2 - 14 dní";
    public static String feature4tyzdne = "3 - 5 týždňov";
    public static String featureMesiac = "viac ako mesiac";

    public static ArrayList<Produkt> prestaIDPremenne;
    static {
        try {
            prestaIDPremenne = PrestaIDRead.filePresta();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm");
    private static LocalDateTime dateTime = LocalDateTime.now();
    public static String formattedDateTime = dateTime.format(formatter);


    //najde posledny modifikovany subor "filename" /ocakava sa, ze ma na konci cislo/ v zadanom adresari "folder"
// a vrati o jedno vyssie cislo - aby sa vytvoril novy subor s rovnakym menom, ale s cislom o jedno vyssim
    public static int findLastModified(String fileName, String folder)
    {
        File vysledok = Met_ZistiDatum.lastFileModified(Premenne.cesta + folder+"\\", fileName);
        String nazovSuboruString = vysledok.getName();
        int pocetPismenSuboru = fileName.length();
        int rightSubor = nazovSuboruString.indexOf(".csv");
        String ojednovyssiecislosuboruString = nazovSuboruString.substring(pocetPismenSuboru, rightSubor);
        int ojednovyssiecislosuboru = Integer.parseInt(ojednovyssiecislosuboruString);
        return ojednovyssiecislosuboru + 1;
    }

    //nacita do Arraylistu vsetky vyrobky, zo zadaneho jednoducheho suboru, len jeden stlpec udajov
    public static ArrayList<String> simpleFileRead(String fileName) throws FileNotFoundException {
        ArrayList<String> vyrobkySKL = new ArrayList<>();
        File fileNew = new File(fileName);
        Scanner scannerSKL = new Scanner(fileNew, "UTF-8");
        while (scannerSKL.hasNextLine()) {
            String pokus = (scannerSKL.nextLine()).trim();
            vyrobkySKL.add(pokus);
        }
        scannerSKL.close();
        return vyrobkySKL;
    }


//    rozne metody na nacitanie udajov z jednoduchych csv suborov, kde ak najdem prvy stlpec, poriesim ho s druhym stlpcom
    public static ArrayList<Produkt> complexReplace (String filename) throws IOException {
        ArrayList<Produkt> temp = new ArrayList<>();
        FileInputStream fin = new FileInputStream(filename);
        BufferedReader citac = new BufferedReader(new InputStreamReader(fin, "UTF-8"));
        String riadok = citac.readLine();
        while (riadok != null) {
            riadok = citac.readLine();
            if (riadok != null) {
                int bodkociarka = riadok.indexOf(";");
//                int bodkociarka2 = riadok.indexOf(";", bodkociarka + 1);
                String ID = riadok.substring(0, bodkociarka);
                String value = riadok.substring(bodkociarka+1);
                temp.add(new Produkt(ID,value));
//                if (original.contains(ID)) {
//                    temp = value;
//                }
//                if (filename.contains("meno")) {

                }

            }
        citac.close();
        return temp;
    }


}
