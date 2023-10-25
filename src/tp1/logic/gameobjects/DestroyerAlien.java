package tp1.logic.gameobjects;

import tp1.logic.AlienManager;
import tp1.logic.Move;

public class DestroyerAlien {
    private int row;
    private int col;
    private int resistance;
    private int damage;
    private int points;
    public static boolean movingLeft = true;
    private	boolean shouldDescend = false;
    private int cyclesToMove;
    private int speed;
    private Move dir;
    private AlienManager alienManager;
    private Bomb bomb;
    private boolean isBombAvailable;

    public DestroyerAlien(int row, int col) {
        this.row = row;
        this.col = col;
        this.resistance = 1;
        this.damage = 1;
        this.points = 10;
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

    public void automaticMove() {
        if (!shouldDescend) {
            if (movingLeft) {
                this.setColumn(this.getColumn() - 1);
                if (isInBorder()) {
                    movingLeft = false;
                    shouldDescend = true; // Set the flag to descend in the next cycle
                }
            } else {
                this.setColumn(this.getColumn() + 1);
                if (isInBorder()) {
                    movingLeft = true;
                    shouldDescend = true; // Set the flag to descend in the next cycle
                }
            }
        } else {
            descent(); // Descend in the current cycle
            shouldDescend = false; // Reset the flag for the next cycle
        }

    }

    private void descent() {
        this.setRow(this.getRow() + 1);
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
}