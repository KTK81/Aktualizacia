package Metody;

import java.io.IOException;
import java.util.ArrayList;

//obcas su divne nazvy, popisy, su tam skryte znaky na nove riadky, prazdne znaky, somariny. Tak ich vyhadzem a necham len normalne znaky.
public class Met_NahradZleZnaky {
    public static String spustima (String povodnyText) {
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < povodnyText.length(); k++) {
//            int asci = povodnyText.charAt(k);
            if ((povodnyText.charAt(k) == 10)||(povodnyText.charAt(k) == 13)) {
                sb.append(' ');
            } else
                sb.append(povodnyText.charAt(k));
        }
        povodnyText = sb.toString();
        povodnyText = povodnyText.replaceAll("\n", " , ").replaceAll(";", ",");
        return povodnyText;
    }
}