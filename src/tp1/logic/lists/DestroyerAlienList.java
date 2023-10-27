package tp1.logic.lists;

import tp1.logic.Position;
import tp1.logic.gameobjects.DestroyerAlien;
import tp1.logic.gameobjects.UCMLaser;

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

    /*public void removeDestroyerAlien(int row, int col) {
        if (counter > 0) { // if there are objects
            int i = 0, j;
            while (i < counter){
                if (destroyerAliens[i].getRow() == row && destroyerAliens[i].getColumn() == col) { // if the object is found
                    for (j = i + 1; j < counter; j++) // big O(n^2) complexity its over
                    {
                        destroyerAliens[i] = destroyerAliens[j]; // move the objects to the left
                        i++; // increase the index
                    }
                    counter--;
                }
                i++;
            }

        }
    }*/

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
