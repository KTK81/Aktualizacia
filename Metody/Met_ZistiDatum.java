package Metody;
import java.io.File;
import java.io.FileFilter;

// ******** zisti, ktory subor /so zadanym Stringom v nazve/ bol posledne modifikovany ********
// ***** v zadanom adresari. Proste zisti, s ktorym suborom ma pracovat *****
public class Met_ZistiDatum {
    public static File lastFileModified(String dir, String nazovSuboru) {
        File fl = new File(dir);
        File[] files = fl.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.isFile();
            }
        });
        long lastMod = Long.MIN_VALUE;
        File choice = null;
        String filename;
        for (File file : files) {
            if (file.lastModified() > lastMod) {
                filename=file.getName();
                if (filename.contains(nazovSuboru))
                {
                    choice = file;
                    lastMod = file.lastModified();
                }
            }
        }
        return choice;
    }
}