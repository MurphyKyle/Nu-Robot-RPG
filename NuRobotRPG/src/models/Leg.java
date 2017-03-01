package models;

import java.util.Random;

import enums.*;

public class Leg extends Part {

	private boolean isTreads;
	private String[] prefixes = { "Cheetah ", "Booster ", "Kick-Master MK2 ", "Master ", "Bionic " };
	private String[] suffixes = { "Treads", "Hopper", "Striker", "Racer", "Driver" };

	public Leg(int difficulty) {
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
		rare = rand.nextInt(10) + 10;
		setWeight(rare);
		float multiplier = 1.0f;
		for (int i = 0; i < Rarity.values().length; i++) {
			if (getRarity() == Rarity.values()[i]) {
				multiplier += (float) (i * .1);
			}
		}
		setMultiplier(multiplier);
		StringBuilder sb = new StringBuilder();
		if (getRarity() == Rarity.EXPERIMENTAL) {
			sb.append("Kick-Master MK4 ");
		} else {
			int rem = rand.nextInt(prefixes.length);
			sb.append(prefixes[rem]);
		}
		rare = rand.nextInt(100) + 1;
		if (rare <= 50) {
			setIsTreads(true);
			sb.append(suffixes[0]);
		} else {
			setIsTreads(false);
			int rem = rand.nextInt(suffixes.length - 1) + 1;
			sb.append(suffixes[rem]);
		}
		setName(sb.toString());
		setPartType("Legs");
	}

	public Leg(String name, int weight, Rarity rare, boolean isTreads) {
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
		setPartType("Legs");
	}

	public Leg(String input) {
		this(input.split(" :: ")[0], Integer.parseInt(input.split(" :: ")[1]),
				Rarity.COMMON.toString().equals(input.split(" :: ")[2]) ? Rarity.COMMON
						: Rarity.UNCOMMON.toString().equals(input.split(" :: ")[2]) ? Rarity.UNCOMMON
								: Rarity.RARE.toString().equals(input.split(" :: ")[2]) ? Rarity.RARE
										: Rarity.EXPERIMENTAL,
		// I'm so sorry but I had to use a triple nested ternary. There's no
		// other way
		input.split(" :: ")[3].equalsIgnoreCase("Treads"));
	}

	public boolean isTreads() {
		return isTreads;
	}

	public void setIsTreads(boolean isTreads) {
		this.isTreads = isTreads;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (!isTreads()) {
			sb.append("Legs\n");
		} else {
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
