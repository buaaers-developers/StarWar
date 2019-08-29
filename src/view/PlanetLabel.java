package view;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

import model.Planet;

public class PlanetLabel extends JLabel{

	private Planet planet; 
	
	public PlanetLabel(Planet planet) {
		super("", JLabel.CENTER);
		this.planet = planet;
		setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (planet != null) {
			Graphics2D g2 = (Graphics2D) g;
			GradientPaint gradient = new GradientPaint(0, 0, planet.getOwner().getColor(), this.getWidth(), this.getHeight(), Color.WHITE);
			g2.setPaint(gradient);
			int diameter = min(this.getWidth(), this.getHeight());
			g2.fillOval((this.getWidth() - diameter) / 2, (this.getHeight() - diameter) / 2, diameter, diameter);
		}
	}

	private int min(int x, int y) {
		return x > y ? y : x;
	}

	public void update() {
		if (planet != null) {
			setText(planet.toString());
		}
	}
}
