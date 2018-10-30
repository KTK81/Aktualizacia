import Metody.Premenne;
import Metody.PrestaIDRead;
import Metody.Produkt;

import java.util.ArrayList;
import java.util.Scanner;
// Program, ktory nacita udaje zo suboru PrestaID, ktory vygeneruje platforma Prestashop
// PrestaID obsahuje teda aktualny stav stranok. Jednotlive XML obsahuju to, ako by to malo vyzerat. Aktualizacia je rozdiel.
//2.3.gfhfgh



/*
OTAZKY :
0. ako by riesil moje zoznamy. Ze WT je biela, ze š80 je 80 cm sirka, externe v txt? ci tak, ako robim? ci ako?
1. Ako nacitam PrestaIDRead iba raz a potom uz cucam len nacitany Arraylist opakovane v roznych inych metodach a classoch? A nie vzdy nanovo cely robit.
2. sql, aby v jednom subore vycuclo sucasne sql + feature sql, co teraz riesim skrz 2 rozne subory. Neviem vycucnut data z 3 nesuvisiacich tabuliek, len z 2
3. ked mam Arraylist, typu Produkt /nejaka Class proste/ o 2 Stringoch a 2 integeroch a nasledne v uplne inej veci, potrebujem ArrayList o 2 Stringoch a 2 integeroch
//ako sa to robi? Lebo v prvom pouzivam nejake getters a tie maju nazvy, ale zrazu v druhom su uplne ine veci. A potom sa stava, ze
// pomocou .getName vytahujem adresu a pomocou .getFarba vytahujem email
4. ako riesi, ked hlada v arrayliste a ak to ten arraylist NEobsahuje, tak daco. Ja to riesim pomocou premennej, napr. pomocna = 0 a ak to najde, zmenim na
pomocna = 1. Ak je na konci 0, viem, ze nenaslo. Google tvrdi : Contains asi nie, lebo vzdy nenajde, iba raz najde. indexOf a ak nevyhodi -1, tak to tam bolo.
5. debugger. Napr. zmenim nieco v Classe NoveProdukty, napr. horne menu, zmenim poradie textov, ako to viem poriesit, nech necakam na cely program?

23.2.
malepuravy v tempodownpic - stahovanie obrazkov k produktom

11.2.2018
uprava TempoDownPic - stiahnutie pripojenych obrazkov - nejake drobnosti

Koniec roka 2017
Rozne drobnosti, hlavne dokoncenie vychytania Novych produktov z DREVONY. Uz to celkom slusne robi automaticky vsetky potrebne udaje.
Takze VSETKO dobre do roku 2018. :)

25.12.
Celkom velke prekopanie zakladov - po novom kazdu cast informacii o produkte - popis, farbu, dostupnost, hocico, co sa necuca priamo z XML v konecnej podobe,
riesim cez jednotlive Metode, takze tak, ako sa to ma. Doteraz som to riesil priamo v jednotlivych Classoch, v XMLTempe, XMLDrevone atd.
Taktiez jednotlive udaje, zoznamy, farba, meno, atd, cucam z externych suborov, Zoznamov, doteraz to bolo nasackovane priamo v Classoch.


12.12.
Prva cast Met_Dimensions - zistovanie rozmerov novych vyrobkov, zatial DREVONA, celkom dost vyrobkov uz odchytenych, taky primitivny regex s If then  :))),
este potrebn dorobit, myslim DREVONU, ostatne ani nehovoriac


21.11.
Met_Color - metoda na zistenie farby, zatial DREVONA a AUTRONIC, na to nadvazuje
Met_name metoda na zistenie nazvu /nasho nazvu = upravi povodny nazov/, zatial DREVONA

19.11.
Velke prerobenie zakladov - KompletAll cast, porovnanie starych a novych veci, sa riesilo priamo do konecneho xml suboru. Po novom
sa to najprv nahadze do arrylistu a az nasledne sa to vypise - lepsia kontrola nad konecnou podobou. Taktiez v casti Produkt,
hlavny Class, som pomenil hlavne vystupy, getters, kazde porovnavanie malo svoj getter /porovnanie Dostupnosti, Ceny, .../, teraz
je len jeden getter a ten ma atribut a skrz ten atribut sa urci, co sa porovnava. Od toho zalezi, co ide do konecneho suboru, vysledok.

10.11.
Tak Bedo chcel, aby defaultn vahu nedavalo 1kg, ale aby davalo, ze "Neurcena", tak som to pomenil

23.10.
Autronic, pri Vahe bol problem, ze bralo udaj z XML Hmotnost, ale tento element nebol u kazdeho vyrobku a ked niekde bol a potom
u dalsich nebol, tak si ponechalo predchadzajuci udaj, takze nespravny udaj. Takze osetrene, pri kazdom cykle sa prida do
premennej vahaInt = 1; a ak sa neskor v cykle zjavi a ma inu hodnotu, tak sa prepise. Ak sa neobjavi, ostane kilo ako defaultna hodnota.

23.10.
U Drevony sa zmenila dlhodoba dostupnost z 2-4 tyzdne na 6-8 tyzdnov. Ale pri porovnavani Dostupnosti, som hladal konkretne cisla, konkretne
som hladal "2", co sa po novom uz v Dostupnosti neobjavovalo, uz sa objavovalo "6-8", takze nic nenaslo a robilo to bludy.
Tak to treba prehodit, aby to hladalo priamo String, Premennu, napr. Premenne.feature4tyzdne ako zastupcu dlhodobej Dostupnosti a ked
sa nieco zmeni, tak uz bude hladat rovno zmenene. Neist cez HardCoding.

21.6.2017
Autronic davalo do feedu namiesto "SKL" "SKL CZ", tak po novom riesi aj "SKL CZ"

29.5.2017
- Drevona, ked nie je skladom a Delivery date vo Feede je ine, ako 4 tyzdne, napr. "Dodanie v 8. mesiaci" alebo "Do vypredania zasob",
tak mu dam dostupnost viac ako mesiac a sajonara. Doteraz pozeralo len na sklady a tak ked nebolo skladom, dalo mu
Dostupnost : 2-4 tyzdne a ono to pritom mohlo mat "Dodanie za 3 mesiace"

23.5.2017
- osetril som v XMLDrevona ceny 0.00 z Drevona Feedu - ignoruje ich a da namiesto nich predchadzajucu cenu, ktoru sme mali
- Autronic, cuca ceny vyrobkov z PrestaID, ale ak ich najde v subore autronic_cennik.txt, tak ju prepise a potom este pozera na
subor autronic_akcia, odkial cuca akciove ceny a tymi prepisuje bezne autronic_cennik ceny
 Mrte dlho, hodiny, mi zabralo, kym som osetril ciarky a bodky v desatinnych miestach, aby sa to naslo medzi sebou
 A potom sa to aj tak znova pokazilo, takze !!! zmena z Double na BigDecimal !!!

1.8.2
NIC som nemenil, len informacne - Autronic z nicoho nic zmenil format datumu, mesiac nema nulu pred cislom, ak je len jedno cislo, cize
 bolo napr. 16.02.2017 a uz je 16.2.2017 - tak vyhodilo vela vyrobkov, ze chce menit datum a bol rovnaky, ako predtym, som hladal chybu a ona ziadna nie je.

1.8.1
porovnavanie DOSTUPNOSTI, bol zle uvedeny nazov vyrobcu, ako Autronic, pricom je AUTRONIC = nefungovalo to, nevedelo sa, ze nefunguje

1.8
zmena v porovnavani AKTIVITY v KompletALL
Nemal som pokryte, ak vyrobok, ktory mame /PrestaID/, nenaslo zhodu s niektorym vyrobkom od dodavatelov /produktAll/.
Takze som to len doplnil o toto, aby ho vtedy deaktivovalo, Aktivity = 0.
 */

public class HLAVNA {
    public static void main(String[] args) throws Exception {
        System.out.println("Vyber programu :");
//		System.out.println("a = HelloWorld - vypise text");
//		System.out.println("b = HelloWorld - pevne zadany text - static method");
        System.out.println("Ako sa aktivuje ||| Co robi ||| Subor odkial nacita udaje ||| Subor s vysledkom");
        System.out.println("0 = KOMPLET aktualizacia ||| *** ||| Google - JAVA - subory - aktualizacia_new.csv");
//		System.out.println("1 = Tempo zasoby");
//		System.out.println("2 = Drevona zasoby");
//		System.out.println("3 = Autronic zasoby, pou��va� NOTEPAD PLUS, nie oby�ajn�");
        System.out.println("5 = ZistiPrestaID - priradi ku KOD-u nase PrestaIDcko ||| Google - JAVA - subory - zistiIDckaImport.txt ||| Google - JAVA - subory - zistiIDckaPrestaVysledok.txt");
        System.out.println("n = Najdi nove produkty");
        System.out.println("s = Stiahni obrazky z Drevony");
        System.out.println("do = Stiahni VSETKY obrazky z Tempa - aktivne produkty");
        System.out.println("");
        System.out.println("!!! Aktualizuj PrestaID subor !!!");

        Scanner readerUser = new Scanner(System.in);
        String vyberUser = readerUser.next();
        switch (vyberUser) {
            case "0":
                KompletALL.spustiProgram();
                break;
/*		case "1": 
*			TempoKomplet.spustiTempo();
*			break;
*/
            case "2":
                XMLDrevona2017.zapisProduktov();
                break;
/*		case "3":
*			AutronicKomplet.spustiAutronic();
*			break;
*/
            case "5":
                ZistiIDPresta.spustiIDPresta();
                break;
            case "n":
                NoveProdukty.spustiHladanie();
                break;
            case "s":
                DownPicDrevona.stiahniOBR();
                break;
            case "d":
                StatisticDrevona.spustiVsetko();
                break;
            case "p":
                PorovnanieCien.spusti();
                break;
            case "do":
                DownPicAll.zapisProduktov();
                break;
            case "pomoc":
                PomocnyClassHocico.zapisProduktov();
                break;


        }
        readerUser.close();
    }
}