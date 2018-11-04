import Metody.*;
//import Metody.Met_Color;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

//****** VYTVORI NOVY ARRAYLIST S KONKRETNYMI PRODUKTAMI - novy subor ******  
 class XMLAutronic {
	public static ArrayList<Produkt> zapisProduktov() throws IOException{
		ArrayList<Produkt>autroProdukty=new ArrayList<>();
		ArrayList<Produkt>autroPrice=new ArrayList<>();
		ArrayList<Produkt>autroAkcia=new ArrayList<>();
		ArrayList<Produkt>prestaIDlist;
		int i, s, p, vahaInt = 1;
		String vyrobca = "AUTRONIC";
		String pomocnaPresta,prestaID = null, code = null, name = null, dostupnost = "nikde", color, category = null,active,
				size = null, vaha = null, IMGURL = null,rozmer = null,objem = null,description = null, priceString = null, priceVOC = null;

		prestaIDlist = Premenne.prestaIDPremenne;

//zapis XMLAutronic do suboru, prva cast kodu najde posledny modifikovany subor a vrati o jedno vyssie cislo, na konci suboru
		String fileFinding = ("autronic_produkty");
		int fileNumber = Premenne.findLastModified (fileFinding, "XML\\autronic");
		PrintWriter writerSubor = new PrintWriter(Premenne.cesta+"XML\\autronic\\"+fileFinding+fileNumber+".csv", "UTF-8");
		writerSubor.println("PrestaID;Kod;Nazov;Farba;Aktivita;VOC;MOC;Zasoba;Dostupnost;Skupina;IMGURL;Vaha");



// vycucnutie produktov z XML feedu a ich zapis do arraylistu
// BEZ OHLADU NA SKLAD ******
		System.out.println("Vytvaram Autronic - zaciatok");
		try {
//            URL url = new URL("http://www.artium.cz/exportvelkoobchodnicenik.ashx");
			URL url = new URL("http://www.artium.sk/exportvelkoobchodnicenik2.ashx");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(url.openStream());
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("Zbozi");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				active = "1";
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//					vahaInt = 1;
					vaha = "neurčená";
					Element eElement = (Element) nNode;
					code = (eElement.getElementsByTagName("MatID").item(0).getTextContent()).trim();
					name = (eElement.getElementsByTagName("Nazev").item(0).getTextContent()).trim();
					category = eElement.getElementsByTagName("SkupinyZbozi").item(0).getTextContent();
					rozmer = eElement.getElementsByTagName("Rozmer").item(0).getTextContent();
//					objem = eElement.getElementsByTagName("Objem").item(0).getTextContent();
					if (eElement.getElementsByTagName("Hmotnost").getLength()>0) {
						vaha = eElement.getElementsByTagName("Hmotnost").item(0).getTextContent();
						vaha = vaha.replaceAll(",", ".");
						vahaInt = (int) Double.parseDouble(vaha);
						if (vahaInt == 0) vahaInt=1;
						vaha = 	String.valueOf(vahaInt);
					}
//					price = (eElement.getElementsByTagName("DoporucenaCenaSDph").item(0).getTextContent()).trim();

// DOSTUPNOST
					String dostupnostModified = "";
					NodeList stockList = eElement.getElementsByTagName("DostupnostSkladem");
					for (int j = 0; j < stockList.getLength(); j++) {
						Node stockNode = stockList.item(j);
						if (stockNode.getNodeType() == Node.ELEMENT_NODE) {
							Element stockElement = (Element) stockNode;
							String skladXML = stockElement.getAttribute("Sklad");
							String dostupnostXML = stockElement.getAttribute("Dostupnost").toString();
							if (!skladXML.contains("Senec")) {
								if (skladXML.contains("CZ")) {
									if (dostupnostXML.contains("klad")) {
										dostupnost = Premenne.feature2tyzdne;
									} else {
										dostupnost = ("nikde");
										active = "0";
									}
								}

								if (skladXML.contains("oruba")) {
									active = "1";
									dostupnostXML = stockElement.getAttribute("Dostupnost").toString();
									StringBuilder sb = new StringBuilder();
									for (int k = 0; k < dostupnostXML.length(); k++) {
										int asci = dostupnostXML.charAt(k);
										if (asci < 127) {
											if (dostupnostXML.charAt(k) == ',') {
												sb.append('.');
											} else
												sb.append(dostupnostXML.charAt(k));
										}
									}
									dostupnostModified = sb.toString();
									double dostupnostPoruba = 0;
									dostupnostPoruba = Double.parseDouble(dostupnostModified);
									if (dostupnostPoruba >= 3) {
										dostupnost = Premenne.featureSKL;
									} else if (dostupnost.contains("nikde")) {
										active = "0";
									}
								}
							}
						}
					}

					description = eElement.getElementsByTagName("Popis").item(0).getTextContent();
					if (eElement.getElementsByTagName("ObrazekHi").getLength()>0)
						IMGURL = eElement.getElementsByTagName("ObrazekHi").item(0).getTextContent();
				}

//nacitam PrestaID subor, z neho PrestaIDcka a Kategorie a to zapisem priamo do suboru
//ak nenajdem PrestaIDcko, tak mu dam PrestaID 123456
				pomocnaPresta = "0";
				for (p = 0; p < prestaIDlist.size(); p++) {
					if (prestaIDlist.get(p).getKod().equals(code)) {
						prestaID = prestaIDlist.get(p).getPrestaID();
						category = prestaIDlist.get(p).getSkupina();
						priceString = prestaIDlist.get(p).getMOC().replace(",",".");
						name = prestaIDlist.get(p).getNazov();
						pomocnaPresta = "2";

						break;
					}
				}
				if (pomocnaPresta.equals("0")) {
					prestaID = "123456";
					priceString = "6666";
					category = "123456";
				}
// ak sa zhodne kategoria alebo kod s vyhodenymi, tak tento vyrobok zmením na neaktívny, ak nie, pokracujem
				active = Met_Activity.zistiAktivitu(category, code, name, active,"AUTRONIC");

// ak sa zhodne cena s akciovou cenou, zmena na akciovu
				autroAkcia = Met_AutAction.produkty();  //nacitanie akciovych produktov
//				autroPrice = Met_AutAction.ceny(autroAkcia);  //nacitanie akciovych cien
				for (i = 0; i < autroAkcia.size(); i++) {
//					String hladanyKod = autroPrice.get(i).getKod();
//					if (hladanyKod.equals(code)) {
					if (code.equals(autroAkcia.get(i).getKod())) {
						String hladanyPrice = String.valueOf(autroAkcia.get(i).getMOCBigDecimal());
						priceString = (hladanyPrice.replace(",",".")).trim();
						break;
					}
				}

				color = Met_Color.zistiFarbu(name, vyrobca);  //z nazvu zisti farbu
//				color = Premenne.complexReplace (Premenne.cesta+"Zoznam_farba.csv");


				autroProdukty.add(new Produkt(prestaID, category, code, dostupnost, "stock", priceString, "6666", name, "novyNazov",vyrobca, active, "popissss",
						"productURL",IMGURL,"navod",vaha,objem,color,rozmer, "sirka","vyska","hlbka","dlzka"));
// Zapis produktov z XML do suboru
				writerSubor.println(prestaID+";"+code+";"+name+";"+color+";"+active+";123456 ;"+priceString+";"+size+";"+dostupnost+";"+category+";"+IMGURL+";"+vaha);
	    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		    	writerSubor.close();
		System.out.println("Vytvaram Autronic - koniec");
				return autroProdukty;
	}
}
		