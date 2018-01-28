import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public final class Functions {
    /**
     * Don't let anyone instantiate this class.
     */
    private Functions() { }

    public static String readAllText(String filePath) {
        Scanner sc = null;
        
        try {
            sc = new Scanner(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            sopln("File \"" + filePath + "\" not found!");
        }
        
        sc.useDelimiter("\\A");
        
        String msg = sc.hasNext() ? sc.next() : "";
        sc.close();
        
        return msg;
    }
    
    public static void sopln(Object obj) {
       System.out.println(obj.toString());
    }
    
    public static void sop(Object obj) {
       System.out.print(obj.toString());
    }
}
