package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import enums.*;
import models.*;

public class Engine {

	public static ArrayList<Robot> favorites = new ArrayList<Robot>();
	public static ArrayList<Part> inventory = new ArrayList<Part>();
	public static Robot currentRobot;
	public static Map currentMap;
	public static int score = 0;

	public static void run(Robot r1, int difficulty) {
		Robot robbie = new Robot(3);

		String speed = Integer.toString(robbie.getSpeed());
		ViewControl.setTextOutput(r1.toString() + "Speed: " + speed);

		System.out.println(robbie);
		System.out.println(robbie.getSpeed());

		Robot rotten = new Robot(3);
		System.out.println(rotten);
		System.out.println(rotten.getSpeed());
//		fight(r1, rotten);
	}
	
	
	public static void fight(Robot atk, Robot def, boolean user) {
		int damage = atk.attack();
		
		def.takeDamage(damage);
		
		if (user) {
			score += damage;
		}
		
		ViewControl.setTextOutput(atk.getName() + " attacked and dealt " + damage + " damage to " + def.getName());		
	}

	
//	public static void fight(Robot player, Robot enemy) {
//		int playerSpeed = player.getSpeed();
//		int enemySpeed = enemy.getSpeed();
//		do {
//			if (playerSpeed >= enemySpeed) {
//				// player turn
//				int damage = player.attack();
//				enemy.takeDamage(damage);
//				score += damage;
//				System.out.println("Player took " + damage + " damage");
//				enemySpeed += enemy.getSpeed();
//			} else {
//				// enemy turn
//				int damage = enemy.attack();
//				player.takeDamage(damage);
//				System.out.println("Enemy took " + damage + " damage");
//				playerSpeed += player.getSpeed();
//			}
//		} while (player.isAlive() && enemy.isAlive());
//		if (player.isAlive()) {
//			inventory.add(enemy.getDrop());
//			System.out.println(inventory);
//			System.out.println("Player Won");
//		} else {
//			// do what happens when a player dies
//			System.out.println("Player Lost");
//		}
//	}

	public static void saveFile(Robot current) {
		File f = new File("NURobotSave.txt");
		try {
			f.createNewFile();
			if (f.isFile() && f.canWrite()) {
				FileWriter write = new FileWriter(f);
				BufferedWriter out = new BufferedWriter(write);
				out.write(current.getName());
				out.newLine();
				out.write(saveParts(current.getHead()));
				for (Arm a : current.getArms()) {
					out.newLine();
					out.write(saveParts(a));
				}
				out.newLine();
				out.write(saveParts(current.getTorso()));
				out.newLine();
				out.write(saveParts(current.getLegs()));
				out.newLine();
				if (favorites.size() > 0) {
					Integer size = favorites.size();
					out.write(size.toString());
					for (Robot r : favorites) {
						out.newLine();
						out.write(r.getName());
						out.newLine();
						out.write(saveParts(current.getHead()));
						for (Arm a : current.getArms()) {
							out.newLine();
							out.write(saveParts(a));
						}
						out.newLine();
						out.write(saveParts(current.getTorso()));
						out.newLine();
						out.write(saveParts(current.getLegs()));
					}
				} else {
					out.write("0");
				}
				out.newLine();
				if (inventory.size() > 0) {
					Integer size = inventory.size();
					out.write(size.toString());
					for (Part p : inventory) {
						out.newLine();
						out.write(saveParts(p));
					}
				} else {
					out.write("0");
				}
				out.newLine();
				Integer l = score;
				out.write(l.toString());
				out.flush();
				out.close();
			} else {
				throw new FileNotFoundException("Save not complete.");
			}
		} catch (IOException e) {
			System.out.println("Unsuccessful Save");
		}
	}

	public static String saveParts(Part p) {
		StringBuilder sb = new StringBuilder();
		if (p.getPartType().equalsIgnoreCase("Head")) {
			Head h = (Head) p;
			sb.append("Head" + " :: ");
			sb.append(h.getName() + " :: " + h.getWeight() + " :: " + h.getRarity().toString() + " :: ");
			if (h.getRarity() == Rarity.EXPERIMENTAL) {
				sb.append(h.getAttackType().toString());
			} else {
				sb.append("null");
			}
			sb.append(" :: " + h.getFunction());
		} else if (p.getPartType().equalsIgnoreCase("Arm")) {
			Arm a = (Arm) p;
			sb.append("Arm" + " :: ");
			sb.append(a.getName() + " :: " + a.getWeight() + " :: " + a.getRarity().toString() + " :: "
					+ a.getAttackType().toString() + " :: " + a.getFunction());
		} else if (p.getPartType().equalsIgnoreCase("Torso")) {
			Torso t = (Torso) p;
			sb.append("Torso" + " :: ");
			sb.append(t.getName() + " :: " + t.getWeight() + " :: " + t.getRarity().toString() + " :: "
					+ t.getDefenceType().toString());
		} else {
			Leg l = (Leg) p;
			sb.append("Leg" + " :: ");
			sb.append(l.getName() + " :: " + l.getWeight() + " :: " + l.getRarity().toString() + " :: ");
			if (l.isTreads()) {
				sb.append("Treads");
			} else {
				sb.append("Legs");
			}
		}
		return sb.toString();
	}

	public static void loadGame() {
		File f = new File("NURobotSave.txt");
		if (f.exists() && f.isFile()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(f));
				String line = null;
				String loadedParts = "";
				boolean builtRobot = false;
				do {
					if ((line = reader.readLine()) != null && !line.isEmpty()) {
						if (!line.contains(" :: ")) {
							loadedParts += line + "\n";
						} else {
							if (line.contains("Head")) {
								loadedParts += line + "\n";
							} else if (line.contains("Arm")) {
								loadedParts += line + "\n";
							} else if (line.contains("Torso")) {
								loadedParts += line + "\n";
							} else if (line.contains("Leg")) {
								loadedParts += line;
								builtRobot = true;
							}
						}
					}
				} while (!builtRobot);
				currentRobot = new Robot(loadedParts);
				line = reader.readLine();
				int numOfFavs = Integer.parseInt(line);
				if (numOfFavs != 0) {
					for (int i = 0; i < numOfFavs; i++) {
						loadedParts = "";
						builtRobot = false;
						do {
							if ((line = reader.readLine()) != null && !line.isEmpty()) {
								if (!line.contains(" :: ")) {
									loadedParts += line + "\n";
								} else {
									if (line.contains("Head")) {
										loadedParts += line + "\n";
									} else if (line.contains("Arm")) {
										loadedParts += line + "\n";
									} else if (line.contains("Torso")) {
										loadedParts += line + "\n";
									} else if (line.contains("Leg")) {
										loadedParts += line;
										builtRobot = true;
									}
								}
							}
						} while (!builtRobot);
						favorites.add(new Robot(loadedParts));
					}
				}
				line = reader.readLine();
				numOfFavs = Integer.parseInt(line);
				if (numOfFavs != 0) {
					for (int i = 0; i < numOfFavs; i++) {
						if ((line = reader.readLine()) != null && !line.isEmpty()) {
							if (line.contains("Head")) {
								inventory.add(new Head(line));
							} else if (line.contains("Arm")) {
								inventory.add(new Arm(line));
							} else if (line.contains("Torso")) {
								inventory.add(new Torso(line));
							} else if (line.contains("Leg")) {
								inventory.add(new Leg(line));
							}
						}
					}
				}
				reader.close();
			} catch (IOException e) {

			}
		}
	}
}