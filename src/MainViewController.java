import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.fxmisc.richtext.StyleClassedTextArea;

import com.google.gson.JsonObject;
import org.w3c.dom.css.Rect;

import java.io.*;
import java.util.*;

public class MainViewController {

	@FXML private GridPane barsGridPane;
	@FXML private Rectangle neutralBox;
	@FXML private Rectangle tentativeBox;
	@FXML private Rectangle confidentBox;
	@FXML private Rectangle analyticalBox;
	@FXML private Rectangle sadnessBox;
	@FXML private Rectangle joyBox;
	@FXML private Rectangle fearBox;
	@FXML private Rectangle disgustBox;
	@FXML private Rectangle angerBox;

	@FXML private Label pathLabel;
	@FXML private StyleClassedTextArea textArea;
	@FXML private ToggleButton toggleButton;
	@FXML private Button analyzeButton;

	private List<Rectangle> rectList;

	private static double maxRectSize;

	private Stage stage;
	private File file;

	@FXML
	public void initialize() {
		rectList = Arrays.asList(new Rectangle[]{neutralBox,
		tentativeBox,
		confidentBox,
		analyticalBox,
		sadnessBox,
		joyBox,
		fearBox,
		disgustBox,
		angerBox});

		maxRectSize = maxRectSize();

		System.out.println(maxRectSize());

		for (Rectangle rect : rectList) {
			barTransition(rect, maxRectSize);
		}
	}

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

		try {
			parseEmotion();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
    
	//TODO: edge case emty moods
    public void runAnalyze(ActionEvent actionEvent) {
        if (textArea.getText().isEmpty()) {
            //TODO: add dialog box
        }        
        Functions.analyzeText(textArea.getText());
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
		connection.interpret(file);

	}

	public double maxRectSize() {
		double width = barsGridPane.getPrefWidth();

		ObservableList<ColumnConstraints> ColCons = barsGridPane.getColumnConstraints();

		double percent =  ColCons.get(1).getPercentWidth() / 100;

		double gap = barsGridPane.getHgap();

		System.out.println("w " + width + ", p " + percent + ", g " + gap);

		return width * percent - gap / 2;
	}

	//slightly random movement is pretty
	public void barTransition(Rectangle rect, double endVal) {
		Random rand = new Random();
		double bias = 0.8 + rand.nextDouble() * 0.4;

		Timeline timeline = new Timeline();
		KeyValue kv = new KeyValue(rect.widthProperty(), endVal);
		KeyFrame kf = new KeyFrame(Duration.millis(1000 * bias), kv);
		timeline.getKeyFrames().add(kf);
		timeline.play();

	}
}
