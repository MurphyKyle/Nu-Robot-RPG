package models;

import enums.*;

public class Part {

	protected Rarity rare;
	protected int weight;
	
	public Rarity getRare() {
		return rare;
	}
	public void setRare(Rarity rare) {
		this.rare = rare;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(getRare().toString().charAt(0));
		sb.append(getRare().toString().substring(1, getRare().toString().length()));
		return sb.toString();
	}
}
