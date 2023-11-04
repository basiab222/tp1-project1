package tp1.logic;

import tp1.logic.gameobjects.*;
import tp1.view.Messages;

import java.util.Random;

public class Game {
    public static final int DIM_X = 9;
    public static final int DIM_Y = 8;
    private Level level;
    private long seed;
    private UCMSpaceship ucmShip;
    private UCMLaser ucmLaser;

    /*private BombList bombList;*/
    private AlienManager alienManager;
    private int cycles;
    private Random random;
    private Ufo ufo;
    public static boolean laserShotObject = false;

    public Game(Level level, long seed) { //initialize everything
        this.level = level;
        this.seed = seed;
        this.random = new Random(seed);
        ucmShip = new UCMSpaceship(DIM_X / 2, DIM_Y - 1);
        alienManager = new AlienManager(this, level,ucmShip);
        alienManager.initializeRegularAliens();
        alienManager.initializeDestroyerAliens();
        //initialize D.aliens

        ufo = new Ufo(8,0);
        ufo.setGame(this);
        cycles = 0; //initialise cycles to 0
    }

    public void reset() {
        // reset game state to all the initial values (destroy all objects, clear lists, reset game cycle....)
        ucmShip = new UCMSpaceship(DIM_X / 2, DIM_Y - 1);
        ucmLaser = null;
        laserShotObject = false;
        restartUFO();
        cycles = 0;
        alienManager.reset();
    }

    public void restartUFO(){
        ufo = new Ufo(8,0);
        ufo.setGame(this);
    }

    public UCMSpaceship getUcmShip() {
        return ucmShip;
    }

    public String stateToString() { //to display state of game
        return "Life: " + ucmShip.getResistance() + "\n" +
                "Points: " + ucmShip.getPoints()
                 + "\n" +
                "shockWave: " + (ucmShip.isShockwaveEnabled() ? "ON" : "OFF")
                + "\n";
    }


    public void moveUCMShip(int x, int y) {
        int newColumn = ucmShip.getColumn() + x;
        int newRow = ucmShip.getRow() + y;

        // check if new position is within the game board boundaries
        if (isValidPosition(newColumn, newRow)) {
            ucmShip.setColumn(newColumn);
            ucmShip.setRow(newRow);
        }
    }

    public void moveAliens(){
        alienManager.moveAlienList();
    }

    public void shootLaser() {
        if (ucmShip.getLaserEnabled()) {
            System.out.println("There's already a laser on the screen!");
        } else {
            ucmShip.setLaserEnabled(true);
            ucmLaser = new UCMLaser(ucmShip.getRow(), ucmShip.getColumn(), Move.UP);
        }
    }


    // check if it is a valid board position.
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
        return cycles;
    }

    public int getRemainingAliens() {
        return 0;
    }

    public String positionToString(int col, int row) { //to display each game object on board
        if (ucmShip.getRow() == row && ucmShip.getColumn() == col) {
            if (ucmShip.getResistance() > 0)
                return Messages.UCMSHIP_SYMBOL;
            else
                return Messages.UCMSHIP_DEAD_SYMBOL; // Display the spaceship symbol
        } else if (ufo.isEnabled() && ufo.getRow() == row && ufo.getColumn() == col && ufo.getResistance() > 0) {
            return String.format(Messages.GAME_OBJECT_STATUS, Messages.UFO_SYMBOL, ufo.getResistance());
        }
        else {
            // Check if there's a regular alien at the specified position
            RegularAlien regularAlien = alienManager.getRegularAlienAtPosition(row, col);
            if (regularAlien != null && regularAlien.getResistance() > 0) {
                return String.format(Messages.GAME_OBJECT_STATUS, Messages.REGULAR_ALIEN_SYMBOL, regularAlien.getResistance());
            }

            // Check if there's a laser on the specified position
            if (ucmShip.getLaserEnabled() && ucmLaser != null && ucmLaser.getRow() == row && ucmLaser.getColumn() == col) {
                return Messages.LASER_SYMBOL;
            }

            // Check if there's a destroyer alien at the specified position
            DestroyerAlien destroyerAlien = alienManager.getDestroyerAlienAtPosition(row, col);
            if (destroyerAlien != null && destroyerAlien.getResistance() > 0) {
                return String.format(Messages.GAME_OBJECT_STATUS, Messages.DESTROYER_ALIEN_SYMBOL, destroyerAlien.getResistance());
            }

            DestroyerAlien[] destroyerAliens = alienManager.getDestroyerAlienList().getDestroyerAliens();
            for (DestroyerAlien da : destroyerAliens) {
                if (da.isBombAvailable() && da.getBomb().getColumn() == col && da.getBomb().getRow() == row) {
                    return Messages.BOMB_SYMBOL;
                }
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

    public void updateGame(){ //do all the update per cycle logic here
        incrementCycles();
        UFOComputerActions();

        if (ucmLaser != null && !laserShotObject && !ucmLaser.isOut()){
            enableLaser();
        } //only call this when there is a laser on screen, so can move before that

        alienManager.bombCollisionShip(ucmShip);

        if (ucmShip.getLaserEnabled()) {
            alienManager.bombCollisionLaser(ucmLaser);
            alienIsShot();
        }
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

    public void UFOComputerActions() {
    	//alienManager.computerAction();
    	ufo.computerAction();
        ufoIsShot();
    }

    public void ufoIsShot() { //ufo laser collision logic
        if (ufo.isEnabled() && ucmLaser != null && ufo.getRow() == ucmLaser.getRow() && ufo.getColumn() == ucmLaser.getColumn()) {
            int newResistance = ufo.getResistance() - 1; // Deduct 1 from UFO's resistance
            ufo.setResistance(newResistance); // Update UFO's resistance

            if (newResistance <= 0) { //delete ufo if dead
                ufo.onDelete();
            }

            ucmShip.setPoints(ucmShip.getPoints() + ufo.getPoints());

            if (!ucmShip.getShockwave()) { //once its dead enable the shockwave
                ucmShip.setShockwaveEnabled(true);
            }

            // get rid of laser
            ucmShip.setLaserEnabled(false);
            ucmLaser = null;
        }
    }

    public void shockWave(){ //shockwave logic, after used set it to "off" again
        alienManager.dealShockwaveDamage();
        ucmShip.setShockwaveEnabled(false);
    }

}
