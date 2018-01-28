import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

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
            
            Rectangle rect = new Rectangle();
            Tone ton = Tone.valueOf(AvgTones.get(i).tone);
            
            switch (ton) {
                case Anger: rect = Main.controller.rectList.get(7); break;
                case Disgust: rect = Main.controller.rectList.get(6); break;
                case Fear: rect = Main.controller.rectList.get(5); break;
                case Joy: rect = Main.controller.rectList.get(4); break;
                case Sadness: rect = Main.controller.rectList.get(3); break;
                case Analytical: rect = Main.controller.rectList.get(2); break;
                case Confident: rect = Main.controller.rectList.get(1); break;
                case Tentative: rect = Main.controller.rectList.get(0); break;
            }
            
            if (ton != Tone.None) {
            	Main.controller.barTransition(rect, AvgTones.get(i).score * MainViewController.maxRectSize);
            }
        }
        
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
