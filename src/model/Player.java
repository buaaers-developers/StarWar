package model;

import java.awt.Color;

import javax.swing.ImageIcon;

public class Player {
	private int totalGrowSpeed = 0;
	private int totalPower = 0;
	private Position focus;
	private ImageIcon icon;
	private Color color;
	private String name;
	
	public Player(Position pos, ImageIcon icon, Color color, String name) {
		super();
		focus = pos;
		this.icon = icon;
		this.color = color;
		this.name = name;
	}
	
	public ImageIcon getImageIcon() { return icon; }
	public Color getColor() { return color; }
	public Position getFocus() { return focus; }
	public int getTotalGrowSpeed() { return totalGrowSpeed; }
	public int getTotalPower() { return totalPower; }
	public String getName () { return name; }
	
	public void update (Field field) {
		totalPower = field.getPlayerTotalPower(this);
		totalGrowSpeed = field.getPlayerTotalGrowSpeed(this);
	}
	
	@Override
	public String toString() {
		return "<html><body>" + name
		+ "&emsp;&emsp;&emsp;Total Growing Speed: " + totalGrowSpeed + "&emsp;&emsp;&emsp;Total Power: " + totalPower + "<body></html>";
	}
}
