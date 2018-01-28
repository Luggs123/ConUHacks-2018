import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.fxmisc.richtext.StyleClassedTextArea;

import com.google.gson.JsonObject;

import java.io.*;
import java.util.Scanner;

public class MainViewController {

	private Stage stage;
	private File file;

	@FXML private Label pathLabel;
	@FXML private StyleClassedTextArea textArea;
	@FXML private ToggleButton toggleButton;
	@FXML private Button analyzeButton;

	public void openText(ActionEvent actionEvent) throws FileNotFoundException {
		textArea.clear();
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Pick a File");
		chooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Text File", ".txt"));
		file = chooser.showOpenDialog(stage);

		pathLabel.setText(file.getName());

		Scanner sc = new Scanner(file);
		String text = sc.hasNext() ? sc.useDelimiter("\\A").next() : "";

		textArea.insertText(0, text);

	}
    
	//TODO: edge case emty moods
    public void runAnalyze(ActionEvent actionEvent) {
        if (textArea.getText().isEmpty()) {
            //TODO: add dialog box
        }        

        try {
			parseEmotion();
		} catch (IOException e) {
			e.printStackTrace();
		}

        for(int i = 0; i<Functions.AnalyzedTones.size();i++){
        	System.out.println(Functions.AnalyzedTones.size());
        	SentenceTone sentence = Functions.AnalyzedTones.get(i);
        	int start = textArea.getText().indexOf(sentence.Text);
        	int end = start+sentence.Text.length();
        	System.out.println(textArea.getText().length() +" " +end);
        	if(sentence.Moods.length > 0 && start>= 0 && (end - 1) < textArea.getText().length()) {
        		highlightText(start,end, sentence.Moods[0].id);
        	}
        }
        
    }

	public void toggleMode(ActionEvent aE) {		
		if(toggleButton.isSelected()) {
			textArea.setEditable(false);
			analyzeButton.setDisable(false);
		}
		else {
			analyzeButton.setDisable(true);
			textArea.setEditable(true);			
		}		
	}
	
	public void highlightText(int from, int to, String cssClass) {
		textArea.setStyleClass(from, to, cssClass);
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void parseEmotion() throws IOException {
		WatsonConnection connection = new WatsonConnection();
		connection.interpret(textArea.getText());
//		Process proc = Runtime.getRuntime().exec(String.format("python %s %s",getClass().getResource("emotionAnalysis.py").getPath(),  file.getAbsolutePath()));
//
//		BufferedReader iStream = new BufferedReader(new InputStreamReader(proc.getInputStream()));
//		BufferedReader errStream = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
//
//		String json = "";
//
//		String input;
//
//		while ((input = iStream.readLine()) != null) {
//			json += input + '\n';
//		}
//
//		String error = "error: ";
//		String err;
//
//		while ((err = errStream.readLine()) != null) {
//			error += err + '\n';
//		}
//
//		System.out.println(json);
//		System.out.println(error);
	}
}
