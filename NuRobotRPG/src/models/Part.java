package models;

import enums.*;

public abstract class Part {

	protected Rarity rarity;
	protected int weight;
<<<<<<< HEAD
	protected String name;
=======
	private String name;
>>>>>>> e0e8f12fb18c99811412ba68f5be84c678a8162c
	
	public Rarity getRarity() {
		return rarity;
	}
	public void setRarity(Rarity rarity) {
		this.rarity = rarity;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		System.out.println("Rarity : ");
		sb.append(getRarity().toString().charAt(0));
		sb.append(getRarity().toString().substring(1, getRarity().toString().length()).toLowerCase());
		sb.append("\n");
		sb.append("Weight : ");
		sb.append(getWeight());
		sb.append(" tons");
		return sb.toString();
	}
}
