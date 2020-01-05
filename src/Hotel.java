import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.google.gson.annotations.SerializedName;
/*www*/
/*
 * This is the class of the Hotel read from the HotelList.json.
 */
public class Hotel {
	/*@SerializedName("HotelID")
	private int ID;
	@SerializedName("HotelStar")
	private int Star;
	private String Locality;
	@SerializedName("Street-Address")
	private String Address;
	@SerializedName("Rooms")
	private RoomType[] RoomTypes;*/
	private int ID;
	private int Star;
	private String Locality;
	private String Address;
	private RoomType[] RoomTypes;
	
	private int SingleRoomPrice;
	private int DoubleRoomPrice;
	private int QuadRoomPrice;
	private Room[] SingleRooms, DoubleRooms, QuadRooms;
	/*
	 * This method initialize the Hotel class.
	 */
	//public void init(int _ID, int _Star, String _Locality, String _Address, int _SingleRoom, int _SinglePrice, int _DoubleRoom, int _DoublePrice, int _QuadRoom, int _QuadPrice) {
	Hotel (int _ID, int _Star, String _Locality, String _Address, int _SingleRoom, int _SinglePrice, int _DoubleRoom, int _DoublePrice, int _QuadRoom, int _QuadPrice) {
		ID = _ID;
		Star = _Star;
		Locality = _Locality;
		Address = _Address;
		
		SingleRooms = new Room[_SingleRoom];
		DoubleRooms = new Room[_DoubleRoom];
		QuadRooms = new Room[_QuadRoom];
		for (int i = 0; i < SingleRooms.length; i++)
			SingleRooms[i] = new Room();
		for (int i = 0; i < DoubleRooms.length; i++) 
			DoubleRooms[i] = new Room();
		for (int i = 0; i < QuadRooms.length; i++) 
			QuadRooms[i] = new Room();
		SingleRoomPrice = _SinglePrice;
		DoubleRoomPrice = _DoublePrice;
		QuadRoomPrice = _QuadPrice;
		
		/*SingleRooms = new Room[RoomTypes[0].getNumber()];
		DoubleRooms = new Room[RoomTypes[1].getNumber()];
		QuadRooms = new Room[RoomTypes[2].getNumber()];
		for (int i = 0; i < SingleRooms.length; i++)
			SingleRooms[i] = new Room();
		for (int i = 0; i < DoubleRooms.length; i++) 
			DoubleRooms[i] = new Room();
		for (int i = 0; i < QuadRooms.length; i++) 
			QuadRooms[i] = new Room();
		SingleRoomPrice = RoomTypes[0].getPrice(); 
		DoubleRoomPrice = RoomTypes[1].getPrice();
		QuadRoomPrice = RoomTypes[2].getPrice();*/
	}
	public int getID() {
		return ID;
	}
	public int getStar() {
		return Star;
	}
	public String getLocality() {
		return Locality;
	}
	public String getAddress() {
		return Address;
	}
	Room[] getSingleRooms() {
		/*Room[] nSingleRooms = new Room[SingleRooms.length];
		for (int i = 0; i < nSingleRooms.length; i++) 
			nSingleRooms[i] = new Room(SingleRooms[i]);
		return nSingleRooms;*/
		return SingleRooms;
	}
	Room[] getDoubleRooms() {
		/*Room[] nDoubleRooms = new Room[DoubleRooms.length];
		for (int i = 0; i < nDoubleRooms.length; i++) 
			nDoubleRooms[i] = new Room(DoubleRooms[i]);
		return nDoubleRooms;*/
		return DoubleRooms;
	}
	Room[] getQuadRooms() {
		/*Room[] nQuadRooms = new Room[QuadRooms.length];
		for (int i = 0; i < nQuadRooms.length; i++) 
			nQuadRooms[i] = new Room(QuadRooms[i]);
		return nQuadRooms;*/
		return QuadRooms;
	}
	public int getSingleRoomPrice() {
		return SingleRoomPrice;
	}
	public int getDoubleRoomPrice() {
		return DoubleRoomPrice;
	}
	public int getQuadRoomPrice() {
		return QuadRoomPrice;
	}
	public String toString() {
		return ID + " " + Star + " " + Locality + " " + Address + " " 
				+ RoomTypes[0] + " " + RoomTypes[1] + " " + RoomTypes[2];
	}
	
	public void setRoomState(String T, int n, int d) {
		if (T.equals("S")) {
			SingleRooms[n].setDateIsOccupied(d);
		}
		else if (T.equals("D")) {
			DoubleRooms[n].setDateIsOccupied(d);
		}
		else if (T.equals("Q")) {
			QuadRooms[n].setDateIsOccupied(d);
		}
	}
}
