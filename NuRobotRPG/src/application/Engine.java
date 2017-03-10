package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import enums.*;
import models.*;

public class Engine{

	public static HashMap<String, Robot> favorites = new HashMap<>();
	public static ArrayList<Part> inventory = new ArrayList<Part>();
	public static Robot currentRobot = null;
	public static Map currentMap;
	public static int score = 0;
	public static Robot preset;
	public boolean isLoadable;

	public static void setPreset(Robot enemy) {
		preset = enemy;
	}
	
	public static Robot getPreset(){
		return preset;
	}

	public static void saveFile() {
		File f = new File("NURobotSave.txt");
		try {
			f.createNewFile();
			if (f.isFile() && f.canWrite()) {
				FileWriter write = new FileWriter(f);
				BufferedWriter out = new BufferedWriter(write);
				out.write(currentRobot.getName());
				out.newLine();
				out.write(saveParts(currentRobot.getHead()));
				for (Arm a : currentRobot.getArms()) {
					out.newLine();
					out.write(saveParts(a));
				}
				out.newLine();
				out.write(saveParts(currentRobot.getTorso()));
				out.newLine();
				out.write(saveParts(currentRobot.getLegs()));
				out.newLine();
				if (favorites.size() > 0) {
					Integer size = favorites.size();
					out.write(size.toString());
					for (String s : favorites.keySet()) {
						Robot r = favorites.get(s);
						out.newLine();
						out.write(r.getName());
						out.newLine();
						out.write(saveParts(r.getHead()));
						for (Arm a : r.getArms()) {
							out.newLine();
							out.write(saveParts(a));
						}
						out.newLine();
						out.write(saveParts(r.getTorso()));
						out.newLine();
						out.write(saveParts(r.getLegs()));
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
			ViewControl.setTextOutput("Unsuccessful Save");
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
		favorites.clear();
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
					} else {
						// there is no data to load from save file
						System.out.println("No data to read from save file.");
						return;
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
						Robot fav = new Robot(loadedParts);
						favorites.put(fav.getName(), fav);
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
				line = reader.readLine();
				score = Integer.parseInt(line);
				reader.close();
			} catch (IOException e) {
				
			}
		}
	}
}