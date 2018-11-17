package XML;
import Metody.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;


//****** VYTVORI NOVY ARRAYLIST S KONKRETNYMI PRODUKTAMI - novy subor ******
public class XMLNellys {

    public static ArrayList<Produkt> zapisProduktov() throws IOException {
        ArrayList<Produkt> nellysProdukty = new ArrayList<>();
        ArrayList<Produkt> prestaIDlist;
        int p;
        String pomocnaPresta, dostupnost = null, prestaID = null, sirka = null, vyska = null, dlzka = null, hlbka = null;
        String vyrobca = "NELLYS";
        prestaIDlist = Premenne.prestaIDPremenne;

//zapis XMLNellys do suboru, prva cast kodu najde posledny modifikovany subor a vrati o jedno vyssie cislo, na konci suboru
        String fileFinding = ("nellys_produkty");
        int fileNumber = Premenne.findLastModified(fileFinding, "XML\\nellys");
        PrintWriter writerSubor = new PrintWriter(Premenne.cesta + "XML\\nellys\\" + fileFinding + fileNumber + ".csv", "UTF-8");
        writerSubor.println("PrestaID;Kod;Nazov povodny;Nazov upraveny;Farba;Popis;Aktivita;VOC;MOC;Zasoba;Dostupnost;Skupina");

// vycucnutie produktov z XML feedu a ich zapis do suboru
// BEZ OHLADU NA SKLAD ******
        System.out.println("Vytvaram Nellys - zaciatok");
        String code = null, nameUpravene = null, name = null, price = null, priceVOC = null, stock = null, category = null, description = null, productURL = null,
                IMGURL = null, active = null, delivery = null;

        try {
            String farba = null;
//            URL url = new URL("https://www.nellys.sk/files/exports/movix.xml");
            URL url = new URL("https://www.nellys.sk/files/exports/movix_stock.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(url.openStream());
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("ITEM");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                IMGURL = "";
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    name = (eElement.getElementsByTagName("NAME").item(0).getTextContent()).trim();
                    code = (eElement.getElementsByTagName("CODE").item(0).getTextContent()).trim();
                    productURL = eElement.getElementsByTagName("URL").item(0).getTextContent();
                    //cena je v elemente PRICES_VAT, kde su childNody PRICE_VAT, ten, ktory ma atribut  level="2" nas zaujima a hodnota tej Nody
                    NodeList priceVOCzoznam = (eElement.getElementsByTagName("PRICE_VAT"));
                    for (int pr=0; pr<priceVOCzoznam.getLength(); pr++) {
                        Node priceJedenElement = priceVOCzoznam.item(pr);
                        if (priceJedenElement.getNodeType() == Node.ELEMENT_NODE) {
                            Element priceElement = (Element) priceJedenElement;
                            if (priceElement.getAttribute("level").contains("2")) {
                                priceVOC = priceElement.getTextContent();
                            }
                        }
                    }
                    //cena. Vieme velkoobchodnu cenu, to je priceVOC, no a chceme si pridat 30%, lebo maloobchodnu cenu nevieme
                    Double priceDouble = Double.parseDouble(priceVOC);
                    priceDouble = priceDouble*1.3;
                    priceDouble = (double)Math.round(priceDouble * 100d) / 100d;
                    price = priceDouble.toString();
                    delivery = eElement.getElementsByTagName("DELIVERY_TIME").item(0).getTextContent();
                    stock = (eElement.getElementsByTagName("AVAILABLE").item(0).getTextContent()).trim();
                    //obrazkov moze byt viacej, takze pekne cez NodeList
                    NodeList IMGzoznam = eElement.getElementsByTagName("IMAGE");
                    for (int z=0; z<IMGzoznam.getLength(); z++) {
                        if (z > 0)
                            IMGURL += ", ";
                        IMGURL += eElement.getElementsByTagName("IMAGE").item(z).getTextContent();
                    }
                    category = eElement.getElementsByTagName("CATEGORY_SHORT").item(0).getTextContent();
                    farba = eElement.getElementsByTagName("PEREX").item(0).getTextContent();
                    description = eElement.getElementsByTagName("DESCRIPTION").item(0).getTextContent();
                    description = Met_Description.zistiPopis(description, "NELLYS");
                    active = "1";
                }

//zaujimaju ma z celej ponuky niekolko tisic vyrobkov len pohovky a kresielka, takze obmedzim sa len na tie
                if ((category.contains("křesílka"))||(category.contains("pohovky"))) {

//nacitam PrestaID subor, z neho PrestaIDcka a Kategorie a to zapisem priamo do suboru
//ak nenajdem PrestaIDcko, tak mu dam PrestaID 123456
                    pomocnaPresta = "0";
                    for (p = 0; p < prestaIDlist.size(); p++) {
                        if (prestaIDlist.get(p).getKod().equals(code)) {
                            prestaID = prestaIDlist.get(p).getPrestaID();
                            category = prestaIDlist.get(p).getSkupina();
                            pomocnaPresta = "2";
                            break;
                        }
                    }
                    if (pomocnaPresta.equals("0")) {
                        prestaID = "123456";
//                    category = Met_Category.zistiKategoriu(category, "NELLYS", "", "");
                    }

//cena, pokial sa v niecom lisi od normal, XML ceny z feedu
//                price = Met_Price.zistiCenu(price, code);

                    if (stock.equals("0")) {
                        if (delivery.equals("Skladom"))
                            dostupnost = Premenne.featureMesiac; //skladom
                    } else
                        dostupnost = Premenne.featureSKL; //1 - 3 dni (skladom)

                ArrayList<Produkt> rozmery = Met_Dimensions.findDimensions(description, vyrobca);
                sirka = rozmery.get(0).getSirka();
                vyska = rozmery.get(0).getVyska();
                hlbka = rozmery.get(0).getHlbka();
                dlzka = rozmery.get(0).getDlzka();

                    farba = Met_Color.zistiFarbu(farba,"NELLYS");
                    category = Met_Category.zistiKategoriu(category,"NELLYS","","");
//                    description ="popis"; //vycucava popis v HTML kode a chaosi z toho openOffice. Tych par vyrobkov nahadzem popis rucne.

//                    System.out.println(code+";"+description);
                    nellysProdukty.add(new Produkt(prestaID, category, category, code, dostupnost, stock, price, priceVOC, name, name, vyrobca, active, description,
                            productURL, IMGURL, "navod", "neurčená", "objem", farba, "rozmer", sirka, hlbka, vyska, dlzka));
// Zapis produktov z XML do suboru
                    writerSubor.println(prestaID + ";" + code + ";" + name + ";" + name + ";" + farba + ";" + description + ";" + active + ";123456 ;"
                            + price + ";" + stock + ";" + dostupnost + ";" + category + ";" + delivery + ";");
                }
        }
        }
         catch (Exception e) {
            e.printStackTrace();
        }
        writerSubor.close();
        System.out.println("Vytvaram NELLYS - koniec");
        return nellysProdukty;
    }
}