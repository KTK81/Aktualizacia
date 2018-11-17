package Metody;
//import Metody.Met_Spy;
import Metody.*;
import Metody.Premenne;
//import Metody.Produkt;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class Met_Zostavy {
    public static void spustiProgram() throws Exception {
        ArrayList<Produkt> produktyALL = new ArrayList<>();

        ArrayList<Produkt> vyrobok = new ArrayList<>();
        vyrobok.add( new Produkt("2", "128"));
        vyrobok.add( new Produkt("3", "666"));
        vyrobok.add( new Produkt("4", "25"));
        vyrobok.add( new Produkt("5", "14"));

        String kody = vyrobok.get(0).getKod();

        vyrobok.add(new Produkt("vyrobcu", "adresu"));
        String vyrobca = vyrobok.get(0).getKod();

        vyrobok.add(new Produkt(" ","645 "," "," 654"));







        String test = Met_Name.zistiNazov("oko","dd","dd","dd","d");
        String oko = Met_Spy.description("kod","www");

        //NEFUNKCNY CLASS
    }


}
//108 riadkov