package models;

public class Room {
	private boolean isOccupied;
	private boolean isDepot;
	private boolean seen = false;

	public boolean isSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
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
