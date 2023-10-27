package tp1.logic.gameobjects;

public class UCMSpaceship {
    private int column;
    private int row;
    private int resistance;
    private boolean isLaserAvailable;
    private boolean shockwaveAvailable;
    private int points;


    // Constructor
    public UCMSpaceship(int initialColumn, int initialRow) {
        this.column = initialColumn;
        this.row = initialRow;
        this.resistance = 3;
        this.isLaserAvailable = false;
        this.shockwaveAvailable = false;
        this.points = 0;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isLaserAvailable() {
        return isLaserAvailable;
    }

    public boolean isShockwaveAvailable() {
        return shockwaveAvailable;
    }

    // Getter
    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public boolean getLaserAvailable() {
        return isLaserAvailable;
    }

    public boolean getShockwave(){
        return shockwaveAvailable;
    }

    public void setLaserAvailable(boolean laserAvailable) {
        this.isLaserAvailable = laserAvailable;
    }

    public void setShockwaveAvailable(boolean shockwaveAvailable){
        this.shockwaveAvailable = shockwaveAvailable;
    }
    // Other methods (add more)

    public void moveHorizontally(int amount) {
        // Implement horizontal movement logic
    }



    public void fireLaser() {
        int laserRow = this.row - 1;
        int laserCol = this.column;

    }

    public int getResistance() {
        return resistance;
    }

    // Other methods specific to UCMShip
}
