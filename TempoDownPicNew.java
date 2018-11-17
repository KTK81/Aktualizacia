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

public class TempoDownPicNew {
    // stiahne VYBRANE /zo zoznamu/ obrazky z Tempo XML feedu ku aktivnym produktom - vsetky, nielen hlavny
    public static void zapisProduktov() throws IOException {
        ArrayList<Produkt> tempoProdukty = new ArrayList<Produkt>();
        ArrayList<String> zoznamKodov = new ArrayList<String>();

        String prestaIDcko = null;

        PrintWriter zapisProduktov = new PrintWriter(Premenne.cesta + "tempoObrazky.csv", "UTF-8");
        zapisProduktov.println("PrestaID;Kod;Meno;Kategoria;Meno obrazok;URL obrazok;Adresar disk");
        tempoProdukty = XMLTempo.zapisProduktov();

// ********  pristup do XML feedu /zadanie mena hesla/, vycuc konkretnych udajov s ich zapisom do suboru ********
//        Authenticator.setDefault(new MyAuthenticator());
        String code = null, obrazokURL = null, obrazky = null, name = null, obrazok = null, category = null, adresar = null;

        File fileImport = new File(Premenne.cestaZoznam + "stiahniObrazok.txt");
        Scanner scannerNovy = new Scanner(fileImport);
        while (scannerNovy.hasNextLine()) {
            zoznamKodov.add(scannerNovy.nextLine());
        }

//        for (int k=0; k<zoznamKodov.size();k++)
//            System.out.println(zoznamKodov.get(k));

        try {
            URL url = new URL("http://shop.tempo-kondela.sk/Feed/feedsk.xml");
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
                            ArrayList<String> pictureList = new ArrayList<String>();
                            for (int j = 0; j < tempoProdukty.size(); j++) {
                                if (tempoProdukty.get(j).getKod().equals(code)) {
                                    prestaIDcko = tempoProdukty.get(j).getPrestaID();
                                }
                            }

                            name = eElement.getElementsByTagName("NAZOV_POLOZKY").item(0).getTextContent();
                            category = eElement.getElementsByTagName("CENOVA_KATEGORIA").item(0).getTextContent();
//                            if (category.contains("Predsie")) adresar = "obr\\predsien\\";
//                            else if (category.contains("Drobn")) adresar = "obr\\doplnky\\";
//                            else if (category.contains("Kuchyne")) adresar = "obr\\kuchyna\\";
//                            else if (category.contains("Stoli")) adresar = "obr\\stolicky\\";
//                            else if (category.contains("Kancel")) adresar = "obr\\kancelaria\\";
//                            else if (category.contains("Komody")) adresar = "obr\\komody\\";
//                            else if (category.contains("Kúpeľ")) adresar = "obr\\kupelna\\";
//                            else if (category.contains("detske")) adresar = "obr\\detska\\";
//                            else if (category.contains("obýva")) adresar = "obr\\obyvacka\\";
//                            else if (category.contains("Pohovk")) adresar = "obr\\pohovky\\";
//                            else if (category.contains("Sedaci")) adresar = "obr\\sedacie\\";
//                            else if (category.contains("Spál")) adresar = "obr\\spalna\\";
//                            else adresar = "obr\\";
                            adresar = "obr\\";

                            obrazokURL = (eElement.getElementsByTagName("URL_OBRAZOK").item(0).getTextContent());
                            if (obrazokURL.contains(".jpg")) {
                                pictureList.add(obrazokURL);
//                                System.out.println("kod :" + code + "; URL :" + obrazokURL);
                            }

                            String obrazkyNaDisku = "cesta"+code+".jpg";
//                            zapisProduktov.println(prestaIDcko + ";" + code + ";" + name + ";" + category + ";" + code + ".jpg;" + obrazokURL + ";" + adresar);
                            NodeList test = eElement.getElementsByTagName("OBRAZOK");
                            for (int so = 0; so < test.getLength(); so++) {
                                obrazokURL = (eElement.getElementsByTagName("OBRAZOK").item(so).getTextContent());
                                pictureList.add(obrazokURL);
                                obrazkyNaDisku+=", cesta"+code + "-" + (so + 1) + ".jpg";
//                                zapisProduktov.println(prestaIDcko + ";" + code + ";" + name + ";" + category + ";" +
//                                        code + "-" + (so + 1) + ".jpg;" + obrazokURL + ";" + adresar);
                            }

                            zapisProduktov.println(prestaIDcko + ";" + code + ";" + name + ";" + category + ";" + obrazkyNaDisku + ";"+ obrazokURL + ";" + adresar);

//samotne stiahnutie obrazku

//                            for (int i = 0; i < pictureList.size(); i++) {
//                                System.out.println("stiahnutie ; " + i + " ;" + pictureList.get(i));
//                            }

//                for (int i = 0; i < pictureList.size(); i++) {
//                    String codeNew = code;
//                    if (i > 0) codeNew = code + "-" + i;
//                    System.out.println("Kod: " + codeNew + " ; " + pictureList.get(i));
//                    try (InputStream in = new URL(pictureList.get(i)).openStream()) {
//                        Files.copy(in, Paths.get(Premenne.cesta + adresar + codeNew + ".jpg"));
//                    } catch (FileAlreadyExistsException e) {
//                        System.out.println(pictureList.get(i) + " : UZ MAS");
//                    } catch (FileNotFoundException e) {
//                        System.out.println(pictureList.get(i) + " : NENASLO");
//                    }
////                    } catch (IOException e) {
//////                            throw new RuntimeException(e);
////                        System.out.println(pictureList.get(i) + " : SOMARINA");
////                    }
//
//                    Thread.sleep(500);
//                }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        zapisProduktov.close();
        System.out.println("Vytvaram Tempo - koniec");

        System.out.println("test");
    }
}
