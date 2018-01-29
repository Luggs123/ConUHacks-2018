import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
	
	public static MainViewController controller;
	
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("View/mainView.fxml"));

		primaryStage.setTitle("Mood Interpreter");
		primaryStage.setScene(new Scene((Parent) loader.load()));


		controller = (MainViewController) loader.getController();
		controller.setStage(primaryStage);

		primaryStage.show();
	}
}
