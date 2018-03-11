package Metody;

//vycucnutie farby z nazvu produktu
public class Met_Name {
    public static String zistiNazov (String nazov, String vyrobca, String popis) {
        String farba;
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
        }
// ************  TEMPO **********
        if (vyrobca.contains("Tempo-Kondela")) {
            if (nazov.contains(",")) {
                int ciarka = nazov.indexOf(",");
                int ciarkaPosledna = nazov.lastIndexOf(",");
                String prva_cast = nazov.substring(0, ciarka);
                String druha_cast = nazov.substring(ciarkaPosledna+1);
                farba = ", "+popis.substring(7);
                nameUpravene = prva_cast + druha_cast + farba;

                //rozne specificke nazvy, kde neplati standardizovany sposob vytvarania nazvu

                //spodne a horne skrinky do kuchyne
                if (nazov.contains("Spodná")) {
                    druha_cast = nazov.substring(ciarkaPosledna+2);
                    prva_cast = prva_cast.replaceAll("Spodná otvorená skrinka "," ").replaceAll("Spodná rohová skrinka "," ")
                            .replaceAll("Spodná skrinka s dvomi policami "," ").replaceAll("Spodná skrinka s tromi šuplíkmi"," ")
                                    .replaceAll("Spodná skrinka "," ");
                    nameUpravene = druha_cast + prva_cast+ farba + ", spodná skrinka";
                }
                if (nazov.contains("Dvierka na")) {
                    druha_cast = nazov.substring(ciarkaPosledna+2);
                    prva_cast = prva_cast.replaceAll("Dvierka na umývačku riadu","").replaceAll("Dvierka na vstavanú umývačku riadu","");
                    nameUpravene = druha_cast + prva_cast+ farba + ", dvierka na umývačku";
                }
                if (nazov.contains("Horná")) {
                    druha_cast = nazov.substring(ciarkaPosledna+2);
                    prva_cast = prva_cast.replaceAll("Horná dvojdverová skrinka so sklom "," ").replaceAll("Horná policová skrinka", " ")
                            .replaceAll("Horná rohová skrinka ", " ").replaceAll("Horná otvorená skrinka ", " ")
                            .replaceAll("Horná dvojdverová skrinka "," ").replaceAll("Horná skrinka "," ");
                    nameUpravene = druha_cast + prva_cast+ farba + ", horná skrinka";
                }

                //postele maju v nazve aj sirku, konkretne cislo; zaroven v nzve je len slovo Postel, nie manzelska, moderna, ani ina
                if ((nameUpravene.contains("osteľ"))) {
                    String sirkaPostele = "";
                    if (nazov.contains("80"))
                        sirkaPostele = " 80";
                    if (nazov.contains("90"))
                        sirkaPostele = " 90";
                    if (nazov.contains("120"))
                        sirkaPostele = " 120";
                    if (nazov.contains("140"))
                        sirkaPostele = " 140";
                    if (nazov.contains("160"))
                        sirkaPostele = " 160";
                    if (nazov.contains("180"))
                        sirkaPostele = " 180";

                    nameUpravene = "Posteľ " + druha_cast + sirkaPostele + farba;
                }
                //taburety, rozne taburety, vzdy len slovo taburet
                if (nameUpravene.contains("aburet"))
                    nameUpravene = "Taburet" + druha_cast + farba;
                if (nameUpravene.contains("otník"))
                    nameUpravene = "Botník" + druha_cast + farba;

            }
        }


        return nameUpravene;
    }
}
