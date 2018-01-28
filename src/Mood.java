import org.json.*;

public class Mood {
    private Tone tone;
    private String id;
    private float score;

    public Mood(JSONObject obj) {
        tone = obj.getEnum(Tone.class, "tone_name");
        id = obj.getString("tone_id");
        score = obj.getFloat("score");
    }

}
