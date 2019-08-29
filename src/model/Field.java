package model;

import java.util.ArrayList;
import java.util.Iterator;

public class Field {

	private Planet[][] field;
	private ArrayList<Bridge> bridges;

	public Field() {
		field = new Planet[FieldConstants.height][FieldConstants.width];
		bridges = new ArrayList<Bridge>();
	}

	public ArrayList<Bridge> getBridges() {
		return bridges;
	}

	public Planet getPlanet(Position pos) {
		return field[pos.getRow()][pos.getCol()];
	}

	public void setPlanet(Position pos, Planet planet) {
		field[pos.getRow()][pos.getCol()] = planet;
	}

	public void addBridge(Bridge bridge) {
		bridges.add(bridge);
		this.getPlanet(bridge.getFrom()).setBridge(bridge);
	}

	public boolean removeBridge(Bridge bridge) {
		this.getPlanet(bridge.getFrom()).setBridge(null);
		Iterator<Bridge> bridgeItr = bridges.iterator();
		while (bridgeItr.hasNext()) {
			Bridge bdg = bridgeItr.next();
			if (bdg == bridge) {
				bridgeItr.remove();
				return true;
			}
		}
		return false;
	}

	public int getPlayerTotalPower(Player player) {
		int total = 0;
		for (int row = 0; row < FieldConstants.height; row++) {
			for (int col = 0; col < FieldConstants.width; col++) {
				Planet planet = field[row][col];
				if (planet != null && planet.getOwner() == player) {
					total += planet.getPower();
				}
			}
		}
		return total;
	}

	public int getPlayerTotalGrowSpeed(Player player) {
		int total = 0;
		for (int row = 0; row < FieldConstants.height; row++) {
			for (int col = 0; col < FieldConstants.width; col++) {
				Planet planet = field[row][col];
				if (planet != null && planet.getOwner() == player) {
					total += planet.getGrowSpeed();
				}
			}
		}
		return total;
	}

	public void update() {
		for (int row = 0; row < FieldConstants.height; row++) {
			for (int col = 0; col < FieldConstants.width; col++) {
				Planet planet = field[row][col];
				if (planet != null) {
					planet.setGrowable(true);
				}
			}
		}

		for (int i = 0; i < bridges.size(); i++) {
			Bridge bridge = bridges.get(i);
			this.getPlanet(bridge.getFrom()).convey(this.getPlanet(bridge.getTo()));
			// bridge.update();
			if (bridge.isDead()) {
				removeBridge(bridge);
				i--;
			}
		}

		for (int row = 0; row < FieldConstants.height; row++) {
			for (int col = 0; col < FieldConstants.width; col++) {
				Planet planet = field[row][col];
				if (planet != null && planet.isGrowable()) {
					planet.grow();
				}
			}
		}
	}
}
