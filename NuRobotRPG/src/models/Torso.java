package models;

import java.util.Random;

import enums.*;

public class Torso extends Part {

	private Type defenceType;

	public Torso(int difficulty) {
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
		String[] prefixes = { "Platinum ", "Hyperpneumatic ", "Fission-Powered ", "Sentinel MK 1 ", "Cage Frame ",
				"Segmented ", "Titanium Alloy " };
		String[] suffixes = { "Chassis", "Torso Unit", "Chest Piece", "Thorax", "Sprocket Frame", "Fuselage Core",
				"Carapace" };
		StringBuilder bob = new StringBuilder();
		if (this.rarity == Rarity.EXPERIMENTAL) {
			bob.append("Sentinel MK 2 ");
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
		rare = rand.nextInt(100) + 1;
		if (rare <= 50) {
			setDefenceType(Type.BALLISTIC);
		} else {
			setDefenceType(Type.BEAM);
		}
		rare = rand.nextInt(5) + 10;
		setWeight(rare);
		setPartType("Torso");
	}

	public Torso(String name, int weight, Rarity rare, Type defenceType) {
		setDefenceType(defenceType);
		setName(name);
		setRarity(rare);
		setWeight(weight);
		float multiplier = 1.0f;
		for (int i = 0; i < Rarity.values().length; i++) {
			if (getRarity() == Rarity.values()[i]) {
				multiplier += (float) (i * .1);
			}
		}
		setMultiplier(multiplier);
		setPartType("Torso");
	}

	public Torso(String input) {
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
												: Type.FIRE);
		// Sorry again
	}

	public Type getDefenceType() {
		return defenceType;
	}

	public void setDefenceType(Type defenceType) {
		this.defenceType = defenceType;
	}

	@Override
	public String toString() {
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
		sb.append(" tons\n");
		return sb.toString();
	}
}
