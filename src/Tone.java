import org.json.*;

public class Tone {
    private String name;
    private String id;
    private float score;

    public Tone(JSONObject obj) {
        obj.getString("tone_name");
        obj.getString("tone_id");
        obj.getFloat("score");
    }

}
