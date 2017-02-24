package models;

import java.util.Random;

import enums.*;

public class Leg extends Part{
	
	private boolean isTreads;
	
	public Leg(){
		
	}
	
	public Leg(int difficulty){
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
		rare = rand.nextInt(10)+10;
		setWeight(rare);
		float multiplier = 1.0f;
		for(int i = 0; i < Rarity.values().length; i++){
			if(getRarity() == Rarity.values()[i]){
				multiplier += (float)(i*.1);
			}
		}
		setMultiplier(multiplier);
		rare = rand.nextInt(100)+1;
		if(rare <= 50){
			setIsTreads(true);
		}else{
			setIsTreads(false);
		}
		setPartType("Legs");
	}
	
	public Leg(String name, int weight, Rarity rare){
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
		setPartType("Legs");
	}
	
	public boolean isTreads(){
		return isTreads;
	}
	
	public void setIsTreads(boolean isTreads){
		this.isTreads = isTreads;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		if(!isTreads()){
			sb.append("Legs\n");
		}else{
			sb.append("Treads\n");
		}
		sb.append(getName());
		sb.append("\n");
		sb.append("Rarity : ");
		sb.append(getRarity().toString().charAt(0));
		sb.append(getRarity().toString().substring(1, getRarity().toString().length()).toLowerCase());
		sb.append("\n");
		sb.append("Weight : ");
		sb.append(getWeight());
		sb.append(" tons\n");
		return sb.toString();
	}
}
