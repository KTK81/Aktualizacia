package Metody;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import XML.*;


//ak je nieco v Zostavach, ci uz sedacia suprava, Obyvacia stena, Kuchynska linka, Detska izba, tak chcem, aby bola zaradena
//v nejakej kategorii, napriklad Dolna skrinka, ale zaroven chcem, aby bola zaradena aj v nejakej Zostave, napriklad Zostava Aladin
//takze, najprv priradim beznu kategoriu, Met_Category, ale potom doplnim este aj Zostavovsku kategoriu
//Hlavna kategoria nech je originalna a zostavovska nech je len doplnkova. Takze prva musi byt original.
public class Met_CatZostavy {
    public static String pridajZostavu(String prestaCislo, String nazov, String kod, String kategoriaOriginal, String kategoriaPovodna) throws IOException {
        String kategoria = kategoriaPovodna, pridalSom = null;
        ArrayList<Produkt> zostavyZoznam = Premenne.complexReplace(Premenne.cestaZoznam + "Zoznam_kategorie_zostavy.csv");


//podla originalnej kategore z Tempa urcim, do ktorej zostavy patri. Ak nahodou ma u mna kategoriu len v Zostave, nejaka somarina, co nie je
//v ziadnej inej normalnej Kategorii, tak to skontrolujem a ak uz je zaradena v Zostavovskej kategorii, tak neriesim, nechavam ako je
        for (int i = 0; i < zostavyZoznam.size(); i++) {
            if ((kategoriaOriginal.equals(zostavyZoznam.get(i).getKod())&&!(kategoria.contains(zostavyZoznam.get(i).getMOC())))) {
//                pridalSom = ", "+zostavyZoznam.get(i).getMOC();
                kategoria += ", "+zostavyZoznam.get(i).getMOC();
//                System.out.println(prestaCislo+";"+kod+";"+kategoriaOriginal+" ***** "+kategoria);

                break;
            }
        }

//a teraz vsetky tie VYNIMKY a DOPLNENIA
        //sedacia suprava BORN nema kategoriu v Tempe, ale mala by mat
        if ((nazov.contains("BORN"))&&!(kategoria.contains("Born"))) {
            kategoria += ", Born, Sedačky na mieru";
        }





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
//        nerezovy dres, je vsade
//        if (kod.equals("04000499"))
//            kategoria += ", Irys, Nova Plus dub sonoma, Nova Plus biela, Vega, Prado červená vysoký lesk";

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


//        if ((meno.contains("VEGA"))&&!(kategoria.contains("Vega"))&&(kategoria.contains("Kuchynské linky"))) {
//            kategoria += ", Vega";
//        }
        //kombinacia, horne su cervene, spodne su sive
//        if (((meno.contains("PRADO"))&&!(kategoria.contains("Prado červená vysoký lesk"))&&(meno.contains("červený")))
//        || (((meno.contains("PRADO"))&&!(kategoria.contains("Prado červená vysoký lesk"))&&(meno.contains("dolná")))&&(meno.contains("sivá"))&&!(meno.contains("biela")))
//        ||(kod.equals("0000112483"))) {
//            kategoria += ", Prado červená vysoký lesk";
//            pomocnyString = "pradoCervena";
//            System.out.println(PrestaCislo+";"+kod+";"+nazov+" ***** "+kategoriaOriginal+" ***** "+kategoria);
//    }


//        System.out.println(PrestaCislo+";"+kod+";"+nazov+";   ;"+kategoriaOriginal+";   ;"+kategoria);
//        return kategoria;
//    }
//}



        return kategoria;
    }
}