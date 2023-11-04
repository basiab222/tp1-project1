package tp1.logic;

import tp1.logic.gameobjects.*;
import tp1.logic.lists.BombList;
import tp1.logic.lists.DestroyerAlienList;
import tp1.logic.lists.RegularAlienList;

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
	private UCMSpaceship ucmSpaceship;
	private int destroyerAlienGroupRow;
	private int destroyerAlienGroupColumn;

/*	private BombList bombList;*/
	private Move dir;

	public AlienManager(Game game, Level level, UCMSpaceship ucmSpaceship) {
		this.level = level;
/*		this.bombList = new BombList(level.getNumDestroyerAliens()); */
		this.game = game;
		this.ucmSpaceship = ucmSpaceship;
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

		int startRow;
		int startCol; // Put destroyer aliens in the center of the 4 spaceships

		if (game.getLevel() == Level.HARD) {
			int rows = level.getNumRowsRegularAliens();
			int cols = level.getNumDestroyerAliens() / rows;
			startRow = level.getNumRowsRegularAliens() + 1;
			startCol = (level.getNumDestroyerAliens() / 2) + 2;

			for (int i = 0; i < cols; i++) {
				for (int j = 0; j < rows; j++) {
					destroyerAlienList.addDestroyerAlien(startRow + i, startCol + j);
					remainingAliens++;
				}
			}

		} else if (game.getLevel() == Level.INSANE) {
			int rows = 4;
			int cols = level.getNumDestroyerAliens() / rows;
			startRow = level.getNumRowsRegularAliens() + 1;
			startCol = 2;

			for (int i = 0; i < cols; i++) {
				for (int j = 0; j < rows; j++) {
					destroyerAlienList.addDestroyerAlien(startRow + i, startCol + j);
					remainingAliens++;
				}
			}

		} else if (game.getLevel() == Level.EASY){
			int rows = level.getNumRowsRegularAliens();
			int cols = level.getNumDestroyerAliens() / rows;

			startRow = rows + 1;
			startCol = (cols / 2) + 2;
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					destroyerAlienList.addDestroyerAlien(startRow + i, startCol + j);
					remainingAliens++;
				}
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

	public boolean onBorder() { //check if any alien is on the border
		RegularAlien[] regularAliens = regularAlienList.getRegularAliens();
		for (RegularAlien regularAlien : regularAliens) { //either condition is met, they are in the border.
			if (regularAlien.getColumn() == 0 || regularAlien.getColumn() == 8 || regularAlien.getRow() == 8)
				return true;
		}
		return false;
	}

	public void moveAlienList() {
		int cycle = game.getCycle();
		int numCyclesToMoveOneCell = level.getNumCyclesToMoveOneCell();

		tryShooting();

		if (cycle % numCyclesToMoveOneCell == 0) {
			checkOnBorder(); //make sure that the whole list is inside the border

			if (shouldDescend) {
				moveAllDown(); // move down in this  cycle
				shouldDescend = false; // reset for  next cycle so that it doesnt keep moving down
			} else {
				if (dir == Move.LEFT) {
					// move to left
					for (RegularAlien regularAlien : regularAlienList.getRegularAliens()) {
						regularAlien.moveLeft(); // move all the regular aliens to the left
					}

					for (DestroyerAlien destroyerAlien : destroyerAlienList.getDestroyerAliens()) {
						destroyerAlien.moveLeft(); //move all the destroyer aliens to the left

					}

				} else if (dir == Move.RIGHT) {
					// move everything to the right
					for (RegularAlien regularAlien : regularAlienList.getRegularAliens()) {
						regularAlien.moveRight();
					}

					for (DestroyerAlien destroyerAlien : destroyerAlienList.getDestroyerAliens()) {
						destroyerAlien.moveRight();

					}
				}

				// after moving, switch direction if needed
				if (onBorder()) {
					shouldDescend = true; // put so that they descend in the next cycle
					if (dir == Move.LEFT) {
						dir = Move.RIGHT; // move to  right or left after descending
					} else {
						dir = Move.LEFT;
					}
				}
				onBorder = false;
			}
		}
	}

	public void tryShooting() {
		DestroyerAlien[] destroyerAliens = destroyerAlienList.getDestroyerAliens();
		for (DestroyerAlien da : destroyerAliens) {
			da.moveBomb();
			if (shootChance())
				da.enableBomb();
		}
	}

	public void alienIsShot(UCMLaser ucmLaser){
		RegularAlien[] regularAliens = regularAlienList.getRegularAliens();
		DestroyerAlien[] destroyerAliens = destroyerAlienList.getDestroyerAliens();
		//check if regular aliens been shot
		for (RegularAlien regularAlien : regularAliens ){
			if (regularAlien.getResistance() > 0 && ucmLaser.getRow() == regularAlien.getRow() && ucmLaser.getColumn() == regularAlien.getColumn()){
				regularAlien.receiveAttack();
				game.getUcmShip().setLaserAvailable(false); //check
				Game.laserShotObject = true;
				ucmSpaceship.setPoints(ucmSpaceship.getPoints() + regularAlien.getPoints());
			}
		}
		//check if any destroyer has been shot
		for (DestroyerAlien destroyerAlien : destroyerAliens ){
			if (destroyerAlien.getResistance() > 0 && ucmLaser.getRow() == destroyerAlien.getRow() && ucmLaser.getColumn() == destroyerAlien.getColumn()){
				destroyerAlien.receiveAttack();
				game.getUcmShip().setLaserAvailable(false); //check
				Game.laserShotObject = true;
				ucmSpaceship.setPoints(ucmSpaceship.getPoints() + destroyerAlien.getPoints());
			}
		}
	}

	public void enableLaser(UCMLaser ucmLaser){ //check if the laser is available
		if (game.isValidPosition(ucmLaser.getColumn(), ucmLaser.getRow()) && game.getUcmShip().getLaserAvailable()) {
			ucmLaser.performLaserMovement();
			if (ucmLaser.isOut()){
				game.getUcmShip().setLaserAvailable(false);
			}
			Game.laserShotObject = false;
		} else {
			game.getUcmShip().setLaserAvailable(true);
		}
	}

	public void reset(){ //reset the aliens positions, put them back as start
		remainingAliens = 0;
		regularAlienList = initializeRegularAliens();
		destroyerAlienList = initializeDestroyerAliens();
	}

	public boolean aliensWin() {
		boolean regularBottomRow = false, destroyerBottomRow = false;

		RegularAlien[] regularAliens = regularAlienList.getRegularAliens(); //check if regular aliens are in the bottom row
		for (RegularAlien regularAlien : regularAliens) {
			if (regularAlien.getResistance() > 0 && regularAlien.getRow() == game.getUcmShip().getRow()) {
				game.getUcmShip().setResistance(0);
				regularBottomRow = true;
			}
		}

		DestroyerAlien[] destroyerAliens = destroyerAlienList.getDestroyerAliens(); //check if destroyers are in bottom row
		for (DestroyerAlien destroyerAlien : destroyerAliens) {
			if (destroyerAlien.getResistance() > 0 && destroyerAlien.getRow() == game.getUcmShip().getRow()) {
				game.getUcmShip().setResistance(0);
				destroyerBottomRow = true;
			}
		}

        return regularBottomRow || destroyerBottomRow || game.getUcmShip().getResistance() == 0; //if either condition is met, aliens win
    }

	public boolean playerWin() { //check if all aliens are dead
		boolean regularDead = true, destroyerDead = true;

		//check if all regular and destroyer aliens are dead, if they are, set it to true
		RegularAlien[] regularAliens = regularAlienList.getRegularAliens();
		for (RegularAlien regularAlien : regularAliens) {
            if (regularAlien.getResistance() > 0) {
                regularDead = false;
                break;
            }
		}

		DestroyerAlien[] destroyerAliens = destroyerAlienList.getDestroyerAliens();
		for (DestroyerAlien destroyerAlien : destroyerAliens){
            if (destroyerAlien.getResistance() > 0) {
                destroyerDead = false;
                break;
            }
		}

		if (regularDead && destroyerDead){
			return true;
		}

		return false;
	}

	public RegularAlien getRegularAlienAtPosition(int row, int col) {
		RegularAlien[] regularAliens = regularAlienList.getRegularAliens();
		for (RegularAlien regularAlien : regularAliens) {
			if (regularAlien.getResistance() > 0 && regularAlien.getRow() == row && regularAlien.getColumn() == col) {
				return regularAlien;
			}
		}
		return null; // No regular alien found there
	}

	public DestroyerAlien getDestroyerAlienAtPosition(int row, int col) {
		DestroyerAlien[] destroyerAliens = destroyerAlienList.getDestroyerAliens();
		for (DestroyerAlien destroyerAlien : destroyerAliens){
			if (destroyerAlien.getResistance() > 0 && destroyerAlien.getRow() == row && destroyerAlien.getColumn() == col){
				return destroyerAlien;
			}
		}
		return null; // destroyer alien not found at position
	}

	public void checkOnBorder() { //check if any alien is on the border
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


	public void dealShockwaveDamage(){ // for all aliens make them receive 1 point of damage if they are alive
		RegularAlien[] regularAliens = regularAlienList.getRegularAliens();
		DestroyerAlien[] destroyerAliens = destroyerAlienList.getDestroyerAliens();

		for (RegularAlien regularAlien : regularAliens){
			if (regularAlien.getResistance() > 0){
				regularAlien.receiveAttack();
			}
		}

		for (DestroyerAlien destroyerAlien : destroyerAliens){
			if (destroyerAlien.getResistance() > 0){
				destroyerAlien.receiveAttack();
			}
		}

	}

	public boolean shootChance(){
		return game.getRandom().nextDouble() < game.getLevel().getShootFrequency();
	}

	public DestroyerAlienList getDestroyerAlienList() {
		return destroyerAlienList;
	}
}
