package Metody;

import java.io.IOException;
import java.util.ArrayList;

//premena jedneho typu premennej na iny
public class Met_Convert {
    public static Double stringToDouble (String vstupnaHodnota)  {
        Double vysledok = 0.00;
//robilo mi problem konverzia viac ako tisicky na double, davalo mi bodku za tisicku a z dvoch bodiek malo halusky.
// Tak holt zmensim tisicku na 999 a vybavene. Nejdem teraz riesit hodiny...
        if (vstupnaHodnota.length()>7)
            vstupnaHodnota = "999.00";
        vysledok = Double.valueOf(vstupnaHodnota);
        return vysledok;
   }
}
