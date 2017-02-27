package models;

import application.Engine;

public class Room {
	private boolean isOccupied;
	private boolean isDepot;

	public void enterRoom(Robot player, int difficulty) {
		if (this.isOccupied()) {
			// Construct Enemy
			Robot enemy = new Robot(difficulty);
			// fight
//			Engine.Fight(player, enemy);
		} else if (this.isDepot()) {
			// Depot Menu
		}
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

	public boolean isDepot() {
		return isDepot;
	}

	public void setDepot(boolean isDepot) {
		this.isDepot = isDepot;
	}
}
