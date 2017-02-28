package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import models.Robot;

public class ViewControl {
	
	private static Stage theStage;
	private Scene theScene;
	private Parent fxmlPane;
	
	public static void setStage(Stage primaryStage) {
		theStage = primaryStage;
	}
	
	
	private void setFXML(String fxmlPath) throws IOException {
		fxmlPane = FXMLLoader.load(getClass().getResource(fxmlPath));
		theScene = new Scene(fxmlPane);
		outputLabel = (Label) theScene.lookup("#outputLabel");
	}
	
	
	@FXML
	public static Scene previousScene;
	
	
	@FXML 
	public Button contButton;
	public ToggleGroup diffChoice;
	
	@FXML
	public Label outputLabel;
	
	
	@FXML
	public void exit() {
		Runtime.getRuntime().exit(0);
	}
	
	
	@FXML
	public void goBack() {
		theStage.setScene(previousScene);
		theStage.show();
	}
	
	
	
	@FXML
	public void createNewGame() throws IOException {
		previousScene = (Scene) contButton.getScene();
		
		setFXML("/view/CreateNewGamePrompt.fxml");
		
		theStage.setScene(theScene);
		theStage.setTitle("Create New Game");
		theStage.show();
		
	}
	
	@FXML
	public void createRobot() {
		int diff = 0;
		Toggle t = diffChoice.getSelectedToggle();
		
		if (t.equals(theStage.getScene().lookup("#easy"))) {
			diff = 1;
		} else if (t.equals(theStage.getScene().lookup("#medium"))) {
			diff = 2;
		} else {
			diff = 3;
		}
		
		Robot r1 = new Robot(diff);
		outputLabel.setText(diff + "\n" + r1.toString());
		
//		theScene.lookup("#contButton").setDisable(false);;
	}
	
	
	@FXML
	public void startGame() {
		
	}
	
	
	@FXML
	public void loadExistingGame() {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
