package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;

/**
 * 
 * Class that represents the laser fired by {@link UCMSpaceship}
 *
 */
public class UCMLaser {
	private int row;
	private int column;
	private int life;
	private DestroyerAlien launcher; //reference to the destroyer ship that launched the bomb

	//TODO fill your code
	private Move dir;
	private Game game;

	public UCMLaser(int row, int column, DestroyerAlien launcher, Move dir, Game game) {
		this.row = row;
		this.column = column;
		this.life = 1;
		this.launcher = launcher;
		this.dir = dir;
		this.game = game;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public DestroyerAlien getLauncher() {
		return launcher;
	}

	public void setLauncher(DestroyerAlien launcher) {
		this.launcher = launcher;
	}

	public Move getDir() {
		return dir;
	}

	public void setDir(Move dir) {
		this.dir = dir;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	/**
	 *  Method called when the laser disappears from the board
	 */
	public void onDelete() {
		game.enableLaser();
	}

	/**
	 *  Implements the automatic movement of the laser	
	 */
	public void automaticMove () {
		performMovement(dir);
		if(isOut())
			die();
	}

	
	// PERFORM ATTACK METHODS
	
	
	
	
	private void die() {
		//TODO fill your code
	}

	private boolean isOut() {
		//TODO fill your code
		return false;
	}

	private void performMovement(Move dir) {
		//TODO fill your code
	}

	/**
	 * Method that implements the attack by the laser to a regular alien.
	 * It checks whether both objects are alive and in the same position.
	 * If so call the "actual" attack method {@link weaponAttack}.
	 * @param other the regular alien possibly under attack
	 * @return <code>true</code> if the alien has been attacked by the laser.
	 */
	public boolean performAttack(RegularAlien other) {
		//TODO fill your code
		return false;
	}

	/**
	 * Method that implements the attack by the laser to a destroyer alien.
	 * It checks whether both objects are alive and in the same position.
	 * If so call the "actual" attack method {@link weaponAttack}.
	 * @param other the destroyer alien possibly under attack
	 * @return <code>true</code> if the alien has been attacked by the laser.
	 */

	/*
	public boolean performAttack(DestroyerAlien other) {
		//TODO fill your code
		return false;
	}
	*/
	
	//TODO fill your code


	//ACTUAL ATTACK METHODS
	

	/**
	 * 
	 * @param other regular alien under attack by the laser
	 * @return always returns <code>true</code>
	 */
	private boolean weaponAttack(RegularAlien other) {
		return other.receiveAttack(this);	
	}

	//TODO fill your code


	// RECEIVE ATTACK METHODS
	
	/**
	 * Method to implement the effect of bomb attack on a laser
	 * @param weapon the received bomb
	 * @return always returns <code>true</code>
	 */
	/*
	public boolean receiveAttack(Bomb weapon) {
		receiveDamage(weapon.getDamage());
		return true;
	}
	*/

}
