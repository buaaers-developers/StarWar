package model;

public class Planet {
	private int power = 0;
	private int powerLimit;
	private int growSpeed;
	private int conveySpeed;
	private Player owner;
	private boolean growable = true;
	private Bridge bridge = null;

	public Planet(int powerLimit, int growSpeed, int conveySpeed, Player owner) {
		this.powerLimit = powerLimit;
		this.growSpeed = growSpeed;
		this.conveySpeed = conveySpeed;
		this.owner = owner;
	}

	public int getPower() {
		return power;
	}

	public int getGrowSpeed() {
		return growSpeed;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public boolean isGrowable() {
		return growable;
	}

	public void setGrowable(boolean growable) {
		this.growable = growable;
	}

	public Bridge getBridge() {
		return bridge;
	}

	public void setBridge(Bridge bridge) {
		this.bridge = bridge;
	}

	public boolean isFullPower() {
		return power >= powerLimit;
	}

	private void addPower(int aid) {
		power += aid;
		if (isFullPower()) {
			power = powerLimit;
		}
	}

	private void lostPower(int dead) {
		power -= dead;
	}

	private void beConquered(Player invader) {
		owner = invader;
		power = Math.abs(power);
	}

	public void grow() {
		addPower(growSpeed);
	}

	public void convey(Planet obj) {
		int convey = conveySpeed;
		if (convey >= power) {
			convey = power;
			bridge.die();
		}
		this.lostPower(convey);
		if (owner == obj.getOwner()) {
			obj.addPower(convey);
			if (obj.isFullPower()) {
				bridge.die();
			}
		} else {
			obj.lostPower(convey);
			if (obj.power < 0) {
				obj.beConquered(owner);
				bridge.die();
			}
		}
		obj.growable = false;
		growable = false;
	}

	@Override
	public String toString() {
		return "<html><body><center>Power: " + power + "</center>" + "<center>Growing Speed: " + growSpeed + "</center>"
				+ "<center>Convey Speed: " + conveySpeed + "</center><body></html>";
	}
}
