package tp1.logic.lists;

import tp1.logic.Position;
import tp1.logic.gameobjects.RegularAlien;
import tp1.logic.gameobjects.UCMLaser;

/**
 * Container of regular aliens, implemented as an array with a counter
 * It is in charge of forwarding petitions from the game to each regular alien
 * 
 */
public class RegularAlienList {

	private RegularAlien[] objects;
	private int counter;

	public RegularAlienList(int numAliens) {
		objects = new RegularAlien[numAliens];
		counter = 0;
	}

	public void addAlien(int row, int col) {
		objects[counter] = new RegularAlien(row, col);
		counter++;
	}

	public boolean foundAlien(int row, int col) {
		boolean found = false;
		int i = 0;
		while (!found && i < counter) {
			if (objects[i].getRow() == row && objects[i].getColumn() == col)
				found = true;
			i++;
		}
		return found;
	}

	public void removeAlien(int row, int col) {
		if (counter > 0) { // if there are objects
			int i = 0, j;
			while (i < counter){
				if (objects[i].getRow() == row && objects[i].getColumn() == col) { // if the object is found
					for (j = i + 1; j < counter; j++) // big O(n^2) complexity its over
					{
						objects[i] = objects[j]; // move the objects to the left
						i++; // increase the index
					}
					counter--;
				}
				i++;
			}

		}
	}

	public void reduceResistance(int damage, int row, int col){
		int i = 0;
		boolean found = false;
		while (!found && i < counter) {
			if (objects[i].getRow() == row && objects[i].getColumn() == col) { //if the object is found decrease the hp
				found = true;
				objects[i].setResistance(objects[i].getResistance() - damage);
			}
			++i;
		}
	}

	// getters and setters for the list
	public RegularAlien[] getObjects() {
		return objects;
	}

	public void setObjects(RegularAlien[] objects) {
		this.objects = objects;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}


}
