package tp1.logic.lists;

import tp1.logic.gameobjects.DestroyerAlien;

public class DestroyerAlienList {

    private DestroyerAlien[] destroyerAliens;
    private int counter;

    public DestroyerAlienList(int numDestroyerAliens) {
        destroyerAliens = new DestroyerAlien[numDestroyerAliens];
        counter = 0;
    }

    public void addDestroyerAlien(int row, int col) {
        destroyerAliens[counter] = new DestroyerAlien(row, col);
        counter++;
    }

    public DestroyerAlien[] getDestroyerAliens() {
        return destroyerAliens;
    }

    public void setDestroyerAliens(DestroyerAlien[] destroyerAliens) {
        this.destroyerAliens = destroyerAliens;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
