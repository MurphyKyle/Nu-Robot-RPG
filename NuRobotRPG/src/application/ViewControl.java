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
	public static Scene previousScene;
	public Button contButton;
	public ToggleGroup diffChoice;
	public static Label outputLabel;
	
	private static String gameplayScreen = "/view/GameplayScreen.fxml";
	private static String startupScreen = "/view/StartupScreen.fxml";
	private static String depotScreen = "/view/DepotScreen.fxml";
	private static String combatScreen = "/view/CombatScreen.fxml";
	private static String existingGamePrompt = "/view/LoadExistingGamePrompt.fxml";
	private static String newGamePrompt = "/view/CreateNewGamePrompt.fxml";
	
	public static void setStage(Stage primaryStage) {
		theStage = primaryStage;
	}
	
	public static void setOutputLabel(String text) {
		outputLabel.setText(text);
	}
	
	
	private void setFXML(String fxmlPath) throws IOException {
		fxmlPane = FXMLLoader.load(getClass().getResource(fxmlPath));
		theScene = new Scene(fxmlPane);
		outputLabel = (Label) theScene.lookup("#outputLabel");
	}
	
	private void setPreviousScene(String fxmlPath) throws IOException{
		fxmlPane = FXMLLoader.load(getClass().getResource(fxmlPath));
		previousScene = new Scene(fxmlPane);
		outputLabel = (Label) theScene.lookup("#outputLabel");
	}

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
		setFXML(newGamePrompt);
		setPreviousScene(startupScreen);
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
		r1.setName("Player");
		setOutputLabel(r1.toString());
		
		theStage.getScene().lookup("#contButton").setDisable(false);
	}
	
	
	@FXML
	public void startGame() throws IOException {
		previousScene = (Scene) contButton.getScene();
		setFXML(gameplayScreen);
		theStage.setScene(theScene);
		theStage.setTitle("Gameplay - Movement");
		theStage.show();
		Engine.run(r1, difficulty);
	}
	
	
//	load game screen
	@FXML
	public void loadExistingGame() {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
