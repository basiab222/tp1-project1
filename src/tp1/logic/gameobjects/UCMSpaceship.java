package tp1.logic.gameobjects;

public class UCMSpaceship {
    private int column;
    private int row;
    private int resistance;
    private boolean isLaserEnabled;
    private boolean shockwaveEnabled;
    private int points;


    // Constructor
    public UCMSpaceship(int initialColumn, int initialRow) {
        this.column = initialColumn;
        this.row = initialRow;
        this.resistance = 3;
        this.isLaserEnabled = false;
        this.shockwaveEnabled = false;
        this.points = 0;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isLaserEnabled() {
        return isLaserEnabled;
    }

    public boolean isShockwaveEnabled() {
        return shockwaveEnabled;
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

    public boolean getLaserEnabled() {
        return isLaserEnabled;
    }

    public boolean getShockwave(){
        return shockwaveEnabled;
    }

    public void setLaserEnabled(boolean laserEnabled) {
        this.isLaserEnabled = laserEnabled;
    }

    public void setShockwaveEnabled(boolean shockwaveEnabled){
        this.shockwaveEnabled = shockwaveEnabled;
    }

    public int getResistance() {
        return resistance;
    }

}
