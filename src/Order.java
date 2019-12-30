import java.util.ArrayList;

/**
 * This is the class of Order.
 * 
 * @author B06505017, B06505032, B06505054, B06902023
 * @version 1.0
 * @since 2019-05-31
 */
public class Order {
	private int ID;
	private int HotelID;
	private String UserID;
	private String CheckInDate;
	private String CheckOutDate;
	private ArrayList<Integer> Snum, Dnum, Qnum;
	private int SumPrice;
	/**
	 * Default constructor.
	 */
	Order() {
		ID = -1;
		UserID = "";
		HotelID = 0;
		CheckInDate = "";
		CheckOutDate = "";
		SumPrice = 0;
		Snum = Dnum = Qnum = null;
	}
	/**
	 * Copy constructor.
	 */
	Order(int _ID, String _UserID, int _HotelID, String _CheckInDate, String _CheckOutDate, ArrayList<Integer> _Snum, ArrayList<Integer> _Dnum, ArrayList<Integer> _Qnum) {
		ID = _ID;
		UserID = _UserID;
		HotelID = _HotelID;
		CheckInDate = _CheckInDate;
		CheckOutDate = _CheckOutDate;
		Snum = new ArrayList<Integer> (); Snum.addAll(_Snum);
		Dnum = new ArrayList<Integer> (); Dnum.addAll(_Dnum);
		Qnum = new ArrayList<Integer> (); Qnum.addAll(_Qnum);
		SumPrice = main.HotelList[HotelID].getSingleRoomPrice() * Snum.size()
				+ main.HotelList[HotelID].getDoubleRoomPrice() * Dnum.size() 
				+ main.HotelList[HotelID].getQuadRoomPrice() * Qnum.size();
	}
	/**
	 * Another copy constructor.
	 */
	Order(Order _Order) {
		ID = _Order.ID;
		UserID = _Order.UserID;
		HotelID = _Order.HotelID;
		CheckInDate = _Order.CheckInDate;
		CheckOutDate = _Order.CheckOutDate;
		Snum = new ArrayList<Integer> (); Snum.addAll(_Order.Snum);
		Dnum = new ArrayList<Integer> (); Dnum.addAll(_Order.Dnum);
		Qnum = new ArrayList<Integer> (); Qnum.addAll(_Order.Qnum);
		SumPrice = main.HotelList[HotelID].getSingleRoomPrice() * Snum.size()
				+ main.HotelList[HotelID].getDoubleRoomPrice() * Dnum.size() 
				+ main.HotelList[HotelID].getQuadRoomPrice() * Qnum.size();
	}
	int getID() {
		return ID;
	}
	String getUserID() {
		return UserID;
	}
	int getHotelID() {
		return HotelID;
	}
	String getCheckInDate() {
		return CheckInDate;
	}
	String getCheckOutDate() {
		return CheckOutDate;
	}
	ArrayList<Integer> getSnum() {
		return Snum;
	}
	ArrayList<Integer> getDnum() {
		return Dnum;
	}
	ArrayList<Integer> getQnum() {
		return Qnum;
	}
	int getSumPrice() {
		return SumPrice;
	}
}
