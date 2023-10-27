package tp1.logic.gameobjects;

import tp1.logic.Game;

public class Bomb {
    private int row;
    private int column;
    private Game game;
    private DestroyerAlien destroyerAlien;

    public Bomb(int row, int column) {
        this.row = row;
        this.column = column;
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

    public void performBombMovement(){
        this.setRow(this.getRow()+1);
    }

    public boolean isOut() { //to check if its exiting the board after not hitting any1
        return (row >= 8);
    }

    //a
}
