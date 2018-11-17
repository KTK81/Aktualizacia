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
import java.util.ArrayList;

//****** VYTVORI NOVY ARRAYLIST S KONKRETNYMI PRODUKTAMI - novy subor ******
public class XMLDrevona2017 {

    public static ArrayList<Produkt> zapisProduktov() throws IOException {
        ArrayList<Produkt> drevonaProdukty = new ArrayList<>();
        ArrayList<Produkt> prestaIDlist;
//        ArrayList<Produkt> rozmery;
        int i, p, s;
        String pomocnaPresta, dostupnost, prestaID = null, sirka = null, vyska = null, dlzka = null, hlbka = null;
        String vyrobca = "DREVONA";
        prestaIDlist = Premenne.prestaIDPremenne;


//zapis XMLDrevona do suboru, prva cast kodu najde posledny modifikovany subor a vrati o jedno vyssie cislo, na konci suboru
        String fileFinding = ("drevona_produkty");
        int fileNumber = Premenne.findLastModified (fileFinding, "XML\\drevona");
        PrintWriter writerSubor = new PrintWriter(Premenne.cesta + "XML\\drevona\\" + fileFinding + fileNumber + ".csv", "UTF-8");
        writerSubor.println("PrestaID;Kod;Nazov povodny;Nazov upraveny;Farba;Popis;Aktivita;VOC;MOC;Zasoba;Dostupnost;Skupina");

// vycucnutie produktov z XML feedu a ich zapis do suboru
// BEZ OHLADU NA SKLAD ******
        System.out.println("Vytvaram Drevonu - zaciatok");
        String code = null, nameUpravene = null, name = null, price = null, priceVOC = null, stock = null, category = null,description = null,productURL = null,
                IMGURL = null, active, delivery = null, rozmer = null;

        try {
            String farba=null;
            URL url = new URL("http://www.rea-nabytok.sk/heurekaSK.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(url.openStream());
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("SHOPITEM");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    code = (eElement.getElementsByTagName("ITEM_ID").item(0).getTextContent()).trim();
                    name = (eElement.getElementsByTagName("PRODUCTNAME").item(0).getTextContent()).trim();
                    description = eElement.getElementsByTagName("DESCRIPTION").item(0).getTextContent();
                    productURL = eElement.getElementsByTagName("URL").item(0).getTextContent();
                    IMGURL = eElement.getElementsByTagName("IMGURL").item(0).getTextContent();
                    price = (eElement.getElementsByTagName("PRICE_VAT").item(0).getTextContent()).trim();
                    priceVOC = (eElement.getElementsByTagName("PRICE").item(0).getTextContent()).trim();
                    category = eElement.getElementsByTagName("CATEGORYTEXT").item(0).getTextContent();
                    delivery = eElement.getElementsByTagName("DELIVERY_DATE").item(0).getTextContent();
                    stock = (eElement.getElementsByTagName("AVAILABLE").item(0).getTextContent()).trim();
                }

//ak sa zhodne kategoria alebo nazov s vyhodenymi, tak tento vyrobok zmením na neaktívny, ak nie, pokracujem
                active = Met_Activity.zistiAktivitu(category, code, name, "","DREVONA");


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
                    category = Met_Category.zistiKategoriu(category, "DREVONA", "", "");
                }

//cena, pokial sa v niecom lisi od normal, XML ceny z feedu
                price = Met_Price.zistiCenu(price, code);

//povodne som tam mal cely text, ze "1 - 3 dni (skladom)", ale ani za boha sa to nevedelo sparovat s textom z PrestaID, tak som to zradikalizoval
                if (stock.equals("0") || stock.equals("1")) {
                    if (delivery.equals("4 tyždne")||delivery.equals("now"))
                        dostupnost = Premenne.feature4tyzdne; //2 - 4 týždne
                    else if (delivery.equals("4-6 tyždnov"))
                        dostupnost = Premenne.featureMesiac; // viac, ako mesiac
                    else if (delivery.equals("Do vypredania zásob")) {
                        dostupnost = Premenne.featureMesiac; // viac, ako mesiac
                        active = "0";
                    }
                    else
                        dostupnost = Premenne.featureMesiac; // viac, ako mesiac
                }
                else
                    dostupnost = Premenne.featureSKL; //1 - 3 dni (skladom)


//popis, cucam z XML
                description = Met_Description.zistiPopis(description, "DREVONA");


//bolo niekolko stoviek produktov s rovnakym kodom, cele rovnako, ale viackrat v XML, nerobilo to dobrotu, takze kontrola
//prebehnem arraylist s doteraz zadanymi kodmi a ak uz bol zadany, tak ignore
                String kontrolaDoubleKodu = "0";
                for (s = 0; s < drevonaProdukty.size(); s++) {
                    if (drevonaProdukty.get(s).getKod().equals(code))
                        kontrolaDoubleKodu = "5";
                }
                if (kontrolaDoubleKodu.equals("0")) {
                    // uprava nazvu a zaroven vycucnutie farby; niektore vyrobky maju inu tvorbu nazvov
                    if (!(name.contains("TEXT"))) {
//                        farba = Met_Color.zistiFarbu (name, "DREVONA");
                        farba = Met_Color.zistiFarbu(name, "DREVONA");
                        nameUpravene = Met_Name.zistiNazov(code, category, name, "DREVONA", description);

                        ArrayList<Produkt>rozmery = Met_Dimensions.findDimensions(description, vyrobca);
                        sirka = rozmery.get(0).getSirka();
                        vyska = rozmery.get(0).getVyska();
                        hlbka = rozmery.get(0).getHlbka();
                        dlzka = rozmery.get(0).getDlzka();

                    }

                    if (code.equals("C30634"))
                        System.out.println("XMLDrevona;"+code+";aktivita:"+active);

                    drevonaProdukty.add(new Produkt(prestaID, category, category, code, dostupnost, stock, price, priceVOC, name, nameUpravene, vyrobca, active, description,
                            productURL, IMGURL, "navod", "neurčená", "objem", farba, "rozmer", sirka, hlbka, vyska, dlzka));
// Zapis produktov z XML do suboru
                    writerSubor.println(prestaID + ";" + code + ";" + name + ";"+nameUpravene+";" + farba+";"+description + ";" + active + ";123456 ;"
                            + price + ";" + stock + ";" + dostupnost + ";" + category+";" + delivery + ";");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        writerSubor.close();
        System.out.println("Vytvaram Drevonu - koniec");
        return drevonaProdukty;
    }
}