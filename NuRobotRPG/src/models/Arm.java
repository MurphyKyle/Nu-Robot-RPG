package models;

import java.util.Random;

import enums.Rarity;
import enums.Type;

public class Arm extends Part {

	private Type attackType;
	private String function;

	public Arm(int difficulty) {
		Random rand = new Random();
		int rare = rand.nextInt(100) + 1;
		if (difficulty == 1) {
			if (rare <= 75) {
				setRarity(Rarity.COMMON);
			} else {
				setRarity(Rarity.UNCOMMON);
			}
		} else if (difficulty == 2) {
			if (rare <= 25) {
				setRarity(Rarity.COMMON);
			} else if (rare <= 75) {
				setRarity(Rarity.UNCOMMON);
			} else if (rare <= 99) {
				setRarity(Rarity.RARE);
			} else {
				setRarity(Rarity.EXPERIMENTAL);
			}
		} else {
			if (rare <= 50) {
				setRarity(Rarity.UNCOMMON);
			} else if (rare <= 90) {
				setRarity(Rarity.RARE);
			} else {
				setRarity(Rarity.EXPERIMENTAL);
			}
		}
		String[] prefixes = { "Hydraulic ", "Plated ", "Composite ", "Pheonix MK 1 ", "Durasteel ", "Guerilla ",
				"Variable-Cored " };
		String[] suffixes = { "Tri-finger", "Brawlfist", "Palm Cannon", "Mechacarpus", "Digihand", "Grip Chassis",
				"Claw" };
		StringBuilder bob = new StringBuilder();
		if (this.rarity == Rarity.EXPERIMENTAL) {
			bob.append("Pheonix MK 2 ");
		} else {
			bob.append(prefixes[rand.nextInt(prefixes.length)]);
		}
		bob.append(suffixes[rand.nextInt(suffixes.length)]);
		this.name = bob.toString();
		float multiplier = 1.0f;
		for (int i = 0; i < Rarity.values().length; i++) {
			if (getRarity() == Rarity.values()[i]) {
				multiplier += (float) (i * .1);
			}
		}
		setMultiplier(multiplier);
		Type[] attacks = Type.values();
		rare = rand.nextInt(attacks.length);
		setAttackType(attacks[rare]);
		setFunction("Attack with " + getAttackType().toString());
		rare = rand.nextInt(4) + 4;
		setWeight(rare);
		setPartType("Arm");
	}

	public Arm(String name, int weight, Rarity rare, Type attackType, String function) {
		setAttackType(attackType);
		setName(name);
		setFunction(function);
		setRarity(rare);
		setWeight(weight);
		float multiplier = 1.0f;
		for (int i = 0; i < Rarity.values().length; i++) {
			if (getRarity() == Rarity.values()[i]) {
				multiplier += (float) (i * .1);
			}
		}
		setMultiplier(multiplier);
		setPartType("Arm");
	}

	public Arm(String input) {
		this(input.split(" :: ")[1], Integer.parseInt(input.split(" :: ")[2]),
				Rarity.COMMON.toString().equals(input.split(" :: ")[3]) ? Rarity.COMMON
						: Rarity.UNCOMMON.toString().equals(input.split(" :: ")[3]) ? Rarity.UNCOMMON
								: Rarity.RARE.toString().equals(input.split(" :: ")[3]) ? Rarity.RARE
										: Rarity.EXPERIMENTAL,
				// I'm so sorry but I had to use a triple nested ternary.
				// There's no other way
				Type.BALLISTIC.toString().equals(input.split(" :: ")[4]) ? Type.BALLISTIC
						: Type.BEAM.toString().equals(input.split(" :: ")[4]) ? Type.BEAM
								: Type.ELECTRICITY.toString().equals(input.split(" :: ")[4]) ? Type.ELECTRICITY
										: Type.EXPLOSIVE.toString().equals(input.split(" :: ")[4]) ? Type.EXPLOSIVE
												: Type.FIRE,
				// Sorry again
				input.split(" :: ")[5]);
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Arm\n");
		sb.append(getName());
		sb.append("\n");
		sb.append("Rarity : ");
		sb.append(getRarity().toString().charAt(0));
		sb.append(getRarity().toString().substring(1, getRarity().toString().length()).toLowerCase());
		sb.append("\n");
		sb.append("Attack Type : ");
		sb.append(getAttackType().toString());
		sb.append("\n");
		sb.append("Weight : ");
		sb.append(getWeight());
		sb.append(" tons\n");
		return sb.toString();
	}
}
