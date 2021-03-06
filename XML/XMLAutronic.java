package XML;
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
 public  class XMLAutronic {
	public static ArrayList<Produkt> zapisProduktov() throws IOException{
		ArrayList<Produkt>autroProdukty=new ArrayList<>();
		ArrayList<Produkt>autroAkciaCena=new ArrayList<>();
		ArrayList<Produkt>autroAkciaKategoria=new ArrayList<>();
		ArrayList<Produkt>prestaIDlist;
		int i, p, vahaInt = 1;
		String vyrobca = "AUTRONIC", pomocnaPresta,prestaID = null, code = null, name = null, dostupnost = "nikde", dostupnostXML = null, color,
				category = null, active, nameUpravene = null, size = null, vaha = null, IMGURL = null,rozmer = null,objem = null,
				description = null, priceString = null, priceXML= null, vyska = null, sirka = null, hlbka = null, dlzka = null, priceVOC = null;

		prestaIDlist = Nacitanie_metod.prestaIDPremenne;

//zapis XMLAutronic do suboru, prva cast kodu najde posledny modifikovany subor a vrati o jedno vyssie cislo, na konci suboru
		String fileFinding = ("autronic_produkty");
		int fileNumber = Premenne.findLastModified (fileFinding, "XML\\autronic");
		PrintWriter writerSubor = new PrintWriter(Premenne.cesta+"XML\\autronic\\"+fileFinding+fileNumber+".csv", "UTF-8");
		writerSubor.println("PrestaID;Kod;Nazov;Farba;Aktivita;VOC;MOC;Zasoba;Dostupnost;Skupina;IMGURL;Vaha");

// vycucnutie produktov z XML feedu a ich zapis do arraylistu
// BEZ OHLADU NA SKLAD ******
		System.out.println("Vytvaram Autronic - zaciatok");
		try {
			URL url = new URL("https://www.artium.sk/exportvelkoobchodnicenik2.ashx");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(url.openStream());
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("Zbozi");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				IMGURL = "";
				category = "original";
				description = "popis";
				active = "1";
				vaha = "neurčená";
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					code = (eElement.getElementsByTagName("MatID").item(0).getTextContent()).trim();
					name = (eElement.getElementsByTagName("Nazev").item(0).getTextContent()).trim();
					NodeList categoryMain = eElement.getElementsByTagName("SkupinaZbozi");
					for (int k=0; k<categoryMain.getLength(); k++) {
						Node stockNodeCategory = categoryMain.item(k);
						if (stockNodeCategory.getNodeType() == Node.ELEMENT_NODE) {
							Element stockElement = (Element) stockNodeCategory;
							category = stockElement.getAttribute("SkupinaZboziID");
						}
					}

					rozmer = eElement.getElementsByTagName("Rozmer").item(0).getTextContent();
//					objem = eElement.getElementsByTagName("Objem").item(0).getTextContent();
					priceXML = (eElement.getElementsByTagName("DoporucenaCenaSDph").item(0).getTextContent()).trim();
					dostupnostXML = (eElement.getElementsByTagName("Dostupnost").item(0).getTextContent()).trim();
					if (eElement.getElementsByTagName("Hmotnost").getLength()>0) {
						vaha = eElement.getElementsByTagName("Hmotnost").item(0).getTextContent();
						vaha = vaha.replaceAll(",", ".");
						vahaInt = (int) Double.parseDouble(vaha);
						if (vahaInt == 0) vahaInt=1;
						vaha = 	String.valueOf(vahaInt);
					}

//					System.out.println("kod:"+code+";category:"+category);

// DOSTUPNOST
					String dostupnostModified = "";
					NodeList stockList = eElement.getElementsByTagName("DostupnostSkladem");
					for (int j = 0; j < stockList.getLength(); j++) {
						Node stockNode = stockList.item(j);
						if (stockNode.getNodeType() == Node.ELEMENT_NODE) {
							Element stockElement = (Element) stockNode;
							String skladXML = stockElement.getAttribute("Sklad");
							String dostupnostXMLSklady = stockElement.getAttribute("Dostupnost").toString();
							if (!skladXML.contains("Senec")) {
								if (skladXML.contains("CZ")) {
									if ((dostupnostXMLSklady.contains("klad"))||(dostupnostXMLSklady.contains("vailab"))||(dostupnostXML.contains("SKL"))) {
										dostupnost = Premenne.feature2tyzdne;
									} else {
										dostupnost = ("nikdeeeee");
										active = "0";
									}
								}

								if (skladXML.contains("oruba")) {
									active = "1";
									dostupnostXMLSklady = stockElement.getAttribute("Dostupnost").toString();
									StringBuilder sb = new StringBuilder();
									for (int k = 0; k < dostupnostXMLSklady.length(); k++) {
										int asci = dostupnostXMLSklady.charAt(k);
										if (asci < 127) {
											if (dostupnostXMLSklady.charAt(k) == ',') {
												sb.append('.');
											} else
												sb.append(dostupnostXMLSklady.charAt(k));
										}
									}
									dostupnostModified = sb.toString();
									double dostupnostPoruba = Met_Convert.stringToDouble(dostupnostModified);
									if (dostupnostPoruba >= 2) {
										dostupnost = Premenne.featureOzajSKL; //ORIGINAL pred vianocami
									} else if (dostupnost.contains("nikde")) {
										active = "0";
									}
								}
							}
						}
					}

					description = eElement.getElementsByTagName("Popis").item(0).getTextContent();
					description = Met_NahradZleZnaky.spustima(description);
					//obrazok + dalsie obrazkycenaAutronicSedaky
					if (eElement.getElementsByTagName("ObrazekHi").getLength()>0)
						IMGURL = eElement.getElementsByTagName("ObrazekHi").item(0).getTextContent();
					NodeList obrazkyAllNode = eElement.getElementsByTagName("Obrazek");
					for (int ob=0; ob<obrazkyAllNode.getLength();ob++) {
						Node stockNodeCategory = obrazkyAllNode.item(ob);
						if (stockNodeCategory.getNodeType() == Node.ELEMENT_NODE) {
							Element stockElement = (Element) stockNodeCategory;
							if (ob>0)
								IMGURL+=", ";
							IMGURL +=stockElement.getAttribute("UrlHi");
						}
					}
//					System.out.println("kod:"+code+";obrazok:" + IMGURL);

					//vycucnutie Parametrov z jednotlivych child nodes. Vzdy si pozriem, aky nazov parametru bol a podla toho urcim,
					// do ktorej premennej to nacitat.
					NodeList listParametrov = eElement.getElementsByTagName("Parametr");
					for (int par=0; par<listParametrov.getLength(); par++) {
						String nodeNazov = null, nodeHodnota = "", pomocnyString = "nic";
						NodeList testovaciaList = listParametrov.item(par).getChildNodes();
						for (int child=0 ; child<testovaciaList.getLength() ; child++) {
							Node najnovsiaNode = testovaciaList.item(child);
							if (najnovsiaNode.getNodeType() == Node.ELEMENT_NODE) {
								Element stockElement = (Element) najnovsiaNode;
								nodeNazov = stockElement.getNodeName();
								nodeHodnota = stockElement.getTextContent();

								if (pomocnyString.contains("Výška (cm)"))
									vyska="Výška: "+nodeHodnota+" cm";
								if (pomocnyString.contains("Šířka (cm)"))
									sirka="Šírka: "+nodeHodnota+" cm";
								if (pomocnyString.contains("Hloubka (cm)"))
									hlbka="Hĺbka: "+nodeHodnota+" cm";
								pomocnyString = nodeHodnota;
							}
						}
//						if (nodeHodnota.contains("Výška (cm)"))
					}
				}

//nacitam PrestaID subor, z neho PrestaIDcka a Kategorie a to zapisem priamo do suboru
//ak nenajdem PrestaIDcko, tak mu dam PrestaID 123456
				pomocnaPresta = "0";
				for (p = 0; p < prestaIDlist.size(); p++) {
					if (prestaIDlist.get(p).getKod().equals(code)) {
						prestaID = prestaIDlist.get(p).getPrestaID();
						category = prestaIDlist.get(p).getSkupina();
						//ak je tovar v akcii, prida ku category este "Akcia Autronic", aby pri zmenach nevyhadzovalo tento produkt z Akcie
						autroAkciaKategoria = Met_AutAction.produkty("kategoria");
						for (i = 0; i < autroAkciaKategoria.size(); i++) {
							if (code.equals(autroAkciaKategoria.get(i).getKod())) {
								category = autroAkciaKategoria.get(i).getMOC(); //je hlupo oznacene v Produkt Classe, druhy String ako MOC
								break;
							}
						}
						priceString = prestaIDlist.get(p).getMOC().replace(",",".");
						name = prestaIDlist.get(p).getNazov();
						pomocnaPresta = "2";

						break;
					}
				}
				if (pomocnaPresta.equals("0")) {
					name = Met_NahradZleZnaky.spustima(name);
					prestaID = "123456";
					priceString = priceXML;
					category = Met_Category.zistiKategoriu(category, vyrobca, name, description);
//					category = "123456";
//										System.out.println("kod:"+code+";category:"+category);
				}
// ak sa zhodne kategoria alebo kod s vyhodenymi, tak tento vyrobok zmením na neaktívny, ak nie, pokracujem
				active = Met_Activity.zistiAktivitu(category, code, name, active,"AUTRONIC");

// ak sa zhodne cena s akciovou cenou, zmena na akciovu
				autroAkciaCena = Met_AutAction.produkty("cena");  //nacitanie akciovych produktov
//				autroPrice = Met_AutAction.ceny(autroAkcia);  //nacitanie akciovych cien
				for (i = 0; i < autroAkciaCena.size(); i++) {
//					String hladanyKod = autroPrice.get(i).getKod();
//					if (hladanyKod.equals(code)) {
					if (code.equals(autroAkciaCena.get(i).getKod())) {
						String hladanyPrice = String.valueOf(autroAkciaCena.get(i).getMOCBigDecimal());
						priceString = (hladanyPrice.replace(",",".")).trim();
						break;
					}
				}

//				if (prestaID.equals("123456")&&(!(active).equals("9")))  {
//					System.out.println("najdi description:"+code);
//					description = Met_Spy.description(code,"hejnabytok");
//				}
				color = Met_Color.zistiFarbu(code, vyrobca);  //z nazvu zisti farbu
				nameUpravene = Met_Name.zistiNazov(code, category, name, "AUTRONIC", color);
//				color = Premenne.complexReplace (Premenne.cesta+"Zoznam_farba.csv");

				autroProdukty.add(new Produkt(prestaID, category, category, code, dostupnost, "stock", priceString, "6666", name, nameUpravene,vyrobca, active, description,
						"productURL",IMGURL,"navod",vaha,objem,color,rozmer, sirka ,vyska, hlbka, ""));
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
		