package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import enums.Rarity;
import enums.Type;

public class Robot {
	private String name;
	private Torso torso;
	private ArrayList<Arm> arms = new ArrayList<>();
	private Head head;
	private Leg legs;
	private int maxHp = 100;
	private int currentHp = 100;
	private int minDmg = 8;
	private int maxDmg = 12;
	private Part drop;
	private boolean isAlive = true;
	private String[] names = {
		"Optimus Prime", "Galvatron", "Skynet", "RoboCop", "Iron Hide", "Wall-E", "Ultron", "Slash", "Motoko Kusanagi", 
		"E.D.I.", "Alpha", "Strike Freedom Gundam", "Jet Fire", "Star Scream"
	};

	public Robot(int difficulty) {
		equipHead(new Head(difficulty));
		equipTorso(new Torso(difficulty));
		arms.add(new Arm(difficulty));
		arms.add(new Arm(difficulty));
		equipLegs(new Leg(difficulty));
		setDrop(getDropPart());
		Random rand = new Random();
		int ren = rand.nextInt(names.length);
		setName(names[ren]);
	}

	public Robot(String name, Torso torso, ArrayList<Arm> arms, Head head, Leg legs) {
		setName(name);
		equipHead(head);
		equipTorso(torso);
		equipArms(arms);
		equipLegs(legs);
		setDrop(getDropPart());
	}

	public Robot(String input) {
		this(input.split("\n")[0], new Torso(input.split("\n")[input.split("\n").length-2]), new ArrayList<Arm>(), new Head(input.split("\n")[1]), new Leg(input.split("\n")[input.split("\n").length-1]));
		ArrayList<Arm> arms = new ArrayList<Arm>();
		for(int i = 2; i < input.split("\n").length-2; i++) {
			arms.add(new Arm(input.split("\n")[i]));
		}
		this.equipArms(arms);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

	public int getCurrentHp() {
		return currentHp;
	}

	public void setCurrentHp(int currentHp) {
		if(currentHp > getMaxHp()){
			currentHp = getMaxHp();
		}else if(currentHp < 0){
			currentHp = 0;
		}
		this.currentHp = currentHp;
	}

	public Part getDrop() {
		return drop;
	}

	public void setDrop(Part drop) {
		this.drop = drop;
	}

	public Torso getTorso() {
		return torso;
	}

	public void equipTorso(Torso torso) {
		this.torso = torso;
		setMaxHp(((int) (torso.getWeight() * 10 * torso.getMultiplier())));
		setCurrentHp(maxHp);
	}

	public Arm getArm(int indexArm) {
		return arms.get(indexArm);
	}
	
	public ArrayList<Arm> getArms(){
		return this.arms;
	}

	public void equipArms(ArrayList<Arm> arms) {
		this.arms = arms;
	}

	public Head getHead() {
		return head;
	}

	public void equipHead(Head head) {
		this.head = head;
	}

	public Leg getLegs() {
		return legs;
	}

	public void equipLegs(Leg legs) {
		this.legs = legs;
	}

	public int getMinDmg() {
		return this.minDmg;
	}

	public void setMinDmg() {
		int minDmg = 0;

		for (Arm a : this.arms) {
			minDmg += a.getWeight();
		}

		minDmg /= arms.size();

		this.minDmg = minDmg;
	}

	public int getMaxDmg() {
		return this.maxDmg;
	}

	public void setMaxDmg() {
		int maxDmg = 0;

		for (Arm a : this.arms) {
			maxDmg += a.getWeight();
		}
		// double check this

		this.maxDmg = maxDmg;
	}

	public boolean isAlive() {
		// if the currentHp is < or == 0, return false because I AM DED
		this.isAlive = true;
		this.isAlive = getCurrentHp() <= 0 ? false : true;
		return isAlive;
	}

	private Part getDropPart() {
		// puts all current robot's parts into a list, shuffles them and
		// randomly picks one to return

		ArrayList<Part> parts = new ArrayList<>();

		parts.add(this.head);
		parts.add(this.torso);
		parts.add(this.legs);
		parts.addAll(this.arms);

		Collections.shuffle(parts);

		return parts.get(new Random().nextInt(parts.size()));
	}
	
	public int attack() {		
		// finish this, each robot now has it's own min and max
		int attack = new Random().nextInt(getMaxDmg()-getMinDmg()) + getMinDmg();	// 8-12 attack range
		return attack;
	}

	public boolean takeDamage(int damage) {
		this.setCurrentHp(getCurrentHp() - damage);

		return isAlive();
	}

	public int getSpeed() {

		// double check this
		int maxSpeed = 80;
		int minSpeed = 40;

		int speed = 0;

		int totalWeight = this.head.getWeight() + this.torso.getWeight() + this.legs.getWeight();

		for (Arm a : this.arms) {
			totalWeight += a.getWeight();
		}

		speed = (60 / totalWeight) * 40;

		// if the legs are treads, speed is decreased by 25% (speed*0.75) --

		if (this.legs.isTreads()) {
			speed -= speed/4;
		}

		speed = (int) ((float)speed * this.legs.getMultiplier());

		if (speed > maxSpeed) {
			speed = maxSpeed;
		} else if (speed < minSpeed) {
			speed = minSpeed;
		}

		return speed;
	}

	public String getPartSpecs(Part part) {
		StringBuilder sb = new StringBuilder();
		if (part.getPartType().equals("Arm")) {
			Arm ar = (Arm) part;
			sb.append("Arm\n");
			sb.append(part.getGeneralSpec());
			sb.append(ar.getAttackType().toString());
			sb.append("\n");
			sb.append(ar.getFunction());
			sb.append("\n");
		} else if (part.getPartType().equals("Torso")) {
			Torso tor = (Torso) part;
			sb.append("Torso\n");
			sb.append(part.getGeneralSpec());
			sb.append(tor.getDefenceType().toString());
			sb.append("\n");
		} else if (part.getPartType().equals("Head")) {
			Head he = (Head) part;
			sb.append("Head\n");
			sb.append(part.getGeneralSpec());
			if (he.getRarity() == Rarity.EXPERIMENTAL) {
				sb.append(he.getAttackType().toString());
			} else {
				sb.append("None");
			}
			sb.append("\n");
			sb.append(he.getFunction());
			sb.append("\n");
		} else {
			Leg le = (Leg) part;
			if (!le.isTreads()) {
				sb.append("Legs\n");
			} else {
				sb.append("Treads\n");
			}
			sb.append(part.getGeneralSpec());
		}
		return sb.toString();
	}

	public ArrayList<Type> getActionType() {
		ArrayList<Type> actions = new ArrayList<>();
		for (Arm a : arms) {
			actions.add(a.getAttackType());
		}
		if (this.head.getRarity() == Rarity.EXPERIMENTAL) {
			actions.add(this.head.getAttackType());
		}
		return actions;
	}
	
	public ArrayList<String> getActionMenu(){
		ArrayList<String> menu = new ArrayList<>();
		for (Arm a : arms) {
			menu.add(a.getFunction());
		}
		if (this.head.getRarity() == Rarity.EXPERIMENTAL) {
			menu.add(this.head.getFunction());
		}
		return menu;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("Name: ");
		sb.append(getName());
		sb.append("\nHP: ");
		sb.append(currentHp);
		sb.append("/");
		sb.append(maxHp);
		sb.append("\n");

		return sb.toString();
	}

}
