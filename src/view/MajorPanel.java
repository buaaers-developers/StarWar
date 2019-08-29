package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.Bridge;
import model.Field;
import model.FieldConstants;
import model.Position;

public class MajorPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private PlanetPanel[][] planetPanel;
	private Field field;
	private Image img;
	
	public MajorPanel(Field field, Image image) {
		this.field = field;
		img = image;
		
		planetPanel = new PlanetPanel[FieldConstants.height][FieldConstants.width];
		setLayout(new GridLayout(FieldConstants.height, FieldConstants.width));
		
		for(int row = 0; row < FieldConstants.height; row++){
			for(int col = 0; col < FieldConstants.width; col++){
				planetPanel[row][col] = new PlanetPanel(field.getPlanet(new Position(row, col)));
				add(planetPanel[row][col]);
			}
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(2.5f));
		
		Bridge[] bridges = field.getBridges().toArray(new Bridge[field.getBridges().size()]);
		for (Bridge bridge : bridges) {
			drawBridge(g2, bridge);
		}
	}

	private void drawBridge(Graphics2D g, Bridge bridge){
		Position from = bridge.getFrom();
		Position to = bridge.getTo();
		
		g.setColor(field.getPlanet(from).getOwner().getColor());
		int h = PlanetPanel.get_Height();
		int w = PlanetPanel.get_Width();
		g.drawLine(from.getCol() * w + w / 2, from.getRow() * h + h / 2, to.getCol() * w + w / 2, to.getRow() * h + h / 2);
	}

	public void clearFocus(){
		for(int row = 0; row < FieldConstants.height; row++)
			for(int col = 0; col < FieldConstants.width; col++)
				planetPanel[row][col].setBorder(BorderFactory.createLineBorder(Color.white));
	}
	
	public void drawFocus(Position focus, Color color){
		planetPanel[focus.getRow()][focus.getCol()].setBorder(BorderFactory.createLineBorder(color, 10));
	}
	
	public void update() {
		for(int row = 0; row < FieldConstants.height; row++){
			for(int col = 0; col < FieldConstants.width; col++){
				planetPanel[row][col].update();
			}
		}
	}
	
//	public static void main(String[] args){
//		Player p1 = new Player(new Position(4, 1), new ImageIcon("RedBall.jpg"), new Color(255, 0, 0));
//		
//		Field field = new Field();
//		field.setPlanet(new Position(2, 3), new Planet(10, 2, 2, p1));
//		field.setPlanet(new Position(6, 5), new Planet(20, 4, 3, p1));
//		
//		JFrame frame = new JFrame();
//		MajorPanel majorPanel = new MajorPanel(field);
//		majorPanel.clearFocus();
//		majorPanel.drawFocus(p1.getFocus(), p1.getColor());
//		
//		PlayerPanel playerPanel = new PlayerPanel(p1);
//		
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setResizable(false);
//		frame.add(majorPanel, BorderLayout.CENTER);
//		frame.add(playerPanel, BorderLayout.NORTH);
//		frame.pack();
//		frame.setVisible(true);
//	}
}
