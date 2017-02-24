package application;

import enums.Rarity;
import enums.Type;
import models.Head;
import models.Map;
import models.Robot;

public class Test {
	
	public static void main(String[] args){
		Robot rb = new Robot(3);
		System.out.println(rb.toString());
		Head he = new Head("Vision", 2, Rarity.EXPERIMENTAL, Type.EXPLOSIVE, "Attack with explosives");
		rb.equipHead(he);
		Robot npc = new Robot(1);
		System.out.println(npc.toString());
		System.out.println(rb.getLegs().getMultiplier());
		System.out.println(rb.getSpeed());
		System.out.println(npc.getSpeed());
	}
	
	public static void run() {
		Map map = new Map(10);
		System.out.println(map);
		map = new Map(20);
		System.out.println(map);
	}
}
