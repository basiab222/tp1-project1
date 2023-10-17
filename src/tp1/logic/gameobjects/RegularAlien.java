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

	public RegularAlien(int row, int col){
		this.row = row;
		this.col = col;
		resistance = 2;
		damage = 5;
	}

	
	//TODO fill your code
	private int cyclesToMove;
	private int speed;
	private Move dir;
	
	private AlienManager alienManager;

	public int getRow(){ return row;}

	public int setRow(int row){ this.row = row; }

	public int getColumn(){ return col; }

	public int setColumn(int column){ this.col = column; }

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
	public void automaticMove() {
		//TODO fill your code
	}

	private void descent() {
		//TODO fill your code
		
	}

	private void performMovement(Move dir) {
		//TODO fill your code
		
	}

	private boolean isInBorder() {
		//TODO fill your code
		return false;
	}

	public boolean receiveAttack(UCMLaser laser) {
		//TODO fill your code
		return false;
	}
	

}
