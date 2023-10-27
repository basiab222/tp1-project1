package tp1.logic.lists;

import tp1.logic.gameobjects.Bomb;

public class BombList {
    private Bomb[] bombList;
    private int counter;

    public BombList(int destroyerAlienNumbers) {
        bombList = new Bomb[destroyerAlienNumbers];
        counter = 0;
    }

    public void addBomb(int row, int column){
            bombList[counter] = new Bomb(row, column);
            counter++;
    }

    //maybe deleteBomb method too

    public Bomb[] getBombList() {
        return bombList;
    }

    public void setBombList(Bomb[] bombList) {
        this.bombList = bombList;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
