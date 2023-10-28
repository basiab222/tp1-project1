package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;


public class Ufo {

	//TODO fill your code
    private int column;
    private int row;
    private int resistance;

    private boolean enabled;
    private Game game;

    private int points;
    // Constructor
    public Ufo(int initialColumn, int initialRow) {
        this.column = initialColumn;
        this.row = initialRow;
        this.resistance = 1;
        this.enabled = false;
        this.points = 25;
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
        if (enabled)
            column += amount;
    }

    public int getResistance() {
        return resistance;
    }

    public Game setGame(Game game) {
        return this.game = game;
    }
	
	//TODO fill your code

	public void computerAction() {
		if(!enabled && canGenerateRandomUfo()) {
            setEnabled(true);
		}
        else if(enabled)
            moveHorizontally(-1);
            if (isOut()){
                setEnabled(false);
            }
	}


	public void onDelete() {
		enabled = false;
	}

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
    	this.enabled = enabled;
    }

    public boolean isOut(){
        return column < 0 || column >= Game.DIM_X;
    }

	private boolean canGenerateRandomUfo(){

        if (!enabled){
            double frequency = game.getLevel().getUfoFrequency();
            boolean canGenerate = game.getRandom().nextDouble() < frequency;

            if (canGenerate)
                setEnabled(true);

            return canGenerate;
        }

        return false;
	}

    public int getPoints() {
        return points;
    }
}
