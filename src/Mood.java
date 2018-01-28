import com.google.gson.JsonObject;

public class Mood {
    private String tone;
    public String id;
    public float score;

    public Mood(JsonObject obj) {
    	System.out.println(obj);
        tone = obj.get("tone_name").getAsString();
        id = obj.get("tone_id").getAsString();
        score = obj.get("score").getAsFloat();
    }

}
