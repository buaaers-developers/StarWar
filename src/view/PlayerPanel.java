package view;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Player;

public class PlayerPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	Player player;
	JLabel label;

	public PlayerPanel(Player player) {
		this.player = player;
		label = new JLabel();
		update();
		add(label);
	}
	
	public void update() {
		label.setText(player.toString());
	}
	
	public void print(String str) {
		label.setText(str);
	}
}
