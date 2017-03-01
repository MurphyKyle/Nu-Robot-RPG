package application;

import models.*;

public class Test {
	
	public static void main(String[] args){
		run();
	}
	
	public static void run() {
		Robot r1 = new Robot(3);
		System.out.println(r1.toString());
		Engine.favorites.add(new Robot(1));
		Engine.favorites.add(r1);
		Engine.inventory.add(new Arm(2));
		Engine.saveFile(r1);
	}
}
