package models;

import enums.*;

public class Torso extends Part{

	private Type defenceType;
	private String name;
	
	public Torso(){
		
	}
	
	public Torso(Type defenceType, String name, Rarity rare, int weight){
		setDefenceType(defenceType);
		setName(name);
		setRarity(rare);
		setWeight(weight);
	}
	
	public Type getDefenceType() {
		return defenceType;
	}
	public void setDefenceType(Type defenceType) {
		this.defenceType = defenceType;
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
		sb.append("Torso\n");
		sb.append(getName());
		sb.append("\n");
		sb.append("Rarity : ");
		sb.append(getRarity().toString().charAt(0));
		sb.append(getRarity().toString().substring(1, getRarity().toString().length()).toLowerCase());
		sb.append("\n");
		sb.append("Defence Type : ");
		sb.append(getDefenceType().toString().charAt(0));
		sb.append(getDefenceType().toString().substring(1, getDefenceType().toString().length()).toLowerCase());
		sb.append("\n");
		sb.append("Weight : ");
		sb.append(getWeight());
		sb.append(" tons");
		return sb.toString();
	}
}
