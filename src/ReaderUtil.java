import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public final class ReaderUtil {
	public static String fileToString(File file) {
		try {
		    Scanner sc = new Scanner(file);
        		String text = sc.hasNext() ? sc.useDelimiter("\\A").next() : "";
        		
        		sc.close();
        		return text;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        }
	}
}
