package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;


public class Ufo {

	//TODO fill your code
    private int column;
    private int row;
    private int resistance;

    // Constructor
    public Ufo(int initialColumn, int initialRow) {
        this.column = initialColumn;
        this.row = initialRow;
        this.resistance = 1;
    }

    // Getter methods
    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    // Other methods
    public void moveHorizontally(int amount) {
        // Implement horizontal movement logic
    }

    public int getResistance() {
        return resistance;
    }
	private boolean enabled;
	private Game game;
	
	//TODO fill your code

	public void computerAction() {
		if(!enabled && canGenerateRandomUfo()) {
			enable();
		}
	}
	
	private void enable() {
		//TODO fill your code
	}

	public void onDelete() {
		//TODO fill your code
	}

	/**
	 * Checks if the game should generate an ufo.
	 * 
	 * @return <code>true</code> if an ufo should be generated.
	 */
	private boolean canGenerateRandomUfo(){
		return game.getRandom().nextDouble() < game.getLevel().getUfoFrequency();
	}
	
}
