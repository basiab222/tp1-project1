package tp1.logic.gameobjects;

public class UCMSpaceship {
    private int column;
    private int row;
    private int resistance;

    // Constructor
    public UCMSpaceship(int initialColumn, int initialRow) {
        this.column = initialColumn;
        this.row = initialRow;
        this.resistance = 3;
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

    // Other methods (add more)

    public void moveHorizontally(int amount) {
        // Implement horizontal movement logic
    }

    public void fireLaser() {
        // Implement logic to fire a laser
    }

    public int getResistance() {
        return resistance;
    }

    // Other methods specific to UCMShip
}
