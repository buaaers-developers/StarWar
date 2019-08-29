package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Planet;

public class PlanetPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Planet planet;
	private static final int height = 120;
	private static final int width = 120;
	JLabel label;

	public static int get_Height() {
		return height;
	}

	public static int get_Width() {
		return width;
	}

	public PlanetPanel(Planet planet) {
		this.planet = planet;
		if (planet != null) {
			label = new JLabel("", JLabel.CENTER);
			update();
			setLayout(new BorderLayout());
			add(label, BorderLayout.CENTER);
		}
		setOpaque(false);
		setPreferredSize(new Dimension(width, height));
	}

	@Override
	public void paint(Graphics g) {
		if (planet != null) {
			Graphics2D g2 = (Graphics2D) g;

			GradientPaint gradient = new GradientPaint(0, 0, planet.getOwner().getColor(), width, height, Color.WHITE);
			g2.setPaint(gradient);

			// g2.setColor(planet.getOwner().getColor());
			g2.fillOval(2, 2, width - 4, height - 4);
		}
		super.paint(g);
		
	}

	public void update() {
		if (planet != null) {
			// label.setIcon(planet.getOwner().getIcon());
			// setBackground(planet.getOwner().getColor());
			label.setText(planet.toString());
		}
	}
}
