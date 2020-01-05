import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

public class SearchController{

	public static Hotel[] HotelList;
	public SearchController() {
		HotelList = DatabaseUtil.HotelList;
	}
	
	/**
	 * This method counts the cost of the given AvailableHotelRoom.
	 * 
	 * @param x is the given room
	 * @return int the summation price 
	 */
	public static int CountSumPrice(AvailableHotelRoom x) {
		return HotelList[x.getHotelID()].getSingleRoomPrice() * x.getSingle() 
			 + HotelList[x.getHotelID()].getDoubleRoomPrice() * x.getDouble()
		     + HotelList[x.getHotelID()].getQuadRoomPrice() * x.getQuad();
	}
	public void UpdateHotelList() {
		HotelList = DatabaseUtil.HotelList;
	}
	/**
	 * This method runs the given Search operation.
	 * 
	 * @param CID is the check-in date
	 * @param COD is the check-out date
	 * @param p is the demand number of people 
	 * @param n is the demand number of rooms
	 * @return ArrayList the list of the available hotel rooms
	 */
	public static ArrayList<AvailableHotelRoom> SearchAvailableHotels(String CID, String COD, int p, int n, String LOC) {
		ArrayList<AvailableHotelRoom> AHR = new ArrayList<AvailableHotelRoom>();

		Date Now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		long start = countDaysBetween(sdf.format(Now), CID);
		long end = countDaysBetween(sdf.format(Now), COD);

		for (Hotel hotel : HotelList) {
			Room[] singleroom = hotel.getSingleRooms();
			Room[] doubleroom = hotel.getDoubleRooms();
			Room[] quadroom = hotel.getQuadRooms();

			int available_sr = 0;
			if (singleroom != null) 
				for (Room sr : singleroom)
					if (checkRoomIsAvailable(sr, start, end))
						available_sr++;
	
			int available_dr = 0;
			if (doubleroom != null) 
				for (Room dr : doubleroom)
					if (checkRoomIsAvailable(dr, start, end))
						available_dr++;
			
			int available_qr = 0;
			if (quadroom != null) {
				for (Room qr : quadroom)
					if (checkRoomIsAvailable(qr, start, end))
						available_qr++;
			}
			/*
			 * solve 1*x + 2*y + 4*z >= p x >= 0, y >= 0, z >= 0, x + y + z = n
			 */
			for (int x = 0; x <= Math.min(n, available_sr); x++)
				for (int y = 0; y <= Math.min(n, available_dr); y++)
					for (int z = 0; z <= Math.min(n, available_qr); z++)
						if (x + y + z == n && 1 * x + 2 * y + 4 * z >= p && hotel.getLocality().equals(LOC))
							AHR.add(new AvailableHotelRoom(hotel.getID(), hotel.getStar(), hotel.getLocality(),
									hotel.getAddress(), x, y, z));
		}
		return AHR;
	}
	
	/**
	 * This methods counts the days between the given two dates. 
	 * If the second date is ahead of the first date, it returns negative numbers.
	 * 
	 * @param D1 is the first date
	 * @param D2 is the second date
	 * @return long the days between the two dates
	 */
	public static long countDaysBetween(String D1, String D2) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		final LocalDate firstDate = LocalDate.parse(D1, formatter);
		final LocalDate secondDate = LocalDate.parse(D2, formatter);
		final long days = ChronoUnit.DAYS.between(firstDate, secondDate);
		// System.out.println("Days between: " + days);
		return days;
	}
	
	/**
	 * This method checks if the given room is available in the given period.
	 * 
	 * @param room the given room
	 * @param Start is the number of (start date - today)
	 * @param end is the number of (end date - today)
	 * @return boolean true if the room is available
	 */
	public static boolean checkRoomIsAvailable(Room room, long Start, long end) {
		boolean[] DIO = room.getDateIsOccupied();
		for (int i = (int) Start; i < end; i++)
			if (DIO[i] == true) 
				return false;
		return true;
	}
}