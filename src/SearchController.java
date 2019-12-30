



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
}