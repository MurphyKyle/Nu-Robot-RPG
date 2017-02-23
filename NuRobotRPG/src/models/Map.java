package models;

import java.util.Random;

public class Map {
	private Room[][] rooms;

	public Map(int difficulty) {
		// choose random size for map
		Random randy = new Random();
		int size = randy.nextInt(11) + 10;
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
			enemies = (int) Math.pow(size, 2) / 3;
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
	}

	public Room[][] getRooms() {
		return rooms;
	}

	public void setRooms(Room[][] rooms) {
		this.rooms = rooms;
	}

	@Override
	public String toString() {
		StringBuilder bob = new StringBuilder();
		for (Room[] rArray : rooms) {
			for (Room r : rArray) {
				bob.append("[");
				bob.append(r.isOccupied() ? "!" : r.isDepot() ? "D" : " ");
				bob.append("]");
			}
			bob.append("\n");
		}
		return bob.toString();
	}
}
