import com.google.gson.JsonObject;

public class Mood {
    private String tone;
    private String id;
    private float score;

    public Mood(JsonObject obj) {
        tone = obj.get("tone_name").getAsJsonObject().getAsString();
        id = obj.get("tone_id").getAsJsonObject().getAsString();
        score = obj.get("score").getAsJsonObject().getAsFloat();
    }

}
