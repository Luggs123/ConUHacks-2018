import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneInput;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneOptions;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {
	public static void main(String[] args) {
		JSONObject credentials = readJSONFile("res/credentials.json");

		String username = credentials.getJSONObject("tone_analyzer").getJSONObject("credentials").getString("username");
		String password = credentials.getJSONObject("tone_analyzer").getJSONObject("credentials").getString("password");

		ToneAnalyzer service = new ToneAnalyzer("2017-09-21");
		service.setUsernameAndPassword(username, password);

		ToneInput toneInput = new ToneInput.Builder()
				.text("meme").build();
		ToneOptions options = new ToneOptions.Builder()
				.toneInput(toneInput).build();

		ToneAnalysis tone = (ToneAnalysis) service.tone(options).execute();

	}

	public static JSONObject readJSONFile(String filePath) {
		try {
			JSONObject obj = new JSONObject(new JSONTokener(new FileInputStream(filePath)));
			return obj;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
