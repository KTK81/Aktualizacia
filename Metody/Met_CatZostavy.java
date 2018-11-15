package Metody;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.io.IOException;
import java.util.ArrayList;

//ak je nieco v Zostavach, ci uz sedacia suprava, Obyvacia stena, Kuchynska linka, Detska izba, tak chcem, aby bola zaradena
//v nejakej kategorii, napriklad Dolna skrinka, ale zaroven chcem, aby bola zaradena aj v nejakej Zostave, napriklad Zostava Aladin
//takze, najprv priradim beznu kategoriu, Met_Category, ale potom doplnim este aj Zostavovsku kategoriu
//Hlavna kategoria nech je originalna a zostavovska nech je len doplnkova. Takze prva musi byt original.
public class Met_CatZostavy {
    public static String pridajZostavu(String PrestaCislo, String nazov, String kod, String kategoriaOriginal, String meno) throws IOException {
        String kategoria = kategoriaOriginal;

        //ak vyrobok obsahuje v nazve zadany text, plus uz nie je zaradeny v Zostave /ze je nepouzitelny inde, len v Sektore, tak ma primarne zaradenu
        //kategoriu Zostavy, tak mu logicky nechcem kategoriu Zostavy priradit znova

//SEDACKY - OBYVACKA
        //15.11. hotovo
//        if ((meno.contains("BITER"))&&!(kategoria.contains("Biter"))) {
//            kategoria += ", Biter, Sedačky na mieru";
//        }
//        if ((meno.contains("BORN"))&&!(kategoria.contains("Born"))) {
//            kategoria += ", Born, Sedačky na mieru";
//        }
//        //pri Ermo je problem, zopar divnych produktov sa tam primiesava, tak som to este obmedzil na Sedacie supravy
//        if ((meno.contains("ERMO"))&&(kategoria.contains("Sedacie súpravy"))&&!(kategoria.contains("Ermo"))) {
//            kategoria += ", Ermo, Sedačky na mieru";
//        }
//        if ((meno.contains("GEVONA"))&&!(kategoria.contains("Gevona"))) {
//            kategoria += ", Gevona, Sedačky na mieru";
//        }
//        //pri tejto kategorii je problem, zopar divnych produktov sa tam primiesava, tak som to este obmedzil dalsimi podmienkami
//        if ((meno.contains("IZA"))&&!(kategoria.contains("Iza new"))&&(kategoria.contains("Obývačka"))&&!(kategoria.contains("kreslá"))) {
//            kategoria += ", Iza new, Sedačky na mieru";
//        }
//        if ((meno.contains("MARIETA"))&&!(kategoria.contains("Marieta"))&&!(meno.contains("žltá"))&&!(kategoria.contains("kreslá"))) {
//            kategoria += ", Marieta, Sedačky na mieru";
//        }
//        if ((meno.contains("OREGON"))&&!(kategoria.contains("Oregon"))) {
//            kategoria += ", Oregon, Sedačky na mieru";
//        }
//        if ((meno.contains("ROSANA"))&&!(kategoria.contains("Rosana"))) {
//            kategoria += ", Rosana, Sedačky na mieru";
//        }
//        if ((meno.contains("RUBA"))&&!(kategoria.contains("Ruba"))&&!(meno.contains("cream"))) {
//            kategoria += ", Ruba, Sedačky na mieru";
//        }

//KUCHYNSKE LINKY
//        if ((meno.contains("IRYS"))&&!(kategoria.contains("Irys"))) {
//            kategoria += ", Irys";
//        }
//
//        if ((meno.contains("NOVA PLUS"))&&!(kategoria.contains("Nova Plus"))&&!(meno.contains("biel"))) {
//            kategoria += ", Nova Plus dub sonoma";
//        }

//        if ((meno.contains("NOVA PLUS"))&&!(kategoria.contains("Nova Plus"))&&(meno.contains("biel"))) {
//            kategoria += ", Nova Plus biela";
//        }
        if ((meno.contains("VEGA"))&&!(kategoria.contains("Vega"))&&(kategoria.contains("Kuchynské linky"))) {
            kategoria += ", Vega";
            System.out.println(PrestaCislo+";"+kod+";"+nazov+" ***** "+kategoriaOriginal+" ***** "+kategoria);
        }




//        System.out.println(PrestaCislo+";"+kod+";"+nazov+";   ;"+kategoriaOriginal+";   ;"+kategoria);
        return kategoria;
    }
}

