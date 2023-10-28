package tp1.logic;

import tp1.logic.gameobjects.*;
import tp1.logic.lists.BombList;
import tp1.logic.lists.RegularAlienList;
import tp1.view.Messages;

import java.util.Calendar;
import java.util.Random;

// TODO implementarlo
public class Game {
    public static final int DIM_X = 9;
    public static final int DIM_Y = 8;
    private Level level;
    private long seed;
    private UCMSpaceship ucmShip;
    private UCMLaser ucmLaser;
    private BombList bombList;

    private AlienManager alienManager;
    private Bomb bomb; //check uses of bomb
    private int cycles;

    private Random random;
    private Ufo ufo;
    public static boolean laserShotObject = false;

    public Game(Level level, long seed) {
        this.level = level;
        this.seed = seed;
        this.random = new Random(seed);
        ucmShip = new UCMSpaceship(DIM_X / 2, DIM_Y - 1);
        alienManager = new AlienManager(this, level,ucmShip);
        alienManager.initializeRegularAliens();

        //initialize D.aliens
        bombList = new BombList(level.getNumDestroyerAliens());

        ufo = new Ufo(8,0);
        ufo.setGame(this);
        //ufo.setEnabled(true);
        cycles = 0; //initialise cycles to 0
    }

    public void reset() {
        // Reset game state to initial values (destroy all objects, clear lists, reset game cycle, etc.)
        ucmShip = new UCMSpaceship(DIM_X / 2, DIM_Y - 1);
        ucmLaser = null;
        bomb = null;
        laserShotObject = false;

        cycles = 0; // Reset the cycles
        alienManager.reset();
    }


    public UCMSpaceship getUcmShip() {
        return ucmShip;
    }

    public String stateToString() {
        return "Life: " + ucmShip.getResistance() + "\n" +
                "Points: " + ucmShip.getPoints()
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

    public void moveAliens(){
        alienManager.moveAlienList();
    }

    public void shootLaser() {
        if (ucmShip.getLaserAvailable()) {
            System.out.println("There's already a laser on the screen!");
        } else {
            ucmShip.setLaserAvailable(true);
            ucmLaser = new UCMLaser(ucmShip.getRow(), ucmShip.getColumn(), Move.UP);
        }
    }

//    public void shootBomb(){
//        if (!destroyerAlien.isBombAvailable()){
//            destroyerAlien.setBombAvailable(true);
//            bomb = new Bomb(destroyerAlien.getRow() + 1, destroyerAlien.getColumn());
//        }
//    }

    // Method to check if a position is within the game board boundaries
    boolean isValidPosition(int column, int row) {
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
        System.out.println();

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
        if (ucmShip.getRow() == row && ucmShip.getColumn() == col) {
            if (ucmShip.getResistance() > 0)
                return Messages.UCMSHIP_SYMBOL;
            else
                return Messages.UCMSHIP_DEAD_SYMBOL; // Display the spaceship symbol
        } else if (ufo.isEnabled() && ufo.getRow() == row && ufo.getColumn() == col) {
            return String.format(Messages.GAME_OBJECT_STATUS, Messages.UFO_SYMBOL, ufo.getResistance());
        }
        else {
            // Check if there's a regular alien at the specified position
            RegularAlien regularAlien = alienManager.getRegularAlienAtPosition(row, col);
            if (regularAlien != null && regularAlien.getResistance() > 0) {
                return String.format(Messages.GAME_OBJECT_STATUS, Messages.REGULAR_ALIEN_SYMBOL, regularAlien.getResistance());
            }

            // Check if there's a laser on the specified position
            if (ucmShip.getLaserAvailable() && ucmLaser != null && ucmLaser.getRow() == row && ucmLaser.getColumn() == col) {
                return Messages.LASER_SYMBOL;
            }

            // Check if there's a destroyer alien at the specified position
            DestroyerAlien destroyerAlien = alienManager.getDestroyerAlienAtPosition(row, col);
            if (destroyerAlien != null && destroyerAlien.getResistance() > 0) {
                return String.format(Messages.GAME_OBJECT_STATUS, Messages.DESTROYER_ALIEN_SYMBOL, destroyerAlien.getResistance());
            }

            if (bomb != null && !bomb.isOut() && bomb.getRow() == row && bomb.getColumn() == col && destroyerAlien.isBombAvailable()) {
                return Messages.BOMB_SYMBOL;
            }

            return " "; // Empty cells
        }
    }


    public boolean playerWin() {
        return alienManager.playerWin();
    }

    public boolean aliensWin() {
        return alienManager.aliensWin();
    }

    public void enableLaser() {
        alienManager.enableLaser(ucmLaser);
    }

    public void alienIsShot(){ //change regularAlien for alienManager
        alienManager.alienIsShot(ucmLaser);
    }

    public void incrementCycles(){
        cycles++;
    }

    public void updateGame(){
        incrementCycles();

        ComputerActions();

        if (ucmLaser != null && !laserShotObject && !ucmLaser.isOut()) //only call this when there is a laser on screen, so can move before that
            enableLaser();

        if (bomb != null && !bomb.isOut())
            alienManager.aliensShoot();

        if (ucmShip.getLaserAvailable())
            alienIsShot();

        if (alienManager.onBorder()){
            alienManager.moveAlienList();
        }
        else{
            moveAliens();
        }

        if (ufo.getRow() >= DIM_Y) {
            ufo.onDelete(); // UFO goes out of bounds, disable it
        }


    }

    public Random getRandom() {
        return random;
    }

    public Level getLevel() {
        return level;
    }

    public BombList getBombList() {
        return bombList;
    }

    public void ComputerActions() {
    	//alienManager.computerAction();
    	ufo.computerAction();
    }


}
