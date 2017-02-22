package models;

import enums.*;

public class Arm extends Part{

	private Type attackType;
	private String name;
	private String function;
	
	public Arm(){
		
	}
	
	public Arm(Type attackType, String name, String function, Rarity rare, int weight){
		setAttackType(attackType);
		setName(name);
		setFunction(function);
		setRarity(rare);
		setWeight(weight);
	}
	
	public Type getAttackType() {
		return attackType;
	}
	public void setAttackType(Type attackType) {
		this.attackType = attackType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Arm\n");
		sb.append(getName());
		sb.append("\n");
		sb.append("Rarity : ");
		sb.append(getRarity().toString().charAt(0));
		sb.append(getRarity().toString().substring(1, getRarity().toString().length()).toLowerCase());
		sb.append("\n");
		sb.append("Attack Type : ");
		sb.append(getAttackType().toString().charAt(0));
		sb.append(getAttackType().toString().substring(1, getAttackType().toString().length()).toLowerCase());
		sb.append("\n");
		sb.append("Weight : ");
		sb.append(getWeight());
		sb.append(" tons");
		return sb.toString();
	}
}
