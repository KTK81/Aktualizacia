import javax.management.modelmbean.RequiredModelMBean;
import java.io.*;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import Metody.Produkt;
import Metody.Premenne;
import XML.*;
/**
 * Created by jano on 09.11.2016.
 * nacita zo suboru zoznam kodov vyrobkov, z XML si nacita odkaz na obrazok k produktu a z toho odkazu ho ulozi na disk
 * a nazov suboru da taky, aky je kod vyrobku
 */
public class DownPicDrevona {
    public static void stiahniOBR() throws IOException {
        ArrayList<Produkt> produktyAutronic = new ArrayList<>();
        ArrayList<Produkt> produktyDrevona = new ArrayList<>();
        ArrayList<Produkt> produktyTempo = new ArrayList<>();
        ArrayList<Produkt> produktyALL = new ArrayList<>();
        ArrayList<String> zoznamKodov = new ArrayList<String>();
        produktyDrevona = XMLDrevona2017.zapisProduktov();
//        produktyAutronic = XMLAutronic.zapisProduktov();
        produktyTempo = XMLTempo.zapisProduktov();
//        produktyALL.addAll(produktyDrevona);

        produktyALL.addAll(produktyDrevona);

     File fileImport = new File(Premenne.cesta+"stiahniObrazok.txt");
        try {
            Scanner scannerNovy = new Scanner(fileImport);
            while (scannerNovy.hasNextLine())
                zoznamKodov.add(scannerNovy.nextLine());

            for (int i = 0; i < zoznamKodov.size(); i++) {
                for (int p = 0; p < produktyALL.size(); p++) {
                    if ((zoznamKodov.get(i).equals(produktyALL.get(p).getKod()))) {
                        String pictureURL=produktyALL.get(p).getIMGURL();
//                        for (int k=0; k<pictureURL.length(); k++) {
//                            char charakter = pictureURL.charAt(k);
//                            int asciiKod = ((int)charakter );
//                            System.out.println("ascii "+k+" "+charakter+" " +asciiKod);
//
//                        }
//                        System.out.println("kod:"+produktyALL.get(p).getKod()+" URL:"+pictureURL+";koniec");
//pre tempo musim urobit vynimku - v XML je IMGURL ako http, ale to hodi somarinu, naozaj musi byt s "s" = https
                        // po novom je to uz osetrene a uz je URL rovno s "s", takze dalsi kod komentujem
//                        if (produktyALL.get(p).getVyrobca().contains("Tempo")) {
//                            String simpleURL = produktyALL.get(p).getIMGURL();
//                            pictureURL = simpleURL.replace("http", "https");
//                        }
                        try (InputStream in = new URL(pictureURL).openStream()) {
                            System.out.println(zoznamKodov.get(i));
                                Files.copy(in, Paths.get(Premenne.cesta+"obr\\"+ produktyALL.get(p).getKod() +".jpg"));
                        }
                        catch (FileAlreadyExistsException e) {
                            System.out.println(zoznamKodov.get(i)+" : UZ MAS");
                        }
                        Thread.sleep(1000);
                    }
                }
            }
           scannerNovy.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}