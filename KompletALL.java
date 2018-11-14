
import java.nio.file.*;
import java.util.ArrayList;
import java.io.*;
import Metody.Produkt;
import Metody.Premenne;
import Metody.PrestaIDRead;

public class KompletALL {
    public static void spustiProgram() throws Exception {
        ArrayList<Produkt> produktyALL = new ArrayList<>();
        ArrayList<ArrayList<Produkt>> docasnyArrayList = new ArrayList<>();
        ArrayList<Produkt> prestaNase;
        String pomocnyAktivity;

        PrintWriter writerVysledokPublicNew = new PrintWriter(Premenne.cesta + "aktualizacia_new.csv", "UTF-8");

        produktyALL.addAll(XMLTempo.zapisProduktov());
        produktyALL.addAll(XMLDrevona2017.zapisProduktov());
//        produktyALL.addAll(XMLAutronic.zapisProduktov());
        produktyALL.addAll(XMLNellys.zapisProduktov());
//        prestaNase = PrestaIDRead.filePresta();
        prestaNase = Premenne.prestaIDPremenne;

// HOTOVO, konečné porovnanie suborov a zapisanie vysledkov
//        writerVysledokPublic.println("PrestaID;Kod;Nazov;Kategoria;Vyrobca;Cena;Short;Activ;Feature;Operacia;Stary udaj");
        System.out.println("Porovnanie - zaciatok");
        for (int p = 0; p < prestaNase.size(); p++) {
            pomocnyAktivity = "0";
//Produkt REA ALFA UP ignorujem, dlhe vysvetlovanie, proste ignore
            if (!(prestaNase.get(p).getNazov().contains("REA ALFA UP"))) {
                for (int s = 0; s < produktyALL.size(); s++) {
                    if (prestaNase.get(p).getKod().equals(produktyALL.get(s).getKod())) {
                        pomocnyAktivity = "5";
                        String prestaAktivita = prestaNase.get(p).getAktivita();
                        String prestaMOC = prestaNase.get(p).getMOC();
                        String prestaDostupnost = prestaNase.get(p).getDostupnost();
                        String XMLAktivita = produktyALL.get(s).getAktivita();
                        String XMLDostupnost = produktyALL.get(s).getDostupnost();
                        String XMLVyrobca = produktyALL.get(s).getVyrobca();
                        String XMLCena = produktyALL.get(s).getMOC();


// *** AKTIVITA ***
//aktivita : 0 nedostupne, 1 dostupne, 9 vyhodene (ak aj je dostupne, nechceme ho)
                        if (!(prestaAktivita.contains(XMLAktivita))) {
                            if ((XMLAktivita.contains("9")) && (prestaAktivita.contains("1"))) {
                                docasnyArrayList.add(produktyALL.get(s).getVysledokInput("vyhodene", "", ""));
                            } else {
                                if ((XMLAktivita.contains("1")) && (!(XMLDostupnost.contains("? tel"))))
                                    docasnyArrayList.add(produktyALL.get(s).getVysledokInput("aktivovanie", Premenne.featureMesiac, "0"));
                                if (XMLAktivita.contains("0")) {
                                    docasnyArrayList.add(produktyALL.get(s).getVysledokInput("vyhodene", "", ""));
                                }
                            }
                        }

// *** CENA ***
// nacitanie cenyOff - sucasnu cenu nahradi cenou nastavenou v subore cenaOff = cenou, aka by mala byt podla feedu
// vyrobok ma cenu, aku chceme, aby mal, to je prestaID.get().getMOC, pripadne priceActual zo suboru cenaOFF
// taktiez je tu cena z feedu, ta spravna cena, to je produktyALL.get().getMOC, pripadne priceFeed zo suboru
// takze spravnu cenu nahradim nasou, pevne danou, nespravnou, cenou a tym padom ju program nebude brat pri porovnavani, ako inu, ale ako keby bola spravna
//                        String priceProduktXML = produktyALL.get(s).getMOC();
                        String codeOFF = "666";
                        FileInputStream fin = new FileInputStream(Premenne.cestaZoznam + "cena_off.txt");
                        BufferedReader citac = new BufferedReader(new InputStreamReader(fin, "UTF-8"));
                        String riadok = citac.readLine();
                        while (riadok != null) {
                            riadok = citac.readLine();
                            if (riadok != null) {
                                int bodkociarka = riadok.indexOf(";");
                                int bodkociarka2 = riadok.indexOf(";", bodkociarka + 1);
                                String code = riadok.substring(0, bodkociarka);
                                String priceFeed = riadok.substring(bodkociarka + 1, bodkociarka2);
                                String priceActual = riadok.substring(bodkociarka2 + 1);
//ak sa zhodne aktualny kod s niektorym kodom zo suboru cenaOFF, tak nahradim cenu za cenu zo suboru
//ale ak sa kody nezhodnu, ostane normalna cena z hornej casti tejto metody, String priceProduktXML
                                if (code.equals(produktyALL.get(s).getKod())) {
                                    codeOFF = code;
                                    if (XMLCena.contains(priceFeed))
                                        XMLCena = priceActual;
                                }
                            }
                        }
                        citac.close();

// zmena ceny, contains, nie equals, prestaID ma desatinne, XML ma bez desatinnej ciarky, aktivita 9 = vyhodene produkty, nechceme ich, Autronic nema ceny
                        if ((!(prestaMOC.contains(XMLCena)) && (XMLAktivita.contains("1")))) {
//ak doslo k zmene ceny, spravi zmenu ceny, ale ak doslo k zmene pevne zadanej ceny /CENAOFF/, tak to zapise, ako "Zmena OFF ceny"
                            if (!(codeOFF.equals(prestaNase.get(p).getKod()))) {
                                System.out.println(prestaNase.get(p).getKod()+";"+prestaMOC+";"+produktyALL.get(s).getMOC()+";"+produktyALL.get(s).getVyrobca());
                                docasnyArrayList.add(produktyALL.get(s).getVysledokInput("cena", Premenne.featureMesiac, prestaMOC));

                            }
                            else
                                docasnyArrayList.add(produktyALL.get(s).getVysledokInput("cenaOFF", Premenne.featureMesiac, prestaMOC));
                            prestaDostupnost = Premenne.featureMesiac;
//                            for (int i = 0; i < docasnyArrayList.size(); i++) {
//                                if (docasnyArrayList.get(i).get(0).getVyrobca().contains("Drevona"))
//                                    System.out.println(docasnyArrayList.get(i).get(0).getVysledokOutput());
//                            }
                        }

// *** DOSTUPNOST ***
//zmena dostupnosti, aktivita 9 = vyhodene produkty, nechceme ich
                        if ((!(prestaDostupnost.equals(XMLDostupnost))) && ((XMLAktivita.contains("1"))) && (!(XMLAktivita.contains("9")))) {
                            if (XMLDostupnost.contains(Premenne.featureSKL))
                                docasnyArrayList.add(produktyALL.get(s).getVysledokInput("dostupnost", Premenne.featureSKL, prestaNase.get(p).getDostupnost()));
                            else if (XMLDostupnost.contains(Premenne.featureOzajSKL))
                                docasnyArrayList.add(produktyALL.get(s).getVysledokInput("dostupnost", Premenne.featureOzajSKL, prestaNase.get(p).getDostupnost()));
                            else if (XMLDostupnost.contains(Premenne.feature2tyzdne))
                                docasnyArrayList.add(produktyALL.get(s).getVysledokInput("dostupnost", Premenne.feature2tyzdne, prestaNase.get(p).getDostupnost()));
                            else if (XMLDostupnost.contains(Premenne.feature4tyzdne))
                                docasnyArrayList.add(produktyALL.get(s).getVysledokInput("dostupnost", Premenne.feature4tyzdne, prestaNase.get(p).getDostupnost()));
                            else if (XMLDostupnost.contains(Premenne.feature2mesiace))
                                docasnyArrayList.add(produktyALL.get(s).getVysledokInput("dostupnost", Premenne.feature2mesiace, prestaNase.get(p).getDostupnost()));
                            else if (XMLDostupnost.contains(Premenne.featureMesiac))
                                docasnyArrayList.add(produktyALL.get(s).getVysledokInput("dostupnost", Premenne.featureMesiac, prestaNase.get(p).getDostupnost()));
                            else
                                docasnyArrayList.add(produktyALL.get(s).getVysledokInput("dostupnost", Premenne.featureMesiac, prestaNase.get(p).getDostupnost()));
                        }
                    }
                    }

//vyhodenie produktov, deaktivacia. Ak ich mame v ponuke, ale nenaslo ich, tak im hodim Aktivitu = 0, no a nabuduce uz nebudu v PrestaID
                    if ((pomocnyAktivity.equals("0")) && (prestaNase.get(p).getAktivita().contains("1"))) {
                        docasnyArrayList.add(prestaNase.get(p).getVysledokInput("vyhodene", "", ""));
                        if (prestaNase.get(p).getKod().equals("C30634")) {
                            System.out.println("3-KompletAll;existuje vnutri pomocnyAktivity");
                            for (int i=0; i<docasnyArrayList.size(); i++) {
                                System.out.print(i+";kod" + docasnyArrayList.get(i).get(0).getKod());
                                System.out.print(";aktivita:" + docasnyArrayList.get(i).get(0).getAktivita());
                                System.out.println(";vyrobca:" + docasnyArrayList.get(i).get(0).getVyrobca());
                            }
                        }
                    }
            }
        }

        writerVysledokPublicNew.println("PrestaID;Kod;Vyrobca;Cena;Short;Activ;Feature;Operacia;Stary udaj;Nazov;Kategoria");
        for (int i = 0; i < docasnyArrayList.size(); i++) {
            if (docasnyArrayList.get(i).get(0).getVyrobca().contains("AUTRONIC")) {
                writerVysledokPublicNew.println(docasnyArrayList.get(i).get(0).getVysledokOutput());
            }
        }
        for (int i = 0; i < docasnyArrayList.size(); i++) {
            if ((docasnyArrayList.get(i).get(0).getVyrobca().contains("DREVONA"))||(docasnyArrayList.get(i).get(0).getVyrobca().contains("Drevona"))) {
                writerVysledokPublicNew.println(docasnyArrayList.get(i).get(0).getVysledokOutput());
            }
        }
        for (int i = 0; i < docasnyArrayList.size(); i++) {
            if (docasnyArrayList.get(i).get(0).getVyrobca().contains("Tempo-Kondela")) {
                writerVysledokPublicNew.println(docasnyArrayList.get(i).get(0).getVysledokOutput());
            }
        }

        System.out.println("Porovnanie - koniec");
        writerVysledokPublicNew.close();

//vytvorenie kopie aktualizacie do History adresara
        copyFile(Premenne.cesta + "aktualizacia_new.csv", Premenne.cesta + "History\\aktualizacia_new_" + Premenne.formattedDateTime + ".csv");
        copyFile(Premenne.cesta + "PrestaID.csv", Premenne.cesta + "History\\PrestaID_" + Premenne.formattedDateTime + ".csv");
    }

    private static void copyFile(String fromPath, String toPath) {
        Path FROM = Paths.get(fromPath);
        Path TO = Paths.get(toPath);
        //overwrite existing file, if exists
        CopyOption[] options = new CopyOption[]{
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES
        };
        try {
            Files.copy(FROM, TO, options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
//108 riadkov