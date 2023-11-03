package tp1.logic.gameobjects;

import tp1.logic.AlienManager;
import tp1.logic.Game;
import tp1.logic.Move;

public class DestroyerAlien {
    private int row;
    private int col;
    private int resistance;
    private int damage;
    private int points;
    private Bomb bomb;
    private boolean isBombAvailable;

    public DestroyerAlien(int row, int col) {
        this.row = row;
        this.col = col;
        this.resistance = 1;
        this.damage = 1;
        this.points = 10;
        this.isBombAvailable = false;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getRow() {
        return row;
    }

    public int setRow(int row) {
        return this.row = row;
    }

    public int getColumn() {
        return col;
    }

    public int setColumn(int column) {
        return this.col = column;
    }


    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }


    //TODO fill your code

    /**
     * Implements the automatic movement of the regular alien
     */

    //new isInBorder to check if it is right in the border, not if its outside or w/e
    private boolean isInBorder() {
        return (col == 0 || col == 8 || row == 8);
    }
    private void descent() {
        this.setRow(this.getRow() + 1);
    }
    public void enableBomb(){
        if (bomb != null) return;
        setBombAvailable(true);
        bomb = new Bomb(this.row + 1, this.col, this);
    }

    public void receiveAttack() {
        if (this.resistance > 0) {
            this.resistance -= 1;
        }
    }

    public boolean isBombAvailable() {
        return isBombAvailable;
    }

    public void setBombAvailable(boolean bombAvailable) {
        isBombAvailable = bombAvailable;
    }

    public void moveLeft() {
        if (this.getResistance() > 0) {
            this.setColumn(this.getColumn() - 1);
        }
    }

    public void moveRight() {
        if (this.getResistance() > 0) {
            this.setColumn(this.getColumn() + 1);
        }
    }

    public void moveBomb() {
        if (bomb != null)
            bomb.performBombMovement();
    }

    public Bomb getBomb() {
        return bomb;
    }

    public void setBomb(Bomb bomb) {
        this.bomb = bomb;
    }
}