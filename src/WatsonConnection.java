import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneInput;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneOptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class WatsonConnection {
	private String username;
	private String password;

	public WatsonConnection() throws FileNotFoundException {
		Gson gson = new Gson();

		JsonObject obj = gson.fromJson(new FileReader("res/credentials.json"), JsonObject.class);
		JsonObject creds = obj.get("tone_analyzer").getAsJsonObject().get("credentials").getAsJsonObject();
		username = creds.get("username").getAsString();
		password = creds.get("password").getAsString();
	}

	public void interpret(String text) {
		ToneAnalyzer service = new ToneAnalyzer("2017-09-21", username, password);

		ToneInput toneInput = new ToneInput.Builder()
				.text(text).build();
		ToneOptions options = new ToneOptions.Builder()
				.toneInput(toneInput).build();
		ToneAnalysis tone = service.tone(options).execute();

		Gson gson = new Gson();
		JsonObject output = gson.fromJson(tone.toString(), JsonObject.class);

		System.out.println(output);

		Functions.flushValues();

		Functions.parseJSON(output);
	}

}
