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
	
	private Order[] orders;
	
	public ManageController() {
		orders = DatabaseUtil.getOrders();
	}
	
	public void UpdateController() {
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
		for (int i = 0; i < orders.length; i++) {
			int id = orders[i].getID();
			String userId = orders[i].getUserID();
			int hotelId = orders[i].getHotelID();
			int reservation = orders[i].getReservations();
			String email = orders[i].getEmail();
			String contactName = orders[i].getContactName();
			String contactPhone = orders[i].getContactPhone();
			String checkInDate = orders[i].getCheckInDate();
			String checkOutDate = orders[i].getCheckOutDate();
			int sn = orders[i].getSnum() != null? orders[i].getSnum().size():0;
			int dn = orders[i].getDnum() != null? orders[i].getDnum().size():0;
			int qn = orders[i].getQnum() != null? orders[i].getQnum().size():0;
			int sumPrice = orders[i].getSumPrice();
			//String go = "Select"; // select
			Object[] data = { id, userId, hotelId, reservation, email, contactName, contactPhone, 
					checkInDate, checkOutDate, sn, dn ,qn, sumPrice};
			tablemodel.addRow(data);
		}
		return tablemodel;
	}
}