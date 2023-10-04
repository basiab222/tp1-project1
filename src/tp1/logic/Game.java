package tp1.logic;

import tp1.logic.gameobjects.UCMSpaceship;

import java.util.Random;

// TODO implementarlo
public class Game {

	public static final int DIM_X = 9;
	public static final int DIM_Y = 8;

	//TODO fill your code
	private UCMSpaceship ucmShip;

	public Game(Level level, long seed) {
		//TODO fill your code
		ucmShip = new UCMSpaceship(4,DIM_Y - 1);

	}

	public String stateToString() {
		//TODO fill your code
		StringBuilder sb = new StringBuilder();

		for (int row = 0; row < DIM_Y; row++){
			for (int col = 0; col < DIM_X; col++){
				if (ucmShip.getRow() == row && ucmShip.getColumn() == col){
					sb.append("^__^");
				} else{
					sb.append(" ");
				}
			}
			sb.append("\n");
		}
		return sb.toString();
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
		String s = "";
		return s;
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
