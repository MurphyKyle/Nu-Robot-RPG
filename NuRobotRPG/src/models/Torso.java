package models;

import java.util.Random;

import enums.*;

public class Torso extends Part{

	private Type defenceType;
	
	public Torso(){
		
	}
	
	public Torso(int difficulty){
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
		float multiplier = 1.0f;
		for(int i = 0; i < Rarity.values().length; i++){
			if(getRarity() == Rarity.values()[i]){
				multiplier += (float)(i*.1);
			}
		}
		setMultiplier(multiplier);
		rare = rand.nextInt(100)+1;
		if(rare <= 50){
			setDefenceType(Type.BALLISTIC);
		}else{
			setDefenceType(Type.BEAM);
		}
		rare = rand.nextInt(5)+10;
		setWeight(rare);
	}
	
	public Torso(Type defenceType, String name, Rarity rare, int weight){
		setDefenceType(defenceType);
		setName(name);
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
	
	public Type getDefenceType() {
		return defenceType;
	}
	public void setDefenceType(Type defenceType) {
		this.defenceType = defenceType;
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
		sb.append("-Resistant");
		sb.append("\n");
		sb.append("Weight : ");
		sb.append(getWeight());
		sb.append(" tons");
		return sb.toString();
	}
}
