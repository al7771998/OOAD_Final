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
	Book(Book _Book) {
		hotelName = _Book.hotelName;
		time = _Book.time;
		contactName = _Book.contactName;
		contactPhone = _Book.contactPhone;
		roomType = _Book.roomType;
		roomQuantity = _Book.roomQuantity;
	}
	
}