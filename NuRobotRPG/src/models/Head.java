package models;

import enums.*;

public class Head extends Part{
	
	private String name;
	private String function;
	
	public Head(){
		
	}
	
	public Head(String name, String function, Rarity rare, int weight){
		setName(name);
		setFunction(function);
		setRarity(rare);
		setWeight(weight);
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
