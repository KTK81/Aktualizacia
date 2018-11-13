import Metody.Produkt;
import Metody.Premenne;
import Metody.PrestaIDRead;

import java.net.URL;
import java.util.ArrayList;
import java.io.*;

public class NoveProdukty {
	public static void spustiHladanie() throws Exception {
		ArrayList<Produkt> vyrobkyDrevona = new ArrayList<>();
		ArrayList<Produkt> vyrobkyTempo = new ArrayList<>();
		ArrayList<Produkt> vyrobkyAutronic = new ArrayList<>();
		ArrayList<Produkt> vyrobkyNellys = new ArrayList<>();
		ArrayList<Produkt> prestaSubor = new ArrayList<>();
		int s, p;
		String pomocnyHladanie,nahrada,produktURL = "0",pocetRiadkov = "0";

		PrintWriter writerVysledok = new PrintWriter(Premenne.cesta + "noveVyrobky.csv", "UTF-8");

		prestaSubor = Premenne.prestaIDPremenne;
//		vyrobkyDrevona = XMLDrevona2017.zapisProduktov();
//		vyrobkyTempo = XMLTempo.zapisProduktov();
//		vyrobkyAutronic = XMLAutronic.zapisProduktov();
		vyrobkyNellys = XMLNellys.zapisProduktov();

	  writerVysledok.println("Kod;Nazov;Nazov upraveny;Popis;Kategoria;Obrazok;Vyrobca;CenaNakup;CenaPredaj;Vaha;Short;Activ;Feature;Farba;Šírka;Hĺbka;Výška;Dĺžka;Výška sedu;Rozkladanie;Materiál;Nosnosť;Čalúnenie");

////DREVONA
//	  for(s=0; s < vyrobkyDrevona.size(); s++) {
////Selekcia produktov, ktore nechcem
//		  if (!(vyrobkyDrevona.get(s).getNazov().contains("REA ALFA") || vyrobkyDrevona.get(s).getNazov().contains("REA PLAY") ||
//				  vyrobkyDrevona.get(s).getNazov().contains("REA REBECCA") || vyrobkyDrevona.get(s).getNazov().contains("REA FLAT") ||
//				  vyrobkyDrevona.get(s).getNazov().contains("ČIELKO") || vyrobkyDrevona.get(s).getNazov().contains("ČELO") ||
//				  vyrobkyDrevona.get(s).getNazov().contains("SEDAK") ||
//				  vyrobkyDrevona.get(s).getPopis().contains("navyše do ") ||(vyrobkyDrevona.get(s).getAktivita().contains("9")))) {
//			  pomocnyHladanie = "0";
//			  for (p = 0; p < prestaSubor.size(); p++) {
//				  if (prestaSubor.get(p).getKod().equals(vyrobkyDrevona.get(s).getKod())) {  //ak najdem zhodny kod v sucasnych vyrobkoch, ide sa dalej
//					  pomocnyHladanie = "2";
//					  break;
//				  }
//			  }
//			  if (pomocnyHladanie.equals("0")) {
//				  writerVysledok.println(vyrobkyDrevona.get(s).getVysledokNove());
//			  }
//		  }
//	  }
//		System.out.println("DREVONA hotovo");
////TEMPO
	  for(s=0; s < vyrobkyTempo.size(); s++) {
		  if (!(vyrobkyTempo.get(s).getAktivita().contains("9"))) {
			  pomocnyHladanie = "0";
			  for (p = 0; p < prestaSubor.size(); p++) {
				  if (prestaSubor.get(p).getKod().equals(vyrobkyTempo.get(s).getKod())) {
					  pomocnyHladanie = "2";
					  break;
				  }
			  }
			  if (pomocnyHladanie.equals("0"))
				  writerVysledok.println(vyrobkyTempo.get(s).getVysledokNove());
		  }
	  }
		System.out.println("TEMPO hotovo");
//AUTRONIC
	for(s=0; s < vyrobkyAutronic.size(); s++) {
		if (!(vyrobkyAutronic.get(s).getAktivita().contains("9"))) {
			pomocnyHladanie = "0";
			for (p = 0; p < prestaSubor.size(); p++) {
				if (prestaSubor.get(p).getKod().equals(vyrobkyAutronic.get(s).getKod())) {
					pomocnyHladanie = "2";
					break;
				}
			}
			if (pomocnyHladanie == "0") {
				writerVysledok.println(vyrobkyAutronic.get(s).getVysledokNove());
			}
		}
	}
			System.out.println("AUTRONIC hotovo");

//NELLYS
		for(s=0; s < vyrobkyNellys.size(); s++) {
			if (!(vyrobkyNellys.get(s).getAktivita().contains("9"))) {
				pomocnyHladanie = "0";
				for (p = 0; p < prestaSubor.size(); p++) {
					if (prestaSubor.get(p).getKod().equals(vyrobkyNellys.get(s).getKod())) {
						pomocnyHladanie = "2";
						break;
					}
				}
				if (pomocnyHladanie == "0") {
					writerVysledok.println(vyrobkyNellys.get(s).getVysledokNove());
				}
			}
		}
		System.out.println("NELLYS hotovo");
	    	writerVysledok.close();
	}
}