package application;

import java.util.Random;

import enums.Type;
import models.Robot;

public class CombatEngine extends Thread {

	private Robot[] currentRobots = new Robot[2];
	private int userAction = -1;
	private boolean running = false;
	public boolean actionSelected = false;
	public Thread c;
	private String output;
	private Thread gui;
	private boolean userTurn = false;

	public CombatEngine(Robot player, Robot enemy) {
		currentRobots[0] = player;
		currentRobots[1] = enemy;
		userAction = -1;
		running = false;
		c = new Thread("combat");
	}

	public void setGui(Thread gui) {
		this.gui = gui;
	}

	public void run() {
		while (!battleEnd()) {
			update();
			if(userTurn){
				synchronized(gui){
					while(userTurn && !actionSelected){
						if(ViewControl.getAction() > userAction){
							setUserAction(ViewControl.getAction());
							ViewControl.setAction(-1);
						}
						try {
							sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					running = true;
				}
			}
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			fight();
		}
		update();
	}

	public void fight() {
		synchronized (currentRobots) {
			while (!running) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (currentRobots[0].getCombatSpeed() >= currentRobots[1].getCombatSpeed()) {
				if (!actionSelected) {
					userTurn = true;
					running = false;
				} else {
					userTurn = false;
					userTurn();
					actionSelected = false;
					running = false;
				}
			} else {
				compTurn();
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				running = false;
			}
			currentRobots.notifyAll();
		}
	}

	public Robot[] getCurrentRobots() {
		return currentRobots;
	}

	public void update() {
		synchronized (gui) {
			while (running) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Engine.currentRobot = currentRobots[0];
			ViewControl.updateStats(currentRobots[0], currentRobots[1]);
			ViewControl.setCombatLabel(output);
			running = true;
			if (userTurn) {
				ViewControl.playerAccess();
				running = true;
			}
			if(battleEnd()){
				ViewControl.aftermath(currentRobots[1]);
				try {
					sleep(700);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			gui.notifyAll();
		}
	}

	public boolean battleEnd() {
		boolean death = false;
		if (!currentRobots[0].isAlive() || !currentRobots[1].isAlive()) {
			running = false;
			death = true;
		}
		return death;
	}

	public void setActionSelected(boolean actionSelected) {
		this.actionSelected = actionSelected;
	}

	public void pauseThread() throws InterruptedException {
		running = false;
	}

	public void resumeThread() {
		running = true;
		notifyAll();
	}

	public void userTurn() {
		Type attackType = currentRobots[0].getActionType().get(userAction);
		int damage = currentRobots[0].attack();
		StringBuilder status = new StringBuilder();
		status.append(currentRobots[0].getName());
		status.append(" attacked and dealt ");
		if (attackType == Type.EXPLOSIVE) {
			status.append(damage);
			status.append(" damage to ");
		} else {
			if (currentRobots[1].getTorso().getDefenceType() == Type.BALLISTIC) {
				if (attackType == Type.BALLISTIC || attackType == Type.ELECTRICITY) {
					damage /= 2;
					status.append(damage);
					status.append(" not very effective damage to ");
				} else {
					damage *= 2;
					status.append(damage);
					status.append(" super effective damage to ");
				}
			} else {
				if (attackType == Type.BEAM || attackType == Type.FIRE) {
					damage /= 2;
					status.append(damage);
					status.append(" not very effective damage to ");
				} else {
					damage *= 2;
					status.append(damage);
					status.append(" super effective damage to ");
				}
			}
		}
		currentRobots[1].takeDamage(damage);
		Engine.score += damage;
		status.append(currentRobots[1].getName());
		currentRobots[1].setCombatSpeed(currentRobots[1].getCombatSpeed() + currentRobots[1].getSpeed());
		output = status.toString();
	}

	public void compTurn() {
		Random rand = new Random();
		int action = rand.nextInt(currentRobots[1].getActionType().size());
		Type attackType = currentRobots[1].getActionType().get(action);
		int damage = currentRobots[1].attack();
		StringBuilder status = new StringBuilder();
		status.append(currentRobots[1].getName());
		status.append(" attacked and dealt ");
		if (attackType == Type.EXPLOSIVE) {
			status.append(damage);
			status.append(" damage to ");
		} else {
			if (currentRobots[0].getTorso().getDefenceType() == Type.BALLISTIC) {
				if (attackType == Type.BALLISTIC || attackType == Type.ELECTRICITY) {
					damage /= 2;
					status.append(damage);
					status.append(" not very effective damage to ");
				} else {
					damage *= 2;
					status.append(damage);
					status.append(" super effective damage to ");
				}
			} else {
				if (attackType == Type.BEAM || attackType == Type.FIRE) {
					damage /= 2;
					status.append(damage);
					status.append(" not very effective damage to ");
				} else {
					damage *= 2;
					status.append(damage);
					status.append(" super effective damage to ");
				}
			}
		}
		currentRobots[0].takeDamage(damage);
		Engine.score += damage;
		status.append(currentRobots[0].getName());
		currentRobots[0].setCombatSpeed(currentRobots[0].getCombatSpeed() + currentRobots[0].getSpeed());
		output = status.toString();
		userAction = -1;
	}

	public void setUserAction(int i) {
		userAction = i;
		setActionSelected(true);
	}

}
