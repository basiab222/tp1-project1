package tp1.logic;

import tp1.logic.gameobjects.DestroyerAlien;
import tp1.logic.gameobjects.RegularAlien;
import tp1.logic.lists.DestroyerAlienList;
import tp1.logic.lists.RegularAlienList;
import tp1.logic.gameobjects.UCMLaser;

/**
 * 
 * Manages alien initialization and
 * used by aliens to coordinate movement
 *
 */
public class AlienManager {
	
	private Level level;
	private Game game;
	private int remainingAliens;
	
	private boolean squadInFinalRow;
	private int shipsOnBorder;
	private boolean onBorder;

	private boolean shouldDescend;

	private RegularAlienList regularAlienList;

	private DestroyerAlienList destroyerAlienList;

	private int destroyerAlienGroupRow;
	private int destroyerAlienGroupColumn;

	private Move dir;

	public AlienManager(Game game, Level level) {
		this.level = level;
		this.game = game;
		this.remainingAliens = 0;
		regularAlienList = initializeRegularAliens();
		destroyerAlienList = initializeDestroyerAliens();

		destroyerAlienGroupRow = level.getNumRowsRegularAliens() + 1;
		destroyerAlienGroupColumn = Game.DIM_X / 2;

		dir = Move.LEFT;
	}
		
	// INITIALIZER METHODS
	
	/**
	 * Initializes the list of regular aliens
	 * @return the initial list of regular aliens according to the current level
	 */
	protected RegularAlienList initializeRegularAliens() { //starts from row 2 col 3
		RegularAlienList regularAlienList = new RegularAlienList(level.getNumRegularAliens());
		int rows = level.getNumRowsRegularAliens();
		int cols = level.getNumRegularAliens() / rows;

		for (int row = 1; row < rows + 1; row++) {
			for (int col = 2; col < cols + 2; col++) { // Start from column 2
				regularAlienList.addAlien(row, col);
				remainingAliens++;
			}
		}
		return regularAlienList;
	}


	/**
	 * Initializes the list of destroyer aliens
	 * @return the initial list of destroyer aliens according to the current level
	 */
	protected DestroyerAlienList initializeDestroyerAliens() {
		DestroyerAlienList destroyerAlienList = new DestroyerAlienList(level.getNumDestroyerAliens());

		int rows = level.getNumRowsRegularAliens();
		int cols = level.getNumDestroyerAliens() / rows;

		int startRow = rows + 1; // put  the destroyers in the row below Regular Aliens lists
		int startCol = (cols / 2) + 2; // put destroyers aliens in the center of the 4 spaceships

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				destroyerAlienList.addDestroyerAlien(startRow + i, startCol + j);
				remainingAliens++;
			}
		}

		return destroyerAlienList;
	}

	
	// CONTROL METHODS
		
	public void shipOnBorder() {
		if(!onBorder) {
			onBorder = true;
			shipsOnBorder = remainingAliens;
		}
	}

	public boolean onBorder() {
		RegularAlien[] regularAliens = regularAlienList.getRegularAliens();
		for (RegularAlien regularAlien : regularAliens) {
			if (regularAlien.getColumn() == 0 || regularAlien.getColumn() == 8 || regularAlien.getRow() == 8)
				return true;
		}
		return false;
	}

	public void moveAlienList() {
		checkOnBorder();

		if (shouldDescend) {
			moveAllDown(); // Descend in this  cycle
			shouldDescend = false; // Reset for the next cycle so that it doesnt keep moving down
		} else {
			if (dir == Move.LEFT) {
				// move to left
				for (RegularAlien regularAlien : regularAlienList.getRegularAliens()) {
					regularAlien.moveLeft();
				}

				for (DestroyerAlien destroyerAlien : destroyerAlienList.getDestroyerAliens()) {
					destroyerAlien.moveLeft();
				}

			} else if (dir == Move.RIGHT) {
				// move to  right
				for (RegularAlien regularAlien : regularAlienList.getRegularAliens()) {
					regularAlien.moveRight();
				}

				for (DestroyerAlien destroyerAlien : destroyerAlienList.getDestroyerAliens()) {
					destroyerAlien.moveRight();
				}
			}

			// After moving, switch dir if needed
			if (onBorder()) {
				shouldDescend = true; // Set to descend in the next cycle
				if (dir == Move.LEFT) {
					dir = Move.RIGHT; // Move to the right or left after descending
				} else {
					dir = Move.LEFT;
				}
			}

			onBorder = false;
		}
	}


	private void moveRegAliensList() {
		for (RegularAlien regularAlien : regularAlienList.getRegularAliens()) {
			if (regularAlien.getResistance() > 0) {
				if (dir == Move.LEFT) {
					regularAlien.setColumn(regularAlien.getColumn() - 1);
				} else {
					regularAlien.setColumn(regularAlien.getColumn() + 1);
				}
			}
		}
	}

	public void alienIsShot(UCMLaser ucmLaser){
		RegularAlien[] regularAliens = regularAlienList.getRegularAliens();
		for (RegularAlien regularAlien: regularAliens ){
			if (regularAlien.getResistance() > 0 && ucmLaser.getRow() == regularAlien.getRow() && ucmLaser.getColumn() == regularAlien.getColumn()){
				regularAlien.receiveAttack();
				game.getUcmShip().setLaserAvailable(false); //check
				game.laserShotObject = true;
			}
		}
	}

	public void enableLaser(UCMLaser ucmLaser){
		if (game.isValidPosition(ucmLaser.getColumn(), ucmLaser.getRow()) && game.getUcmShip().getLaserAvailable()) {
			ucmLaser.performLaserMovement();
			if (ucmLaser.isOut()){
				game.getUcmShip().setLaserAvailable(false);
			}
			game.laserShotObject = false;
		} else {
			game.getUcmShip().setLaserAvailable(true);
		}
	}

	public void reset(){
		remainingAliens = 0;
		regularAlienList = initializeRegularAliens();
		destroyerAlienList = initializeDestroyerAliens();
	}

	public boolean aliensWin() {
		RegularAlien[] regularAliens = regularAlienList.getRegularAliens();
		for (RegularAlien regularAlien : regularAliens) {
			if (regularAlien.getResistance() > 0 && regularAlien.getRow() == game.getUcmShip().getRow()) {
				game.getUcmShip().setResistance(0);
				return true;
			}
		}
		return false;
	}

	public boolean playerWin() {
		RegularAlien[] regularAliens = regularAlienList.getRegularAliens();
		for (RegularAlien regularAlien : regularAliens) {
			if (regularAlien.getResistance() > 0) {
				return false;
			}
		}
		return true;
	}

	public RegularAlien getRegularAlienAtPosition(int row, int col) {
		RegularAlien[] regularAliens = regularAlienList.getRegularAliens();
		for (RegularAlien regularAlien : regularAliens) {
			if (regularAlien.getResistance() > 0 && regularAlien.getRow() == row && regularAlien.getColumn() == col) {
				return regularAlien;
			}
		}
		return null; // No regular alien found at the specified position
	}

	public DestroyerAlien getDestroyerAlienAtPosition(int row, int col) {
		DestroyerAlien[] destroyerAliens = destroyerAlienList.getDestroyerAliens();
		for (DestroyerAlien destroyerAlien : destroyerAliens){
			if (destroyerAlien.getResistance() > 0 && destroyerAlien.getRow() == row && destroyerAlien.getColumn() == col){
				return destroyerAlien;
			}
		}
		return null;
	}

	public void checkOnBorder() {
		for (RegularAlien regularAlien : regularAlienList.getRegularAliens()) {
			if (regularAlien.getResistance() > 0
					&& (regularAlien.getRow() + 1 == Game.DIM_Y || regularAlien.getRow() == 0
					|| regularAlien.getColumn() + 1 == Game.DIM_X || regularAlien.getColumn() == 0)) {
				onBorder = true;
				break; // No need to check once one alien reaches the border
			}
		}
	}

	public void moveAllDown() {
		for (RegularAlien regularAlien : regularAlienList.getRegularAliens()) {
			if (regularAlien.getResistance() > 0) {
				regularAlien.setRow(regularAlien.getRow() + 1);
			}
		}
		//moves the destroyer aliens down as well
		for (DestroyerAlien destroyerAlien : destroyerAlienList.getDestroyerAliens()) {
			if (destroyerAlien.getResistance() > 0) {
				destroyerAlien.setRow(destroyerAlien.getRow() + 1);
			}
		}

	}

	public boolean checkLaserCollision(UCMLaser laser) {
		for (RegularAlien regularAlien : regularAlienList.getRegularAliens()) {
			if (regularAlien.getRow() == laser.getRow() && regularAlien.getColumn() == laser.getColumn()) {
				// Handle collision and damage to the alien
				regularAlien.receiveAttack();
				return true; // Collision occurred
			}
		}
		return false; // No collision
	}

	private void moveDestroyerAliens() {
		DestroyerAlien[] destroyerAliens = destroyerAlienList.getDestroyerAliens();
		for (DestroyerAlien destroyerAlien : destroyerAliens) {
			if (destroyerAlien.getResistance() > 0) {
				// Move Destroyer Aliens as a synchronized group
				destroyerAlien.setRow(destroyerAlienGroupRow);
				destroyerAlien.setColumn(destroyerAlienGroupColumn);
			}
		}
	}


}
