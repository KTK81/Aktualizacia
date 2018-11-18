package Metody;
import java.io.IOException;
import java.util.ArrayList;

//SEDACKY hotovo


//ak je nieco v Zostavach, ci uz sedacia suprava, Obyvacia stena, Kuchynska linka, Detska izba, tak chcem, aby bola zaradena
//v nejakej kategorii, napriklad Dolna skrinka, ale zaroven chcem, aby bola zaradena aj v nejakej Zostave, napriklad Zostava Aladin
//takze, najprv priradim beznu kategoriu, Met_Category, ale potom doplnim este aj Zostavovsku kategoriu
//Hlavna kategoria nech je originalna a zostavovska nech je len doplnkova. Takze prva musi byt original.

//kategoriaOriginal je priamo v XML, kategoriaPovodna je uz nasa kategoria, ale nepridana zostavovska kategoria
public class Met_CatZostavy {
    public static String pridajZostavu(String prestaCislo, String nazov, String kod, String kategoriaOriginal, String kategoriaPovodna) throws IOException {
        String kategoria = kategoriaPovodna, pridalSom = null;
        ArrayList<Produkt> zostavyZoznam = Premenne.complexReplace(Premenne.cestaZoznam + "Zoznam_kategorie_zostavy.csv");

//podla originalnej kategore z Tempa urcim, do ktorej zostavy patri. Ak nahodou ma u mna kategoriu len v Zostave, nejaka somarina, co nie je
//v ziadnej inej normalnej Kategorii, tak to skontrolujem a ak uz je zaradena v Zostavovskej kategorii, tak neriesim, nechavam ako je

//SEDACKY funguju takto - nie su vacsinou nikde inde, len v zostavovskej kategorii, takze aj tempo ich ma dobre zaradene
        for (int i = 0; i < zostavyZoznam.size(); i++) {
            if ((kategoriaOriginal.equals(zostavyZoznam.get(i).getKod())&&!(kategoria.contains(zostavyZoznam.get(i).getMOC())))) {
                kategoria += ", "+zostavyZoznam.get(i).getMOC();
                break;
            }
        }

//a teraz vsetky tie VYNIMKY a DOPLNENIA
        //sedacia suprava BORN nema kategoriu v Tempe, ale mala by mat. Vacsina je zaradena priamo do Born kategorie, len 2 hotove sedacky su v sedackach
        if ((kod.equals("0000210903"))||(kod.equals("0000210902")))
            kategoria += ", Born, Sedačky na mieru";

        if ((kod.equals("0000186981"))||(kod.equals("0000186982"))||(kod.equals("0000186979"))||(kod.equals("0000186974")))
            kategoria += ", Gevona, Sedačky na mieru";

        if ((kod.equals("0000184894"))||(kod.equals("0000184895")))
            kategoria += ", Iza new, Sedačky na mieru";

        if ((kod.equals("0000029894"))||(kod.equals("0000026460"))||(kod.equals("0000029896"))||(kod.equals("0000015399")))
            kategoria += ", Oregon, Sedačky na mieru";


//Tak SMOLA, kopec veci je zotriedenych do zostav, ale dokonca aj Tempo to ma hodene len v konkretnej kategorii, ako jednotlive vyrobky,
//nie v zostava kategoriach. Komoda je komoda a nie Zostava Adonis

//OBYVACIE PROGRAMY
        if ((nazov.contains("ADONIS"))&&(nazov.contains("biela")))
            kategoria += ", Adonis biely";

        if ((nazov.contains("ADONIS"))&&(nazov.contains("čierna")))
            kategoria += ", Adonis čierny";

        if (nazov.contains("ARMOND"))
            kategoria += ", Armond";

        if (nazov.contains("ARTEK"))
            kategoria += ", Artek";

        if (nazov.contains("DAVIS"))
            kategoria += ", Davis dub sonoma";

        if (nazov.contains("DUNAJ"))
            kategoria += ", Dunaj";

        //zadava par produktov naviac, tak tie vyhodim priamo cez kod
        if ((nazov.contains("GRAND"))&&(!(kod.equals("0000063016"))&&!(kod.equals("0000063017"))&&!(kod.equals("0000063521"))
                    &&!(kod.equals("0000210125"))&&!(kod.equals("0000210123"))&&!(kod.equals("0000210124"))&&!(kod.equals("0000210958"))
                    &&!(kod.equals("0000210957"))))
            kategoria += ", Grand";

        if (nazov.contains("CHAMION"))
            kategoria += ", Chamion";

        if ((nazov.contains("INFINITY"))&&(nazov.contains("biely")))
            kategoria += ", Infinity jaseň biely";

        if ((nazov.contains("INFINITY"))&&(nazov.contains("jaseň svetlý")))
            kategoria += ", Infinity jaseň svetlý";

        if ((nazov.contains("INFINITY"))&&(nazov.contains("jaseň tmavý")))
            kategoria += ", Infinity jaseň tmavý";

    //LEDky na infinity zostavy
        if ((kod.equals("0000108458"))||(kod.equals("0000108456"))||(kod.equals("0000108459"))||(kod.equals("0000108455"))
                ||(kod.equals("0000108457")))
            kategoria += ", Infinity jaseň biely, Infinity jaseň tmavý, Infinity jaseň svetlý";

        if ((nazov.contains("KORA"))&&(nazov.contains("samoa king")))
            kategoria += ", Kora samoa king";

        if ((nazov.contains("LAVELI"))&&!(kod.equals("0000213152")))
            kategoria += ", Laveli";

        if (nazov.contains("LEONARDO"))
            kategoria += ", Leonardo";

        if ((nazov.contains("LIONA"))&&(!(kategoria.contains("postele"))&&!(kategoria.contains("skrine"))))
            kategoria += ", Liona";

        if ((nazov.contains("LYNATET"))&&(nazov.contains("vysoký lesk")))
            kategoria += ", Lynatet vysoký lesk";

        if ((nazov.contains("MONTANA"))&&(!(kod.equals("0000086191"))&&!(kod.equals("0000086181"))&&!(kod.equals("0000086181"))
                &&!(kod.equals("0000086179"))&&!(kod.equals("03022530"))&&!(kod.equals("0000022180"))&&!(kod.equals("03022529"))))
            kategoria += ", Montana";

        if ((nazov.contains("MONTE"))&&(!(kod.equals("0000127779"))&&!(kod.equals("0000186841"))))
            kategoria += ", Montana";

        if (nazov.contains("NOKO-SINGA"))
            kategoria += ", Noko-Singa";

        if (nazov.contains("OSLO"))
            kategoria += ", Oslo";

        if (nazov.contains("PANAMA"))
            kategoria += ", Panama";

        if ((nazov.contains("PELLO"))&&!(kod).equals("10020617"))
            kategoria += ", Pello";

//mohol by som dat, ze kategoria nesmie obsahovat Kuchyna a Spalna a mam pokoj. Ale bojim sa, aby to nevyhodilo nejake produkty, ktore chcem nechat.
        if ((nazov.contains("PROVANCE"))&&(!(kategoria.contains("Dolné skrinky"))&&!(kategoria.contains("Horné skrinky"))
                &&!(kategoria.contains("Dvierka"))&&!(kategoria.contains("postele"))&&!(kategoria.contains("Nočné"))&&!(kategoria.contains("spálne"))
                &&!(kod.equals("0000194045"))&&!(kod.equals("0000194046"))&&!(kod.equals("0000194144"))&&!(kod.equals("0000195344"))))
            kategoria += ", Provance sosna";

        if ((nazov.contains("ROYAL"))&&(!(kategoria.contains("Dolné skrinky"))&&!(kategoria.contains("Horné skrinky"))
                &&!(kategoria.contains("Dvierka"))&&!(kategoria.contains("postele"))&&!(kategoria.contains("Nočné"))&&!(kategoria.contains("spálne"))
                &&!(kod.equals("0000194045"))&&!(kod.equals("0000194046"))&&!(kod.equals("0000194144"))&&!(kod.equals("0000195344"))))
            kategoria += ", Royal sosna";

        if (nazov.contains("SPICE"))
            kategoria += ", Spice";

        if (nazov.contains("STRAGY"))
            kategoria += ", Stragy";

        if ((nazov.contains("TEDY")&&!(kategoria.contains("postele"))&&!(kategoria.contains("Nočné"))))
            kategoria += ", Tedy";

        if (nazov.contains("TOFI"))
            kategoria += ", Tofi NEW";

        if ((nazov.contains("VALERIA"))&&!(kategoria.contains("Dolné skrinky"))&&!(kategoria.contains("Horné skrinky"))&&!(kategoria.contains("Posuvné"))
                &&!(kategoria.contains("Dvierka"))&&!(kategoria.contains("postele"))&&!(kategoria.contains("Nočné"))&&!(kategoria.contains("spálne"))
                &&!(kod.equals("0000194045"))&&!(kod.equals("0000194046"))&&!(kod.equals("0000194144"))&&!(kod.equals("0000195344")))
            kategoria += ", Valeria";

        return kategoria;
    }
}