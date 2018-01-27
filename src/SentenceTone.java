import org.json.JSONObject;

public class SentenceTone {
    private String text;
    private int id;
    private Mood mood;

    public SentenceTone(JSONObject obj) {
        text = obj.getString("text");
        id = obj.getInt("sentence_id");
        mood = new Mood(obj.getJSONArray("tones").getJSONObject(0));
    }

}
