import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

public class SearchResultController{

	private static ArrayList<AvailableHotelRoom> AHR;
	
	String[] heading = new String[] { "ID", "Star", "City", "Address", "Single", "Double", "Quad", "Price", "Select" };
	
	public SearchResultController(ArrayList<AvailableHotelRoom> AHR) {
		this.AHR = AHR;
	}
	
	/**
	 * set up a default table model for the hotel list
	 * 
	 * @param _AHR an array list which store all the hotel list
	 * @return returns a default table model 
	 */
	public DefaultTableModel makeHotellist() {
		DefaultTableModel tablemodel = new DefaultTableModel(heading, 0);
		// get data
		for (int i = 0; i < AHR.size(); i++) {
			int id = AHR.get(i).getHotelID(); // id
			int star = AHR.get(i).getHotelStar(); // star
			String locality = AHR.get(i).getLocality(); // locality
			String address = AHR.get(i).getAddress(); // address
			int sroom = AHR.get(i).getSingle(); // single room
			int droom = AHR.get(i).getDouble(); // double room
			int qroom = AHR.get(i).getQuad(); // quad room
			int price = countSumPrice(AHR.get(i)); // price
			String go = "Select"; // select
			Object[] data = { id, star, locality, address, sroom, droom, qroom, price, go };
			tablemodel.addRow(data);
		}
		return tablemodel;
	}
	
	public DefaultTableModel makeHotellist(ArrayList<AvailableHotelRoom> _AHR) {
		DefaultTableModel tablemodel = new DefaultTableModel(heading, 0);
		// get data
		for (int i = 0; i < _AHR.size(); i++) {
			int id = _AHR.get(i).getHotelID(); // id
			int star = _AHR.get(i).getHotelStar(); // star
			String locality = _AHR.get(i).getLocality(); // locality
			String address = _AHR.get(i).getAddress(); // address
			int sroom = _AHR.get(i).getSingle(); // single room
			int droom = _AHR.get(i).getDouble(); // double room
			int qroom = _AHR.get(i).getQuad(); // quad room
			int price = countSumPrice(_AHR.get(i)); // price
			String go = "Select"; // select
			Object[] data = { id, star, locality, address, sroom, droom, qroom, price, go };
			tablemodel.addRow(data);
		}
		return tablemodel;
	}
	
	/**
	 * This method counts the cost of the given AvailableHotelRoom.
	 * 
	 * @param x is the given room
	 * @return int the summation price 
	 */
	public static int countSumPrice(AvailableHotelRoom x) {
		return DatabaseUtil.HotelList[x.getHotelID()].getSingleRoomPrice() * x.getSingle() 
			 + DatabaseUtil.HotelList[x.getHotelID()].getDoubleRoomPrice() * x.getDouble()
		     + DatabaseUtil.HotelList[x.getHotelID()].getQuadRoomPrice() * x.getQuad();
	}
	
	/**
	 * This method sorts the given list AvailableHotelRoom by price.
	 * When option is 1, sort in descending order. When option is 2, sort in ascending order.
	 * 
	 * @param AHR is the given list
	 * @param op is the option
	 * @return ArrayList the sorted list
	 */
	public static ArrayList<AvailableHotelRoom> SortByPrice(int op) {
		ArrayList<AvailableHotelRoom> _AHR = (ArrayList<AvailableHotelRoom>) AHR.clone();
		Collections.sort(_AHR, new Comparator<AvailableHotelRoom>() {
			public int compare(AvailableHotelRoom a, AvailableHotelRoom b) {
				return (op == 1 ? (countSumPrice(a) - countSumPrice(b)) : (countSumPrice(b) - countSumPrice(a)));
			}
		});
		return _AHR;
	}
	
	/**
	 * This method selects the matched hotels and rooms from the list AvailableHotelRoom based on the given hotel's star.
	 * 
	 * @param AHR is the AvailableHotelRoom list
	 * @param Star is the given hotel's star
	 * @return ArrayList the new AvailableHotelRoom list
	 */
	public static ArrayList<AvailableHotelRoom> SearchByStar(int Star) {
		ArrayList<AvailableHotelRoom> nAHR = new ArrayList<AvailableHotelRoom>();
		for (AvailableHotelRoom ahr : AHR)
			if (ahr.getHotelStar() == Star)
				nAHR.add(ahr);
		return nAHR;
	}
	
	public static boolean checkHotel(String CID, String COD, int HotelID, int sn, int dn, int qn) {
		Date Now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		long start = CountDaysBetween(sdf.format(Now), CID);
		long end = CountDaysBetween(sdf.format(Now), COD);

		return CheckAllRooms(HotelID, start, end, sn, dn, qn);
	}
	
	/**
	 * This method checks if all the rooms in the hotel of the current order are available for booking.
	 * It calls the CheckRoomIsAvailable() to check the rooms one by one.
	 * 
	 * @param HotelID the current hotel
	 * @param start is the number of (start date - today)
	 * @param end is the number of (end date - today)
	 * @param sn is the demand number of single room
	 * @param dn is the demand number of double room
	 * @param qn is the demand number of quad room
	 * @return boolean true if all the rooms are available
	 */
	public static boolean CheckAllRooms(int HotelID, long start, long end, int sn, int dn, int qn) {
		Hotel hotel = DatabaseUtil.HotelList[HotelID];
		Room[] singleroom = hotel.getSingleRooms();
		Room[] doubleroom = hotel.getDoubleRooms();
		Room[] quadroom = hotel.getQuadRooms();

		if (sn > 0) {
			if (singleroom == null)
				return false;
			int ok = 0;
			for (Room sr : singleroom)
				if (CheckRoomIsAvailable(sr, start, end))
					ok++;
			if (ok < sn)
				return false;
		}
		if (dn > 0) {
			int ok = 0;
			for (Room dr : doubleroom)
				if (CheckRoomIsAvailable(dr, start, end))
					ok++;
			if (ok < dn)
				return false;
		}
		if (qn > 0) {
			int ok = 0;
			for (Room qr : quadroom)
				if (CheckRoomIsAvailable(qr, start, end))
					ok++;
			if (ok < qn)
				return false;
		}
		return true;
	}
	
	/**
	 * This method checks if the given room is available in the given period.
	 * 
	 * @param room the given room
	 * @param Start is the number of (start date - today)
	 * @param end is the number of (end date - today)
	 * @return boolean true if the room is available
	 */
	public static boolean CheckRoomIsAvailable(Room room, long Start, long end) {
		boolean[] DIO = room.getDateIsOccupied();
		for (int i = (int) Start; i < end; i++)
			if (DIO[i] == true) 
				return false;
		return true;
	}
	
	/**
	 * This methods counts the days between the given two dates. 
	 * If the second date is ahead of the first date, it returns negative numbers.
	 * 
	 * @param D1 is the first date
	 * @param D2 is the second date
	 * @return long the days between the two dates
	 */
	public static long CountDaysBetween(String D1, String D2) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		final LocalDate firstDate = LocalDate.parse(D1, formatter);
		final LocalDate secondDate = LocalDate.parse(D2, formatter);
		final long days = ChronoUnit.DAYS.between(firstDate, secondDate);
		// System.out.println("Days between: " + days);
		return days;
	}
}