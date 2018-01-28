import org.json.JSONObject;

public class SentenceTone {
    public String Text;
    public int ID;
    public Mood Mood;

    public SentenceTone(JSONObject obj) {
        Text = obj.getString("text");
        ID = obj.getInt("sentence_id");
        Mood = new Mood(obj.getJSONArray("tones").getJSONObject(0));
    }
}
