package Metody;

import java.io.IOException;
import java.util.ArrayList;

//vycucnutie farby z nazvu produktu
public class Met_Color {
    public static String zistiFarbu (String nazov, String vyrobca) throws IOException {
        String farba = "Farba: ";
        String subor = "null";
        if (vyrobca.equals("DREVONA"))
            subor = "Zoznam_farba_drevona.csv";
        if (vyrobca.equals("Tempo-Kondela"))
            subor = "Zoznam_farba_tempo.csv";
        if (vyrobca.equals("AUTRONIC"))
            subor = "Zoznam_farba_tempo.csv";

        ArrayList<Produkt> temp=Premenne.complexReplace(Premenne.cestaZoznam+subor);
        for (int i=0; i<temp.size();i++) {
            if (nazov.contains(temp.get(i).getKod())) {
                farba += temp.get(i).getMOC();
                break;
            }
        }

//        if (vyrobca.contains("AUTRONIC")) {
//            if (nazov.contains("WT")) {
//                farba = "biela";
//            } else if (nazov.contains(" BG")) {
//                farba = "krémová";
//            } else if (nazov.contains("BK")) {
//                farba = "čierna";
//            } else if (nazov.contains("BLUE")) {
//                farba = "modrá";
//            } else if (nazov.contains("BOR")) {
//                farba = "červená";
//            } else if (nazov.contains("BR")) {
//                farba = "hnedá";
//            } else if (nazov.contains("BUK")) {
//                farba = "buk";
//            } else if (nazov.contains("CAN")) {
//                farba = "dub canyon";
//            } else if (nazov.contains("CAP")) {
//                farba = "cappuccino";
//            } else if (nazov.contains(" CLR")) {
//                farba = "sklo";
//            } else if (nazov.contains("COF")) {
//                farba = "hnedá";
//            } else if (nazov.contains(" CRM")) {
//                farba = "krémová";
//            } else if (nazov.contains(" CR")) {
//                farba = "chróm";
//            } else if (nazov.contains("GREY")) {
//                farba = "sivá";
//            } else if (nazov.contains("GRN")) {
//                farba = "zelená";
//            } else if (nazov.contains("KARI")) {
//                farba = "žltá";
//            } else if (nazov.contains("LAN")) {
//                farba = "lanýž";
//            } else if (nazov.contains("LIM")) {
//                farba = "zelená";
//            } else if (nazov.contains(" MAT")) {
//                farba = "biela";
//            } else if (nazov.contains(" MIL")) {
//                farba = "sklo";
//            } else if (nazov.contains("NAT")) {
//                farba = "prírodná";
//            } else if (nazov.contains("OAK")) {
//                farba = "dub";
//            } else if (nazov.contains(" OL")) {
//                farba = "jelša";
//            } else if (nazov.contains("ORA")) {
//                farba = "oranžová";
//            } else if (nazov.contains(" PET")) {
//                farba = "modrá";
//            } else if (nazov.contains("PINK")) {
//                farba = "ružová";
//            } else if (nazov.contains(" PW2")) {
//                farba = "viacfarebné";
//            } else if (nazov.contains("RED")) {
//                farba = "červená";
//            } else if (nazov.contains("SIL")) {
//                farba = "strieborná";
//            } else if (nazov.contains("SON")) {
//                farba = "dub sonoma";
//            } else if (nazov.contains("SRE")) {
//                farba = "dub";
//            } else if (nazov.contains(" TR")) {
//                farba = "čerešňa";
//            } else if ((nazov.contains("TR2") || nazov.contains("TR3"))) {
//                farba = "čerešňa";
//            } else if (nazov.contains("WAL")) {
//                farba = "orech";
//            } else if (nazov.contains("YEL")) {
//                farba = "žltá";
//            } else if (nazov.contains("ZEB")) {
//                farba = "čierna";
//
//            } else {
//                return null;
//            }
//        }

//        if (vyrobca.contains("DREVONA")) {
//
//
//
//
//            if (nazov.contains("BIELA")) {
//                farba += "biela";
//            } else if (nazov.contains("NAVARRA")) {
//                farba += "navarra";
//            } else if (nazov.contains("WENGE")) {
//                farba += "wenge";
//            } else if (nazov.contains("ORECH ROCKPILE")) {
//                farba += "orech rockpile";
//            } else if (nazov.contains("DUB CANYON")) {
//                farba += "dub canyon";
//                } else if (nazov.contains("BUK")) {
//                farba += "buk";
//            } else if (nazov.contains("DUB BARDOLIN")) {
//                farba += "dub bardolino";
//            } else if (nazov.contains("TYRKYS")) {
//                farba += "tyrkys";
//            } else if (nazov.contains("ZELEN")) {
//                farba += "zelená";
//            } else if (nazov.contains("CAPPUCCINO")) {
//                farba += "cappucino";
//            } else if (nazov.contains("SORO")) {
//                farba += "sivá";
//            } else if (nazov.contains("GRAPHITE")) {
//                farba += "graphite";
//            } else if (nazov.contains("ROSE")) {
//                farba += "ružová";
//            } else if (nazov.contains("BLUE")) {
//                farba += "modrá";
//            } else if (nazov.contains("PLUM")) {
//                farba += "ružová";
//            } else {
//                farba += null;
//            }
//
//        }
        return farba;
    }
}
