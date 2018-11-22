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
        //Sedacky BITER musia ist takto, nie cez ten zoznam
        if ((nazov.contains("BITER"))&&!(kategoriaPovodna.contains("Biter"))&&(nazov.contains("čierna")))
            kategoria += ", Biter";


//a teraz vsetky tie VYNIMKY a DOPLNENIA
//        sedacia suprava BORN nema kategoriu v Tempe, ale mala by mat. Vacsina je zaradena priamo do Born kategorie, len 2 hotove sedacky su v sedackach
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
//
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

        if ((nazov.contains("INFINITY"))&&(nazov.contains("jaseň tmavý"))&&!(kod.equals("0000149934")))
            kategoria += ", Infinity jaseň tmavý";

    //LEDky na infinity zostavy
        if ((kod.equals("0000108458"))||(kod.equals("0000108456"))||(kod.equals("0000108459"))||(kod.equals("0000108455"))
                ||(kod.equals("0000108457")))
            kategoria += ", Infinity jaseň biely, Infinity jaseň tmavý, Infinity jaseň svetlý, Infinity biela, Infinity svetlá, Infinity tmavá";

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


//SPALNE
        if ((nazov.contains("ARMOND"))&&!(kategoriaPovodna.contains("TV stolíky"))&&!(nazov.contains("edálenský")&&!(nazov.contains("itrína")))
                &&!(kategoriaPovodna.contains("Konferenčné stolíky"))&&!(kategoriaPovodna.contains("Armond")))
            kategoria += ", Armond";

        if ((nazov.contains("ASIENA"))&&!(kategoriaPovodna.contains("TV stolíky"))&&!(nazov.contains("edálenský")&&!(nazov.contains("itrína")))
                &&!(kategoriaPovodna.contains("Konferenčné stolíky"))&&!(kategoriaPovodna.contains("Asiena")))
            kategoria += ", Asiena";

        if ((nazov.contains("BETINO"))&&!(kategoriaPovodna.contains("TV stolíky"))&&!(nazov.contains("edálenský")&&!(nazov.contains("itrína")))
                &&!(kategoriaPovodna.contains("Konferenčné stolíky"))&&!(kategoriaPovodna.contains("Betino")))
            kategoria += ", Betino";

        if ((nazov.contains("BETTY 4"))&&!(kategoriaPovodna.contains("TV stolíky"))&&!(nazov.contains("edálenský")&&!(nazov.contains("itrína")))
                &&!(kategoriaPovodna.contains("Konferenčné stolíky"))&&!(kategoriaPovodna.contains("Betty 4")))
            kategoria += ", Betty 4";

        if ((nazov.contains("DEVON"))&&!(kategoriaPovodna.contains("TV stolíky"))&&!(nazov.contains("edálenský")&&!(nazov.contains("itrína")))
                &&!(kategoriaPovodna.contains("Konferenčné stolíky"))&&!(kategoriaPovodna.contains("Devon NEW")))
            kategoria += ", Devon NEW";

        if ((nazov.contains("FLAMENGO"))&&!(kategoriaPovodna.contains("TV stolíky"))&&!(nazov.contains("edálenský")&&!(nazov.contains("itrína")))
                &&!(kategoriaPovodna.contains("Konferenčné stolíky"))&&!(kategoriaPovodna.contains("stoly"))&&!(kategoriaPovodna.contains("sedačky"))
                &&!(kategoriaPovodna.contains("Flamengo")))
            kategoria += ", Flamengo";

        if ((nazov.contains("INFINITY"))&&!(kategoriaPovodna.contains("TV stolíky"))&&!(nazov.contains("edálenský")&&!(nazov.contains("itrína")))
                &&!(kategoriaPovodna.contains("Konferenčné stolíky"))&&!(kategoriaPovodna.contains("stoly"))&&!(kategoriaPovodna.contains("sedačky"))
                &&!(kategoriaPovodna.contains("Infinity biela"))&&(nazov.contains("biely")))
            kategoria += ", Infinity biela";
        if ((nazov.contains("INFINITY"))&&!(kategoriaPovodna.contains("TV stolíky"))&&!(nazov.contains("edálenský")&&!(nazov.contains("itrína")))
                &&!(kategoriaPovodna.contains("Konferenčné stolíky"))&&!(kategoriaPovodna.contains("stoly"))&&!(kategoriaPovodna.contains("sedačky"))
                &&!(kategoriaPovodna.contains("Infinity svetlá"))&&(nazov.contains("svetlý")))
            kategoria += ", Infinity svetlá";
        if ((nazov.contains("INFINITY"))&&!(kategoriaPovodna.contains("TV stolíky"))&&!(nazov.contains("edálenský")&&!(nazov.contains("itrína")))
                &&!(kategoriaPovodna.contains("Konferenčné stolíky"))&&!(kategoriaPovodna.contains("stoly"))&&!(kategoriaPovodna.contains("sedačky"))
                &&!(kategoriaPovodna.contains("Infinity tmavá"))&&(nazov.contains("tmavý")))
            kategoria += ", Infinity tmavá";
        if (kod.contains("0000149942")) //LEDka ku posteli
            kategoria += ", Infinity biela, Infinity svetlá, Infinity tmavá";

        if ((nazov.contains("ITALIA"))&&!(kategoriaPovodna.contains("TV stolíky"))&&!(nazov.contains("edálenský")&&!(nazov.contains("itrína")))
                &&!(kategoriaPovodna.contains("Konferenčné stolíky"))&&!(kategoriaPovodna.contains("stoly"))&&!(kategoriaPovodna.contains("sedačky"))
                &&!(kategoriaPovodna.contains("Italia")))
            kategoria += ", Italia";

        if ((nazov.contains("KORA"))&&!(kategoriaPovodna.contains("TV stolíky"))&&!(nazov.contains("edálenský")&&!(nazov.contains("itrína")))
                &&!(kategoriaPovodna.contains("Konferenčné stolíky"))&&!(kategoriaPovodna.contains("stoly"))&&!(kategoriaPovodna.contains("sedačky"))
                &&!(kategoriaPovodna.contains("Kora Samoa"))&&(nazov.contains("samoa king")))
            kategoria += ", Kora Samoa";

        if ((nazov.contains("KORA"))&&!(kategoriaPovodna.contains("TV stolíky"))&&!(nazov.contains("edálenský")&&!(nazov.contains("itrína")))
                &&!(kategoriaPovodna.contains("Konferenčné stolíky"))&&!(kategoriaPovodna.contains("stoly"))&&!(kategoriaPovodna.contains("sedačky"))
                &&!(kategoriaPovodna.contains("Kora Sosna"))&&(nazov.contains("sosna andersen")))
            kategoria += ", Kora Sosna";

        if ((nazov.contains("LAVELI"))&&!(kategoriaPovodna.contains("TV stolíky"))&&!(nazov.contains("edálenský")&&!(nazov.contains("itrína")))
                &&!(kategoriaPovodna.contains("Konferenčné stolíky"))&&!(kategoriaPovodna.contains("stoly"))&&!(kategoriaPovodna.contains("sedačky"))
                &&!(kategoriaPovodna.contains("Laveli biela")))
            kategoria += ", Laveli biela";

        if ((nazov.contains("LIONA"))&&!(kategoriaPovodna.contains("TV stolíky"))&&!(nazov.contains("edálenský")&&!(nazov.contains("itrína")))
                &&!(kategoriaPovodna.contains("Konferenčné stolíky"))&&!(kategoriaPovodna.contains("stoly"))&&!(kategoriaPovodna.contains("sedačky"))
                &&!(kategoriaPovodna.contains("Liona biela")))
            kategoria += ", Liona biela";

        if ((nazov.contains("LUMERA"))&&!(kategoriaPovodna.contains("TV stolíky"))&&!(nazov.contains("edálenský"))&&!(kategoriaPovodna.contains("Skrinky a vitríny"))
                &&!(kategoriaPovodna.contains("Konferenčné stolíky"))&&!(kategoriaPovodna.contains("stoly"))&&!(kategoriaPovodna.contains("sedačky"))
                &&!(kategoriaPovodna.contains("obývačky"))&&!(kategoriaPovodna.contains("Kúpeľňové"))&&!(kategoriaPovodna.contains("Predsieňové"))
                &&!(kategoriaPovodna.contains("panely"))&&!(kategoriaPovodna.contains("Lumera")))
            kategoria += ", Lumera";

        if ((nazov.contains("LYNATET"))&&!(kategoriaPovodna.contains("TV stolíky"))&&!(nazov.contains("edálenský"))&&!(kategoriaPovodna.contains("Skrinky a vitríny"))
                &&!(kategoriaPovodna.contains("Konferenčné stolíky"))&&!(kategoriaPovodna.contains("stoly"))&&!(kategoriaPovodna.contains("sedačky"))
                &&!(kategoriaPovodna.contains("obývačky"))&&!(kategoriaPovodna.contains("Kúpeľňové"))&&!(kategoriaPovodna.contains("Predsieňové"))
                &&!(kategoriaPovodna.contains("panely"))&&!(kategoriaPovodna.contains("Lynatet lesk")))
            kategoria += ", Lynatet lesk";

        if ((nazov.contains("MARTINA"))&&!(kategoriaPovodna.contains("TV stolíky"))&&!(nazov.contains("edálenský")&&!(nazov.contains("itrína")))
                &&!(kategoriaPovodna.contains("Konferenčné stolíky"))&&!(kategoriaPovodna.contains("stoly"))&&!(kategoriaPovodna.contains("sedačky"))
                &&!(kategoriaPovodna.contains("Martina")))
            kategoria += ", Martina";

        if ((nazov.contains("MEDIOLAN"))&&!(kategoriaPovodna.contains("TV stolíky"))&&!(nazov.contains("edálenský")&&!(nazov.contains("itrína")))
                &&!(kategoriaPovodna.contains("Konferenčné stolíky"))&&!(kategoriaPovodna.contains("stoly"))&&!(kategoriaPovodna.contains("sedačky"))
                &&!(kategoriaPovodna.contains("Mediolan")))
            kategoria += ", Mediolan";

        if ((nazov.contains("MIRAN"))&&!(kategoriaPovodna.contains("TV stolíky"))&&!(nazov.contains("edálenský")&&!(nazov.contains("itrína")))
                &&!(kategoriaPovodna.contains("Konferenčné stolíky"))&&!(kategoriaPovodna.contains("stoly"))&&!(kategoriaPovodna.contains("sedačky"))
                &&!(kategoriaPovodna.contains("Miran dub")))
            kategoria += ", Miran dub";

        if ((nazov.contains("MONTE"))&&!(kategoriaPovodna.contains("TV stolíky"))&&!(nazov.contains("edálenský")&&!(nazov.contains("itrína")))
                &&!(kategoriaPovodna.contains("Konferenčné stolíky"))&&!(kategoriaPovodna.contains("stoly"))&&!(kategoriaPovodna.contains("sedačky"))
                &&!(kategoriaPovodna.contains("Monte dub"))&&!(kategoriaPovodna.contains("Obývacie"))&&!(kategoriaPovodna.contains("Predsieň")))
            kategoria += ", Monte dub";

        if ((nazov.contains("PROVANCE"))&&!(kategoriaPovodna.contains("TV stolíky"))&&!(nazov.contains("edálenský"))&&!(kategoriaPovodna.contains("Skrinky a vitríny"))
                &&!(kategoriaPovodna.contains("Konferenčné stolíky"))&&!(kategoriaPovodna.contains("stoly"))&&!(kategoriaPovodna.contains("sedačky"))
                &&!(kategoriaPovodna.contains("obývačky"))&&!(kategoriaPovodna.contains("Kúpeľňové"))&&!(kategoriaPovodna.contains("Predsieňové"))
                &&!(kategoriaPovodna.contains("panely"))&&!(kategoriaPovodna.contains("Provance spálňa"))&&!(kategoriaPovodna.contains("Dolné skrinky"))
                &&!(kategoriaPovodna.contains("Horné skrinky"))&&!(kategoriaPovodna.contains("Dvierka"))&&!(kategoriaPovodna.contains("Potravinové")))
            kategoria += ", Provance spálňa";

        if ((nazov.contains("REKATO"))&&!(kategoriaPovodna.contains("TV stolíky"))&&!(nazov.contains("edálenský")&&!(nazov.contains("itrína")))
                &&!(kategoriaPovodna.contains("Konferenčné stolíky"))&&!(kategoriaPovodna.contains("stoly"))&&!(kategoriaPovodna.contains("sedačky"))
                &&!(kategoriaPovodna.contains("Rekato"))&&!(kategoriaPovodna.contains("Obývacie")))
            kategoria += ", Rekato";

        if ((nazov.contains("ROYAL"))&&!(kategoriaPovodna.contains("TV stolíky"))&&!(nazov.contains("edálenský"))&&!(kategoriaPovodna.contains("Skrinky a vitríny"))
                &&!(kategoriaPovodna.contains("Konferenčné stolíky"))&&!(kategoriaPovodna.contains("stoly"))&&!(kategoriaPovodna.contains("sedačky"))
                &&!(kategoriaPovodna.contains("obývačky"))&&!(kategoriaPovodna.contains("Kúpeľňové"))&&!(kategoriaPovodna.contains("Predsieňové"))
                &&!(kategoriaPovodna.contains("panely"))&&!(kategoriaPovodna.contains("Dolné skrinky"))&&!(kategoriaPovodna.contains("Dvierka"))
                &&!(kategoriaPovodna.contains("Horné skrinky"))&&!(kategoriaPovodna.contains("Royal spálňa")))
            kategoria += ", Royal spálňa";

        if ((nazov.contains("SIRIUS"))&&!(kategoriaPovodna.contains("TV stolíky"))&&!(nazov.contains("edálenský")&&!(nazov.contains("itrína")))
                &&!(kategoriaPovodna.contains("Konferenčné stolíky"))&&!(kategoriaPovodna.contains("stoly"))&&!(kategoriaPovodna.contains("sedačky"))
                &&!(kategoriaPovodna.contains("Sirius")))
            kategoria += ", Sirius";

        if ((nazov.contains("TEDY"))&&!(kategoriaPovodna.contains("TV stolíky"))&&!(nazov.contains("edálenský")&&!(nazov.contains("itrína")))
                &&!(kategoriaPovodna.contains("Konferenčné stolíky"))&&!(kategoriaPovodna.contains("stoly"))&&!(kategoriaPovodna.contains("sedačky"))
                &&!(kategoriaPovodna.contains("Tedy dub")))
            kategoria += ", Tedy dub";

        if ((nazov.contains("VALERIA"))&&!(kategoriaPovodna.contains("TV stolíky"))&&!(nazov.contains("edálenský")&&!(nazov.contains("itrína")))
                &&!(kategoriaPovodna.contains("Konferenčné stolíky"))&&!(kategoriaPovodna.contains("stoly"))&&!(kategoriaPovodna.contains("sedačky"))
                &&!(kategoriaPovodna.contains("Valeria dub")))
            kategoria += ", Valeria dub";


//KUPELNA
        if ((nazov.contains("GALENA"))&&!(kategoriaPovodna.contains("Galena")))
            kategoria += ", Galena";

        if ((nazov.contains("HENRY"))&&!(kategoriaPovodna.contains("Henry")))
            kategoria += ", Henry";

        if ((nazov.contains("KEIT"))&&!(kategoriaPovodna.contains("Keit")))
            kategoria += ", Keit";

        if ((nazov.contains("KIARA"))&&!(kategoriaPovodna.contains("Kiara")))
            kategoria += ", Kiara";

        if ((nazov.contains("LESSY"))&&!(kategoriaPovodna.contains("Lessy pololesk")))
            kategoria += ", Lessy pololesk";

        if ((nazov.contains("LYNATET"))&&!(kategoriaPovodna.contains("Lynatet extra lesk"))&&(kategoriaPovodna.contains("Kúpeľňové")))
            kategoria += ", Lynatet extra lesk";
        //Osvetlenie
        if (kod.equals("0000064091"))
            kategoria += ", Lynatet extra lesk";

        if ((nazov.contains("OLIVIA"))&&!(kategoriaPovodna.contains("Olivia"))&&(kategoriaPovodna.contains("Kúpeľňové")))
            kategoria += ", Olivia";
        //Umyvadlo
        if (kod.equals("0000087969"))
            kategoria += ", Olivia";

//PREDSIEN
        if ((nazov.contains("DERBY"))&&!(kategoriaPovodna.contains("Derby")))
            kategoria += ", Derby";

        if ((nazov.contains("LISSI"))&&!(kategoriaPovodna.contains("Lissi")))
            kategoria += ", Lissi";

        if ((nazov.contains("LYNATET"))&&!(kategoriaPovodna.contains("Lynatet"))&&((kategoriaPovodna.contains("Zrkadlá"))||
                (kategoriaPovodna.contains("Vešiakové"))||(nazov.contains("Visiaca"))||(kategoriaPovodna.contains("Predsieňové"))||
                (kategoriaPovodna.contains("Komody)"))))
            kategoria += ", Lynatet";

        if ((nazov.contains("MARIANA"))&&!(kategoriaPovodna.contains("Mariana")))
            kategoria += ", Mariana";

        if ((nazov.contains("Mario"))&&!(kategoriaPovodna.contains("Mario"))&&((kategoriaPovodna.contains("Botníky"))||
                (kategoriaPovodna.contains("Vešiakové"))||(kategoriaPovodna.contains("Komody"))||(kategoriaPovodna.contains("skrine"))))
            kategoria += ", Mario";

        if ((nazov.contains("MECKI"))&&!(kategoriaPovodna.contains("Mecki New")))
            kategoria += ", Mecki New";

        if ((nazov.contains("PROVENSAL"))&&!(kategoriaPovodna.contains("Provensal")))
            kategoria += ", Provensal";

        if ((nazov.contains("RACHEL"))&&!(kategoriaPovodna.contains("Rachel"))&&!(kategoriaPovodna.contains("obývačky")))
            kategoria += ", Rachel";

        if ((nazov.contains("SPACE"))&&!(kategoriaPovodna.contains("Space")))
            kategoria += ", Space";

//KANCELARIA
        if ((nazov.contains("TEMPO ASISTENT"))&&(nazov.contains("bardolino")))
            kategoria += ", ASISTENT NEW bardolino";

        if ((nazov.contains("TEMPO ASISTENT"))&&(nazov.contains("buk")))
            kategoria += ", ASISTENT NEW buk";

        if ((nazov.contains("TEMPO ASISTENT"))&&(nazov.contains("čerešňa")))
            kategoria += ", ASISTENT NEW čerešňa";

        if ((nazov.contains("TEMPO ASISTENT"))&&(nazov.contains("sonoma")))
            kategoria += ", ASISTENT NEW dub sonoma";

        if ((nazov.contains("GRAND"))&&!(kategoriaPovodna.contains("Jedálenské"))&&!(kategoriaPovodna.contains("postele"))
                &&!(kategoriaPovodna.contains("Nočné"))&&!(kategoriaPovodna.contains("Posuvné"))&&!(kategoriaPovodna.contains("sedačky"))
                &&!(kategoriaPovodna.contains("Rohové")))
            kategoria += ", Grand dub sonoma";

        if ((nazov.contains("JOHAN"))&&(nazov.contains("slivka")))
            kategoria += ", Johan slivka";

        if ((nazov.contains("JOHAN"))&&(nazov.contains("sonoma")))
            kategoria += ", Johan dub sonoma";

        if ((nazov.contains("JOHAN"))&&(nazov.contains("biela"))&&!(nazov.contains("slivka"))&&!(nazov.contains("sonoma")))
            kategoria += ", Johan biela";

        if (nazov.contains("MAURUS"))
            kategoria += ", Maurus New";

        if (nazov.contains("OSCAR"))
            kategoria += ", Oscar";

        if ((nazov.contains("TOFI"))&&!(kategoriaPovodna.contains("Dvierka")))
            kategoria += ", Tofi";


//DETSKA IZBA
//        DIEVCENSKA
        if (((kategoriaOriginal.contains("Dievčenská izba"))&&(nazov.contains("EGO")))||(kod.equals("06021813"))||(kod.equals("06022132"))
                ||(kod.equals("06022129"))||(kod.equals("06022130")))
            kategoria += ", Ego fialová";

        if (((kategoriaOriginal.contains("Dievčenská izba"))&&(nazov.contains("EMIO")))||(kod.equals("0000083798"))||(kod.equals("0000083797"))
                ||(kod.equals("0000083786")))
            kategoria += ", Emio biela";

        if (((kategoriaOriginal.contains("Dievčenská izba"))&&(nazov.contains("LOBETE")))||(kod.equals("0000071583"))||(kod.equals("0000071580"))
                ||(kod.equals("0000071581"))||(kod.equals("0000071582")))
            kategoria += ", Lobete";

        if (((kategoriaOriginal.contains("Dievčenská izba"))&&(nazov.contains("PIERE"))&&(nazov.contains("fialová")))||(kod.equals("0000087639"))
                ||(kod.equals("0000087638"))||(kod.equals("0000087634"))||(kod.equals("0000087632"))||(kod.equals("0000087630"))||(kod.equals("0000087631")))
            kategoria += ", Piere fialová";

        if (((kategoriaOriginal.contains("Dievčenská izba"))&&(nazov.contains("SVEND")))||(kod.equals("0000185919"))||(kod.equals("0000185916"))
                ||(kod.equals("0000185923")))
            kategoria += ", Svend zelená, Svend červená, Svend";
//CHLAPCENSKA

        if (((kategoriaOriginal.contains("Chlapčenská izba"))&&(nazov.contains("EGO")))||(kod.equals("06024749"))||(kod.equals("06023898"))
                ||(kod.equals("06023900"))||(kod.equals("06023901")))
            kategoria += ", Ego zelená";

        if (((kategoriaOriginal.contains("Chlapčenská izba"))&&(nazov.contains("EMIO")))||(kod.equals("0000083812"))||(kod.equals("0000083817")))
            kategoria += ", Emio zelená";

        if (((kategoriaOriginal.contains("Chlapčenská izba"))&&(nazov.contains("CHAMION"))))
            kategoria += ", Chamion";

        if (((kategoriaOriginal.contains("Chlapčenská izba"))&&(nazov.contains("LOTTY")))||(kod.equals("0000185691"))||(kod.equals("0000185688"))
                ||(kod.equals("0000185687"))||(kod.equals("0000185685")))
            kategoria += ", Lotty";

        if (((kategoriaOriginal.contains("Chlapčenská izba"))&&(nazov.contains("ORESTES")))||(kod.equals("0000210481"))||(kod.equals("0000210480"))
                ||(kod.equals("0000210486"))||(kod.equals("0000210485")))
            kategoria += ", Orestes, Orestes dub";

        if (((kategoriaOriginal.contains("Chlapčenská izba"))&&(nazov.contains("PIERE")))||(kod.equals("0000087639"))||(kod.equals("0000087638"))
                ||(kod.equals("0000087634"))||(kod.equals("0000087648"))||(kod.equals("0000087646"))||(kod.equals("0000087647")))
            kategoria += ", Piere zelená";

        if (nazov.contains("TIDY"))
            kategoria += ", Tidy, Tidy biela";

//PRE NAJMENSICH
        if (((kategoriaOriginal.contains("Nábytok pre najmenších"))&&(nazov.contains("ANGEL")))||(kod.equals("0000109401"))||(kod.equals("0000107272"))
                ||(kod.equals("0000107267"))||(kod.equals("0000107271"))||(kod.equals("0000107265"))||(kod.equals("0000107266"))||(kod.equals("0000107264"))
                ||(kod.equals("0000107268"))||(kod.equals("0000107270"))||(kod.equals("0000194416")))
            kategoria += ", Angel";

        if (((kategoriaOriginal.contains("Nábytok pre najmenších"))&&(nazov.contains("PUFF")))||(kod.equals("0000042437")))
            kategoria += ", Puff";

//STUDENTSKA IZBA
        if (nazov.contains("DIAZ"))
            kategoria += ", Diaz";

        if ((nazov.contains("MARSIE"))&&!(kategoria.contains("stolíky")))
            kategoria += ", Marsie";

        if (nazov.contains("OSCAR"))
            kategoria += ", Oscar";

        if (nazov.contains("OSLO"))
            kategoria += ", Oslo";

        if (nazov.contains("NOKO-SINGA"))
            kategoria += ", Singa";

        if (nazov.contains("TEYO"))
            kategoria += ", Teyo";

        if ((nazov.contains("VALERIA"))&&!(kod.equals("0000146210"))&&!(kod.equals("0000064109")))
            kategoria += ", Valeria";

        if (nazov.contains("WINIE"))
            kategoria += ", Winie";

        return kategoria;
    }
}