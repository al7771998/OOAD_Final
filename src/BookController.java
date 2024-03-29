import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

public class BookController {
	private String hotelName;
	private String startTime;
	private String endTime;
	private int reservations;
	private String contactName;
	private String contactPhone;
	private int singleRoomNum, doubleRoomNum, quadRoomNum;
	private String emailAddress;
	
	/**
	 * Default constructor
	 */
	public BookController() {
		hotelName = "";
		reservations = 0;
		contactName = "";
		contactPhone = "";
		singleRoomNum = 0;
		doubleRoomNum = 0;
		quadRoomNum = 0;
	}
	/**
	 * Copy constructor
	 */
	public BookController(String _hotelName, String _startTime, String _endTime, int _reservations, String _contactName, 
			String _contactPhone, String _email, int _single_num, int _double_num, int _quad_num) {
		hotelName = _hotelName;
		startTime = _startTime;
		endTime = _endTime;
		reservations = _reservations;
		contactName = _contactName;
		contactPhone = _contactPhone;
		singleRoomNum = _single_num;
		doubleRoomNum = _double_num;
		quadRoomNum = _quad_num;
		emailAddress = _email;
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
	
	public static boolean CheckRoomIsAvailable(Room room, long Start, long end) {
		boolean[] DIO = room.getDateIsOccupied();
		for (int i = (int) Start; i < end; i++)
			if (DIO[i] == true) 
				return false;
		return true;
	}
	
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
	
	public static long CountDaysBetween(String D1, String D2) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		final LocalDate firstDate = LocalDate.parse(D1, formatter);
		final LocalDate secondDate = LocalDate.parse(D2, formatter);
		final long days = ChronoUnit.DAYS.between(firstDate, secondDate);
		// System.out.println("Days between: " + days);
		return days;
	}
	
	public static ArrayList<ArrayList<Integer>> Reserve(int HotelID, String UserID, long start, long end, int sn, int dn, int qn) {
		Hotel hotel = DatabaseUtil.HotelList[HotelID];
		Room[] singleroom = hotel.getSingleRooms();
		Room[] doubleroom = hotel.getDoubleRooms();
		Room[] quadroom = hotel.getQuadRooms();
		
		ArrayList<ArrayList<Integer>> RoomNumbers = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < 3; i++)
			RoomNumbers.add(new ArrayList<Integer> ());
		
		if (sn > 0) { // single room
			int booked = 0;
			for (int i = 0; i < singleroom.length; i++) {
				if (CheckRoomIsAvailable(singleroom[i], start, end)) {
					for (int t = (int)start; t < end; t++)
						singleroom[i].setDateIsOccupied(t);
					RoomNumbers.get(0).add(i);
					booked++;
				}
				if (booked == sn)
					break;
			}
		}
		if (dn > 0) { // double room
			int booked = 0;
			for (int i = 0; i < doubleroom.length; i++) {
				if (CheckRoomIsAvailable(doubleroom[i], start, end)) {
					for (int t = (int)start; t < end; t++)
						doubleroom[i].setDateIsOccupied(t);
					RoomNumbers.get(1).add(i);
					booked++;
				}
				if (booked == dn)
					break;
			}
		}
		if (qn > 0) { // quad room
			int booked = 0;
			for (int i = 0; i < quadroom.length; i++) {
				if (CheckRoomIsAvailable(quadroom[i], start, end)) {
					for (int t = (int) start; t < end; t++)
						quadroom[i].setDateIsOccupied(t);
					RoomNumbers.get(2).add(i);
					booked++;
				}
				if (booked == qn)
					break;
			}
		}
		return RoomNumbers;
	}
	
	public Order BookHotel() {
		Date Now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		long start = CountDaysBetween(sdf.format(Now), startTime);
		long end = CountDaysBetween(sdf.format(Now), endTime);

		if (CheckAllRooms(Integer.valueOf(hotelName), start, end, singleRoomNum, doubleRoomNum, quadRoomNum)) {
			//等等都搬去DatabaseUtil
			/*String cmd = "SELECT * FROM Hotel WHERE HotelID=\'" + hotelName + "\';";
			String roominfo;
			try {
				results = stmt.executeQuery(cmd);
				if (results.next()) {
					roominfo = result.getString(startTime.replace("/","_"));
				} else {
					System.out.println("Changing Hotel Database Error!!");
					return false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			String[] s_d_q = roominfo.split(":");
			int current_sn = Integer.valueOf(s_d_q[0]) - singleRoomNum, 
				current_dn = Integer.valueOf(s_d_q[1]) - doubleRoomNum,
				current_qn = Integer.valueOf(s_d_q[2]) - quadRoomNum;
			cmd = "UPDATE Hotel SET "
				+ startTime.replace("/","_") //YYYY_MM_DD single:double:quad
				+ " = "
				+ "\'" + Integer.toString(current_sn) + ":" + Integer.toString(current_dn)
				+ ":" + Integer.toString(current_qn) + "\';"
				+"WHERE HotelID=\'" + hotelName + "\';";
			try {
				stmt.execute(cmd);
			} catch (SQLException e) {
				e.getStackTrace();
				return false;
			}
			return true;*/
			//這些保留
			
			ArrayList<ArrayList<Integer>> re = Reserve(Integer.valueOf(hotelName), contactName, start, end, singleRoomNum, doubleRoomNum, quadRoomNum);
			Order nOrder = new Order(DatabaseUtil.getNewOrderID(), DatabaseUtil.user.getUserID(), Integer.parseInt(hotelName), reservations, emailAddress, contactName, contactPhone, startTime, endTime, re.get(0), re.get(1), re.get(2));
			DatabaseUtil.insertOrder(nOrder);
			return nOrder;
		}
		return null;
	}

}