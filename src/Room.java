/**
 * This is the class of Room.
 * 
 * @author B06505017, B06505032, B06505054, B06902023
 * @version 1.0
 * @since 2019-05-31
 */
public class Room {
	private boolean[] DateIsOccupied;
	/**
	 * Default constructor
	 */
	Room() {
		DateIsOccupied = new boolean[365];
		for (int i = 0; i < DateIsOccupied.length; i++)
			DateIsOccupied[i] = false;
	}
	/**
	 * Copy constructor
	 */
	Room(Room _Room) {
		DateIsOccupied = new boolean[365];
		for (int i = 0; i < _Room.DateIsOccupied.length; i++)
			DateIsOccupied[i] = _Room.DateIsOccupied[i];
	}
	boolean[] getDateIsOccupied() {
		/*boolean[] nDateIsOccupied = new boolean[190];
		for (int i = 0; i < DateIsOccupied.length; i++)
			nDateIsOccupied[i] = DateIsOccupied[0];
		return nDateIsOccupied;*/
		return DateIsOccupied;
	}
	void setDateIsOccupied(int i) {
		DateIsOccupied[i] = true;
	}
	void setDateIsNotOccupied(int i) {
		DateIsOccupied[i] = false;
	}
}