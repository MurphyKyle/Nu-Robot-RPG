package models;

import java.util.Random;

import enums.*;

public class Head extends Part{
	
	private String function;
	private Type attackType;
	private float multiplier;
	
	public Head(){
		
	}
	
	public Head(int difficulty){
		Random rand = new Random();
		int rare = rand.nextInt(100)+1;
		if(difficulty == 1){
			if(rare <= 75){
				setRarity(Rarity.COMMON);
			}else{
				setRarity(Rarity.UNCOMMON);
			}
		}else if(difficulty == 2){
			if(rare <= 25){
				setRarity(Rarity.COMMON);
			}else if(rare <= 75){
				setRarity(Rarity.UNCOMMON);
			}else if(rare <= 99){
				setRarity(Rarity.RARE);
			}else{
				setRarity(Rarity.EXPERIMENTAL);
			}
		}else{
			if(rare <= 50){
				setRarity(Rarity.UNCOMMON);
			}else if(rare <= 90){
				setRarity(Rarity.RARE);
			}else{
				setRarity(Rarity.EXPERIMENTAL);
			}
		}
		setFunction("No function");
		rare = rand.nextInt(3)+2;
		setWeight(rare);
		if(getRarity() == Rarity.EXPERIMENTAL){
			setMultiplier(1.4f);
			Type[] attacks = Type.values();
			rare = rand.nextInt(attacks.length);
			setAttackType(attacks[rare]);
			setFunction("Attack with " + getAttackType().toString());
		}
	}
	
	public Head(String name, String function, Rarity rare, int weight, Type attackType){
		setName(name);
		setFunction(function);
		setRarity(rare);
		setWeight(weight);
		setAttackType(attackType);
		float multiplier = 1.0f;
		for(int i = 0; i < Rarity.values().length; i++){
			if(getRarity() == Rarity.values()[i]){
				multiplier += (float)(i*.1);
			}
		}
		setMultiplier(multiplier);
	}
	
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public Type getAttackType() {
		return attackType;
	}
	public void setAttackType(Type attackType) {
		this.attackType = attackType;
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
		if(getRarity() == Rarity.EXPERIMENTAL){
			sb.append(getAttackType().toString());
		}
		sb.append("Weight : ");
		sb.append(getWeight());
		sb.append(" tons");
		sb.append(getFunction());
		return sb.toString();
	}
}
