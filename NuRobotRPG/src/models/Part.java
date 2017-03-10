package models;

import enums.*;

public abstract class Part {

	protected Rarity rarity;
	protected int weight;
	protected String name;
	protected float multiplier;
	protected String partType;
	
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
	public float getMultiplier() {
		return multiplier;
	}
	public void setMultiplier(float multiplier) {
		this.multiplier = multiplier;
	}
	public String getPartType() {
		return partType;
	}
	public void setPartType(String partType) {
		this.partType = partType;
	}
	
	public String displaySpec(){
		StringBuilder sb = new StringBuilder();
		sb.append(getPartType());
		sb.append(", ");
		sb.append(getName());
		sb.append(", ");
		return sb.toString();
	}
	
	public String getGeneralSpec(){
		StringBuilder sb = new StringBuilder();
		sb.append("Name: ");
		sb.append(this.getName());
		sb.append("\nWeight: ");
		sb.append(this.getWeight());
		sb.append(" tons\nRarity: ");
		sb.append(this.getRarity().toString());
		sb.append("\n");
		return sb.toString();
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(getPartType());
		sb.append(", ");
		sb.append(getName());
		System.out.println(", ");
		sb.append(getRarity().toString().charAt(0));
		sb.append(getRarity().toString().substring(1, getRarity().toString().length()).toLowerCase());
		sb.append(", ");
		sb.append(getWeight());
		return sb.toString();
	}
}
