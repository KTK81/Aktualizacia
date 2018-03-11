import Metody.Produkt;
import Metody.Premenne;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import Metody.PrestaIDRead;


public class ZistiIDPresta {
    public static void spustiIDPresta() throws IOException {
        PrintWriter writerVysledok = new PrintWriter(Premenne.cesta + "zistiIDckaPrestaVysledok.txt", "UTF-8");
    	ArrayList<String> zoznamKodov = new ArrayList<String>();
    	ArrayList<Produkt> prestaIDlist = new ArrayList<>();
    	
    	prestaIDlist = PrestaIDRead.filePresta();
    	
    	File fileImport = new File(Premenne.cesta + "zistiIDckaImport.txt");
    	try {
    	Scanner scannerNovy = new Scanner(fileImport);
    	while (scannerNovy.hasNextLine()) 
    	{
    		String hladany = scannerNovy.nextLine();
    		zoznamKodov.add(hladany);
//    		System.out.println(hladany);
    	}
    	scannerNovy.close();
        } catch (FileNotFoundException e) {
    		e.printStackTrace();
        }

      	int i=0;
    	int p=0;
    	String hladanyKod;
    	String pomocnaPresta;
    	writerVysledok.println("*** Vysledky ***");

    	for (i=0; i < zoznamKodov.size(); i++)
    	{
    		hladanyKod=zoznamKodov.get(i);
//    		System.out.println(hladanyKod);
    			pomocnaPresta="0";
    				for (p = 0; p < prestaIDlist.size(); p++) {
//    					System.out.println(prestaIDlist.get(p).getKod());
    					if (prestaIDlist.get(p).getKod().equals(hladanyKod)) {
    					writerVysledok.println
    					(prestaIDlist.get(p).getPrestaID()+";"+hladanyKod+";"+prestaIDlist.get(p).getMOC()+";"+prestaIDlist.get(p).getSkupina()+";"+prestaIDlist.get(p).getVyrobca());
    					pomocnaPresta="2";
    					}
    				}
   				if (pomocnaPresta=="0")
    					writerVysledok.println("123456"+";"+hladanyKod);
    	}
    	System.out.println("Hotovo");
  	writerVysledok.close();
    }       
}
