package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Robot {
	private String name = "[Unknown]";
	private Torso torso = new Torso();
	private ArrayList<Arm> arms = new ArrayList<>();
	private Head head = new Head();
	private ArrayList<Leg> legs = new ArrayList<>();
	private int maxHp =  100;
	private int currentHp = 100;
	private Part drop;
	
	
	public Robot() {
		
	}
	
	
	public Robot(String name, Torso torso, ArrayList<Arm> arms, Head head, ArrayList<Leg> legs, int maxHp, int currentHp) {
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


	public void equipLegs(ArrayList<Leg> legs) {
		this.legs = legs;
	}


	private Part getDropPart() {	
		//	puts all current robot's parts into a list, shuffles them and randomly picks one to return
		
		ArrayList<Part> parts = new ArrayList<>();
		
		parts.add(this.head);
		parts.add(this.torso);
		parts.addAll(this.arms);
		parts.addAll(this.legs);
		
		Collections.shuffle(parts);
		
		return parts.get(new Random().nextInt(parts.size()) + 1);
	}
	
	
	public int attack(Robot enemy) {
		
		
		return 0;
	}
	
	
	public void takeDamage(int damage) {
		
		
	}
	
	
	public int getSpeed() {
		
		return 0;
	}
	
	
	public String getPartSpecs(Part part) {
		StringBuilder sb = new StringBuilder();
		
		return sb.toString();
	}
	
	
	public ArrayList<String> getActionMenu() {
		ArrayList<String> actions = new ArrayList<>();
		
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
