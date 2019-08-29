package model;

public class Bridge {
	private Position from;
	private Position to;
	private boolean dead = false;
	
	public Bridge(Position from, Position to) {
		this.from = from;
		this.to = to;
	}

	public Position getFrom() { return from; }
	public Position getTo() { return to; }
	public void die () { dead = true; }
	public boolean isDead () { return dead; }
//	
//	public void update () {
//		from.convey(to);
//	}
}
