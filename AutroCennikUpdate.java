import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import Metody.Produkt;
import Metody.Premenne;
import Metody.PrestaIDRead;
/**
 * Created by jano on 31.10.2018
 */
public class AutroCennikUpdate {
    public static void  spustiProgram() throws IOException {
        //Ked zistím zmenu v cenách, tak nemá zmysel ju zadávať priamo na Produkte v BackoFfice, pretože mi ju program zmeí, pretože ceny
        //u výrobkov Autronic si musím zadávat rucne do cenniku v Exceli. Takze rozumnejsie je zmenit tu sumu v tom subore. ALE v cenniku
        // je tisic produktov, ako zmenim len 50 z nich? Ked su kade tade v tom subore rozhadzane a neviem programovat v Exceli? :)
        //Chytim, nacitam si cennik, nacitam pomocny subor s kodmi vyrobkov a spravnymi cenami, sparujem to a vyhodim novy, zmeneny, cennik.
        String code=null, price=null;
        PrintWriter writerSubor = new PrintWriter(Premenne.cestaZoznam+"autronic_cennik_modif.txt", "UTF-8");
        ArrayList<Produkt> autronic = Premenne.complexReplace(Premenne.cestaZoznam + "autronic_cennik.txt");
        ArrayList<Produkt> konkurencia = Premenne.complexReplace(Premenne.cestaZoznam + "konkurencia_cennik.txt");

//nacitanie autroCenniku
            for (int i = 0; i < autronic.size(); i++) {
                code = autronic.get(i).getKod();
                price = autronic.get(i).getMOC();
                System.out.println("1 autro kod:"+code+";price:"+price);
                for (int j=0; j<konkurencia.size(); j++) {
                   if (konkurencia.get(j).getKod().equals(code)) {
                       price = konkurencia.get(j).getMOC();
                       System.out.println("2 konku kod:"+konkurencia.get(j).getKod()+";price:"+price);
                       break;
                   }
                }
                System.out.println("3 kod konecny:"+code+"cena konecna:"+price);
                writerSubor.println(code+";"+price);
            }
            writerSubor.close();
    }
}

