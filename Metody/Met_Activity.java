package Metody;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import java.io.IOException;
import java.util.ArrayList;

// ak sa zhodne kategoria, kod alebo nazov s vyhodenymi, tak tento vyrobok zmením na neaktívny, ak nie, necham activitu na "1" /aktivny produkt/
public class Met_Activity {

    public static String zistiAktivitu(String category, String code, String name, String original, String vyrobca) throws IOException {
        String active = "1";
        String subor = null;
        if (vyrobca.equals("NELLYS"))
            subor = "vyhodene_kategorie_nellys.txt";
        if (vyrobca.equals("DREVONA"))
            subor = "vyhodene_kategorie_drevona.txt";
        //kategoriu "Koberce" neriesim, davam rovno activitu na "9" /vyhodene produkty, ignore/
        if (vyrobca.equals("Tempo-Kondela")) {
//            if (category.contains("Koberce")) {
//                active = "9";
//            }
            subor = "vyhodene_kategorie_tempo.txt";
        }
//u Autronicu sa activita priebezne meni, nie je vzdy "1" pri spusteni metody, takze si vycucnem povodnu hodnotu a ak nedojde k zmene na "9", vratim povodnu
        if (vyrobca.equals("AUTRONIC")) {
            active = original;
            subor = "vyhodene_kategorie_autronic.txt";
        }

//ak som cestou zmenil activitu na "9", ani nepustam metodu poriadne, rovno vratim active "9", inak pustim normal metodu na zistenie aktivity
        if (active.equals("9"))
            return active;
        ArrayList<String> vyhodeneKategorie = Premenne.simpleFileRead(Premenne.cestaZoznam + subor);  //co je vyhodene, Active = 9
        for (int i = 0; i < vyhodeneKategorie.size(); i++) {
            String hladany = vyhodeneKategorie.get(i);
            if (hladany.equals(category) || hladany.equals(code) || hladany.equals(name)) {
                active = "9";
                break;
            }
        }
        return active;
    }

}
