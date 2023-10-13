package tp1.logic;

import tp1.logic.gameobjects.UCMSpaceship;
import tp1.logic.gameobjects.UCMLaser;
import tp1.view.Messages;

import java.util.Random;

// TODO implementarlo
public class Game {
	public static final int DIM_X = 9;
	public static final int DIM_Y = 8;

	//TODO fill your code
	private UCMSpaceship ucmShip;
	private UCMLaser ucmLaser;
	public Game(Level level, long seed) {
		//TODO fill your code
		ucmShip = new UCMSpaceship(DIM_X / 2,DIM_Y-1);


	}

	public UCMSpaceship getUcmShip() {
		return ucmShip;
	}

	public String stateToString() { //THIS METHOD IS RETURNING THE STUFF BEFORE THE REMAINING ALIENS LINE, SO NEED TO FIX THAT BEFORE ADDING THE SHIP
		return null;
	}

	public void moveUCMShip(int x, int y) {
		int newColumn = ucmShip.getColumn() + x;
		int newRow = ucmShip.getRow() + y;

		// Check if the new position is within the game board boundaries
		if (isValidPosition(newColumn, newRow)) {
			ucmShip.setColumn(newColumn);
			ucmShip.setRow(newRow);
		}
	}

	public void ShootLaser(){
		if (ucmShip.getLaserAvailable()){
			System.out.println("There's already a laser on the screen!");
		}
		else{
			ucmLaser = new UCMLaser(ucmShip.getRow() - 1, ucmShip.getColumn(), Move.UP);
			ucmShip.setLaserAvailable(true);
		}
	}


	// Method to check if a position is within the game board boundaries
	private boolean isValidPosition(int column, int row) {
		return column >= 0 && column < DIM_X && row >= 0 && row < DIM_Y;
	}

	public void displayHelp() {
		System.out.println("[m]ove <left|lleft|right|rright>: Moves UCM-Ship to the indicated direction. \r\n" + 
					"[s]hoot: UCM-Ship launches a laser.\r\n" + 
					"shock[W]ave: UCM-Ship releases a shock wave.\r\n" +
					"[l]ist: Prints the list of available ships.\r\n" +
					"[r]eset: Starts a new game.\r\n" + 
					"[h]elp: Prints this help message.\r\n" +
					"[e]xit: Terminates the program. \r\n" + 
					"[n]one: Skips one cycle.");
			
	}

public int getCycle() {
		//TODO fill your code
		return 0;
	}

	public int getRemainingAliens() {
		//TODO fill your code
		return 0;
	}
	public String positionToString(int col, int row) {
		//String s = "";

		if (ucmShip.getRow() == row && ucmShip.getColumn() == col) {
			return Messages.UCMSHIP_SYMBOL; // Display the spaceship symbol
		} else if (ucmShip.getLaserAvailable() && ucmLaser.getRow() == row && ucmLaser.getColumn() == col){
			return Messages.LASER_SYMBOL; //add option if it cannot be shot
		}else{
			return " "; // Empty cells
		}
		//return s;
	}

	public boolean playerWin() {
		//TODO fill your code
		return false;
	}

	public boolean aliensWin() {
		//TODO fill your code
		return false;
	}

	public void enableLaser() {
		//TODO fill your code		
	}

	public Random getRandom() {
		//TODO fill your code
		return null;
	}

	public Level getLevel() {
		//TODO fill your code
		return null;
	}


}
