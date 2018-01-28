import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public final class Functions {
    /**
     * Don't let anyone instantiate this class.
     */
    private Functions() { }
    
    public static LinkedList<SentenceTone> AnalyzedTones = new LinkedList<SentenceTone>();
    public static LinkedList<Mood> AvgTones = new LinkedList<Mood>();
    private static String outputText = "";

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
    
    public static void analyzeText(String text) {

    }
    
    public static void parseJSON(JsonObject json) {
        String dir = getResourceDir("uh.json");
        JsonElement elly = json.get("sentences_tone");
        JsonArray arr = null;
        
        if(elly != null) {
	        arr = elly.getAsJsonArray();
	        for (int i = 0; i < arr.size(); i++) {
	            JsonObject obj = arr.get(i).getAsJsonObject();
	            AnalyzedTones.add(new SentenceTone(obj));
	        }
        }
        arr = json.get("document_tone").getAsJsonObject().get("tones").getAsJsonArray();
        for (int i = 0; i < arr.size(); i++) {
            JsonObject obj = arr.get(i).getAsJsonObject();
            AvgTones.add(new Mood(obj));
        }
        System.out.print(AvgTones.get(0).score);
    }

    /**
     * Gets the directory of a file in the resources folder.
     */
    public static String getResourceDir(String path)
    {
        return new File("res/" + path).toURI().toString();
    }
    
    public static void sopln(Object obj) {
       System.out.println(obj.toString());
    }
    
    public static void sop(Object obj) {
       System.out.print(obj.toString());
    }
}
