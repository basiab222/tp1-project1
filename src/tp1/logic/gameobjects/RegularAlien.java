package tp1.logic.gameobjects;

/**
 * 
 * Class representing a regular alien
 *
 */
public class RegularAlien {
	private int row;
	private int col;
	private int resistance;
	private int damage;
	private int points;
	public static boolean movingLeft = true;
	private	boolean shouldDescend = false;

	public RegularAlien(int row, int col) {
		this.row = row;
		this.col = col;
		this.resistance = 2;
		this.damage = 5;
		this.points = 5;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getRow() {
		return row;
	}

	public int setRow(int row) {
		return this.row = row;
	}

	public int getColumn() {
		return col;
	}

	public int setColumn(int column) {
		return this.col = column;
	}


	public int getResistance() {
		return resistance;
	}


	/**
	 * Implements the automatic movement of the regular alien
	 */

	public void moveRight(){
		if (this.getResistance() > 0) {
			this.setColumn(this.getColumn() + 1);
		}
	}

	public void moveLeft(){
		if (this.getResistance() > 0) {
			this.setColumn(this.getColumn() - 1);
		}
	}


	public void receiveAttack() {
		if (this.resistance > 0) {
			this.resistance -= 1;
		}
	}
}
