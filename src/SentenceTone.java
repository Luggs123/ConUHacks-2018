import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class SentenceTone {
    public int start;
    public int end;
    public String Text;
    public int ID;
    public Mood[] Moods;

    public SentenceTone(JsonObject obj) {
        Text = obj.get("text").getAsString();
        ID = obj.get("sentence_id").getAsInt();
        
        JsonArray arr = obj.get("tones").getAsJsonArray();
        Moods = new Mood[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            Moods[i] = new Mood(arr.get(i).getAsJsonObject());
        }
    }
}
