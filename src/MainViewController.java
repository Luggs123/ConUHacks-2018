import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainViewController {

	private Stage stage;

	@FXML private Label pathLabel;
	@FXML private TextArea textArea;

	public void openText(ActionEvent actionEvent) throws FileNotFoundException {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Pick a File");
		File file = chooser.showOpenDialog(stage);

		pathLabel.setText(file.getName());

		Scanner sc = new Scanner(file);
		String text = sc.hasNext() ? sc.useDelimiter("\\A").next() : "";

		textArea.setText(text);

	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}