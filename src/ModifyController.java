import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class ModifyController{
	
	public ModifyController() {
		
	}
	
	public void cancel(int orderID) {
		DatabaseUtil.deleteOrder(orderID);
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