

public class BrowseController{
	private String UserID;
	private Order[] OrderList;
	
	public BrowseController(){
		UserID = DatabaseUtil.user.getUserID();
	}
	
	public Order[] getOrderList() {
		return DatabaseUtil.getOrderByUserID(UserID);
	}
}
