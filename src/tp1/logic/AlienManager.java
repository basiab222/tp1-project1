package tp1.logic;

import tp1.logic.gameobjects.DestroyerAlien;
import tp1.logic.gameobjects.RegularAlien;
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

	private RegularAlienList regularAlienList;

	private DestroyerAlienList destroyerAlienList;

	public AlienManager(Game game, Level level) {
		this.level = level;
		this.game = game;
		this.remainingAliens = 0;
		regularAlienList = initializeRegularAliens();
		//destroyerAlienList = initializeDestroyerAliens();
	}
		
	// INITIALIZER METHODS
	
	/**
	 * Initializes the list of regular aliens
	 * @return the initial list of regular aliens according to the current level
	 */
	protected RegularAlienList initializeRegularAliens() {
		RegularAlienList regularAlienList = new RegularAlienList(level.getNumRegularAliens());
		for (int i = 0; i < level.getNumRowsRegularAliens(); i++) {
			for (int j = 0; j < level.getNumRegularAliens() / level.getNumRowsRegularAliens(); j++) { //so it prints in the middle
				regularAlienList.addAlien(i, j);
				remainingAliens++;
			}
		}
		return regularAlienList;
	}

	/**
	 * Initializes the list of destroyer aliens
	 * @return the initial list of destroyer aliens according to the current level
	 */
	//protected  DestroyerAlienList initializeDestroyerAliens() {
	//	return null;
	//}

	
	// CONTROL METHODS
		
	public void shipOnBorder() {
		if(!onBorder) {
			onBorder = true;
			shipsOnBorder = remainingAliens;
		}
	}

	public boolean onBorder() {
		RegularAlien[] regularAliens = regularAlienList.getObjects();
		for (RegularAlien regularAlien : regularAliens) {
				if (regularAlien.getColumn() == 0 || regularAlien.getColumn() == 8 || regularAlien.getRow() == 8)
					return true;
		}
		return false;
	}

	public void moveAlienList(){
		moveRegAliensList();
	}

	private void moveRegAliensList(){
		for (RegularAlien regularAlien : regularAlienList.getObjects()) {
			if (regularAlien.getResistance() > 0){
				regularAlien.automaticMove();
			}
		}
	}


}
