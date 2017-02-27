package application;

import java.util.ArrayList;
import java.util.Random;

import models.Part;
import models.Robot;

public class Engine {

	public static ArrayList<Robot> favorites = new ArrayList<Robot>();
	public static ArrayList<Part> inventory = new ArrayList<Part>();
	public static int score = 0;

	public static void run() {
		Robot robbie = new Robot(3);
		System.out.println(robbie);
		System.out.println(robbie.getSpeed());
		Robot rotten = new Robot(3);
		System.out.println(rotten);
		System.out.println(rotten.getSpeed());
		fight(robbie, rotten);
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
				System.out.println("player took " + damage + " damage");
				enemySpeed += enemy.getSpeed();
			} else {
				// enemy turn
				int damage = enemy.attack();
				player.takeDamage(damage);
				System.out.println("enemy took " + damage + " damage");
				playerSpeed += player.getSpeed();
			}
		} while (player.isAlive() && enemy.isAlive());
		if (player.isAlive()) {
			inventory.add(enemy.getDrop());
			System.out.println(inventory);
			System.out.println("Player won");
		} else {
			// do what happens when a player dies
			System.out.println("Player lost");
		}
	}
}