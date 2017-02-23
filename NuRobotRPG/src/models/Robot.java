package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import enums.Type;

public class Robot {
	private static int robotCount = 0;
	private String name = "[Robot - " + robotCount++ + "]";
	private Torso torso;
	private ArrayList<Arm> arms = new ArrayList<>();
	private Head head;
	private Leg legs;
	private int maxHp =  100;
	private int currentHp = 100;
	private int minDmg = 8;
	private int maxDmg = 12;
	private Part drop;
	private boolean isAlive = true;
	
	
	public Robot(int difficulty) {
		arms.add(new Arm(difficulty));
		arms.add(new Arm(difficulty));
		this.head = new Head(difficulty);
		this.torso = new Torso(difficulty);
		this.legs = new Leg(difficulty);
		
		drop = getDropPart();
	}
	
	
	public Robot(String name, Torso torso, ArrayList<Arm> arms, Head head, Leg legs, int maxHp, int currentHp) {
		super();
		this.name = name;
		this.torso = torso;
		this.arms = arms;
		this.head = head;
		this.legs = legs;
		this.maxHp = maxHp;
		this.currentHp = currentHp;
		
		drop = getDropPart();
	}	
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getMaxHp() {
		return maxHp;
	}


	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}


	public int getCurrentHp() {
		return currentHp;
	}


	public void setCurrentHp(int currentHp) {
		this.currentHp = currentHp;
	}


	public Part getDrop() {
		return drop;
	}


	public void setDrop(Part drop) {
		this.drop = drop;
	}


	public void equipTorso(Torso torso) {
		this.torso = torso;
	}


	public void equipArms(ArrayList<Arm> arms) {
		this.arms = arms;
	}


	public void equipHead(Head head) {
		this.head = head;
	}


	public void equipLegs(Leg legs) {
		this.legs = legs;
	}
	
	public int getMinDmg() {
		return this.minDmg;
	}
	
	public void setMinDmg() {
		int minDmg = 0;
		
		for (Arm a : this.arms) {
			minDmg += a.getWeight();
		}
		
		minDmg /= arms.size();
		
		this.minDmg = minDmg;
	}
	
	public int getMaxDmg() {
		return this.maxDmg;
	}
	
	public void setMaxDmg() {
		int maxDmg = 0;
		
		for (Arm a : this.arms) {
			maxDmg += a.getWeight();
		}
		// double check this
		
		this.maxDmg = maxDmg;
	}
	
	public boolean isAlive() {
//		if the currentHp is < or == 0, return false because I AM DED
		return this.currentHp <= 0 ? false : true;
	}


	private Part getDropPart() {	
		//	puts all current robot's parts into a list, shuffles them and randomly picks one to return
		
		ArrayList<Part> parts = new ArrayList<>();
		
		parts.add(this.head);
		parts.add(this.torso);
		parts.add(this.legs);
		parts.addAll(this.arms);
		
		Collections.shuffle(parts);
		
		return parts.get(new Random().nextInt(parts.size()) + 1);
	}
	
	
	public int attack(Robot enemy) {		
		// finish this, each robot now has it's own min and max
		
		int attack = new Random().nextInt(5) + 8;	// 8-12 attack range
		
		return attack;
	}
	
	
	public boolean takeDamage(int damage) {
		this.setCurrentHp(this.getCurrentHp() - damage);
		
		return isAlive();
	}
	
	
	public int getSpeed() {
		
		// double check this
		int maxSpeed = 80;
		int minSpeed = 40;
		
		int speed = 0;
		
		int totalWeight = this.head.getWeight() + this.torso.getWeight() + this.legs.getWeight();
		
		for (Arm a : this.arms) {
			totalWeight += a.getWeight();
		}
		
		speed = (60 / totalWeight) * 40;
		
		// if the legs are treads, speed is decreased by 25% (speed*0.75) -- added later
		
		if (speed > maxSpeed) {
			speed = maxSpeed;
		} else if (speed < minSpeed) {
			speed = minSpeed;
		}
		
		return speed;
	}
	
	
	public String getPartSpecs(Part part) {
		StringBuilder sb = new StringBuilder();
		// finish this
		
		return sb.toString();
	}
	
	
	public ArrayList<String> getActionMenu() {
		ArrayList<String> actions = new ArrayList<>();
		// finish this
		return actions;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Name: ");
		sb.append(getName());
		sb.append("\nHP: ");
		sb.append(currentHp);
		sb.append("/");
		sb.append(maxHp);
		
		return sb.toString();
	}
	
}
