public class Book {
	private String hotelName;
	private Time time;
	private int reservations;
	private String contactName;
	private String contactPhone;
	private String roomType;
	private String roomQuantity;
	
	/**
	 * Default constructor
	 */
	Book() {
		hotelName = "";
		contactName = "";
		contactPhone = "";
		roomType = "";
		roomQuantity = "";
	}
	/**
	 * Copy constructor
	 */
	Book(String _hotelName, Time _time, int _reservations, String _contactName, 
			String _contactPhone, String _roomType, String _roomQuantity) {
		hotelName = _hotelName;
		time = _time;
		contactName = _contactName;
		contactPhone = _contactPhone;
		roomType = _roomType;
		roomQuantity = _roomQuantity;
	}
	
	public void print() {
		System.out.println(hotelName);
		System.out.println(contactName);
		return;
	}

}