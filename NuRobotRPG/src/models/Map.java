package models;

import java.util.Random;

public class Map {
	private Room[][] rooms;
	private int xCoord;
	private int yCoord;
	private int mapSize;


	public Map(int difficulty) {
		// choose random size for map
		Random randy = new Random();
		int size = randy.nextInt(11) + 10;
		mapSize = size;
		// construct rooms
		rooms = new Room[size][size];
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				rooms[x][y] = new Room();
			}
		}
		// randomize the isOccupieds of rooms
		// randomize depots of rooms
		int depots = (int) Math.pow(size, 2) / 15 + 1;
		int enemies;

		switch (difficulty) {
		case 1:
			enemies = (int) Math.pow(size, 2) / 4;
			break;
		case 2:
			enemies = (int) Math.pow(size, 2) * 2 / 5;
			break;
		case 3:
			enemies = (int) Math.pow(size, 2) / 2;
			break;
		default:
			enemies = 0;
		}
		for (int i = 0; i < depots; i++) {
			boolean empty = true;
			Room chosenRoom;
			do {
				empty = true;
				chosenRoom = rooms[randy.nextInt(size)][randy.nextInt(size)];
				if (chosenRoom.isDepot() || chosenRoom.isOccupied()) {
					empty = false;
				}
			} while (!empty);
			chosenRoom.setDepot(true);
		}
		for (int i = 0; i < enemies; i++) {
			boolean empty = true;
			Room chosenRoom;
			do {
				empty = true;
				chosenRoom = rooms[randy.nextInt(size)][randy.nextInt(size)];
				if (chosenRoom.isDepot() || chosenRoom.isOccupied()) {
					empty = false;
				}
			} while (!empty);
			chosenRoom.setOccupied(true);
		}
		// Sets player coordinates to the middle
		xCoord = size / 2;
		yCoord = size / 2;
	}

	public Map(int difficulty, int size) {
		Random randy = new Random();
		// use int size to make map
		// construct rooms
		rooms = new Room[size][size];
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				rooms[x][y] = new Room();
			}
		}
		// randomize the isOccupieds of rooms
		// randomize depots of rooms
		int depots = (int) Math.pow(size, 2) / 15;
		int enemies = (int) Math.pow(size, 2) / 3;
		for (int i = 0; i < depots; i++) {
			boolean empty = true;
			Room chosenRoom;
			do {
				empty = true;
				chosenRoom = rooms[randy.nextInt(size)][randy.nextInt(size)];
				if (chosenRoom.isDepot() || chosenRoom.isOccupied()) {
					empty = false;
				}
			} while (!empty);
			chosenRoom.setDepot(true);
		}
		for (int i = 0; i < enemies; i++) {
			boolean empty = true;
			Room chosenRoom;
			do {
				empty = true;
				chosenRoom = rooms[randy.nextInt(size)][randy.nextInt(size)];
				if (chosenRoom.isDepot() || chosenRoom.isOccupied()) {
					empty = false;
				}
			} while (!empty);
			chosenRoom.setOccupied(true);
		}
		// Sets player coordinates to the middle
		xCoord = size / 2;
		yCoord = size / 2;
	}

	public Room[][] getRooms() {
		return rooms;
	}

	public void setRooms(Room[][] rooms) {
		this.rooms = rooms;
	}

	public boolean moveUp() {
		if (yCoord > 0) {
			yCoord--;
			return true;
		} else {
			return false;
		}
	}

	public boolean moveDown() {
		if (yCoord < rooms.length - 1) {
			yCoord++;
			return true;
		} else {
			return false;
		}
	}

	public boolean moveLeft() {
		if (xCoord > 0) {
			xCoord--;
			return true;
		} else {
			return false;
		}
	}

	public boolean moveRight() {
		if (xCoord < rooms.length - 1) {
			xCoord++;
			return true;
		} else {
			return false;
		}
	}

	public int getXCoord() {
		return this.xCoord;
	}

	public int getYCoord() {
		return this.yCoord;
	}
	
	public int getMapSize(){
		return this.mapSize;
	}

	@Override
	public String toString() {
		StringBuilder bob = new StringBuilder();
		Room r;
		for (int y = 0; y < rooms.length; y++) {
			for (int x = 0; x < rooms.length; x++) {
				r = rooms[x][y];
				bob.append("[ ");
				if (x == xCoord && y == yCoord) {
					bob.append("X");
				} else {
					if (xCoord + 1 == x && yCoord == y) {
						rooms[x][y].setSeen(true);
						bob.append(r.isOccupied() ? "!" : r.isDepot() ? "D" : " ");
						// ^ If its occupied "!" else if its a depot "D" else
						// its just a space
					} else if (xCoord - 1 == x && yCoord == y) {
						rooms[x][y].setSeen(true);
						bob.append(r.isOccupied() ? "!" : r.isDepot() ? "D" : " ");
						// ^ If its occupied "!" else if its a depot "D" else
						// its just a space
					} else if (xCoord == x && yCoord + 1== y) {
						rooms[x][y].setSeen(true);
						bob.append(r.isOccupied() ? "!" : r.isDepot() ? "D" : " ");
						// ^ If its occupied "!" else if its a depot "D" else
						// its just a space
					} else if (xCoord == x && yCoord - 1== y) {
						rooms[x][y].setSeen(true);
						bob.append(r.isOccupied() ? "!" : r.isDepot() ? "D" : " ");
						// ^ If its occupied "!" else if its a depot "D" else
						// its just a space
					} else if (this.rooms[x][y].isSeen()){
						bob.append(r.isOccupied() ? "!" : r.isDepot() ? "D" : " ");
						// ^ If its occupied "!" else if its a depot "D" else
						// its just a space
					}else {
						bob.append("?");
					}
				}
				bob.append(" ] ");
			}
			bob.append("\n");
		}
		return bob.toString();
	}
}
