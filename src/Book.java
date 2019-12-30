import java.time.*;
import java.time.format.DateTimeFormatter;

public class Book {
	private String hotelName;
	private String startTime;
	private String endTime;
	private int reservations;
	private String contactName;
	private String contactPhone;
	private String roomType;
	private int roomQuantity;
	
	/**
	 * Default constructor
	 */
	Book() {
		hotelName = "";
		reservations = 0;
		contactName = "";
		contactPhone = "";
		roomType = "";
		roomQuantity = 0;
	}
	/**
	 * Copy constructor
	 */
	Book(String _hotelName, String _startTime, String _endTime, int _reservations, String _contactName, 
			String _contactPhone, String _roomType, int _roomQuantity) {
		hotelName = _hotelName;
		startTime = _startTime;
		endTime = _endTime;
		reservations = _reservations;
		contactName = _contactName;
		contactPhone = _contactPhone;
		roomType = _roomType;
		roomQuantity = _roomQuantity;
		
		
		print();
	}
	
	
	private void print() {
		
		System.out.println("You book hotel:" + hotelName + " !");
		System.out.println("Date:------------");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate startDate = LocalDate.parse(startTime, formatter);
		LocalDate endDate = LocalDate.parse(endTime, formatter);
		while(startDate.compareTo(endDate) < 0) { // startDate less than endDate
			System.out.println(startDate.format(formatter));
			startDate = startDate.plusDays(1);
		}
		System.out.println("-----------------");

		System.out.println(contactName);
		System.out.println(contactPhone);
		return;
	}

}