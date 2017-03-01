package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import enums.Rarity;
import models.*;

public class Engine {

	public static ArrayList<Robot> favorites = new ArrayList<Robot>();
	public static ArrayList<Part> inventory = new ArrayList<Part>();
	public static int score = 0;

	public static void run(Robot r1, int difficulty) {
		Robot robbie = new Robot(3);

		String speed = Integer.toString(robbie.getSpeed());
		ViewControl.setOutputLabel(r1.toString() + "Speed: " + speed);

		System.out.println(robbie);
		System.out.println(robbie.getSpeed());

		Robot rotten = new Robot(3);
		System.out.println(rotten);
		System.out.println(rotten.getSpeed());
		fight(r1, rotten);
	}

	public static void fight(Robot player, Robot enemy) {
		int playerSpeed = player.getSpeed();
		int enemySpeed = enemy.getSpeed();
		do {
			if (playerSpeed >= enemySpeed) {
				// player turn
				int damage = player.attack();
				enemy.takeDamage(damage);
				score += damage;
				System.out.println("Player took " + damage + " damage");
				enemySpeed += enemy.getSpeed();
			} else {
				// enemy turn
				int damage = enemy.attack();
				player.takeDamage(damage);
				System.out.println("Enemy took " + damage + " damage");
				playerSpeed += player.getSpeed();
			}
		} while (player.isAlive() && enemy.isAlive());
		if (player.isAlive()) {
			inventory.add(enemy.getDrop());
			System.out.println(inventory);
			System.out.println("Player Won");
		} else {
			// do what happens when a player dies
			System.out.println("Player Lost");
		}
	}

	public static void saveFile(Robot current) {
		File f = new File(
				"C:\\Users\\Sagebrecht\\Google Drive\\Neumont\\CSC150\\Project\\Final\\Nu-Robot-RPG\\Save\\NURobotSave.txt");
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
	
}