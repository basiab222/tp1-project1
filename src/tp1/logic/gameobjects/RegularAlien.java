package tp1.logic.gameobjects;

import tp1.logic.AlienManager;
import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;

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
	public static boolean movingLeft = true;

	public RegularAlien(int row, int col){
		this.row = row;
		this.col = col;
		this.resistance = 2;
		this.damage = 5;
	}

	
	//TODO fill your code
	private int cyclesToMove;
	private int speed;
	private Move dir;
	
	private AlienManager alienManager;

	public int getRow(){ return row;}

	public int setRow(int row){ return this.row = row;}

	public int getColumn(){ return col; }

	public int setColumn(int column){ return this.col = column;}


	public int getResistance() {
		return resistance;
	}
	
	public void setResistance(int resistance) {
		this.resistance = resistance;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	

	//TODO fill your code

	/**
	 *  Implements the automatic movement of the regular alien	
	 */
	//private boolean isInBorder() {
		//return col > 0 && col <= 8 && row > 0 && row < 8;
	//}
			//new isInBorder to check if it is right in the border, not if its outside or w/e
	private boolean isInBorder() {
		return (col == 0 || col == 8 || row == 8);
	}

	public void automaticMove() {
		if (movingLeft) {
			this.setColumn(this.getColumn() - 1);
				if (isInBorder()) {
				descent();
				movingLeft = false;
			}
		} else {
			this.setColumn(this.getColumn() + 1);
				if (isInBorder()) {
					descent();
					movingLeft = true;
				}
			}
		}

	private void descent() {
		this.setRow(this.getRow() + 1);
	}

	private void performMovement(Move dir) {
		//TODO fill your code
		return;
	}

	public boolean receiveAttack(UCMLaser laser) {
		if (this.resistance > 0) {
			this.resistance -= 1;
			return true;
		}
		return false;
	}
	

}
