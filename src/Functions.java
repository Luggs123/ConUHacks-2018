import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public final class Functions {
    /**
     * Don't let anyone instantiate this class.
     */
    private Functions() { }
    
    public static LinkedList<SentenceTone> AnalyzedTones = new LinkedList<SentenceTone>();
    public static LinkedList<Mood> AvgTones = new LinkedList<Mood>();

    public static float[] TotMoods;
    public static int[] MoodCount;

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
        JsonElement elly = json.get("sentences_tone");
        JsonArray arr = null;
        
        if(elly != null) {
	        arr = elly.getAsJsonArray();
	        
	        for (int i = 0; i < arr.size(); i++) {
	            JsonObject obj = arr.get(i).getAsJsonObject();
	            SentenceTone senTon = new SentenceTone(obj);
	            AnalyzedTones.add(senTon);
	            
	            for (Mood tone : senTon.Moods)
	            {

	                Tone ton = Tone.valueOf(AvgTones.get(i).tone);
	                switch (ton) {
	                    case Anger: TotMoods[8] += tone.score;
	                        MoodCount[8]++;
	                        break;
	                    case Disgust: TotMoods[7] += tone.score;
                        MoodCount[7]++;
                        break;
	                    case Fear: TotMoods[6] += tone.score;
                        MoodCount[6]++;
                        break;
	                    case Joy: TotMoods[5] += tone.score;
                        MoodCount[5]++;
                        break;
	                    case Sadness: TotMoods[4] += tone.score;
                        MoodCount[4]++;
                        break;
	                    case Analytical: TotMoods[3] += tone.score;
                        MoodCount[3]++;
                        break;
	                    case Confident: TotMoods[2] += tone.score;
                        MoodCount[2]++;
                        break;
	                    case Tentative: TotMoods[1] += tone.score;
                        MoodCount[1]++;
                        break;
	                    default:
	                        break;
	                }
	            }
	        }
        }
        
        arr = json.get("document_tone").getAsJsonObject().get("tones").getAsJsonArray();
        for (int i = 0; i < arr.size(); i++) {
            JsonObject obj = arr.get(i).getAsJsonObject();
            AvgTones.add(new Mood(obj));
            
            Main.controller.barTransition(Main.controller.rectList.get(8), (TotMoods[8] / MoodCount[8]) * MainViewController.maxRectSize);
            Main.controller.barTransition(Main.controller.rectList.get(7), (TotMoods[7] / MoodCount[6]) * MainViewController.maxRectSize);
            Main.controller.barTransition(Main.controller.rectList.get(6), (TotMoods[6] / MoodCount[5]) * MainViewController.maxRectSize);
            Main.controller.barTransition(Main.controller.rectList.get(5), (TotMoods[5] / MoodCount[4]) * MainViewController.maxRectSize);
            Main.controller.barTransition(Main.controller.rectList.get(4), (TotMoods[4] / MoodCount[3]) * MainViewController.maxRectSize);
            Main.controller.barTransition(Main.controller.rectList.get(3), (TotMoods[3] / MoodCount[2]) * MainViewController.maxRectSize);
            Main.controller.barTransition(Main.controller.rectList.get(2), (TotMoods[2] / MoodCount[2]) * MainViewController.maxRectSize);
            Main.controller.barTransition(Main.controller.rectList.get(1), (TotMoods[1] / MoodCount[1]) * MainViewController.maxRectSize);
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
