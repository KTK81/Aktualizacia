import com.sun.xml.internal.ws.api.ha.StickyFeature;

import java.io.*;
import java.util.ArrayList;
import Metody.Premenne;

/**
 * Created by jano on 21.02.2017.
 */
public class StatisticDrevona {
    public static void spustiVsetko () throws IOException {
        ArrayList<Produkt_dodavatel> polePomocne = new ArrayList<>();
        int drevona = 535;
        int drevonamax = 538;

        for (int i=drevona; i<=drevonamax; i++) {
            polePomocne = porovnanie(i,i-1);
            zapisStatistic(polePomocne, i);
        }
    }


    public static void zapisStatistic (ArrayList<Produkt_dodavatel> vstupnySubor, int pohyb) throws IOException {
        PrintWriter writerSubor = new PrintWriter(Premenne.cesta+"\\drevona\\pohyby_dr"+(pohyb)+".csv", "UTF-8");
        writerSubor.println("Presta ID;	Kod;Name;Aktiv;VOC;MOC;zasoby;dostup;skupina;pohyb;sucet;koef;Sumar");
        for (int i=0; i<vstupnySubor.size(); i++) {
    //        System.out.println(vstupnySubor.get(i).getKod()+";"+vstupnySubor.get(i).getKoeficient()+";"+vstupnySubor.get(i).getSumar());
            writerSubor.println(vstupnySubor.get(i).getCSV());
        }
        writerSubor.close();
    }

    public static ArrayList<Produkt_dodavatel> porovnanie (int drevonaSubor, int pohybySubor) throws IOException {
        ArrayList<Produkt_dodavatel> drevonaFile = new ArrayList<>();
        ArrayList<Produkt_dodavatel> pohybFile = new ArrayList<>();
        ArrayList<Produkt_dodavatel> noveVyrobky = new ArrayList<>();
        String nazovSuboru = Premenne.cesta + "\\drevona\\drevona_produkty" + drevonaSubor + ".csv";
        String nazovSuboru2 = Premenne.cesta + "\\drevona\\pohyby_dr" + pohybySubor + ".csv";

        drevonaFile = fileRead(nazovSuboru, false);
        pohybFile = fileRead(nazovSuboru2, true);

        int dlzkaArraylistu = pohybFile.size();
        for (int p = 0; p < dlzkaArraylistu; p++) {
            String sumarText = pohybFile.get(p).getSumar();
            String sumarTextNovy = "0;";
            pohybFile.get(p).setPohyb(0);
            for (int s = 0; s < drevonaFile.size(); s++) {
                if (pohybFile.get(p).getKod().equals(drevonaFile.get(s).getKod())) {
                    if (!(pohybFile.get(p).getZasobyint() == drevonaFile.get(s).getZasobyint())) {
                        int zasobaStara = pohybFile.get(p).getZasobyint();
                        int zasobaNova = drevonaFile.get(s).getZasobyint();
                        if (zasobaNova < zasobaStara) {
                            pohybFile.get(p).setZasobyint(zasobaNova);
                            int rozdiel = pohybFile.get(p).getSucet();
                            rozdiel += (zasobaStara - zasobaNova);
                            pohybFile.get(p).setSucet(rozdiel);
                            pohybFile.get(p).setPohyb(zasobaStara - zasobaNova);
                            sumarTextNovy = (Integer.toString(zasobaStara - zasobaNova)+";");
                         } else if (zasobaNova > zasobaStara) {
//                            System.out.println(pohybFile.get(p).getKod()+":stara:"+zasobaStara+";nova:"+zasobaNova);
                            pohybFile.get(p).setZasobyint(zasobaNova);
                            pohybFile.get(p).setPohyb(zasobaStara - zasobaNova);
                            sumarTextNovy = (Integer.toString(zasobaNova - zasobaStara)+";");
                        }
                    }
                }
                }
                String sumarTextNovyAdd = sumarTextNovy+sumarText;
            pohybFile.get(p).setSumar(sumarTextNovyAdd);
            if (pohybFile.get(p).getKod().contains("C27088"))
                System.out.println(("kod:"+pohybFile.get(p).getKod()+";Presta:"+pohybFile.get(p).getPrestaID()+";dostupnost:"+pohybFile.get(p).getDostupnost()));


           }
        for (int s = 0; s < drevonaFile.size(); s++) {
            boolean nenasloZhodu = true;
            for (int p = 0; p < dlzkaArraylistu; p++) {
                if (drevonaFile.get(s).getKod().equals(pohybFile.get(p).getKod())) {
                    nenasloZhodu = false;
                }
            }
            if (nenasloZhodu) {
//                System.out.println("nenasloZhodu:"+s+";"+drevonaFile.get(s).getKod());
                   noveVyrobky.add(new Produkt_dodavatel(drevonaFile.get(s).getPrestaID(),drevonaFile.get(s).getKod(),drevonaFile.get(s).getNazov(),
                           drevonaFile.get(s).getAktivita(),drevonaFile.get(s).getVOC(),drevonaFile.get(s).getMOCint(),drevonaFile.get(s).getZasobyint(),
                           drevonaFile.get(s).getDostupnost(),drevonaFile.get(s).getSkupina(),0,0,0,"Sumar=0"));

                    }

                }
        pohybFile.addAll(noveVyrobky);

            return pohybFile;
        }


     public static ArrayList<Produkt_dodavatel> fileRead(String fileName, boolean prepinac) throws IOException {
        ArrayList<Produkt_dodavatel> fileToReturn = new ArrayList<>();

         FileInputStream fis = null;
         BufferedReader reader = null;
         fis = new FileInputStream(fileName);
         reader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
         String hladany = reader.readLine();
         hladany = reader.readLine();

         while (hladany != null) {

             if (hladany != null) {

                 int bodkociarka1 = hladany.indexOf(";");
                 int bodkociarka2 = hladany.indexOf(";",bodkociarka1+1);
                 int bodkociarka3 = hladany.indexOf(";",bodkociarka2+1);
                 int bodkociarka4 = hladany.indexOf(";",bodkociarka3+1);
                 int bodkociarka5 = hladany.indexOf(";",bodkociarka4+1);
                 int bodkociarka6 = hladany.indexOf(";",bodkociarka5+1);
                 int bodkociarka7 = hladany.indexOf(";",bodkociarka6+1);
                 int bodkociarka8 = hladany.indexOf(";",bodkociarka7+1);
                 String idPresta = hladany.substring(0, bodkociarka1);
                 String kod = hladany.substring(bodkociarka1+1, bodkociarka2);
                 String name = hladany.substring(bodkociarka2+1, bodkociarka3);
                 String aktivny = hladany.substring(bodkociarka3+1, bodkociarka4);
                 String VOC = hladany.substring(bodkociarka4+1, bodkociarka5);
                 String MOC = (hladany.substring(bodkociarka5+1, bodkociarka6)).trim();
                 double MOCdouble = Double.parseDouble(MOC);
                 int MOCint = (int)MOCdouble;
                 String zasoby = hladany.substring(bodkociarka6+1, bodkociarka7);
                 double zasobyDouble = Double.parseDouble(zasoby);
                 int zasobyInt = (int)zasobyDouble;
                 String dostupnost = hladany.substring(bodkociarka7+1, bodkociarka8);
                 String kategoria = hladany.substring(bodkociarka8+1);

                 if (prepinac) {
                     int bodkociarka9 = hladany.indexOf(";",bodkociarka8+1);
                     int bodkociarka10 = hladany.indexOf(";",bodkociarka9+1);
                     int bodkociarka11 = hladany.indexOf(";",bodkociarka10+1);
                     kategoria = hladany.substring(bodkociarka8+1, bodkociarka9);
                     String pohyb = hladany.substring(bodkociarka9+1, bodkociarka10);
                     int pohybInt = Integer.parseInt(pohyb);
                     String sucet = hladany.substring(bodkociarka10+1, bodkociarka11);
                     int sucetInt = Integer.parseInt(sucet);
                     int koeficient  = MOCint*sucetInt;
                     String sumar = (hladany.substring(bodkociarka11+1)).trim();

     //                System.out.println("kod:"+kod+";koeficient:"+koeficient+";sumar:"+sumar);
                     fileToReturn.add(new Produkt_dodavatel(idPresta,kod, name, aktivny, VOC, MOCint,zasobyInt, dostupnost,kategoria,
                             pohybInt, sucetInt,koeficient, sumar));
                 }
                 else {
                     if (kod.contains("C27088"))
                         System.out.println(("kod:"+kod+";Presta:"+idPresta+";dostupnost:"+dostupnost));

                     fileToReturn.add(new Produkt_dodavatel(idPresta, kod, name, aktivny, VOC, MOCint, zasobyInt, dostupnost, kategoria));
                 }
             }
             hladany = reader.readLine();
         }
         reader.close();
         return fileToReturn;
    }
}

