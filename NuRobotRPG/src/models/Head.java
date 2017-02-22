package models;

import enums.*;

public class Head extends Part{
	
	private String function;
	private float multiplier;
	
	public Head(){
		
	}
	
	public Head(String name, String function, Rarity rare, int weight){
		setName(name);
		setFunction(function);
		setRarity(rare);
		setWeight(weight);
		float multiplier = 1.0f;
		for(int i = 0; i < Rarity.values().length; i++){
			if(getRarity() == Rarity.values()[i]){
				multiplier += (float)(i*.1);
			}
		}
		setMultiplier(multiplier);
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
	public float getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(float multiplier) {
		this.multiplier = multiplier;
	}
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Head\n");
		sb.append(getName());
		sb.append("\n");
		sb.append("Rarity : ");
		sb.append(getRarity().toString().charAt(0));
		sb.append(getRarity().toString().substring(1, getRarity().toString().length()).toLowerCase());
		sb.append("\n");
		sb.append("Weight : ");
		sb.append(getWeight());
		sb.append(" tons");
		return sb.toString();
	}
}
