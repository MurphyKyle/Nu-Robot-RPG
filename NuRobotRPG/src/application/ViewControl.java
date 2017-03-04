package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.Map;
import models.Robot;

public class ViewControl {
	
	private static Stage theStage;
	private static Scene theScene;
	private static Scene previousScene;
	public ToggleGroup diffChoice;
	private static Label outputLabel;
	private Button contButton;
	private static Label mapLabel;
	
	private int difficulty;
	private Robot playerRobot;
	private static ArrayList<Scene> sceneList = new ArrayList<>();
	private static int currentSceneIndex = 0;
	private static int prevSceneIndex = 0;
	
	private static String startupScreen = "/view/StartupScreen.fxml";
	private static String newGamePrompt = "/view/StartGameScreen.fxml";
	private static String gameplayScreen = "/view/GameplayScreen.fxml";
	private static String combatScreen = "/view/CombatScreen.fxml";
	private static String depotScreen = "/view/DepotScreen.fxml";	
	private static String changePartsScreen = "/view/ChangePartsScreen.fxml";
	
	public void setScenes() throws IOException {
		sceneList.add(new Scene(FXMLLoader.load(getClass().getResource(startupScreen))));
		sceneList.add(new Scene(FXMLLoader.load(getClass().getResource(newGamePrompt))));
		sceneList.add(new Scene(FXMLLoader.load(getClass().getResource(gameplayScreen))));
		sceneList.add(new Scene(FXMLLoader.load(getClass().getResource(combatScreen))));
		sceneList.add(new Scene(FXMLLoader.load(getClass().getResource(depotScreen))));
		sceneList.add(new Scene(FXMLLoader.load(getClass().getResource(changePartsScreen))));
	}
	
	public static void setStage(Stage primaryStage) {
		theStage = primaryStage;
	}
	
	public static void setTextOutput(String text) {
		outputLabel.setText(text);
	}

	public void exit() {
		Runtime.getRuntime().exit(0);
	}
	
	
	private void setOutputLabel() {
		outputLabel = (Label) theScene.lookup("#outputLabel");
	}
	
	
	public void goBack() {
		if (currentSceneIndex >= 1) {
			theStage.setScene(previousScene);
			if (currentSceneIndex <= 1) {
				 prevSceneIndex = 0;
			} else {
				prevSceneIndex--;
			}
			previousScene = sceneList.get(prevSceneIndex);
			currentSceneIndex--;
		}
		
		theStage.show();
	}
	
	
	private void setPreviousScene() {
		// set the previous scene to the current scene
		// increment current scene to be loaded after this method is done
		if (currentSceneIndex < sceneList.size()-1) {
			previousScene = sceneList.get(currentSceneIndex);
			if (currentSceneIndex >= 1) {
				prevSceneIndex++;
			}
			currentSceneIndex++;			
		}
	}
	
	
//	new game screen
	@FXML
	public void createNewGame() throws IOException {
		setPreviousScene();
		theScene = sceneList.get(currentSceneIndex);
		setOutputLabel();
		theStage.setScene(theScene);
		theStage.setTitle("Create New Game");
		theStage.show();
	}
	
	
	@FXML
	public void createRobot() {
		getDifficulty();
		Engine.currentRobot = new Robot(difficulty);
		Engine.currentRobot.setName("Player");
		setTextOutput(Engine.currentRobot.toString());
		theStage.getScene().lookup("#contBtn").setDisable(false);
	}
	
	private void getDifficulty() {
		difficulty = 0;
		
		Toggle t = diffChoice.getSelectedToggle();
		
		if (t.equals(theStage.getScene().lookup("#easy"))) {
			difficulty = 1;
		} else if (t.equals(theStage.getScene().lookup("#medium"))) {
			difficulty = 2;
		} else {
			difficulty = 3;
		}
	}
	
//	on the start game screen - this starts the game with a new map based on chosen difficulty
	@FXML
	public void startGame() throws IOException {
		getDifficulty();
		setPreviousScene();
		theScene = sceneList.get(currentSceneIndex);
		setOutputLabel();
		theStage.setScene(theScene);
		theStage.setTitle("Gameplay");
		
		mapLabel = (Label) theScene.lookup("#mapLabel");
		Engine.currentMap = new Map(difficulty);
		mapLabel.setText(Engine.currentMap.toString());
		theStage.show();
	}
	
	@FXML
	public void newMap() {
		Engine.currentMap = new Map(difficulty);
		mapLabel.setText(Engine.currentMap.toString());
	}
	
//	this loads the start game screen without create robot button
	@FXML
	public void loadExistingGame() throws IOException {
		setPreviousScene();
		theScene = sceneList.get(currentSceneIndex);
		setOutputLabel();
		theStage.setScene(theScene);
		theScene.lookup("#createRobotBtn").setDisable(true);
		theScene.lookup("#contBtn").setDisable(false);
		theStage.setTitle("Start Game");
		theStage.show();
	}

	
//	on gameplay screen - navigation
	@FXML
	public void moveUp() {
		GridPane gp = (GridPane) theScene.lookup("#navGrid");
		List<Node> list = gp.getChildren();
		Button btn = (Button) list.get(0);
		btn.setDisable(Engine.currentMap.moveUp());
		mapLabel.setText(Engine.currentMap.toString());
	}
	
	@FXML
	public void moveDown() {
		theScene.lookup("#navGrid").lookup("#downBtn").setDisable(Engine.currentMap.moveDown());
		mapLabel.setText(Engine.currentMap.toString());
	}
	
	@FXML
	public void moveLeft() {
		theScene.lookup("#navGrid").lookup("#downBtn").setDisable(Engine.currentMap.moveLeft());
		mapLabel.setText(Engine.currentMap.toString());
	}
	
	@FXML
	public void moveRight() {
		theScene.lookup("#navGrid").lookup("#downBtn").setDisable(Engine.currentMap.moveRight());
		mapLabel.setText(Engine.currentMap.toString());
	}
	
	
	
//	combat screen
	@FXML
	public void attack() {
		
	}
	
	@FXML
	public void defend() {
		
	}
	
	
	
//	depo screen
	@FXML
	public void changeParts() {
		
	}
	
	@FXML
	public void saveGame() {
		Engine.saveFile(playerRobot);
	}
	
	
//	change parts screen
	@FXML
	public void swapParts() {
		
	}
	
	
	
	
	
	
	
	
	
	
}
