package Metody;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//vycucnutie farby z nazvu produktu
public class Met_Name {
    public static String zistiNazov (String nazov, String vyrobca, String popis) {
        String farba = " , null";
        String nameUpravene=null;
//********** DREVONA *************
        if (vyrobca.contains("DREVONA")) {
            if (nazov.contains("BIELA")) { //najprv poriesim farbu a okresanie kodu o koncove cisla
                farba = "biela";
                nameUpravene = nazov.replaceAll("(\\s+)(BIELA)(.*)", ", " + farba);
            } else if (nazov.contains("NAVARRA")) {
                farba = "navarra";
                nameUpravene = nazov.replaceAll("(\\s+)(NAVARRA)(.*)", ", " + farba);
            } else if (nazov.contains("WENGE")) {
                farba = "wenge";
                nameUpravene = nazov.replaceAll("(\\s+)(WENGE)(.*)", ", " + farba);
            } else if (nazov.contains("ORECH ROCKPILE")) {
                farba = "orech rockpile";
                nameUpravene = nazov.replaceAll("(\\s+)(ORECH ROCKPILE)(.*)", ", " + farba);
            } else if (nazov.contains("DUB CANYON")) {
                farba = "dub canyon";
                nameUpravene = nazov.replaceAll("(\\s+)(DUB CANYON)(.*)", ", " + farba);
            } else if (nazov.contains("BUK")) {
                farba = "buk";
                nameUpravene = nazov.replaceAll("(\\s+)(BUK)(.*)", ", " + farba);
            } else if (nazov.contains("DUB BARDOLIN")) {
                farba = "dub bardolino";
                nameUpravene = nazov.replaceAll("(\\s+)(DUB BARDOLIN)(.*)", ", " + farba);
            } else if (nazov.contains("DUB BARDOLIN")) {
                farba = "dub bardolino";
                nameUpravene = nazov.replaceAll("(\\s+)(DUB BARDOLIN)(.*)", ", " + farba);
            } else if (nazov.contains("TYRKYS")) {
                farba = "tyrkys";
                nameUpravene = nazov.replaceAll("(\\s+)(TYRKYS)(.*)", ", " + farba);
            } else if (nazov.contains("ZELEN")) {
                farba = "zelená";
                nameUpravene = nazov.replaceAll("(\\s+)(ZELEN)(.*)", ", " + farba);
            } else if (nazov.contains("CAPPUCCINO")) {
                farba = "cappucino";
                nameUpravene = nazov.replaceAll("(\\s+)(CAPPUCCINO)(.*)", ", " + farba);
            } else if (nazov.contains("SORO")) {
                farba = "sivá";
                nameUpravene = nazov.replaceAll("(\\s+)(SORO)(.*)", ", " + farba);
            } else if (nazov.contains("GRAPHITE")) {
                farba = "graphite";
                nameUpravene = nazov.replaceAll("(\\s+)(GRAPHITE)(.*)", ", " + farba);
            } else if (nazov.contains("ROSE")) {
                farba = "ružová";
                nameUpravene = nazov.replaceAll("(\\s+)(ROSE)(.*)", ", " + farba);
            } else if (nazov.contains("BLUE")) {
                farba = "modrá";
                nameUpravene = nazov.replaceAll("(\\s+)(BLUE)(.*)", ", " + farba);
            } else if (nazov.contains("PLUM")) {
                farba = "ružová";
                nameUpravene = nazov.replaceAll("(\\s+)(PLUM)(.*)", ", " + farba);
            } else {
                nameUpravene = null;
            }
// a teraz poriesim prvu cast nazvu, podla kategorie, popisu, celkovo
            if (popis.contains("Komoda")) {
                nameUpravene = "Komoda "+nameUpravene;
            }
            else if (popis.contains("Nočný stolík")) {
                nameUpravene = "Nočný stolík "+nameUpravene;
            }
            else if (popis.contains("Jedálenský stôl")) {
                nameUpravene = "Jedálenský stôl "+nameUpravene;
            }
            else if ((popis.contains("egál ")) || (popis.contains("Otvorený regál"))) {
                nameUpravene = "Regál "+nameUpravene;
            }
            else if (popis.contains("Konferenčný stolík")) {
                nameUpravene = "Konferenčný stolík "+nameUpravene;
            }
            else if (popis.contains("Rohová skriňa")) {
                nameUpravene = "Rohová skriňa "+nameUpravene;
            }
            else if (popis.contains("Rohová skrinka")) {
                nameUpravene = "Rohová skrinka "+nameUpravene;
            }
            else if (popis.contains("Sedacia súprava")) {
                nameUpravene = "Sedacia súprava "+nameUpravene;
            }
        }
// ************  TEMPO **********
        if (vyrobca.contains("Tempo-Kondela")) {
            if (nazov.contains(",")) {
                int ciarka = nazov.indexOf(",");
                int ciarkaDruha = nazov.indexOf(",", ciarka+1);
                int ciarkaTretia = nazov.indexOf(",", ciarkaDruha+1);
                int ciarkaPosledna = nazov.lastIndexOf(",");
                String prva_cast = nazov.substring(0, ciarka);
                String druha_cast = nazov.substring(ciarkaPosledna+1);
                String strednaCast = "nenaslo";
                String strednaCast2 = "nenaslo";
                String pomocna = null;
                //NAZOV vyrobku davam taky isty, ako ma Tempo - nedavam konkretnu farbu, ako davam do Feature, Vlastnosti, ale skopcim proste cely text o farbe
                //ALE samozrejme, ze Tempo to ma rozne, raz je farba tam, raz inde, takze byva v DVOCH roznych sekciach, bud za prvou ciarkou alebo za druhou
                //takze vycucnem obe tie sekcie, pozriem, ktora z nich ma nieco s farbou - obsahuje zmienku o nejakej farbe a potom celu tu sekciu dam do nazvu
                //ALE tym padom sa moze stat, ze nazov vyrobku v Tempe neobsahuje aj tu druhu cast, proste v nazve je len jedna ciarka
                //tym padom take nazvy mi vhodia error, preto to davam do try catch a ak nenajde tu druhu ciarku, tak len do pomocnej premennej zapisem hodnotu
                try {
                    strednaCast = nazov.substring(ciarka, ciarkaDruha);
                    strednaCast2 = nazov.substring(ciarkaDruha, ciarkaTretia);
                }
                catch (StringIndexOutOfBoundsException e)  {
//                    System.out.println("met Name catch: " +nazov);
                    pomocna = "nieco";
                }

                if (strednaCast.contains("biel")||strednaCast.contains("čiern")||strednaCast.contains("siv")||strednaCast.contains("červen")
                        ||strednaCast.contains("béžov")||strednaCast.contains("capuccino")||strednaCast.contains("dub")||strednaCast.contains("buk")
                        ||strednaCast.contains("čereš")||strednaCast.contains("hned")||strednaCast.contains("modr")||strednaCast.contains("orech")
                        ||strednaCast.contains("wenge")||strednaCast.contains("zelen")||strednaCast.contains("žlt")||strednaCast.contains("zlat")
                        ||strednaCast.contains("čiern")||strednaCast.contains("látka")||strednaCast.contains("koža")||strednaCast.contains("mocca")
                        ||strednaCast.contains("ružov")||strednaCast.contains("vzor")||strednaCast.contains("tyrky")||strednaCast.contains("viacfareb")
                        ||strednaCast.contains("slonovi")||strednaCast.contains("jaseň")||strednaCast.contains("elš")||strednaCast.contains("remo")
                        ||strednaCast.contains("natural")||strednaCast.contains("lesk")||strednaCast.contains("bardolino")||strednaCast.contains("samoa")
                        ||strednaCast.contains("krémov")||strednaCast.contains("mentolov")||strednaCast.contains("sosna")||strednaCast.contains("sonoma")
                        ||strednaCast.contains("woodline")||strednaCast.contains("chróm")||strednaCast.contains("slivka")||strednaCast.contains("javor")
                        ||strednaCast.contains("strieb")||strednaCast.contains("cappuccino")||strednaCast.contains("sklo")||strednaCast.contains("prírod")
                        ||strednaCast.contains("oranžov")||strednaCast.contains("fialov")) {

                    farba = strednaCast;
                }
                if (strednaCast2.contains("biel")||strednaCast2.contains("čierna")||strednaCast2.contains("siv")||strednaCast2.contains("červen")
                        ||strednaCast2.contains("béžov")||strednaCast2.contains("capuccino")||strednaCast2.contains("dub")||strednaCast2.contains("buk")
                        ||strednaCast2.contains("čereš")||strednaCast2.contains("hned")||strednaCast2.contains("modr")||strednaCast2.contains("orech")
                        ||strednaCast2.contains("wenge")||strednaCast2.contains("zelen")||strednaCast2.contains("žlt")||strednaCast2.contains("zlat")
                        ||strednaCast2.contains("čiern")||strednaCast2.contains("látka")||strednaCast2.contains("koža")||strednaCast2.contains("mocca")
                        ||strednaCast2.contains("ružov")||strednaCast2.contains("vzor")||strednaCast2.contains("tyrky")||strednaCast2.contains("viacfareb")
                        ||strednaCast2.contains("slonovi")||strednaCast2.contains("jaseň")||strednaCast2.contains("elš")||strednaCast2.contains("remo")
                        ||strednaCast2.contains("natural")||strednaCast2.contains("lesk")||strednaCast2.contains("bardolino")||strednaCast2.contains("samoa")
                        ||strednaCast2.contains("krémov")||strednaCast2.contains("mentolov")||strednaCast2.contains("sosna")||strednaCast2.contains("sonoma")
                        ||strednaCast2.contains("woodline")||strednaCast2.contains("chróm")||strednaCast2.contains("slivka")||strednaCast2.contains("javor")
                        ||strednaCast2.contains("strieb")||strednaCast2.contains("cappuccino")||strednaCast2.contains("sklo")||strednaCast2.contains("prírod")
                        ||strednaCast2.contains("oranžov")||strednaCast2.contains("fialov")) {
                    farba = strednaCast2;
                }
//                farba = ", "+popis.substring(7);
                nameUpravene = prva_cast + druha_cast + farba;

                //postel, rozmery - v texte, "x" ko sa normalne nevystihuje
//                Pattern p = Pattern.compile("\\d*x\\d*");
//                Matcher m = p.matcher(strednaCast);
//                if (m.find())
//                    nameUpravene+=strednaCast;
//                m = p.matcher(strednaCast2);
//                if (m.find())
//                    nameUpravene+=strednaCast2;


                //rozne specificke nazvy, kde neplati standardizovany sposob vytvarania nazvu

                // sedacie supravy, LAVE a PRAVE - pridat na koniec nazvu
                if (nazov.contains("pravá"))
                    nameUpravene+= ", pravá";
                if (nazov.contains("ľavá"))
                    nameUpravene+= ", ľavá";

                //spodne a horne skrinky do kuchyne
//                if (nazov.contains("Spodná")) {
//                    druha_cast = nazov.substring(ciarkaPosledna+2);
//                    prva_cast = prva_cast.replaceAll("Spodná otvorená skrinka "," ").replaceAll("Spodná rohová skrinka "," ")
//                            .replaceAll("Spodná skrinka s dvomi policami "," ").replaceAll("Spodná skrinka s tromi šuplíkmi"," ")
//                                    .replaceAll("Spodná skrinka "," ");
//                    nameUpravene = druha_cast + prva_cast+ farba + ", spodná skrinka";
//                }
//                if (nazov.contains("Dvierka na")) {
//                    druha_cast = nazov.substring(ciarkaPosledna+2);
//                    prva_cast = prva_cast.replaceAll("Dvierka na umývačku riadu","").replaceAll("Dvierka na vstavanú umývačku riadu","");
//                    nameUpravene = druha_cast + prva_cast+ farba + ", dvierka na umývačku";
//                }
//                if (nazov.contains("Horná")) {
//                    druha_cast = nazov.substring(ciarkaPosledna+2);
//                    prva_cast = prva_cast.replaceAll("Horná dvojdverová skrinka so sklom "," ").replaceAll("Horná policová skrinka", " ")
//                            .replaceAll("Horná rohová skrinka ", " ").replaceAll("Horná otvorená skrinka ", " ")
//                            .replaceAll("Horná dvojdverová skrinka "," ").replaceAll("Horná skrinka "," ");
//                    nameUpravene = druha_cast + prva_cast+ farba + ", horná skrinka";
//                }

                //postele maju v nazve aj sirku, konkretne cislo; zaroven v nzve je len slovo Postel, nie manzelska, moderna, ani ina
//                if ((nameUpravene.contains("osteľ"))) {
//                    String sirkaPostele = "";
//                    if (nazov.contains("80"))
//                        sirkaPostele = " 80";
//                    if (nazov.contains("90"))
//                        sirkaPostele = " 90";
//                    if (nazov.contains("120"))
//                        sirkaPostele = " 120";
//                    if (nazov.contains("140"))
//                        sirkaPostele = " 140";
//                    if (nazov.contains("160"))
//                        sirkaPostele = " 160";
//                    if (nazov.contains("180"))
//                        sirkaPostele = " 180";
//
//                    nameUpravene = prva_cast + druha_cast + sirkaPostele + farba;
//                }

                //taburety, rozne taburety, vzdy len slovo taburet
//                if (nameUpravene.contains("aburet"))
//                    nameUpravene = "Taburet" + druha_cast + farba;
//                if (nameUpravene.contains("otník"))
//                    nameUpravene = "Botník" + druha_cast + farba;

                //koberce, pridat aj rozmer do nazvu
                if (nameUpravene.contains("oberec")) {
                    nameUpravene+=strednaCast2;
                }


            }
        }


        return nameUpravene;
    }
}
