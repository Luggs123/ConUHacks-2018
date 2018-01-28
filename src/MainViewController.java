import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.fxmisc.richtext.StyleClassedTextArea;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainViewController {

	private Stage stage;

	@FXML private Label pathLabel;
	@FXML private StyleClassedTextArea textArea;
	@FXML private ToggleButton toggleButton;

	public void openText(ActionEvent actionEvent) throws FileNotFoundException {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Pick a File");
		File file = chooser.showOpenDialog(stage);

		pathLabel.setText(file.getName());

		Scanner sc = new Scanner(file);
		String text = sc.hasNext() ? sc.useDelimiter("\\A").next() : "";

		textArea.insertText(0, text);

	}
	public void toggleMode(ActionEvent aE) {		
		if(toggleButton.isSelected()) {
			textArea.setEditable(false);
		}
		else {
			textArea.setEditable(true);			
		}		
	}
	public void highlightText(int from, int to, String cssClass) {
		textArea.setStyleClass(from, to, cssClass);
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
