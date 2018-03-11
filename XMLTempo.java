import Metody.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;

//****** VYTVORI NOVY ARRAYLIST S KONKRETNYMI PRODUKTAMI - novy subor ******
public class XMLTempo {

    public static ArrayList<Produkt> zapisProduktov() throws IOException {
        ArrayList<Produkt> tempoProdukty = new ArrayList<>();
        ArrayList<Produkt> prestaIDlist;
        int i, p, vahaInt = 0;
        String pomocnaPresta, vahaString, prestaID = null;
        String vyrobca = "Tempo-Kondela", nameUpravene = null, sirka=null, vyska=null, hlbka=null, dlzka=null;
        prestaIDlist = Premenne.prestaIDPremenne;

//zapis XMLTempo do suboru, prva cast kodu najde posledny modifikovany subor a vrati o jedno vyssie cislo, na konci suboru
        String fileFinding = ("tempo_produkty");
        int fileNumber = Premenne.findLastModified(fileFinding, "XML\\tempo");
        PrintWriter writerSubor = new PrintWriter(Premenne.cesta + "XML\\tempo\\" + fileFinding + fileNumber + ".csv", "UTF-8");
        writerSubor.println("PrestaID;Kod;Nazov;Aktivita;VOC;MOC;Zasoba;Dostupnost;Skupina;Vaha;Navod");

// ********  pristup do XML feedu /zadanie mena hesla/, vycuc konkretnych udajov s ich zapisom do suboru ********
        String code = null, name = null, price = null, dostupnost = null, category = null, active, name_spatny_nazov,
                description = null, navod = null, vaha = null, productURL = null, objem = null, IMGURL = null, farba = null;
        System.out.println("Vytvaram Tempo - zaciatok");
        Authenticator.setDefault(new MyAuthenticator());

        try {
            URL url = new URL("http://shop.tempo-kondela.sk/Feed/feedsk.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(url.openStream());
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("PRODUKT");
            for (int temp = 0; temp < nList.getLength(); temp++) {
//				active = "1";
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    code = (eElement.getElementsByTagName("KOD_TOVARU").item(0).getTextContent()).trim();
                    name = eElement.getElementsByTagName("H1").item(0).getTextContent();
                    description = eElement.getElementsByTagName("POPIS_TOVARU").item(0).getTextContent();
                    category = eElement.getElementsByTagName("CENOVA_KATEGORIA").item(0).getTextContent();
                    dostupnost = (eElement.getElementsByTagName("DOSTUPNOST").item(0).getTextContent()).trim();
                    price = (eElement.getElementsByTagName("MOC_SK").item(0).getTextContent()).trim();
                    IMGURL = eElement.getElementsByTagName("URL_OBRAZOK").item(0).getTextContent();
                    navod = eElement.getElementsByTagName("URL_MONTAZNY_NAVOD").item(0).getTextContent();
                    productURL = eElement.getElementsByTagName("URL_PRODUKT").item(0).getTextContent();
                    vaha = eElement.getElementsByTagName("HMOTNOST").item(0).getTextContent();
                    vahaInt = (int) Double.parseDouble(vaha); //zaokruhli na cele cislo
                    if (vahaInt == 0) vaha = "neurčená"; //ak niektore vyrobky maju nulovu vahu, nahradi slovom neurcena
                    else
                        vaha = String.valueOf(vahaInt); //vsetky ostatne, ktore uz maju zadanu vahu, zapise do premennej vaha
                    name_spatny_nazov = (eElement.getElementsByTagName("NAZOV_POLOZKY").item(0).getTextContent()).trim();
                    objem = eElement.getElementsByTagName("OBJEM").item(0).getTextContent();
                }


// ak sa zhodne kategoria alebo nazov s vyhodenymi, tak tento vyrobok zmením na neaktívny, ak nie, pokracujem
                active = Met_Activity.zistiAktivitu(category, code, name, "", "Tempo-Kondela");

//nacitam PrestaID subor, z neho PrestaIDcka a Kategorie a to zapisem priamo do suboru
//ak nenajdem PrestaIDcko, tak mu dam PrestaID 123456
                pomocnaPresta = "0";
                for (p = 0; p < prestaIDlist.size(); p++) {
                    if (prestaIDlist.get(p).getKod().equals(code)) {
                        prestaID = prestaIDlist.get(p).getPrestaID();
                        category = prestaIDlist.get(p).getSkupina();
                        pomocnaPresta = "2";
                        if (dostupnost.equals("Na sklade"))
                            dostupnost = Premenne.featureSKL;
                        if (dostupnost.contains("vypreda") || dostupnost.equals("Na objednávku"))
                            active = "0";
                        break;
                    }
                }
                if (pomocnaPresta.equals("0")) {
                    prestaID = "123456";
                    category = Met_Category.zistiKategoriu(category, vyrobca, name, description);
                }


//mam trochu problemy s par produktami, hadze mi error, sa tu nebudem kaslat s 10 produktami, hodil som ich do active=9 a spravim to len pre aktivne...
                farba = Met_Color.zistiFarbu(name, vyrobca);
                description = Met_Description.zistiPopis(description, "Tempo-Kondela");
                nameUpravene = Met_Name.zistiNazov(name, "Tempo-Kondela", farba);
                if (!code.equals("0000192487")) {
                    ArrayList<Produkt> rozmery = Met_Dimensions.findDimensions(description, vyrobca);
                    sirka = rozmery.get(0).getSirka();
                    vyska = rozmery.get(0).getVyska();
                    hlbka = rozmery.get(0).getHlbka();
                    dlzka = rozmery.get(0).getDlzka();
                }


                tempoProdukty.add(new Produkt(prestaID, category, code, dostupnost, "stock", price, name, nameUpravene, vyrobca, active, description,
                        productURL, IMGURL, navod, vaha, objem, farba, "rozmer", sirka, hlbka, vyska, dlzka));
// Zapis produktov z XML do suboru
                writerSubor.println(prestaID + ";" + code + ";" + name + ";" + active + ";123456 ;" + price + ";null;" +
                        dostupnost + ";" + category + ";" + vaha + ";" + navod);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        writerSubor.close();
        System.out.println("Vytvaram Tempo - koniec");
        return tempoProdukty;
    }
}

//******** zadanie mena a hesla do tempo kondely ********
class MyAuthenticator extends Authenticator {
    protected PasswordAuthentication getPasswordAuthentication() {
// String promptString = getRequestingPrompt();
// String hostname = getRequestingHost();
// InetAddress ipaddr = getRequestingSite();
        String username = "kondelafeedvosk";
        String password = "Rsd3r89w";
        return new PasswordAuthentication(username, password.toCharArray());
    }
}