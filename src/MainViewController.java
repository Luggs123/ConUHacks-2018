import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.MouseOverTextEvent;
import org.fxmisc.richtext.StyleClassedTextArea;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainViewController {

	public GridPane textPane;
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

	public List<Rectangle> rectList;

	public static double maxRectSize;

	private Stage stage;
	private File file;

	private boolean analyzeMode = false;

	@FXML
	public void initialize() {

		textArea = new StyleClassedTextArea();
		textArea.prefHeight(292);
		textArea.prefWidth(450);
		textArea.setWrapText(true);
		
		VirtualizedScrollPane<StyleClassedTextArea> scroll = new VirtualizedScrollPane<StyleClassedTextArea>(textArea);
		textPane.add(scroll, 0, 1);


		rectList = Arrays.asList(new Rectangle[]{
		tentativeBox,
		confidentBox,
		analyticalBox,
		sadnessBox,
		joyBox,
		fearBox,
		disgustBox,
		angerBox});
		
		Functions.TotMoods = new float[rectList.size()];
		Functions.MoodCount = new int[rectList.size()];

		maxRectSize = maxRectSize();

		System.out.println(maxRectSize());

//		for (Rectangle rect : rectList) {
//			barTransition(rect, maxRectSize);
//		}
		setupHoverEvent();

	}

	public void openText(ActionEvent actionEvent) throws FileNotFoundException {
		textArea.clear();
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Pick a File");
		chooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Text File", ".txt"));
		file = chooser.showOpenDialog(stage);



		textArea.insertText(0, ReaderUtil.fileToString(file));

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
        	SentenceTone sentence = Functions.AnalyzedTones.get(i);
        	int start = textArea.getText().indexOf(sentence.Text);
        	sentence.start = start;
        	int end = start+sentence.Text.length();
        	sentence.end = end;
        	System.out.println(textArea.getText().length() +" " +end);
        	if (sentence.Moods.length > 0 && start>= 0 && (end - 1) < textArea.getText().length()) {
        		highlightText(start,end, sentence.Moods[0].id);
        	}
        }
        
    }

	public void toggleMode(ActionEvent aE) {		
		if(toggleButton.isSelected()) {
			textArea.setEditable(false);
			analyzeButton.setDisable(false);
			analyzeMode = true;
		}
		else {
			analyzeButton.setDisable(true);
			textArea.setEditable(true);
			analyzeMode = false;
			
			highlightText(0, textArea.getText().length(), "neutral");
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

	}

	private double maxRectSize() {
		double width = barsGridPane.getPrefWidth();

		ObservableList<ColumnConstraints> ColCons = barsGridPane.getColumnConstraints();

		double percent =  ColCons.get(1).getPercentWidth() / 100;

		double gap = barsGridPane.getHgap();

		return width * percent - gap / 2;
	}

	//slightly random movement is pretty
	public void barTransition(Rectangle rect, double endVal) {
		Random rand = new Random();
		double bias = 0.8 + rand.nextDouble() * 0.4;

		Timeline timeline = new Timeline();
		KeyValue kv = new KeyValue(rect.widthProperty(), Math.max(5, endVal));
		KeyFrame kf = new KeyFrame(Duration.millis(1000 * bias), kv);
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}

	//make pop ups
	public void setupHoverEvent() {
		Popup popup = new Popup();
		Label popupText = new Label();
		popup.getContent().add(popupText);
		popupText.setPadding(new Insets(0, 10, 0, 10));

		popupText.setStyle("-fx-background-color: white;" +
				"-fx-border-color: grey");

		textArea.setMouseOverTextDelay(java.time.Duration.ofMillis(700));
		textArea.addEventHandler(MouseOverTextEvent.MOUSE_OVER_TEXT_BEGIN, e -> {
			if (analyzeMode) {
				SentenceTone sentence = findSentenceByIndex(e.getCharacterIndex());
				if (sentence == null) {
					return;
				}
				javafx.geometry.Point2D point = e.getScreenPosition();
				System.out.println(sentence.start);
				System.out.println(e.getCharacterIndex());
				System.out.println(sentence.end + "\n");

				StringBuilder text = new StringBuilder();

				Mood[] moods = sentence.Moods;
				for (int i = 0; i < moods.length; i++) {
					Mood mood = moods[i];
					text.append(i == 0 ? "" : ", ").append(mood.id).append(": ").append(mood.score);
				}

				popupText.setText(text.toString());
				popup.show(textArea, point.getX()+10, point.getY());
				
			}

		});

		textArea.addEventHandler(MouseOverTextEvent.MOUSE_OVER_TEXT_END, e -> {
			popup.hide();;
		});

		//bar hover event
		for (Rectangle rect : rectList) {
			Popup barPopup = new Popup();
			Label popText = new Label();
			barPopup.getContent().add(popText);

			popText.textProperty().bind(Bindings.createStringBinding(
					() -> ""+ String.format("%.0f",(rect.widthProperty().get() / maxRectSize * 100)) + "%",
					rect.widthProperty()));

			rect.setOnMouseEntered(e -> {
				Bounds bounds = rect.getBoundsInLocal();
				Bounds screenBounds = rect.localToScreen(bounds);
				barPopup.show(barsGridPane, screenBounds.getMinX(), screenBounds.getMaxY());
				System.out.println("test");

			});

			rect.setOnMouseExited( e -> {
				barPopup.hide();
			});
		}
	}

	public SentenceTone findSentenceByIndex(int charIndex) {
		SentenceTone pickedSentence = null;

		for (SentenceTone sentence : Functions.AnalyzedTones) {
			if ((sentence.start != 0 || sentence.end != 0 ) && charIndex >= sentence.start && charIndex < sentence.end) {
				pickedSentence = sentence;
				break;
			}
		}

		return pickedSentence;
	}
}
