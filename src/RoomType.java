import com.google.gson.annotations.SerializedName;

/**
 * This is the class of the type of the rooms read from the HotelList.json.
 * 
 * @author B06505017, B06505032, B06505054, B06902023
 * @version 1.0
 * @since 2019-05-31
 */
public class RoomType {
	@SerializedName("RoomType")
	private String Type;
	@SerializedName("RoomPrice")
	private int Price;
	@SerializedName("Number")
	private int Number;
	/*
	 * Default constructor.
	 */
	RoomType() {
		Type = "Single";
		Price = 0;
		Number = 0;
	}
	/*
	 * Copy constructor.
	 */
	RoomType(String _Type, int _Price, int _Number) {
		Type = _Type;
		Price = _Price;
		Number = _Number;
	}	
	/*
	 * Another copy constructor.
	 */
	RoomType(RoomType _RoomType) {
		Type = _RoomType.Type;
		Price = _RoomType.Price;
		Number = _RoomType.Number;
	}
	public String getType() {
		return Type;
	}
	public int getPrice() {
		return Price;
	}
	public int getNumber() {
		return Number;
	}
	public String toString() {
		return "[" + Type + " " + Price + " " + Number + "]";
	}
}