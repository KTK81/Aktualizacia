package Metody;

import java.io.IOException;
import java.util.ArrayList;

public class Met_Description {
    public static String zistiPopis(String popis, String vyrobca) throws IOException {
        String description = popis;
        if (vyrobca.equals("DREVONA")) {
//DESCRIPTION - na beznej www.drevona.sk je lepsi popis, ako z XML, takze kuk na beznu a ak tam najdem, dam ten
//ale nie vzdy je uz vyrobok aj na maloobchode, vtedy pouzijem popis z XML
//            URL najdiKod = new URL("http://www.drevona.sk/najdene-produkty/" + code + "/");
//            BufferedReader in = new BufferedReader(new InputStreamReader(najdiKod.openStream()));
//            String riadokDrevonaURL = null,popisVyrobkuMaloobchod = null;
//            while ((riadokDrevonaURL = in.readLine()) != null)    //nacita URL source kod, kym su riadky, tak ide
//            {
//                if (riadokDrevonaURL.contains("<h3><a href=")) {
//                    int leftOdkaz = riadokDrevonaURL.indexOf("<h3><a href=");
//                    int rightOdkaz = riadokDrevonaURL.indexOf("title=");
//                    produktURL = riadokDrevonaURL.substring(leftOdkaz + 13, rightOdkaz - 2); //vycucne odkaz
//                    URL drevona = new URL(produktURL);
//                    BufferedReader in2 = new BufferedReader(new InputStreamReader(drevona.openStream(), "UTF-8"));
//                    String kazdyRiadok;
//                    while ((kazdyRiadok = in2.readLine()) != null) {
//                        if (kazdyRiadok.contains("<div class=\"flypage-desc\"><h2>") || kazdyRiadok.contains("<div class=\"flypage-desc\"><h2 style"))
//                            pocetRiadkov = "5";
//                        if (kazdyRiadok.contains("<p><u><a href=") || kazdyRiadok.contains("<u><a href=") || kazdyRiadok.contains("<p><span style=\"font"))
//                            pocetRiadkov = "0";
//                        if (pocetRiadkov.equals("5")) {
//                            if (kazdyRiadok.contains("<div class=\"flypage-desc\">"))
//                                kazdyRiadok = kazdyRiadok.substring(28);
//                            if (kazdyRiadok.contains(";")) {
//                                nahrada = kazdyRiadok.replace(";", ",");
//                                kazdyRiadok = nahrada;
//                            }
//                            if (kazdyRiadok.contains("<span style=\"font-size:11px,\">")) {
//                                nahrada = kazdyRiadok.replace("<span style=\"font-size:11px,\">", "");
//                                kazdyRiadok = nahrada;
//                            }
//                            popisVyrobkuMaloobchod = popisVyrobkuMaloobchod+kazdyRiadok;
//                        }
//                    }
//                    break;
//                }
//            }
//            Thread.sleep(1000);
//*/

//v DESCRIPTION v XML nefunguje UTF-8 encoding a diakritika, tak musim nahradit somariny pismenami
            description = description.replaceAll("&aacute;", "á").replaceAll("&eacute;", "é").replaceAll("&iacute;", "í").replaceAll("&oacute;", "ó").replaceAll("&uacute;", "ú");
            description = description.replaceAll("&Aacute;", "Á").replaceAll("&Eacute;", "É").replaceAll("&Iacute;", "Í").replaceAll("&Oacute;", "Ó").replaceAll("&Uacute;", "Ú");
            description = description.replaceAll("&yacute;", "ý").replaceAll("&ocirc;", "ô");
            description = description.replaceAll("&scaron;", "š").replaceAll("&ntilde;", "ñ");
            description = description.replaceAll("&Ntilde;", "Ñ").replaceAll("&ndash;", "-");
            description = description.replaceAll(";", " ").replaceAll("<p><br />", "<p> ").replaceAll("(\r\n|\n\r|\r|\n)", "<br />");
        }

        if (vyrobca.equals("Tempo-Kondela")) {
            description = description.replaceAll("\n", " , ").replaceAll(";", ",");
        }

        return description;
    }


}
