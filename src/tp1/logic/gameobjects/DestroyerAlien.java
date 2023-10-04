package tp1.logic.gameobjects;

public class DestroyerAlien {
    private int column;
    private int row;
    private int resistance;

    public DestroyerAlien(int column, int row, int resistance) {
        this.column = column;
        this.row = row;
        this.resistance = resistance;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public void horizontalMove(){

    }
}
