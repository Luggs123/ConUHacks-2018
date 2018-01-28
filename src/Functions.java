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

    public static void flushValues() {
		while (!AnalyzedTones.isEmpty())
			AnalyzedTones.remove(0);

		while (!AvgTones.isEmpty())
			AvgTones.remove(0);

		for (int i = 0; i < TotMoods.length; i++) {
			TotMoods[i] = 0;
		}
		for (int i = 0; i < MoodCount.length; i++) {
			MoodCount[i] = 0;
		}
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

	                Tone ton = Tone.valueOf(tone.tone);
	                switch (ton) {
	                    case Anger: TotMoods[7] += tone.score;
	                        MoodCount[7]++;
	                        break;
	                    case Disgust: TotMoods[6] += tone.score;
                        MoodCount[6]++;
                        break;
	                    case Fear: TotMoods[5] += tone.score;
                        MoodCount[5]++;
                        break;
	                    case Joy: TotMoods[4] += tone.score;
                        MoodCount[4]++;
                        break;
	                    case Sadness: TotMoods[3] += tone.score;
                        MoodCount[3]++;
                        break;
	                    case Analytical: TotMoods[2] += tone.score;
                        MoodCount[2]++;
                        break;
	                    case Confident: TotMoods[1] += tone.score;
                        MoodCount[1]++;
                        break;
	                    case Tentative: TotMoods[0] += tone.score;
                        MoodCount[0]++;
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

			for (float f : TotMoods) {
				System.out.println(f + ", ");
			}

			for (int j = 7; j >= 0; j--) {
				double result = TotMoods[j] / MoodCount[j];
				System.out.println(result);
				if (MoodCount[j] != 0)
					Main.controller.barTransition(Main.controller.rectList.get(j), (result - 0.3) * 10/7 * MainViewController.maxRectSize);
				else
					Main.controller.barTransition(Main.controller.rectList.get(j), 0);

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
