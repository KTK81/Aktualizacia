package Metody;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//vycucnutie rozmerov z popisu produktu
/*ZAKLADY REGEXU, tu vyuzivané :
http://www.vogella.com/tutorials/JavaRegularExpressions/article.html
znak je znak, napr. š, v, h = jeden konkretny znak
\d je cislo, ako digit, ked je \D, tak vsetko okrem cisla
\w je word
. je hocijaky znak
| = or, alebo, nieco, or este daco
ked pridam k danej veci "+", tak bud sa vyskytuje aspon raz alebo viackrat
ked pridam ?, tak nula-krat alebo viackrat, ako keby moze byt, ale nemusi
\ je v JAVE reserved znak, nejde pouzit len tak, musi sa pouzit lomitko pred nim, cize napr. niekolko cisiel by bolo \\d+
pomocou zatvoriek sa daju oddelit jednolive casti, potom sa k nim da prstupovat cez m.group(cislo danej skupiny)

*/
public class Met_Dimensions {
    public static ArrayList<Produkt> findDimensions(String description, String vyrobca) {

        ArrayList<Produkt> vysledok = new ArrayList<>();
//dlzku nechavam, ale inak nie je, dlzka = hlbka
        String rozmer = null;
        String sirka = "Šírka: ";
        String hlbka = "Hĺbka: ";
        String vyska = "Výška: ";
        String dlzka = "Dĺžka: ";

//        String popis = ("<p><strong>3-sed  pevný bez funkcie</strong><br /> <br /> rozmer: š173cm x v70,5cm x h75 x <br /> sedenie: výška 45cm, hĺbka 50cm, šírka 139, šírka podrúčky 17cm<br /> <br /> - skladba kostry: masív buk, smrek, DTD, DVD<br /> - sedák: rám, vlnitá pružina, PDP, deliaca vrstva, molitan, rúno, nábytková látka<br /> - operadlo: masív smrek, DTD, popruhy, molitan, rúno, nábytková látka<br /> - podrúčky: smrek, DTD, DVD, molitan, rúno, nábytková látka<br /> - nožičky masív buk morené výšky 4cm<br /> <br /> V V ponuke v látkach podla<strong> vzorkovníka AVA </strong><strong>+ </strong>textilná koža <strong>MG15-čierna, MG17-hnedá a MG1/D-béžová</strong></p>");

//********** DREVONA *********
//ak popis obsahuje text s rozmer: š173cm x v70,5cm x h75 x <br />, pripadne slovo rozmerY, pripadne prve pismeno je velke, tak sa udeje toto:
        if (vyrobca.contains("DREVONA")) {
            if ((description.contains("ozmer: š")) || (description.contains("ozmery: š")) || (description.contains("ozmer: d"))) {
                //vycucnem text v okoli rozmerov, zacne slovom rozmer, konci bud znakom "<br />" alebo slovom "priestoru", podla potreby
                Pattern p = Pattern.compile(".ozmer(.)?: (.*?)(<br />|priestoru)");
                Matcher m = p.matcher(description);
                while (m.find()) {
                    rozmer = m.group();
                }

                //vycucnem jednolive rozmery
                p = Pattern.compile("š(\\d+((\\.|\\,)\\d+)?)");
                m = p.matcher(rozmer);
                while (m.find()) {
                    sirka += m.group(1) + " cm";
                }

                p = Pattern.compile("h(<strong>)?(\\d+((\\.|\\,)\\d+)?)");
                m = p.matcher(rozmer);
                while (m.find()) {
                    hlbka += m.group(2) + " cm";
                }

                p = Pattern.compile("v(\\d+((\\.|\\,)\\d+)?)");
                m = p.matcher(rozmer);
                while (m.find()) {
                    vyska += m.group(1) + " cm";
                }

                p = Pattern.compile("d(\\d+((\\.|\\,)\\d+)?)");
                m = p.matcher(rozmer);
                while (m.find()) {
                    hlbka += m.group(1) + " cm";
                }
            }
            if (description.contains("Rozmer: (šxhxv)")) {
                Pattern p = Pattern.compile("(.ozmer: \\(šxhxv\\))(.*?)(<br />|priestoru)");
                Matcher m = p.matcher(description);
                while (m.find()) {
                    rozmer = m.group();
                }

                //vycucnem jednolive rozmery
                p = Pattern.compile("\\).(\\d+((\\.|\\,)\\d+)?)");
                m = p.matcher(rozmer);
                while (m.find()) {
                    sirka += m.group(1) + " cm";
                }

                p = Pattern.compile("x.(\\d+((\\.|\\,)\\d+)?).x");
                m = p.matcher(rozmer);
                while (m.find()) {
                    hlbka += m.group(1) + " cm";
                }

                p = Pattern.compile("x.(\\d+((\\.|\\,)\\d+)?).c");
                m = p.matcher(rozmer);
                while (m.find()) {
                    vyska += m.group(1) + " cm";
                }
            }
        }


        if (vyrobca.contains("Tempo-Kondela")) {
            try {
                if (description.contains("ŠxHxV")) {
                    //vycucnem text v okoli rozmerov
                    Pattern p = Pattern.compile("ŠxHxV(.*?)(cm| , )");
//                Pattern p = Pattern.compile("ŠxHxV(.*)");
                    Matcher m = p.matcher(description);
                    while (m.find()) {
                        rozmer = m.group();
                    }

                    //vycucnem jednolive rozmery
// !!!!! TO DO OPRAVIT - neviem vycucnut iba prve cislo z napr. : (ŠxHxV): 80x3x62 - medzera pred 80 nie je medzera, ale akysi shit !!!!!
// tak \s nefunguje na odlisenie prveho a druheho cisla, tak mi vzdy najde aj druhe cislo medzi x-kami
// tak som to poriesil cez repeateed integer, ktory navysim a zadam tam len prvu occurence, len ked je nenavyseny ten repeated
// neviem ako spravit, aby ak najde x pred cislom, tak to nebralo, ale len ak najde cisto cislo samotne, s x za cislom
//                    p = Pattern.compile("(\\d+((\\.|\\,)?\\d+))x");
                    p = Pattern.compile("(\\s|:)?(\\d+((\\.|\\,)\\d+)?)x");
                    m = p.matcher(rozmer);
                    int repeated = 0; //sluzi na odlisenie prvej occurence, od druhej
                    while (m.find()) {
                        if (repeated == 0) { //vid vyssie - sluzi na odlisenie prvej occurence, od druhej
                        sirka += m.group(2) + " cm";
//                        sirka += m.group(2) + " cm";
                        }
                        repeated = 1;  // vid este vyssie - sluzi na odlisenie prvej occurence, od druhej
                    }

                    p = Pattern.compile("x(\\d+((\\.|\\,)\\d+)?)x");
                    m = p.matcher(rozmer);
                    while (m.find()) {
                        hlbka += m.group(1) + " cm";
                    }

                    p = Pattern.compile("x(\\d+((\\.|\\,|\\-)\\d+)?)(\\s|c|,)");
                    m = p.matcher(rozmer);
                    while (m.find()) {
                        vyska += m.group(1) + " cm";

                    }
                }

                else if (description.contains("ŠxH")) {
                    //vycucnem text v okoli rozmerov, zacne slovom rozmer, konci bud znakom "<br />" alebo slovom "priestoru", podla potreby
                    Pattern p = Pattern.compile("ŠxH(.*?)(cm| , )");
                    Matcher m = p.matcher(description);
                    while (m.find()) {
                        rozmer = m.group();
                    }
                }


                if (description.contains("ŠxV")) {
                    //vycucnem text v okoli rozmerov
                    Pattern p = Pattern.compile("ŠxV(.*?)(cm| , )");
                    Matcher m = p.matcher(description);
                    while (m.find()) {
                        rozmer = m.group();
                    }

                    //vycucnem jednolive rozmery
                    p = Pattern.compile("(\\s|:)(\\d+((\\.|\\,)\\d+)?)x");
                    m = p.matcher(rozmer);
                    while (m.find()) {
                        sirka += m.group(2) + " cm";
                    }

                    p = Pattern.compile("x(\\d+((\\.|\\,)\\d+)?)(\\s|c)");
                    m = p.matcher(rozmer);
                    while (m.find()) {
                        vyska += m.group(1) + " cm";
                    }
                }

                    if (description.contains("Rozmery (ŠxD)")) {
                        //vycucnem text v okoli rozmerov
                        Pattern p = Pattern.compile("ŠxD(.*?)(cm| , )");
                        Matcher m = p.matcher(description);
                        while (m.find()) {
                            rozmer = m.group();
                        }

                        //vycucnem jednolive rozmery
                        p = Pattern.compile("(\\s|:)(\\d+((\\.|\\,)\\d+)?)x");
                        m = p.matcher(rozmer);
                        while (m.find()) {
                            sirka += m.group(2) + " cm";
                        }

                        p = Pattern.compile("x(\\d+((\\.|\\,)\\d+)?)(\\s|c)");
                        m = p.matcher(rozmer);
                        while (m.find()) {
                            vyska += m.group(1) + " cm";
                            if (description.contains("PEBBLE")) {
//                                System.out.println("dlzka "+m.group(1));
                            }
                        }
                    }

                //pri zostavach je problem s vycucnutim udajov, treba riesit rucne
                if (description.contains("zostava")) {
                    sirka = "rucne";
                    vyska = "rucne";
                    hlbka = "rucne";
                }
                dlzka = rozmer;
//            }

            }
            catch (NullPointerException e) {
                System.out.println("nenasloooo  " + description);
            }

            catch (Exception e) {
                System.out.println("nenasloooo  " + description);
                e.printStackTrace();
            }

        }

//        System.out.println(sirka+";"+vyska+";"+hlbka);
//        System.out.println("sirka:"+sirka+"*** vyska:"+vyska+"*** hlbka:"+hlbka);
            if (sirka.contains(",")) sirka = sirka.replaceAll(",", ".");
            if (vyska.contains(",")) vyska = vyska.replaceAll(",", ".");
            if (hlbka.contains(",")) hlbka = hlbka.replaceAll(",", ".");
//        if (dlzka.contains(",")) dlzka.replaceAll(",",".");

            vysledok.add(new Produkt(sirka, hlbka, vyska, dlzka));
            return vysledok;
//        }
    }
}



