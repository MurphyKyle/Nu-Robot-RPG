package models;

import java.util.Random;

import enums.Rarity;
import enums.Type;

public class Arm extends Part{

	private Type attackType;
	private String function;
	private float multiplier;
	
	public Arm(int difficulty){
		Random rand = new Random();
		int rare = rand.nextInt(100)+1;
		if(difficulty == 1){
			if(rare <= 75){
				setRarity(Rarity.COMMON);
			}else{
				setRarity(Rarity.EXPERIMENTAL);
			}
		}else if(difficulty == 2){
			
		}else{
			
		}
	}
	
	public Arm(Type attackType, String name, String function, Rarity rare, int weight){
		setAttackType(attackType);
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
	
	public Type getAttackType() {
		return attackType;
	}
	public void setAttackType(Type attackType) {
		this.attackType = attackType;
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
