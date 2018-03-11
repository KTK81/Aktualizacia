import java.io.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;



public class HelloWorld {
	public void printList(String word) throws IOException, InterruptedException {


System.out.println("test");
		String text = null;
		String text1="Mama ma Evu.";
		String text2="Mama ma Evulienku.";
		String text3="Mama ma Adama.";
		String hladany="Evu";
		int i;
		if (text1.matches("(.*)(\\bEvu)(.*)"))
			System.out.println("ano"+text1);
		if (text1.contains(hladany))
			System.out.println(text1);
		if (text2.contains(hladany))
			System.out.println(text2);
		if (text2.contains("Evu"))
			System.out.println(text2);
//		if (text2.contains((.+)(.*)))
//			System.out.println(text2);




//		KompletALL.copyFile(Premenne.cesta + "aktualizacia.csv",Premenne.cesta+"History\\aktualizacia_"+Premenne.formattedDateTime+".csv" );
//		KompletALL.copyFile(Premenne.cesta + "PrestaID.csv",Premenne.cesta+"History\\PrestaID_"+Premenne.formattedDateTime+".csv" );

	}
}	