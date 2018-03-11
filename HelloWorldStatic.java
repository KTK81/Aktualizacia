import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Metody.Premenne;

public class HelloWorldStatic {
	public static void printList2() {
		Premenne.cesta="popopopo";
		System.out.println(Premenne.cesta);
		String prvy="51166 WT";
		String druhy=prvy.replaceAll("\\s","+");
		System.out.println(druhy);

//regex jednoduchy string replaceall
		String simpleString = "size:110x76x60 cm ; 150 x 64,5 x 200 cm ; l90 x h55 x w60 cm ; 149 x v110 x h40";
		String replacedString = simpleString.replaceAll("cm", "AAA");
		System.out.println(replacedString);

//regex pattern a matcher
		String celeSlovo="Mama ma Evu a vazi 125 kilogramov.";
/*		String hladanyString = "Evu";
		Pattern pattern4 = Pattern.compile(hladanyString);
		Matcher matcher = pattern4.matcher(celeSlovo);
*/		Matcher matcher = Pattern.compile("Evu").matcher(celeSlovo);
		while(matcher.find()) {
			System.out.println(matcher.replaceAll("Adam"));
		}

// String to be scanned to find the pattern.
		String line = "This order was placed for QT123456! OK?";
        String patternTest = "(for)?(.+)(\\d+)(.*)";
        String drevonaName1 = "REA DENISA UP 001 BIELA 001";
        String patternDrevona = "(\\s+)(BIELA)(.*)";
        String farba;
        if (drevonaName1.contains("BIELA")) {
            drevonaName1 = drevonaName1.replaceAll("(\\s+)(BIELA)(.*)", ", biela");
            farba = "biela";
            System.out.println("nazov:"+drevonaName1+";farba:"+farba);
        }

//	drevonaNajdiMeno ("testovacieMeno");
		drevonaName1 = "REA DENISA UP 001 BIELA 001";
		Pattern r = Pattern.compile(patternDrevona);
		Matcher m = r.matcher(drevonaName1);
		if (m.find( )) {

			System.out.println("group(): " + m.group() );
			System.out.println("group(1): " + m.group(1) );
			System.out.println("group(2): " + m.group(2) );
			System.out.println("group(3): " + m.group(3) );
			System.out.println("group(4): " + m.group(4) );
		}else {
			System.out.println("NO MATCH");
		}
	}
/*

	public static String drevonaNajdiMeno(String drevonaName) {
		String noveMeno = null;
		if (drevonaName.contains("BIELA")) {
			noveMeno = drevonaName.replaceAll("(\\s+)(BIELA)(.*)", ", biela");
			farba = "biela";
			System.out.println("nazov:"+drevonaName+";farba:"+farba);
		}

		return noveMeno;
	}
	*/
}