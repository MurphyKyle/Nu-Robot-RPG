package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import models.Arm;
import models.Head;
import models.Leg;
import models.Map;
import models.Part;
import models.Robot;
import models.Room;
import models.Torso;

public class ViewControl {

	private static Stage theStage;
	private static Scene theScene;
	private static Scene previousScene;
	public ToggleGroup diffChoice;
	private static Label outputLabel;
	private static Label mapLabel;

	private Robot enemyBot;
	private static AnchorPane mapPane;

	private static int difficulty;
	private static ArrayList<Scene> sceneList = new ArrayList<>();
	private static int currentSceneIndex = 0;
	private static int prevSceneIndex = 0;

	private static String startupScreen = "/view/StartupScreen.fxml";
	private static String newGamePrompt = "/view/StartGameScreen.fxml";
	private static String gameplayScreen = "/view/GameplayScreen.fxml";
	private static String combatScreen = "/view/CombatScreen.fxml";
	private static String depotScreen = "/view/DepotScreen.fxml";
	private static String changePartsScreen = "/view/ChangePartsScreen.fxml";
	private CombatEngine combat;
	private static int action = -1;

	// general methods
	public void setScenes() throws IOException {
		sceneList.add(new Scene(FXMLLoader.load(getClass().getResource(startupScreen)))); // 0
		sceneList.add(new Scene(FXMLLoader.load(getClass().getResource(newGamePrompt)))); // 1
		sceneList.add(new Scene(FXMLLoader.load(getClass().getResource(gameplayScreen)))); // 2
		sceneList.add(new Scene(FXMLLoader.load(getClass().getResource(combatScreen)))); // 3
		sceneList.add(new Scene(FXMLLoader.load(getClass().getResource(depotScreen)))); // 4
		sceneList.add(new Scene(FXMLLoader.load(getClass().getResource(changePartsScreen)))); // 5
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
			if(currentSceneIndex == 4){
				previousScene = sceneList.get(2);
				currentSceneIndex = 4;
			}else if(currentSceneIndex == 5){
				previousScene = sceneList.get(4);
				currentSceneIndex = 5;
			}
			theScene = previousScene;
			theStage.setScene(theScene);
			if (previousScene.equals(sceneList.get(2))) {
				updateMap();
				// this should solve a problem where you cannot go back more
				// than twice from a depot
				prevSceneIndex = 1;
				currentSceneIndex = 2;
			}

			if (currentSceneIndex <= 1) {
				prevSceneIndex = 0;
			} else {
				if(prevSceneIndex == 0){
					prevSceneIndex = 0;
				}else{
					prevSceneIndex--;
				}
			}
			previousScene = sceneList.get(prevSceneIndex);
			currentSceneIndex--;
		}

		theStage.show();
	}

	private void setPreviousScene() {
		// set the previous scene to the current scene
		// increment current scene to be loaded after this method is done
		if (currentSceneIndex < sceneList.size() - 1) {
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
		previousScene = sceneList.get(1);
		theScene = sceneList.get(2);
		// load appropriate screen if it is
		// if theres an enemy
		if (map[Engine.currentMap.getXCoord()][Engine.currentMap.getYCoord()].isOccupied()) {
			// index 3 is combat
			occupied = true;
			previousScene = sceneList.get(2);
			theScene = sceneList.get(3);
			setOutputLabel();
			theStage.setScene(theScene);
			Button act1 = (Button) theStage.getScene().lookup("#action1");
			act1.setText(Engine.currentRobot.getActionMenu().get(0));
			act1.setDisable(true);
			Button act2 = (Button) theStage.getScene().lookup("#action2");
			act2.setText(Engine.currentRobot.getActionMenu().get(1));
			act2.setDisable(true);
			Button act3 = (Button) theStage.getScene().lookup("#action3");
			if (Engine.currentRobot.getActionMenu().size() == 2) {
				act3.setText("No Function");
				act3.setDisable(true);
			} else {
				act3.setText(Engine.currentRobot.getActionMenu().get(2));
			}
			enemyBot = new Robot(difficulty);
			// ChoiceBox<String> cb = (ChoiceBox<String>)
			// theStage.getScene().lookup("#actionMenu");
			// ObservableList<String> items =
			// FXCollections.observableArrayList(Engine.currentRobot.getActionMenu());
			// cb.setItems(items);
			// if there isn't an enemy but there is a depot
		} else if (map[Engine.currentMap.getXCoord()][Engine.currentMap.getYCoord()].isDepot()) {
			// index 4 is depot
			// currentSceneIndex = 2;
			// setPreviousScene();
			previousScene = sceneList.get(2);
			Engine.currentRobot.setCurrentHp(Engine.currentRobot.getMaxHp());
			theStage.setScene(sceneList.get(4));
			theScene = sceneList.get(4);
			setOutputLabel();
			setTextOutput("You made it to a checkpoint! Please pick an option:");
			theStage.setScene(theScene);
		} else {
			// the room should be empty
			updateMap();
			return;
		}

		setOutputLabel();
		theStage.show();
		if (occupied) {
			Task<Void> task = new Task<Void>() {
				@Override
				public Void call() throws Exception {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							updateStats(Engine.currentRobot, enemyBot);
						}
					});
					return null;
				}
			};
			Thread th = new Thread(task);
			th.setDaemon(true);
			th.start();
			combatInitiate();
		}
	}

	public void combatInitiate() {
		combat = new CombatEngine(Engine.currentRobot, enemyBot);
		combat.setGui(Thread.currentThread());
		combat.start();
		theStage.show();
	}

	public static void aftermath(Robot bot) {
		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() throws Exception {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						if (Engine.currentRobot.isAlive()) {
							Engine.currentRobot.takeDamage(-1 * (Engine.currentRobot.getMaxHp() / 2));
							Engine.currentRobot.setCombatSpeed(Engine.currentRobot.getSpeed());
							Engine.inventory.add(bot.getDrop());
							Engine.currentMap.getRooms()[Engine.currentMap.getXCoord()][Engine.currentMap.getYCoord()]
									.setOccupied(false);
							theScene = previousScene;
							theStage.setScene(previousScene);
							mapRenew();
						} else {
							currentSceneIndex = 0;
							theStage.setScene(sceneList.get(0));
							Engine.currentRobot.setCurrentHp(Engine.currentRobot.getMaxHp());
						}
					}
				});
				return null;
			}
		};
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}

	public static void setCombatLabel(String l) {
		Label outputLabel = (Label) theStage.getScene().lookup("#outputLabel");
		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() throws Exception {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						outputLabel.setText(l);
					}
				});
				return null;
			}
		};
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}

	public static void updateStats(Robot current, Robot enemy) {
		Label enemyLab = (Label) theStage.getScene().lookup("#enemyOutputLbl");
		Label playerLab = (Label) theStage.getScene().lookup("#playerOutputLbl");
		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() throws Exception {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						enemyLab.setText(enemy.toString());
						playerLab.setText(current.toString());
					}
				});
				return null;
			}
		};
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
		Engine.currentRobot = current;
	}

	public static void playerAccess() {
		for (int i = 1; i < Engine.currentRobot.getActionMenu().size() + 1; i++) {
			Button act1 = (Button) theStage.getScene().lookup("#action" + i);
			act1.setDisable(false);
		}
	}

	public static int getAction() {
		return action;
	}

	public static void setAction(int act) {
		action = act;
	}

	@FXML
	public void action0() {
		for (int i = 1; i < Engine.currentRobot.getActionMenu().size() + 1; i++) {
			Button action = (Button) theStage.getScene().lookup("#action" + i);
			action.setDisable(true);
		}
		action = 0;
	}

	@FXML
	public void action1() {
		for (int i = 1; i < Engine.currentRobot.getActionMenu().size() + 1; i++) {
			Button action = (Button) theStage.getScene().lookup("#action" + i);
			action.setDisable(true);
		}
		action = 1;
	}

	@FXML
	public void action2() {
		for (int i = 1; i < Engine.currentRobot.getActionMenu().size() + 1; i++) {
			Button action = (Button) theStage.getScene().lookup("#action" + i);
			action.setDisable(true);
		}
		action = 2;
	}

	private void updateMap() {
		mapPane = (AnchorPane) theScene.lookup("#mapPane");

		// This code should make a group of rectangles that are a room.
		for (int x = 0; x < Engine.currentMap.getMapSize(); x++) {
			for (int y = 0; y < Engine.currentMap.getMapSize(); y++) {
				Rectangle rect = new Rectangle(25, 25);
				// If it is within range set seen to true
				if (Engine.currentMap.getXCoord() + 1 == x && Engine.currentMap.getYCoord() == y) {
					Engine.currentMap.getRooms()[x][y].setSeen(true);
				} else if (Engine.currentMap.getXCoord() - 1 == x && Engine.currentMap.getYCoord() == y) {
					Engine.currentMap.getRooms()[x][y].setSeen(true);
				} else if (Engine.currentMap.getXCoord() == x && Engine.currentMap.getYCoord() + 1 == y) {
					Engine.currentMap.getRooms()[x][y].setSeen(true);
				} else if (Engine.currentMap.getXCoord() == x && Engine.currentMap.getYCoord() - 1 == y) {
					Engine.currentMap.getRooms()[x][y].setSeen(true);
				}
				// if it has been seen at some point, fill it with the right
				// color
				if (Engine.currentMap.getRooms()[x][y].isSeen()) {
					if (Engine.currentMap.getRooms()[x][y].isDepot()) {
						rect.setFill(Color.BLUE);
					} else if (Engine.currentMap.getRooms()[x][y].isOccupied()) {
						rect.setFill(Color.RED);
					} else {
						rect.setFill(Color.WHITE);
					}
				} else {
					rect.setFill(Color.SLATEGRAY);
				}
				if (x == Engine.currentMap.getXCoord() && y == Engine.currentMap.getYCoord()) {
					rect.setFill(Color.GREEN);
				}
				rect.setStroke(Color.BLACK);
				rect.setLayoutX(x * 25);
				rect.setLayoutY(y * 25);
				mapPane.getChildren().addAll(rect);
			}
		}
	}

	@FXML
	public static void mapRenew() {
		mapLabel.setText(Engine.currentMap.toString());
		mapPane = (AnchorPane) theScene.lookup("#mapPane");

		// This code should make a group of rectangles that are a room.
		for (int x = 0; x < Engine.currentMap.getMapSize(); x++) {
			for (int y = 0; y < Engine.currentMap.getMapSize(); y++) {
				Rectangle rect = new Rectangle(25, 25);
				// If it is within range set seen to true
				if (Engine.currentMap.getXCoord() + 1 == x && Engine.currentMap.getYCoord() == y) {
					Engine.currentMap.getRooms()[x][y].setSeen(true);
				} else if (Engine.currentMap.getXCoord() - 1 == x && Engine.currentMap.getYCoord() == y) {
					Engine.currentMap.getRooms()[x][y].setSeen(true);
				} else if (Engine.currentMap.getXCoord() == x && Engine.currentMap.getYCoord() + 1 == y) {
					Engine.currentMap.getRooms()[x][y].setSeen(true);
				} else if (Engine.currentMap.getXCoord() == x && Engine.currentMap.getYCoord() - 1 == y) {
					Engine.currentMap.getRooms()[x][y].setSeen(true);
				}
				// if it has been seen at some point, fill it with the right
				// color
				if (Engine.currentMap.getRooms()[x][y].isSeen()) {
					if (Engine.currentMap.getRooms()[x][y].isDepot()) {
						rect.setFill(Color.BLUE);
					} else if (Engine.currentMap.getRooms()[x][y].isOccupied()) {
						rect.setFill(Color.RED);
					} else {
						rect.setFill(Color.WHITE);
					}
				} else {
					rect.setFill(Color.SLATEGRAY);
				}
				if (x == Engine.currentMap.getXCoord() && y == Engine.currentMap.getYCoord()) {
					rect.setFill(Color.GREEN);
				}
				rect.setStroke(Color.BLACK);
				rect.setLayoutX(x * 25);
				rect.setLayoutY(y * 25);
				mapPane.getChildren().addAll(rect);
			}
		}
	}

	@FXML
	public void newMap() {
		Engine.currentMap = new Map(difficulty);
		mapLabel.setText(Engine.currentMap.toString());

		// This code should make a group of nodes that are a room. IDK what the
		// location
		// on the screen for them should be. Once it's been created you can make
		// a new
		// scene with root as its root. I think I did it a little off tho.

		// Here's the link I got it from :
		// http://stackoverflow.com/questions/27870674/display-2d-array-as-grid-in-javafx

		// Group root = new Group();
		// for(int x = 0; x < Engine.currentMap.getMapSize(); x++) {
		// for(int y = 0; y < Engine.currentMap.getMapSize(); y++) {
		// Node room = new Node(Engine.currentMap.getRooms()[x][y], horizontal
		// location, vertical location);
		// root.getChildren().add(room);
		// }
		// }
		updateMap();

	}

	// startup screen - [0]
	@FXML
	public void createNewGame() throws IOException {
		setPreviousScene();
		theScene = sceneList.get(currentSceneIndex);
		setOutputLabel();

		if (theScene.lookup("#createRobotBtn").isDisabled()) {
			theScene.lookup("#contBtn").setDisable(true);
			theScene.lookup("#createRobotBtn").setDisable(false);
			setTextOutput("");
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

	// start game screen - [1]
	@FXML
	public void createRobot() {
		getDifficulty();
		Engine.currentRobot = new Robot(difficulty);
		Engine.currentRobot.setName("Player");
		setTextOutput(Engine.currentRobot.toString());
		theStage.getScene().lookup("#contBtn").setDisable(false);
		Engine.favorites.put(Engine.currentRobot.getName(), Engine.currentRobot);
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
		updateMap();
		theStage.show();
	}

	// GameplayScreen - [2]
	@FXML
	public void moveUp() {
		GridPane gp = (GridPane) theScene.lookup("#navGrid");
		List<Node> list = gp.getChildren();
		Button btn = (Button) list.get(0);
		btn.setDisable(!Engine.currentMap.moveUp());
		if (Engine.currentMap.getYCoord() == 0) {
			btn.setDisable(true);
		}
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
		if (Engine.currentMap.getYCoord() == Engine.currentMap.getMapSize() - 1) {
			btn.setDisable(true);
		}
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
		if (Engine.currentMap.getXCoord() == 0) {
			btn.setDisable(true);
		}
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
		if (Engine.currentMap.getXCoord() == Engine.currentMap.getMapSize() - 1) {
			btn.setDisable(true);
		}
		Button btn2 = (Button) list.get(2);
		btn2.setDisable(false);
		checkRoom();
	}

	// depo screen
	

	// change parts screen

	private static Part currentPart;
	private static Robot building;
	private int partIndex;
	private static ArrayList<Part> parts = new ArrayList<>();

	@FXML
	public void changeParts() {
		System.out.println("Change parts button smash");
		currentSceneIndex = 5;
		previousScene = sceneList.get(4);
		theScene = sceneList.get(5);
		setOutputLabel();
		theStage.setScene(theScene);

		parts.clear();
		ArrayList<Part> l = new ArrayList<>();
		for (Part p : Engine.inventory) {
			l.add(p);
		}
		parts = l;
		partIndex = 0;
		currentPart = parts.get(partIndex);
		setPart();

		updateFavs();

		Button right = (Button) theStage.getScene().lookup("#right");
		right.setDisable(true);
		Button left = (Button) theStage.getScene().lookup("#left");
		left.setDisable(true);
		Button head = (Button) theStage.getScene().lookup("#accessHead");
		head.setDisable(true);
		Button torso = (Button) theStage.getScene().lookup("#accessTorso");
		torso.setDisable(true);
		Button legs = (Button) theStage.getScene().lookup("#accessLegs");
		legs.setDisable(true);
		Button acRight = (Button) theStage.getScene().lookup("#accessRight");
		acRight.setDisable(true);
		Button acLeft = (Button) theStage.getScene().lookup("#accessLeft");
		acLeft.setDisable(true);
		theStage.show();
	}

	public void accessHead() {
		Label rel = (Label) theStage.getScene().lookup("#robSpecs");
		rel.setText(building.getPartSpecs(building.getHead()));
	}

	public void accessTorso() {
		Label rel = (Label) theStage.getScene().lookup("#robSpecs");
		rel.setText(building.getPartSpecs(building.getTorso()));
	}

	public void accessLegs() {
		Label rel = (Label) theStage.getScene().lookup("#robSpecs");
		rel.setText(building.getPartSpecs(building.getLegs()));
	}

	public void accessLeft() {
		Label rel = (Label) theStage.getScene().lookup("#robSpecs");
		rel.setText(building.getPartSpecs(building.getArm(0)));
	}

	public void accessRight() {
		Label rel = (Label) theStage.getScene().lookup("#robSpecs");
		rel.setText(building.getPartSpecs(building.getArm(1)));
	}

	public void setPart() {
		Label part = (Label) theStage.getScene().lookup("#partInfo");
		part.setText(Engine.currentRobot.getPartSpecs(currentPart));
	}

	public void goUp() {
		if (partIndex == parts.size() - 1) {
			partIndex = 0;
		} else {
			partIndex++;
		}
		currentPart = parts.get(partIndex);
		setPart();
	}

	public void goDown() {
		if (partIndex == 0) {
			partIndex = parts.size() - 1;
		} else {
			partIndex--;
		}
		currentPart = parts.get(partIndex);
		setPart();
	}

	public void setName() {
		TextField field = (TextField) theStage.getScene().lookup("#setName");
		String name = field.getText();
		Engine.favorites.put(name, building);
		building = Engine.favorites.get(name);
		building.setName(name);
		updateFavs();
	}

	public void updateFavs() {
		@SuppressWarnings("unchecked")
		ChoiceBox<String> robotsMenu = (ChoiceBox<String>) theStage.getScene().lookup("#favoriteRobots");
		ArrayList<String> robots = new ArrayList<>();
		robotsMenu.getItems().clear();
		for (String s : Engine.favorites.keySet()) {
			if(s.equals("temp")){
				
			}else{
			robots.add(s);
			}
		}
		ObservableList<String> robotList = FXCollections.observableArrayList(robots);
		robotsMenu.setItems(robotList);
		robotsMenu.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				String val = robotsMenu.getItems().toString();
				val = val.replace('[', ' ');
				val = val.replace(']', ' ');
				val = val.trim();
				for(String s : Engine.favorites.keySet()){
					if(s.equals(val)){
						building= new Robot(Engine.favorites.get(val));
					}
				}
				Button head = (Button) theStage.getScene().lookup("#accessHead");
				head.setDisable(false);
				Button torso = (Button) theStage.getScene().lookup("#accessTorso");
				torso.setDisable(false);
				Button legs = (Button) theStage.getScene().lookup("#accessLegs");
				legs.setDisable(false);
				Button acRight = (Button) theStage.getScene().lookup("#accessRight");
				acRight.setDisable(false);
				Button acLeft = (Button) theStage.getScene().lookup("#accessLeft");
				acLeft.setDisable(false);
			}
		});
	}

	public void equipPart() {
		if (currentPart.getPartType().equals("Arm")) {
			Button right = (Button) theStage.getScene().lookup("#right");
			right.setDisable(false);
			Button left = (Button) theStage.getScene().lookup("#left");
			left.setDisable(false);
		} else if (currentPart.getPartType().equals("Head")) {
			Head h = (Head) currentPart;
			parts.add(building.getHead());
			building.equipHead(h);
			parts.remove(partIndex);
			currentPart = parts.get(partIndex);
			setPart();
			accessHead();
		} else if (currentPart.getPartType().equals("Torso")) {
			Torso h = (Torso) currentPart;
			parts.add(building.getTorso());
			building.equipTorso(h);
			parts.remove(partIndex);
			currentPart = parts.get(partIndex);
			setPart();
			accessTorso();
		} else if (currentPart.getPartType().equals("Legs")) {
			Leg h = (Leg) currentPart;
			parts.add(building.getLegs());
			building.equipLegs(h);
			parts.remove(partIndex);
			currentPart = parts.get(partIndex);
			setPart();
			accessLegs();
		}
	}

	public void equipRight() {
		Arm h = (Arm) currentPart;
		parts.add(building.getArm(1));
		building.equipArm(1, h);
		parts.remove(partIndex);
		currentPart = parts.get(partIndex);
		setPart();
		Button right = (Button) theStage.getScene().lookup("#right");
		right.setDisable(true);
		Button left = (Button) theStage.getScene().lookup("#left");
		left.setDisable(true);
		accessRight();
	}

	public void equipLeft() {
		Arm h = (Arm) currentPart;
		parts.add(building.getArm(0));
		building.equipArm(0, h);
		parts.remove(partIndex);
		currentPart = parts.get(partIndex);
		setPart();
		Button right = (Button) theStage.getScene().lookup("#right");
		right.setDisable(true);
		Button left = (Button) theStage.getScene().lookup("#left");
		left.setDisable(true);
		accessLeft();
	}

	@FXML
	public void saveGame() {
		Engine.saveFile();
		setTextOutput("Save complete");

		previousScene = sceneList.get(2);
		theScene = sceneList.get(4);
		setOutputLabel();
	}


}
