package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.Map;
import models.Robot;
import models.Room;



public class ViewControl {
	
	private static Stage theStage;
	private static Scene theScene;
	private static Scene previousScene;
	public ToggleGroup diffChoice;
	private static Label outputLabel;
	private static Label mapLabel;
	private static ProgressBar playerCounterBar;
	private static ProgressBar enemyCounterBar;
	
	private int difficulty;
	private static ArrayList<Scene> sceneList = new ArrayList<>();
	private static int currentSceneIndex = 0;
	private static int prevSceneIndex = 0;
	
	private static String startupScreen = "/view/StartupScreen.fxml";
	private static String newGamePrompt = "/view/StartGameScreen.fxml";
	private static String gameplayScreen = "/view/GameplayScreen.fxml";
	private static String combatScreen = "/view/CombatScreen.fxml";
	private static String depotScreen = "/view/DepotScreen.fxml";	
	private static String changePartsScreen = "/view/ChangePartsScreen.fxml";
	
	
	
	
	
//	general methods
	public void setScenes() throws IOException {
		sceneList.add(new Scene(FXMLLoader.load(getClass().getResource(startupScreen))));	//	0
		sceneList.add(new Scene(FXMLLoader.load(getClass().getResource(newGamePrompt))));	//	1
		sceneList.add(new Scene(FXMLLoader.load(getClass().getResource(gameplayScreen))));	//	2
		sceneList.add(new Scene(FXMLLoader.load(getClass().getResource(combatScreen))));	//	3
		sceneList.add(new Scene(FXMLLoader.load(getClass().getResource(depotScreen))));		//	4
		sceneList.add(new Scene(FXMLLoader.load(getClass().getResource(changePartsScreen))));	//	5
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
	
	
	private void checkRoom() {
		// need to check if a room is occupied
		boolean occupied = false;
		Room[][] map = Engine.currentMap.getRooms();
		// load appropriate screen if it is
		
		if (map[Engine.currentMap.getXCoord()][Engine.currentMap.getYCoord()].isOccupied()) {
			// index 3 is combat
			occupied = true;
			theStage.setScene(sceneList.get(3));
		} else if (map[Engine.currentMap.getXCoord()][Engine.currentMap.getYCoord()].isDepot()) {
			// index 4 is depot
			theStage.setScene(sceneList.get(4));
		} else {
			// the room should be empty
			return;
		}
		
		theStage.show();
		
		if (occupied) {
			startAFight();
		}
		
	}
	
	
	private void startAFight() {
		Timer enemyT = new Timer();
		Timer userT = new Timer();
		Robot enemy = new Robot(difficulty);
		playerCounterBar = (ProgressBar) theStage.getScene().lookup("#playerCounterBar");
		playerCounterBar = (ProgressBar) theStage.getScene().lookup("#enemyCounterBar");
		
		long enemyDelay = 2000;
		long atkDelay = 2000;
		
		
		TimerTask enemyAtk = new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("Enemy attacked");
				Engine.fight(enemy, Engine.currentRobot, false);
			}
		};
		
		
		TimerTask userAtk = new TimerTask() {
			
			@Override
			public synchronized void run() {
				System.out.println("User attacked");
				Engine.fight(Engine.currentRobot, enemy, true);
			}
		};
		
		enemyT.schedule(enemyAtk, enemyDelay, enemyDelay);
		userT.schedule(userAtk, atkDelay, atkDelay);
		
	}

	
	
	@FXML
	public void newMap() {
		Engine.currentMap = new Map(difficulty);
		mapLabel.setText(Engine.currentMap.toString());
	}
	
	
	
	
	
//	startup screen - [0]
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
	
	
//	start game screen - [1]
	@FXML
	public void createRobot() {
		getDifficulty();
		Engine.currentRobot = new Robot(difficulty);
		Engine.currentRobot.setName("Player");
		setTextOutput(Engine.currentRobot.toString());
		theStage.getScene().lookup("#contBtn").setDisable(false);
	}
	
	
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
	
	
	
	
	
//	GameplayScreen - [2]
	@FXML
	public void moveUp() {
		GridPane gp = (GridPane) theScene.lookup("#navGrid");
		List<Node> list = gp.getChildren();
		Button btn = (Button) list.get(0);
		btn.setDisable(!Engine.currentMap.moveUp());
		if(Engine.currentMap.getYCoord() == 0){
			btn.setDisable(true);
		}
		mapLabel.setText(Engine.currentMap.toString());
		Button btn2 = (Button) list.get(1);
		btn2.setDisable(false);
		checkRoom();
	}
	
	
	@FXML
	public void moveDown() {
		GridPane gp = (GridPane) theScene.lookup("#navGrid");
		List<Node> list = gp.getChildren();
		Button btn = (Button) list.get(1);
		btn.setDisable(!Engine.currentMap.moveDown());
		if(Engine.currentMap.getYCoord() == Engine.currentMap.getMapSize()-1){
			btn.setDisable(true);
		}
		mapLabel.setText(Engine.currentMap.toString());
		Button btn2 = (Button) list.get(0);
		btn2.setDisable(false);
		checkRoom();
	}
	
	
	@FXML
	public void moveLeft() {
		GridPane gp = (GridPane) theScene.lookup("#navGrid");
		List<Node> list = gp.getChildren();
		Button btn = (Button) list.get(2);
		btn.setDisable(!Engine.currentMap.moveLeft());
		if(Engine.currentMap.getXCoord() == 0){
			btn.setDisable(true);
		}
		mapLabel.setText(Engine.currentMap.toString());
		Button btn2 = (Button) list.get(3);
		btn2.setDisable(false);
		checkRoom();
	}
	
	
	@FXML
	public void moveRight() {
		GridPane gp = (GridPane) theScene.lookup("#navGrid");
		List<Node> list = gp.getChildren();
		Button btn = (Button) list.get(3);
		btn.setDisable(!Engine.currentMap.moveRight());
		if(Engine.currentMap.getXCoord() == Engine.currentMap.getMapSize()-1){
			btn.setDisable(true);
		}
		mapLabel.setText(Engine.currentMap.toString());
		Button btn2 = (Button) list.get(2);
		btn2.setDisable(false);
		checkRoom();
	}
	
	
	
//	combat screen
	@FXML
	public void attack() {
		
	}
	
	
	
	
	
//	depo screen
	@FXML
	public void changeParts() {
		
	}
	
	@FXML
	public void saveGame() {
		Engine.saveFile(Engine.currentRobot);
	}
	
	
//	change parts screen
	@FXML
	public void swapParts() {
		
	}
	
	
	
	
	
	
	
	
	
	
}
