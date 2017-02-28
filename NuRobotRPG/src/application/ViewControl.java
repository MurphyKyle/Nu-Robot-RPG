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
	private Robot r1;
	private Parent fxmlPane;
	private int difficulty;
	
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
	
	
//	new game screen
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
		difficulty = 0;
		
		Toggle t = diffChoice.getSelectedToggle();
		
		if (t.equals(theStage.getScene().lookup("#easy"))) {
			difficulty = 1;
		} else if (t.equals(theStage.getScene().lookup("#medium"))) {
			difficulty = 2;
		} else {
			difficulty = 3;
		}
		
		r1 = new Robot(difficulty);
		outputLabel.setText(difficulty + "\n" + r1.toString());
		
		theStage.getScene().lookup("#contButton").setDisable(false);
	}
	
	
	@FXML
	public void startGame() {
		Engine.run(r1, difficulty);
	}
	
	
//	load game screen
	@FXML
	public void loadExistingGame() {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
