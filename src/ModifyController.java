import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

public class ModifyController{
	
	public ModifyController() {
		
	}
	
	public void cancel(int orderID) {
		DatabaseUtil.deleteOrder(orderID);
	}
	

	public Order modifyHotel(int orderID,int hotelID,int nsn,int ndn,int nqn,String nCID,String nCOD) {
		Date Now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		long nStart = CountDaysBetween(sdf.format(Now), nCID);
		long nEnd = CountDaysBetween(sdf.format(Now), nCOD);
		
		Order oldOrder = DatabaseUtil.getOrderByOrderID(orderID);
		Hotel hotel = DatabaseUtil.HotelList[hotelID];
		Room[] singleroom = hotel.getSingleRooms();
		Room[] doubleroom = hotel.getDoubleRooms();
		Room[] quadroom = hotel.getQuadRooms();

		long start = CountDaysBetween(sdf.format(Now), oldOrder.getCheckInDate());
		long end = CountDaysBetween(sdf.format(Now), oldOrder.getCheckOutDate());
		
		ArrayList<Integer> Snum = oldOrder.getSnum();
		ArrayList<Integer> Dnum = oldOrder.getDnum();
		ArrayList<Integer> Qnum = oldOrder.getQnum();
		
		//update Date
		if (Snum.size() > 0) {
			for (int i = 0; i < Snum.size(); i++) 
				for (int t = (int)start; t < end; t++) 
					if (t < nStart || t >= nEnd) 
						singleroom[Snum.get(i)].setDateIsNotOccupied(t);
		}
		if (Dnum.size() > 0) {
			for (int i = 0; i < Dnum.size(); i++) 
				for (int t = (int)start; t < end; t++) 
					if (t < nStart || t >= nEnd) 
						doubleroom[Dnum.get(i)].setDateIsNotOccupied(t);
		}
		if (Qnum.size() > 0) {
			for (int i = 0; i < Qnum.size(); i++) 
				for (int t = (int)start; t < end; t++) 
					if (t < nStart || t >= nEnd) 
						quadroom[Qnum.get(i)].setDateIsNotOccupied(t);
		}
		
		//update room num
		int sn = oldOrder.getSnum().size();
		if (nsn < sn) {
			for (int i = sn-1; i >= nsn; i--) {
				for (int t = (int)start; t < end; t++)
					singleroom[Snum.get(i)].setDateIsNotOccupied(t);
				Snum.remove(i);
				Snum.trimToSize();
			}
		} 
		int dn = oldOrder.getDnum().size();
		if (ndn < dn) {
			for (int i = dn-1; i >= ndn; i--) {
				for (int t = (int)start; t < end; t++) 
					doubleroom[Dnum.get(i)].setDateIsNotOccupied(t);
				Dnum.remove(i);
				Dnum.trimToSize();
			}
		} 
		int qn = oldOrder.getQnum().size();
		if (nqn < qn) {
			for (int i = qn-1; i >= nqn; i--) {
				for (int t = (int)start; t < end; t++) 
					quadroom[Qnum.get(i)].setDateIsNotOccupied(t);
				Qnum.remove(i);
				Qnum.trimToSize();
			}
		}
		
		//insert into DB
		Order nOrder = new Order(orderID, DatabaseUtil.user.getUserID(), hotelID, oldOrder.getReservations(), oldOrder.getEmail(), oldOrder.getContactName(),  oldOrder.getContactPhone(), nCID, nCOD, Snum, Dnum, Qnum);
		DatabaseUtil.insertOrder(nOrder);
		return nOrder;
	}
	
	public static long CountDaysBetween(String D1, String D2) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		final LocalDate firstDate = LocalDate.parse(D1, formatter);
		final LocalDate secondDate = LocalDate.parse(D2, formatter);
		final long days = ChronoUnit.DAYS.between(firstDate, secondDate);
		// System.out.println("Days between: " + days);
		return days;
	}
	
	/**
	 * This method checks whether the revised date is valid.
	 * Valid means that the revised date period must be lagrget thean 0, and smaller than the original date period.
	 * 
	 * @param OrderID is the given order's ID
	 * @param nCID is the revised check-in date
	 * @param nCOD is the revised check-out date
	 * @return boolean true if the revised is valid
	 */
	public static boolean CheckDateforReviseDate(int OrderID, String nCID, String nCOD) {
		Order order = DatabaseUtil.getOrderByOrderID(OrderID);
		long Days = CountDaysBetween(order.getCheckInDate(), order.getCheckOutDate());
		long D = CountDaysBetween(nCID, nCOD);
		
		return D > 0 && D < Days && CountDaysBetween(order.getCheckInDate(), nCID) >= 0;
	}
	
	public int getSumPrice(int hotelID,int sn,int dn,int qn) {
		return DatabaseUtil.HotelList[hotelID].getSingleRoomPrice() * sn
		+ DatabaseUtil.HotelList[hotelID].getDoubleRoomPrice() * dn
		+ DatabaseUtil.HotelList[hotelID].getQuadRoomPrice() * qn;
	}
}