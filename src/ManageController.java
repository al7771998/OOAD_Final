import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ManageController{
	
	String[] heading = new String[] { "ID","UserID", "HotelID",
			"Reservations",
			"Email",
			"ContactName",
			"ContactPhone",
			"CheckInDate",
			"CheckOutDate",
			"SingleRoom",
			"DoubleRoom",
			"QuadRoom",
			"SumPrice" };
	
	private ArrayList<Order> orders;
	
	public ManageController() {
		orders = DatabaseUtil.getOrders();
	}
	
	/**
	 * set up a default table model for the hotel list
	 * 
	 * @param _AHR an array list which store all the hotel list
	 * @return returns a default table model 
	 */
	public DefaultTableModel makeOrderList() {
		DefaultTableModel tablemodel = new DefaultTableModel(heading, 0);
		if(orders == null)
			return tablemodel;
		// get data
		for (int i = 0; i < orders.size(); i++) {
			int id = orders.get(i).getID();
			String userId = orders.get(i).getUserID();
			int hotelId = orders.get(i).getHotelID();
			int reservation = orders.get(i).getReservations();
			String email = orders.get(i).getEmail();
			String contactName = orders.get(i).getContactName();
			String contactPhone = orders.get(i).getContactPhone();
			String checkInDate = orders.get(i).getCheckInDate();
			String checkOutDate = orders.get(i).getCheckOutDate();
			int sn = orders.get(i).getSnum() != null? orders.get(i).getSnum().size():0;
			int dn = orders.get(i).getDnum() != null? orders.get(i).getSnum().size():0;
			int qn = orders.get(i).getQnum() != null? orders.get(i).getSnum().size():0;
			int sumPrice = orders.get(i).getSumPrice();
			//String go = "Select"; // select
			Object[] data = { id, userId, hotelId, reservation, email, contactName, contactPhone, 
					checkInDate, checkOutDate, sn, dn ,qn, sumPrice};
			tablemodel.addRow(data);
		}
		return tablemodel;
	}
}