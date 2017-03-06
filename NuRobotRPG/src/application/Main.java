package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Robot;


public class Main extends Application {	
	
	public static void main(String[] args) {
//		Engine.run(new Robot(1), 1);
		launch(args);
	}

	
	@Override
	public void start(Stage primaryStage) {
		try {
			ViewControl.setStage(primaryStage);
			AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/StartupScreen.fxml"));
			Scene scene = new Scene(pane);
			ViewControl control = new ViewControl();
			control.setScenes();
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Robot RPG");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
