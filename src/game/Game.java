package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import model.*;
import view.MajorPanel;
import view.PlayerPanel;

public class Game {

	private Player p1;
	private Player p2;
	private Player npc;
	private Field field;
	private JFrame frame;
	private MajorPanel majorPanel;
	private JPanel topPanel;
	private PlayerPanel pp1;
	private PlayerPanel pp2;
	private ChosenPlanet buff1;
	private ChosenPlanet buff2;
	private Control p1Control;
	private Control p2Control;
	
	public static void main(String[] args) {
		Game game = new Game();
		do {
			game.update();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while ( !game.judgeOver() );
	}

	public Game() {
		field = new Field();
		
		p1 = new Player(new Position(0, 0), new ImageIcon("BackgoundPicture//RedBall.jpg"), Color.YELLOW, "player 1");
		p2 = new Player(new Position(FieldConstants.height - 1, FieldConstants.width - 1), new ImageIcon("BackgoundPicture//BlueBall.jpg"), Color.RED, "player 2");
		npc = new Player(null, null, Color.GRAY, "npc");
		
		fieldInit();
		
		ImageIcon backgroundImage = new ImageIcon("BackgoundPicture//Space.jpg");
		majorPanel = new MajorPanel(field, backgroundImage.getImage());
		
		topPanel = new JPanel();
		pp1 = new PlayerPanel(p1);
		pp2 = new PlayerPanel(p2);
		topPanel.add(pp1);
		topPanel.add(pp2);
		topPanel.setBackground(Color.BLUE);
		
		buff1 = new ChosenPlanet(p1);
		buff2 = new ChosenPlanet(p2);
		
		p1Control = new Control(p1, buff1, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_SPACE);
		p2Control = new Control(p2, buff2, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_ENTER);
		
		frame = new JFrame("Planet War");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(majorPanel, BorderLayout.CENTER);
		frame.add(topPanel, BorderLayout.NORTH);
		frame.pack();
		frame.setVisible(true);
		
		focusUpdate();
	}

	public void fieldInit() {
		field.setPlanet(new Position(0, 0), new Planet(40, 2, 2, p1));
		field.setPlanet(new Position(4, 7), new Planet(20, 4, 3, npc));
		field.setPlanet(new Position(6, 12), new Planet(20, 4, 3, npc));
		field.setPlanet(new Position(1, 14), new Planet(50, 2, 7, npc));
		field.setPlanet(new Position(2, 3), new Planet(25, 6, 8, npc));
		field.setPlanet(new Position(3, 10), new Planet(28, 5, 1, npc));
		field.setPlanet(new Position(6, 2), new Planet(30, 2, 3, npc));
		field.setPlanet(new Position(7, 6), new Planet(60, 1, 6, npc));
		field.setPlanet(new Position(0, 8), new Planet(30, 3, 4, npc));
		field.setPlanet(new Position(3, 12), new Planet(40, 4, 3, npc));
		field.setPlanet(new Position(FieldConstants.height - 1, FieldConstants.width - 1), new Planet(40, 2, 2, p2));
	}
	
	public void update () {
		p1.update(field);
		p2.update(field);
		pp1.update();
		pp2.update();
		field.update();
		majorPanel.update();
		frame.repaint();
	}
	
	private void focusUpdate () {
		majorPanel.clearFocus();
		majorPanel.drawFocus(p1.getFocus(), p1.getColor());
		majorPanel.drawFocus(p2.getFocus(), p2.getColor());
		buff1.update();
		buff2.update();
		frame.repaint();
	}
	
	private boolean judgeOver () {
		if (p1.getTotalGrowSpeed() == 0) {
			pp1.print(p1.getName() + " Lose!");
			pp2.print(p2.getName() + " Win!");
			return true;
		} else if (p2.getTotalGrowSpeed() == 0) {
			pp2.print(p2.getName() + " Lose!");
			pp1.print(p1.getName() + " Win!");
			return true;
		} else {
			return false;
		}
	}
	
	private class ChosenPlanet {
		Position pos;
		Player player;
		
		public ChosenPlanet (Player player) {
			this.player = player;
			pos = null;
		}

		public void choose (Position focus) {
			Planet chosenPlanet = field.getPlanet(focus);
			
			if (pos != null) {
				Planet prechosenPlanet = field.getPlanet(pos);
				//Avoid the case that the previous-chosen planet be conquered.
				if (prechosenPlanet.getOwner() != player) {
					majorPanel.drawFocus(pos, player.getColor());
					pos = null;
				}
			}
			
			if (chosenPlanet != null) {
				if (pos != null) {
					Planet prechosenPlanet = field.getPlanet(pos);
					majorPanel.drawFocus(pos, player.getColor());
					if (prechosenPlanet.getBridge() != null) {
						field.removeBridge(prechosenPlanet.getBridge());
					}
					if (prechosenPlanet != chosenPlanet) {
						field.addBridge(new Bridge(pos, new Position(focus.getRow(), focus.getCol())));
					}
					pos = null;
				} else if (chosenPlanet.getOwner() == player) {
					pos = new Position(focus.getRow(), focus.getCol());
					update();
				}
			}
		}
		
		public void update () {
			if (pos != null) {
				majorPanel.drawFocus(pos, Color.ORANGE);
			}
		}
	}
	
	public class Control {

		private Player player;
		private ChosenPlanet chosenPlanet;
		private int leftKeyCode;
		private int rightKeyCode;
		private int upKeyCode;
		private int downKeyCode;
		private int chooseKeyCode;
		
		public Control(Player player, ChosenPlanet chosenPlanet, int leftKeyCode, int rightKeyCode, int upKeyCode, int downKeyCode, int chooseKeyCode) {
			this.player = player;
			this.chosenPlanet = chosenPlanet;
			this.leftKeyCode = leftKeyCode;
			this.rightKeyCode = rightKeyCode;
			this.upKeyCode = upKeyCode;
			this.downKeyCode = downKeyCode;
			this.chooseKeyCode = chooseKeyCode;
			
			InputMap imap = majorPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
			imap.put(KeyStroke.getKeyStroke(leftKeyCode, 0, true), player + "left");
			imap.put(KeyStroke.getKeyStroke(rightKeyCode, 0, true), player + "right");
			imap.put(KeyStroke.getKeyStroke(upKeyCode, 0, true), player + "up");
			imap.put(KeyStroke.getKeyStroke(downKeyCode, 0, true), player + "down");
			imap.put(KeyStroke.getKeyStroke(chooseKeyCode, 0, true), player + "choose");
			
			ActionMap amap = majorPanel.getActionMap();
			amap.put(player + "left", new LeftAction());
			amap.put(player + "right", new RightAction());
			amap.put(player + "up", new UpAction());
			amap.put(player + "down", new DownAction());
			amap.put(player + "choose", new ChooseAction());
		}
		
		private class LeftAction extends AbstractAction {
			@Override
			public void actionPerformed(ActionEvent event) {
				player.getFocus().left();
				focusUpdate();
			}
		}
		
		private class RightAction extends AbstractAction {
			@Override
			public void actionPerformed(ActionEvent event) {
				player.getFocus().right();
				focusUpdate();
			}
		}
		
		private class UpAction extends AbstractAction {
			@Override
			public void actionPerformed(ActionEvent event) {
				player.getFocus().up();
				focusUpdate();
			}
		}
		
		private class DownAction extends AbstractAction {
			@Override
			public void actionPerformed(ActionEvent event) {
				player.getFocus().down();
				focusUpdate();
			}
		}
		
		private class ChooseAction extends AbstractAction {
			@Override
			public void actionPerformed(ActionEvent event) {
				chosenPlanet.choose(player.getFocus());
			}
		}
	}
}
