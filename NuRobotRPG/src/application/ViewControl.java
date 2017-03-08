package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
	
	
	private static void setOutputLabel() {
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
			previousScene = sceneList.get(2);
			theStage.setScene(sceneList.get(3));
			Button act1 = (Button) theStage.getScene().lookup("#action1");
			act1.setText(Engine.currentRobot.getActionMenu().get(0));
			act1.setDisable(true);
			Button act2 = (Button) theStage.getScene().lookup("#action2");
			act2.setText(Engine.currentRobot.getActionMenu().get(1));
			act2.setDisable(true);
			Button act3 =  (Button) theStage.getScene().lookup("#action3");
			if(Engine.currentRobot.getActionMenu().size() == 2){
				act3.setText("No Function");
				act3.setDisable(true);
			}else{
				act3.setText(Engine.currentRobot.getActionMenu().get(2));
			}
//			ChoiceBox<String> cb = (ChoiceBox<String>) theStage.getScene().lookup("#actionMenu");
//			ObservableList<String> items = FXCollections.observableArrayList(Engine.currentRobot.getActionMenu());
//			cb.setItems(items);
		} else if (map[Engine.currentMap.getXCoord()][Engine.currentMap.getYCoord()].isDepot()) {
			// index 4 is depot
			currentSceneIndex = 2;
			setPreviousScene();
			previousScene = sceneList.get(2);
			theStage.setScene(sceneList.get(4));
		} else {
			// the room should be empty
			return;
		}
		
		setOutputLabel();
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
		
//		This code should make a group of nodes that are a room. IDK what the location
//		on the screen for them should be. Once it's been created you can make a new
//		scene with root as its root. I think I did it a little off tho.
		
//		Here's the link I got it from : 
//		http://stackoverflow.com/questions/27870674/display-2d-array-as-grid-in-javafx
		
//		Group root = new Group();
//		for(int x = 0; x < Engine.currentMap.getMapSize(); x++) {
//			for(int y = 0; y < Engine.currentMap.getMapSize(); y++) {
//				Node room = new Node(Engine.currentMap.getRooms()[x][y], horizontal location, vertical location);
//				root.getChildren().add(room);
//			}
//		}
	}
	
	
	
	
	
//	startup screen - [0]
	@FXML
	public void createNewGame() throws IOException {
		setPreviousScene();
		theScene = sceneList.get(currentSceneIndex);
		setOutputLabel();
		
		if (theScene.lookup("#createRobotBtn").isDisabled()) {
			theScene.lookup("#contBtn").setDisable(true);
			theScene.lookup("#createRobotBtn").setDisable(false);
		}
		
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
		theStage.setTitle("Start Game");
		theScene.lookup("#createRobotBtn").setDisable(true);
		theScene.lookup("#contBtn").setDisable(false);
		Engine.loadGame();
		setTextOutput("Load complete");
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
		Engine.saveFile();
		setTextOutput("Load complete");
	}
	
//	change parts screen
	@FXML
	public void swapParts() {
		
	}
	
	
	
	
	
	
	
	
	
	
}
