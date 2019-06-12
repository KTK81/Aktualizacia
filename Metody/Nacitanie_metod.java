package Metody;
import XML.*;
//import XML.XMLAutronic;
import XML.XMLTempo;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Nacitanie_metod {

    public static ArrayList<Produkt> prestaIDPremenne;

    static {
        try {
            System.out.println("*** Nacitanie PrestaID");
            prestaIDPremenne = PrestaIDRead.filePresta();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Produkt> XMLTempoPremenne;
    static {
        try {
            System.out.println("*** Nacitanie XML Tempo");
            XMLTempoPremenne = XMLTempo.zapisProduktov();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Produkt> XMLAutronicPremenne;
    static {
        try {
            System.out.println("*** Nacitanie XML Autronic");
            XMLAutronicPremenne = XMLAutronic.zapisProduktov();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Produkt> XMLDrevonaPremenne;
    static {
        try {
            System.out.println("*** Nacitanie XML Drevona");
            XMLDrevonaPremenne = XMLDrevona2017.zapisProduktov();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Produkt> XMLNellysNacitanie;
    static {
        try {
            System.out.println("*** Nacitanie XML NELLYS");
            XMLNellysNacitanie = XMLNellys.zapisProduktov();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
