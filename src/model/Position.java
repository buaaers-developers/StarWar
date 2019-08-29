package model;

public class Position {

	private int row;
	private int col;
	
	public Position(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public int getRow() { return row; }
	public int getCol() { return col; }
	
	public void left() {
		if (col > 0) {
			col--;
		}
	}
	
	public void right() {
		if (col < FieldConstants.width - 1) {
			col++; 
		}
	}
	
	public void up() {
		if (row > 0) {
			row--;
		}
	}
	
	public void down() {
		if (row < FieldConstants.height - 1) {
			row++;
		}
	}
}
