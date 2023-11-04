package tp1.logic.gameobjects;

import tp1.logic.Game;

public class Bomb {
    private int row;
    private int column;
    private Game game;
    private DestroyerAlien destroyerAlien;

    public Bomb(int row, int column, DestroyerAlien destroyerAlien) {
        this.row = row;
        this.column = column;
        this.destroyerAlien = destroyerAlien;
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
        if (!isOut())
            this.setRow(this.getRow() + 1);
    }

    public boolean performAttack(UCMSpaceship ucmShip){
        boolean hitShip = false;

        if ((ucmShip.getColumn() == this.getColumn() && ucmShip.getRow() == this.getRow())){
            hitShip = true;
        }
        return hitShip;
    }

    public boolean isOut() { //to check if its exiting the board after not hitting any1
        return row >= Game.DIM_Y;
    }

}
