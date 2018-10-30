import Metody.Premenne;
import Metody.Produkt;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.Authenticator;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class PomocnyClassHocico {
    //HOCIJAKE veci, zmeny produktov, vycucy, daco
    public static void zapisProduktov() throws IOException {
        ArrayList<Produkt> tempoProdukty = new ArrayList<Produkt>();
        ArrayList<Produkt> prestaIDlist;
        tempoProdukty = XMLTempo.zapisProduktov();
        prestaIDlist = Premenne.prestaIDPremenne;
        String prestaIDcko, code, name, nameAktualne, nameUpravene, kategoria;

        PrintWriter zapisVysledku = new PrintWriter(Premenne.cesta + "pomocnySuborVysledok.csv", "UTF-8");
        zapisVysledku.println("PrestaID;Kod;Meno;Meno Upravene;Meno Aktualne;Kategoria");

        for (int i=0; i<prestaIDlist.size();i++) {
            for (int j=0; j<tempoProdukty.size();j++) {
                if ((prestaIDlist.get(i).getKod().equals(tempoProdukty.get(j).getKod()))&&(prestaIDlist.get(i).getAktivita().equals("1"))) {
                    prestaIDcko=prestaIDlist.get(i).getPrestaID();
                    code=prestaIDlist.get(i).getKod();
                    name=tempoProdukty.get(j).getNazov();
                    nameUpravene=tempoProdukty.get(j).getNazovNovy();
                    nameAktualne=prestaIDlist.get(i).getNazov();
                    kategoria=prestaIDlist.get(i).getSkupina();
//                    System.out.println("prestaID:"+prestaIDcko+";kod:"+code+";name:"+name+";nameUpravene:"+nameUpravene+";kategoria:"+kategoria);
                    zapisVysledku.println(prestaIDcko+";"+code+";"+name+";"+nameUpravene+";"+nameAktualne+";"+kategoria);
                }
            }
        }
        zapisVysledku.close();
    }
}
