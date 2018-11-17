import XML.*;
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

public class DownPicAll {
    // stiahne VYBRANE /zo zoznamu/ obrazky z XML feedu - vsetky, nielen hlavny
    public static void zapisProduktov() throws IOException {
        ArrayList<Produkt> tempoProdukty = new ArrayList<Produkt>();
        ArrayList<String> zoznamKodov = new ArrayList<String>();
        ArrayList<Produkt> prestaIDlist;

        //vypol som autentificator, lebo hodilo error, ako som prehadzoval XML Tempo Class do XML Package
        //LENZE tym padom toto treba aj tak prerobit - uz mozem cucat XML tempo rovno zo suboru, uz ma pusti, tym padom nemusim 2x riesit nacitanie XML


        // PROSTE TO PREROB






        String prestaIDcko = null;

        PrintWriter zapisProduktov = new PrintWriter(Premenne.cesta + "dalsieObrazky.csv", "UTF-8");
        zapisProduktov.println("PrestaID;Kod;Meno;Kategoria;Zmaz obr;URL obrazok");
        tempoProdukty = XMLTempo.zapisProduktov();
        prestaIDlist = Premenne.prestaIDPremenne;

// ********  pristup do XML feedu /zadanie mena hesla/, vycuc konkretnych udajov s ich zapisom do suboru ********
//        Authenticator.setDefault(new MyAuthenticator());
        String code = null, obrazokURL = null, obrazky = null, name = null, category = null, adresar = null;

        File fileImport = new File(Premenne.cesta + "stiahniObrazok.txt");
        Scanner scannerNovy = new Scanner(fileImport);
        while (scannerNovy.hasNextLine()) {
            zoznamKodov.add(scannerNovy.nextLine());
        }

        for (int l=0; l<zoznamKodov.size();l++)
            System.out.println("kod, pozicia "+l+" : "+zoznamKodov.get(l));

        try {
            URL url = new URL("https://shop.tempo-kondela.sk/Feed/feedsk.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(url.openStream());
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("PRODUKT");
//            NodeList nList2 = doc.getElementsByTagName("OBRAZOK");
            for (int temp = 0; temp < nList.getLength(); temp++) {
//                System.out.println(temp + " of " + nList.getLength());
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    code = eElement.getElementsByTagName("KOD_TOVARU").item(0).getTextContent();
                    for (int k = 0; k < zoznamKodov.size(); k++) {
                        if (zoznamKodov.get(k).equals(code)) {
                            for (int j = 0; j < prestaIDlist.size(); j++) {
                                if (prestaIDlist.get(j).getKod().equals(code)) {
                                    prestaIDcko = prestaIDlist.get(j).getPrestaID();
                                    category = prestaIDlist.get(j).getSkupina();
                                    name = prestaIDlist.get(j).getNazov();
                                }
                            }

                            obrazokURL = (eElement.getElementsByTagName("URL_OBRAZOK").item(0).getTextContent());
//                            if (obrazokURL.contains(".jpg")) {
                                obrazky = obrazokURL;
//                            }

                            NodeList test = eElement.getElementsByTagName("OBRAZOK");
                            for (int so = 0; so < test.getLength(); so++) {
                                obrazokURL = (eElement.getElementsByTagName("OBRAZOK").item(so).getTextContent());
                                obrazky+= ", "+obrazokURL;
                            }

                            zapisProduktov.println(prestaIDcko + ";" + code + ";" + name + ";" + category + ";1;" + obrazky);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        zapisProduktov.close();
        System.out.println("Vytvaram Tempo - koniec");
    }
}
