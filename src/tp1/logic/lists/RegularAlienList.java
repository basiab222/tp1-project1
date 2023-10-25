package tp1.logic.lists;

import tp1.logic.gameobjects.RegularAlien;

/**
 * Container of regular aliens, implemented as an array with a counter
 * It is in charge of forwarding petitions from the game to each regular alien
 * 
 */
public class RegularAlienList {

	private RegularAlien[] regularAliens;
	private int counter;

	public RegularAlienList(int numAliens) {
		this.regularAliens = new RegularAlien[numAliens];
		counter = 0;
	}

	public void addAlien(int row, int col) {
		regularAliens[counter] = new RegularAlien(row, col);
		counter++;
	}

	public boolean foundAlien(int row, int col) {
		boolean found = false;
		int i = 0;
		while (!found && i < counter) {
			if (regularAliens[i].getRow() == row && regularAliens[i].getColumn() == col)
				found = true;
			i++;
		}
		return found;
	}

	public void removeAlien(int row, int col) {
		if (counter > 0) { // if there are objects
			int i = 0, j;
			while (i < counter){
				if (regularAliens[i].getRow() == row && regularAliens[i].getColumn() == col) { // if the object is found
					for (j = i + 1; j < counter; j++) // big O(n^2) complexity its over
					{
						regularAliens[i] = regularAliens[j]; // move the objects to the left
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
			if (regularAliens[i].getRow() == row && regularAliens[i].getColumn() == col) { //if the object is found decrease the hp
				found = true;
				regularAliens[i].setResistance(regularAliens[i].getResistance() - damage);
			}
			++i;
		}
	}

	// getters and setters for the list
	public RegularAlien[] getRegularAliens() {
		return regularAliens;
	}

	public void setRegularAliens(RegularAlien[] regularAliens) {
		this.regularAliens = regularAliens;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}


}
