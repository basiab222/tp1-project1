package tp1.logic.gameobjects;

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
        this.bomb = null;
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


    /**
     * Implements the automatic movement of the regular alien
     */

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
        if (bomb != null && bomb.isOut()) {
            setBomb(null);
            setBombAvailable(false);
        }
    }

    public Bomb getBomb() {
        return bomb;
    }

    public void setBomb(Bomb bomb) {
        this.bomb = bomb;
    }
}