package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {	
	
	public Stage stage;
	
	public static void main(String[] args) {
//		Engine.run();
		launch(args);
	}

	public Stage getStage() {
		return stage;
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/StartupScreen.fxml"));
			Scene scene = new Scene(pane);
//			scene.getStylesheets().add(getClass().getResource("view/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Robot RPG");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
