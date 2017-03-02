package models;

import java.util.Random;

import enums.*;

public class Head extends Part {

	private String function;
	private Type attackType;
	private String[] prefixes = { "V-Gazer ", "Bite-Master ", "Dealth-Gaze MK2 ", "Moustache ", "Byte-Reader " };
	private String[] suffixes = { "Guardmouth", "Helm", "Sharkface", "Dulleyes", "Hawkeye" };

	public Head(int difficulty) {
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
		setFunction("No function");
		rare = rand.nextInt(3) + 2;
		setWeight(rare);
		StringBuilder sb = new StringBuilder();
		if (getRarity() == Rarity.EXPERIMENTAL) {
			setMultiplier(1.4f);
			Type[] attacks = Type.values();
			rare = rand.nextInt(attacks.length);
			setAttackType(attacks[rare]);
			setFunction("Attack with " + getAttackType().toString());
			sb.append("Dealth-Gaze MK5 ");
		} else {
			setAttackType(null);
			int rem = rand.nextInt(prefixes.length);
			sb.append(prefixes[rem]);
		}
		int rem = rand.nextInt(suffixes.length);
		sb.append(suffixes[rem]);
		setName(sb.toString());
		setPartType("Head");
	}

	public Head(String name, int weight, Rarity rare, Type attackType, String function) {
		setName(name);
		setFunction(function);
		setRarity(rare);
		setWeight(weight);
		setMultiplier(1.4f);
		setAttackType(attackType);
		setFunction(function);
		setMultiplier(multiplier);
		setPartType("Head");
	}
	
	public Head(String input) {
		this(input.split(" :: ")[1], Integer.parseInt(input.split(" :: ")[2]),
				Rarity.COMMON.toString().equals(input.split(" :: ")[3]) ? Rarity.COMMON
						: Rarity.UNCOMMON.toString().equals(input.split(" :: ")[3]) ? Rarity.UNCOMMON
								: Rarity.RARE.toString().equals(input.split(" :: ")[3]) ? Rarity.RARE
										: Rarity.EXPERIMENTAL,
				// I'm so sorry but I had to use a triple nested ternary.
				// There's no
				// other way
				Type.BALLISTIC.toString().equals(input.split(" :: ")[4]) ? Type.BALLISTIC
						: Type.BEAM.toString().equals(input.split(" :: ")[4]) ? Type.BEAM
								: Type.ELECTRICITY.toString().equals(input.split(" :: ")[4]) ? Type.ELECTRICITY
										: Type.EXPLOSIVE.toString().equals(input.split(" :: ")[4]) ? Type.EXPLOSIVE
												: Type.FIRE,
				// Sorry again
				input.split(" :: ")[4]);
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Head\n");
		sb.append(getName());
		sb.append("\n");
		sb.append("Rarity : ");
		sb.append(getRarity().toString().charAt(0));
		sb.append(getRarity().toString().substring(1, getRarity().toString().length()).toLowerCase());
		sb.append("\n");
		if (getRarity() == Rarity.EXPERIMENTAL) {
			sb.append(getAttackType().toString());
			sb.append("\n");
			sb.append(getFunction());
			sb.append("\n");
		}
		sb.append("Weight : ");
		sb.append(getWeight());
		sb.append(" tons\n");
		return sb.toString();
	}
}
