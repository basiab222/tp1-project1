package tp1.logic;

import tp1.logic.gameobjects.RegularAlien;
import tp1.logic.gameobjects.UCMSpaceship;
import tp1.logic.gameobjects.UCMLaser;
import tp1.logic.lists.RegularAlienList;
import tp1.view.Messages;

import java.util.Random;

// TODO implementarlo
public class Game {
    public static final int DIM_X = 9;
    public static final int DIM_Y = 8;

    //TODO fill your code
    private UCMSpaceship ucmShip;
    private UCMLaser ucmLaser;
    private RegularAlien regularAlien;

    private RegularAlienList regularAlienList;

    private int cycles;

    public static boolean laserShotObject = false;

    public Game(Level level, long seed) {
        ucmShip = new UCMSpaceship(DIM_X / 2, DIM_Y - 1);
        regularAlien = new RegularAlien(5,(DIM_Y / 2) + 2);


        cycles = 0; //initialise cycles to 0
    }

    public void reset() {
        // Reset game state to initial values (destroy all objects, clear lists, reset game cycle, etc.)
        ucmShip = new UCMSpaceship(DIM_X / 2, DIM_Y - 1);
        ucmLaser = null;
        regularAlien = new RegularAlien(5, (DIM_Y / 2) + 2);
        laserShotObject = false;
        cycles = 0; // Reset the cycles

    }


    public UCMSpaceship getUcmShip() {
        return ucmShip;
    }

    public String stateToString() {
        return "Life: " + ucmShip.getResistance() + "\n" +
                "Points: " //+ ucmShip.getPoints()
                 + "\n" +
                "shockWave: " //+ (ucmShip.isShockWaveEnabled() ? "ON" : "OFF")
                + "\n";
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

    public void moveRegAliens(){
        regularAlien.automaticMove();
    }

    public void shootLaser() {
        if (ucmShip.getLaserAvailable()) {
            System.out.println("There's already a laser on the screen!");
        } else {
            ucmShip.setLaserAvailable(true);
            ucmLaser = new UCMLaser(ucmShip.getRow(), ucmShip.getColumn(), Move.UP);

        }
    }


    // Method to check if a position is within the game board boundaries
    private boolean isValidPosition(int column, int row) {
        return column >= 0 && column < DIM_X && row > 0 && row < DIM_Y;
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
        return cycles;
    }

    public int getRemainingAliens() {
        //TODO fill your code
        return 0;
    }

    public String positionToString(int col, int row) {
        //String s = "";

        if (ucmShip.getRow() == row && ucmShip.getColumn() == col) {
            if ( ucmShip.getResistance() > 0)
                return Messages.UCMSHIP_SYMBOL;
            else
                return Messages.UCMSHIP_DEAD_SYMBOL;// Display the spaceship symbol
        } else if (regularAlien.getRow() == row && regularAlien.getColumn() == col && regularAlien.getResistance() > 0){
            return String.format(Messages.GAME_OBJECT_STATUS,Messages.REGULAR_ALIEN_SYMBOL,regularAlien.getResistance());
        } else if (ucmShip.getLaserAvailable() && ucmLaser.getRow() == row && ucmLaser.getColumn() == col) {
            return Messages.LASER_SYMBOL; //add option if it cannot be shot
        } else {
            return " "; // Empty cells
        }
        //return s;
    }

    public boolean playerWin() {
        return regularAlien.getResistance() == 0;
    }

    public boolean aliensWin() {
        if (regularAlien.getRow() == ucmShip.getRow()){
            ucmShip.setResistance(0);
            return true;
        }
        return false;
    }

    public void enableLaser() {
        if (isValidPosition(ucmLaser.getColumn(), ucmLaser.getRow()) && ucmShip.getLaserAvailable()) {
            ucmLaser.performLaserMovement();
            if (ucmLaser.isOut()){
                ucmShip.setLaserAvailable(false);
            }
            laserShotObject = false;
        } else {
            ucmShip.setLaserAvailable(true);
        }
    }

    public void alienIsShot(){
        if (regularAlien.getRow() == ucmLaser.getRow() && regularAlien.getColumn() == ucmLaser.getColumn() + 1){
            regularAlien.receiveAttack();
            ucmShip.setLaserAvailable(false);
            laserShotObject = true;
        }
    }

    public void incrementCycles(){
        cycles++;
    }

    public void updateGame(){
        incrementCycles();
        if (ucmLaser != null && !laserShotObject ) //only call this when there is a laser on screen, so can move before that
            enableLaser();

        if (ucmShip.getLaserAvailable())
            alienIsShot();

        moveRegAliens();
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
