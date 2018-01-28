import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public final class ReaderUtil {
	public static String fileToString(File file) {
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "";
		}
		String text = sc.hasNext() ? sc.useDelimiter("\\A").next() : "";
		return text;
	}
}
