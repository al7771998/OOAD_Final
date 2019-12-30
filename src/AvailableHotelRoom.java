
/**
 * This is the class of the return type AvailableHotelRoom for GUI.
 * 
 * @author B06505017, B06505032, B06505054, B06902023
 * @version 1.0
 * @since 2019-05-31
 */
public class AvailableHotelRoom {
	private int HotelID;
	private int HotelStar;
	private int Single, Double, Quad;
	
	private String Locality, Address;

	/**
	 * Default constructor.
	 */
	public AvailableHotelRoom() {
		HotelID = 0;
		HotelStar = 0;
		Single = Double = Quad = 0;
	}
	/**
	 * 	Copy constructor.
	 */
	public AvailableHotelRoom(int _HotelID, int _HotelStar, String _Locality, String _Address,int _Single, int _Double, int _Quad) {
		HotelID = _HotelID;
		HotelStar = _HotelStar;
		Locality = _Locality;
		Address = _Address;
		Single = _Single;
		Double = _Double;
		Quad = _Quad;
	}
	public int getHotelID() {
		return HotelID;
	}
	public int getHotelStar() {
		return HotelStar;
	}
	public String getLocality() {
		return Locality;
	}
	public String getAddress() {
		return Address;
	}
	public int getSingle() {
		return Single;
	}
	public int getDouble() {
		return Double;
	}
	public int getQuad() {
		return Quad;
	}
	public void setSingle(int x) {
		Single = x;
	}
	public void setDouble(int x) {
		Double = x;
	}
	public void setQuad(int x) {
		Quad = x;
	}
}