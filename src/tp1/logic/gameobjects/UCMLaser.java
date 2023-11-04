package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;

/**
 * Class that represents the laser fired by {@link UCMSpaceship}
 */
public class UCMLaser {
    private int row;
    private int column;
    private int life;
    private Move dir;
    private Game game;

    public UCMLaser(int row, int column, Move dir) {
        this.row = row;
        this.column = column;
        this.life = 1;
        this.dir = dir;
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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void performLaserMovement() {
        if (getRow() >= 0) {
            setRow(getRow() - 1);
        }
    }

    public boolean isOut() { //to check if its exiting the board after not hitting any1
        return (row < 0);
    }
}
