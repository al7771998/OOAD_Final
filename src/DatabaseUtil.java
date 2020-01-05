
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.simple.*;
import org.json.simple.parser.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * <h1>This is the class of database<\h1>
 * 
 * @author momo, tim, catherine, sopia
 * @version 1.0
 * @since 2019-05-31
 */
public class DatabaseUtil {
	// MySQL
	static Connection connect = null;
	static Statement stmt = null;
	static ResultSet results = null;
	
	public static User user;

	/**
	 * build connection to MySQL database(user:root, password:root)
	 * 
	 */
	public static void buildConnection() {
		// build connection to MySQL
		try {
			System.out.print("Connecting to MySQL...");

			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/?user=user&password=123");
			stmt = connect.createStatement();
			stmt.execute("USE `hotelList`;");
			System.out.println("finish!");
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	/**
	 * read SQL Script to build tables
	 * 
	 */
	public static void initDatabase() {
		// build database
		BufferedReader fin = null;
		try {
			System.out.print("Building database...");

			fin = new BufferedReader(new FileReader("backup.sql"));
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = fin.readLine()) != null) {
				sb.append(line + "\n");
			}
			fin.close();

			String[] cmds = sb.toString().split(";");
			for (int i = 0; i < cmds.length; i++) {
				if (!cmds[i].trim().equals("")) {
					stmt.execute(cmds[i]);
				}
			}
			
			/*SimpleDateFormat sdf=new SimpleDateFormat("yyyy_MM_dd");
			String str="2020_01_05";
			Date dt=sdf.parse(str);
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(dt);
			
			int i = 0;
			while (i <= 365) {
				rightNow.add(Calendar.DATE,1);
				Date dt1=rightNow.getTime();
				String reStr = sdf.format(dt1);
				String cmd = "ALTER TABLE Rooms ADD "
						+ reStr
						+ " VARCHAR(20);";
				stmt.execute(cmd);
				i++;
			}*/
			System.out.println("finish building table!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * insert a user to table 'Users' by given User object
	 * 
	 * @param newUser new User to be insert
	 * @return True if insert successfully, False instead.
	 */
	public static boolean insertUser(User newUser) {
		String cmd = "INSERT INTO Users"
						+ "(UID, password, isManager)" 
						+ "VALUES"
						+ "(\'" + newUser.getUserID() + "\', \'" + newUser.getPassword() + "\', "+ newUser.isManager +");";
		System.out.println(cmd);
		try {
			stmt.execute(cmd);
		} catch (SQLException e) {
			e.getStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean insertManager(User newUser) {
		String cmd = "INSERT INTO Users"
						+ "(UID, password, isManager)" 
						+ "VALUES"
						+ "(\'" + newUser.getUserID() + "\', \'" + newUser.getPassword() + "\', "+ newUser.isManager +");";
		try {
			stmt.execute(cmd);
		} catch (SQLException e) {
			e.getStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * get the certain User by given UserID
	 * @param UID User's ID
	 * @return User with UID
	 */
	public static User getUser(String UID) {
		String cmd = "SELECT * FROM Users WHERE UID=\'" + UID + "\';";
		try {
			results = stmt.executeQuery(cmd);
			if (results.next()) {
				return new User(results.getString("UID"), results.getString("password"), results.getBoolean("isManager"));
			} else {
				System.out.println("No such User!!");
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return new User();
	}
	

	/**
	 * insert a Order to table 'Orders' by given Order object
	 * @param newOrder The order to be insert
	 * @return True if insert successfully, False instead
	 */
	public static boolean insertOrder(Order newOrder) {
		String SR = "", DR = "", QR = "";
		for (Integer num : newOrder.getSnum())
			SR = SR + num.toString() + ":";
		for (Integer num : newOrder.getDnum())
			DR = DR + num.toString() + ":";
		for (Integer num : newOrder.getQnum())
			QR = QR + num.toString() + ":";
		
		String cmd = "INSERT INTO Orders"
						+ "(OrderID, UID, HotelID, Reservations, Email, ContactName, ContactPhone, SingleRoom, DoubleRoom, QuadRoom, CheckIn, CheckOut, SumPrice)" 
						+ "VALUES("
						+ newOrder.getID() + ", " 
						+ "\'" + newOrder.getUserID() + "\'" + ", "
						+ newOrder.getHotelID() + ", "
						+ newOrder.getReservations() + ", "
						+ "\'" + newOrder.getEmail() + "\'" + ", "
						+ "\'" + newOrder.getContactName() + "\'" + ", "
						+ "\'" + newOrder.getContactPhone() + "\'" + ", "
						+ "\'" + SR + "\'" + ", "
						+ "\'" + DR + "\'" + ", "
						+ "\'" + QR + "\'" + ", "
						+ "\'" + newOrder.getCheckInDate().replace('/', '-') + "\'" + ", "
						+ "\'" + newOrder.getCheckOutDate().replace('/', '-') + "\'"+ ", "
						+ newOrder.getSumPrice()
						+ ");";
		try {
			if (getOrderByOrderID(newOrder.getID()) != null) {
				stmt.execute("DELETE FROM Orders WHERE OrderID=" + newOrder.getID() + ";");
			}
			stmt.execute(cmd);
		} catch (SQLException e) {
			e.getStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * To check if String s is an integer
	 * @param s String to be checked
	 * @return True if s is a integer, False instead
	 */
	private static boolean isInt(String s) {
	    try {
	        Integer.parseInt(s);
	        return true;
	    } catch (NumberFormatException ex) {
	        return false;
	    }
	}
	
	//get all orders
	public static Order[] getOrders() {
		/*String cmd = "SELECT * FROM Orders;";
		ArrayList<Order> result = new ArrayList<Order>();
		try {
			results = stmt.executeQuery(cmd);	
			if (results.next()) {
				Order order = new Order();
				result.add(order);
				ArrayList<Integer> SRoom = new ArrayList<Integer>();
				ArrayList<Integer> DRoom = new ArrayList<Integer>();
				ArrayList<Integer> QRoom = new ArrayList<Integer>();
				String SR = results.getString("SingleRoom"), DR = results.getString("DoubleRoom"), QR = results.getString("QuadRoom");
				for (String num : SR.split(":")) {
					if (!isInt(num)) break;
					SRoom.add(Integer.valueOf(num));
				}
				for (String num : DR.split(":")) {
					if (!isInt(num)) break;
					DRoom.add(Integer.valueOf(num));
				}
				for (String num : QR.split(":")) {
					if (!isInt(num)) break;
					QRoom.add(Integer.valueOf(num));
				}
				return new Order(results.getInt("OrderID"), 
								 results.getString("UID"), 
								 results.getInt("HotelID"), 
								 results.getInt("Reservations"),
								 results.getString("Email"),
								 results.getString("ContactName"),
								 results.getString("ContactPhone"),
								 results.getDate("CheckIn").toString().replace('-', '/'),
								 results.getDate("CheckOut").toString().replace('-', '/'),
								 SRoom, 
								 DRoom, 
								 QRoom);
			} else {
				System.out.println("No such Order!!");
				return null;
			}
			while(results.next()) {
				//TODO add to arrayList
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;*/
		String cmd = "SELECT * FROM Orders;";
		
		try {
			int len;
			results = stmt.executeQuery(cmd);
			results.last();
			len = results.getRow();
			results.first();
			
			if (len == 0) {
				System.out.println("No such Order!!");
				return null;
			}
			
			Order[] retList = new Order[len];
			int index = 0;
			do {
				ArrayList<Integer> SRoom = new ArrayList<Integer>();
				ArrayList<Integer> DRoom = new ArrayList<Integer>();
				ArrayList<Integer> QRoom = new ArrayList<Integer>();
				String SR = results.getString("SingleRoom"), DR = results.getString("DoubleRoom"), QR = results.getString("QuadRoom");
				for (String num : SR.split(":")) {
					if (num.equals("")) 
						break;
					SRoom.add(Integer.valueOf(num));
				}
				for (String num : DR.split(":")) {
					if (num.equals("")) break;
					DRoom.add(Integer.valueOf(num));
				}
				for (String num : QR.split(":")) {
					if (num.equals("")) break;
					QRoom.add(Integer.valueOf(num));
				}
				retList[index++] = new Order(results.getInt("OrderID"), 
											 results.getString("UID"), 
											 results.getInt("HotelID"), 
											 results.getInt("Reservations"),
											 results.getString("Email"),
											 results.getString("ContactName"),
											 results.getString("ContactPhone"),
											 results.getDate("CheckIn").toString().replace('-', '/'),
											 results.getDate("CheckOut").toString().replace('-', '/'),
											 SRoom, 
											 DRoom, 
											 QRoom);
			} while(results.next());
			
			return retList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * get the certain OrderID by given OrderID
	 * @param OrderID order ID
	 * @return Order with OrderID
	 */
	public static Order getOrderByOrderID(int OrderID) {
		String cmd = "SELECT * FROM Orders WHERE OrderID=" + OrderID + ";";
		try {
			results = stmt.executeQuery(cmd);
			
			if (results.next()) {
				ArrayList<Integer> SRoom = new ArrayList<Integer>();
				ArrayList<Integer> DRoom = new ArrayList<Integer>();
				ArrayList<Integer> QRoom = new ArrayList<Integer>();
				String SR = results.getString("SingleRoom"), DR = results.getString("DoubleRoom"), QR = results.getString("QuadRoom");
				for (String num : SR.split(":")) {
					if (!isInt(num)) break;
					SRoom.add(Integer.valueOf(num));
				}
				for (String num : DR.split(":")) {
					if (!isInt(num)) break;
					DRoom.add(Integer.valueOf(num));
				}
				for (String num : QR.split(":")) {
					if (!isInt(num)) break;
					QRoom.add(Integer.valueOf(num));
				}
				return new Order(results.getInt("OrderID"), 
								 results.getString("UID"), 
								 results.getInt("HotelID"), 
								 results.getInt("Reservations"),
								 results.getString("Email"),
								 results.getString("ContactName"),
								 results.getString("ContactPhone"),
								 results.getDate("CheckIn").toString().replace('-', '/'),
								 results.getDate("CheckOut").toString().replace('-', '/'),
								 SRoom, 
								 DRoom, 
								 QRoom);
			} else {
				System.out.println("No such Order!!");
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * get the Order list by given UserID
	 * @param UID user ID
	 * @return Order list with UID
	 */
	public static Order[] getOrderByUserID(String UID) {
		String cmd = "SELECT * FROM Orders WHERE UID=\'" + UID + "\';";
		
		try {
			int len;
			results = stmt.executeQuery(cmd);
			results.last();
			len = results.getRow();
			results.first();
			
			if (len == 0) {
				System.out.println("No such Order!!");
				return null;
			}
			
			Order[] retList = new Order[len];
			int index = 0;
			do {
				ArrayList<Integer> SRoom = new ArrayList<Integer>();
				ArrayList<Integer> DRoom = new ArrayList<Integer>();
				ArrayList<Integer> QRoom = new ArrayList<Integer>();
				String SR = results.getString("SingleRoom"), DR = results.getString("DoubleRoom"), QR = results.getString("QuadRoom");
				for (String num : SR.split(":")) {
					if (num.equals("")) 
						break;
					SRoom.add(Integer.valueOf(num));
				}
				for (String num : DR.split(":")) {
					if (num.equals("")) break;
					DRoom.add(Integer.valueOf(num));
				}
				for (String num : QR.split(":")) {
					if (num.equals("")) break;
					QRoom.add(Integer.valueOf(num));
				}
				retList[index++] = new Order(results.getInt("OrderID"), 
											 results.getString("UID"), 
											 results.getInt("HotelID"), 
											 results.getInt("Reservations"),
											 results.getString("Email"),
											 results.getString("ContactName"),
											 results.getString("ContactPhone"),
											 results.getDate("CheckIn").toString().replace('-', '/'),
											 results.getDate("CheckOut").toString().replace('-', '/'),
											 SRoom, 
											 DRoom, 
											 QRoom);
			} while(results.next());
			
			return retList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * Get the certain Order list by given HotelID
	 * @param HotelID hotel ID
	 * @return Order list with HotelID
	 */
	public static Order[] getOrderByHotelID(int HotelID) {
		String cmd = "SELECT * FROM Orders WHERE HotelID=" + HotelID + ";";
		try {
			int len;
			results = stmt.executeQuery(cmd);
			results.last();
			len = results.getRow();
			results.first();
			
			if (len == 0) {
				System.out.println("No such Order!!");
				return null;
			}
			
			Order[] retList = new Order[len];
			int index = 0;
			do {
				ArrayList<Integer> SRoom = new ArrayList<Integer>();
				ArrayList<Integer> DRoom = new ArrayList<Integer>();
				ArrayList<Integer> QRoom = new ArrayList<Integer>();
				String SR = results.getString("SingleRoom"), DR = results.getString("DoubleRoom"), QR = results.getString("QuadRoom");
				for (String num : SR.split(":")) {
					if (num == "") break;
					SRoom.add(Integer.valueOf(num));
				}
				for (String num : DR.split(":")) {
					if (num == "") break;
					DRoom.add(Integer.valueOf(num));
				}
				for (String num : QR.split(":")) {
					if (num == "") break;
					QRoom.add(Integer.valueOf(num));
				}
				retList[index++] = new Order(results.getInt("OrderID"), 
											 results.getString("UID"), 
											 results.getInt("HotelID"), 
											 results.getInt("Reservations"),
											 results.getString("Email"),
											 results.getString("ContactName"),
											 results.getString("ContactPhone"),
											 results.getDate("CheckIn").toString().replace('-', '/'),
											 results.getDate("CheckOut").toString().replace('-', '/'),
											 SRoom, 
											 DRoom, 
											 QRoom);
			} while(results.next());
			return retList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}	
	
	/**
	 * Delete the certain Order with OrderID
	 * @param OrderID order ID
	 */
	public static void deleteOrder(int OrderID) {
		try {
			stmt.execute("DELETE FROM Orders WHERE OrderID=" + OrderID + ";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the next available Order ID
	 * @return next available Order ID
	 */
	public static int getNewOrderID() {	
		try {
			results = stmt.executeQuery("SELECT * FROM Orders ORDER BY OrderID DESC;");
			if (!results.next())	return 0;
			int lastID = results.getInt("OrderID");
			return lastID + 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		
	}
	/**
	 * main method to test databaseUtil.java
	 * @param args not use
	 */
	/*public static void main(String[] args) {
		buildConnection();
		initDatabase();
		String test = "";
		
		ArrayList<Integer> s, d, q;
		s = new ArrayList<Integer>();
		s.add(1);
		s.add(2);
		
		d = new ArrayList<Integer>();
		d.add(2);
		d.add(3);
		
		q = new ArrayList<Integer>();
		q.add(3);
		q.add(4);
		
		Order testO = new Order(0, "0", 0, "2019/06/01", "2019/06/01", s, d, q);
		Order test1 = new Order(1, "1", 2, "2019/06/02", "2019/06/02", s, d, q);
		Order test2 = new Order(2, "1", 2, "2019/06/03", "2019/06/03", s, d, q);
		insertOrder(testO);
		insertOrder(test1);
		insertOrder(test2);
		
		Order[] ret = getOrderByHotelID(2);
		System.out.println(ret[0].getCheckInDate());
		System.out.println(ret[1].getCheckInDate());
		try {
			if (connect != null)
				connect.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}*/
	
	/**
	 * This method reads the hotel list.
	 */
	public static Hotel[] HotelList;
	public static void ReadHotelList()  {
		/*try (Reader reader = new InputStreamReader(main.class.getResourceAsStream("HotelList.json"), "big5")) {
			// try (BufferedReader reader = new BufferedReader(new FileReader(file)) {
			Gson gson = new GsonBuilder().create();
			HotelList = gson.fromJson(reader, Hotel[].class);
			for (Hotel h : HotelList) {
				h.init();
				String cmd = "INSERT INTO Hotels"
						+ "(HotelID, HotelStar, Locality, StreetAddress, SingleRoom, SinglePrice, DoubleRoom, DoublePrice, QuadRoom, QuadPrice)"
						+ "VALUES"
						+ "(" + h.getID() + ", "
						+ h.getStar() + ", "
						+ "\'" + h.getLocality() + "\'" + ", "
						+ "\'" + h.getAddress() + "\'" + ", "
						+ h.getSingleRooms().length + ", "
						+ h.getSingleRoomPrice() + ", "
						+ h.getDoubleRooms().length + ", "
						+ h.getDoubleRoomPrice() + ", "
						+ h.getQuadRooms().length + ", "
						+ h.getQuadRoomPrice()
						+ ");";
				stmt.execute(cmd);
			
				String cmd_h = "INSERT INTO Rooms "
						+ "(HotelID)"
						+ "VALUES"
						+ "(" + h.getID() + ");";
				stmt.execute(cmd_h);
				
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy_MM_dd");
				String str="2020_01_05";
				Date dt=sdf.parse(str);
				Calendar rightNow = Calendar.getInstance();
				rightNow.setTime(dt);
				
				String temp = "\'" + Integer.toString(h.getSingleRooms().length)
				+ ":" + Integer.toString(h.getDoubleRooms().length)
				+ ":" + Integer.toString(h.getQuadRooms().length) + "\'"
				+ " WHERE HotelID = ";
				
				int i = 0;
				while (i <= 365) {
					rightNow.add(Calendar.DATE,1);
					Date dt1=rightNow.getTime();
					String reStr = sdf.format(dt1);
					String cmd_r = "UPDATE Rooms SET "
							+ reStr + "="
							+ temp
							+ h.getID()
							+ ";";
					stmt.execute(cmd_r);
					i++;
				}
			}
			System.out.println("finish inserting Hotel, Room!");
//			for (Hotel h : HotelList)
//				System.out.println(h);
		}*/
		try {
			String cmd = "SELECT * FROM Hotels;";
			
			int len;
			results = stmt.executeQuery(cmd);
			results.last();
			len = results.getRow();
			results.first();

			HotelList = new Hotel[len];
			
			//HotelList[results.getInt("HotelID")]= new Hotel(results.getInt("HotelID"), results.getInt("HotelStar"), results.getString("Locality"), results.getString("StreetAddress"), results.getInt("SingleRoom"), results.getInt("SinglePrice"), results.getInt("DoubleRoom"), results.getInt("DoublePrice"), results.getInt("QuadRoom"), results.getInt("QuadPrice"));
			//System.out.println(HotelList[0]);
			
			do {
				HotelList[results.getInt("HotelID")]= new Hotel(results.getInt("HotelID"), results.getInt("HotelStar"), results.getString("Locality"), results.getString("StreetAddress"), results.getInt("SingleRoom"), results.getInt("SinglePrice"), results.getInt("DoubleRoom"), results.getInt("DoublePrice"), results.getInt("QuadRoom"), results.getInt("QuadPrice"));
				//System.out.println(HotelList[results.getInt("HotelID")].getID());
				
			} while(results.next());
			
			String cmd_o = "SELECT * FROM Orders;";
			
			results = stmt.executeQuery(cmd_o);
			results.last();
			results.first();
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy_MM_dd");
			String str="2020_01_05";
			Date dt=sdf.parse(str);
			
			do {
				Date date1 = new SimpleDateFormat("yyyy-mm-dd").parse(results.getString("CheckIn")); 
				Date date2 = new SimpleDateFormat("yyyy-mm-dd").parse(results.getString("CheckOut")); 
				
				long day = -1;
				if ((date2.getTime()-dt.getTime())/(24*60*60*1000)>0) {
					day = (date1.getTime()-dt.getTime())/(24*60*60*1000) > 0 ? (date2.getTime()-date1.getTime())/(24*60*60*1000): 
				(date2.getTime()-dt.getTime())/(24*60*60*1000);
				}
				
				String SR = results.getString("SingleRoom"), DR = results.getString("DoubleRoom"), QR = results.getString("QuadRoom");
				
				int i = 0;
				int start_d = (date1.getTime()-dt.getTime())/(24*60*60*1000) > 0 ? (int)((date1.getTime()-dt.getTime())/(24*60*60*1000)):0;
				//System.out.println(start_d);
				while (i <= (int)day) {
					for (String num : SR.split(":")) {
						if (num.equals("")) {
							//System.out.println("S");
							break;
						}
						//HotelList[results.getInt("HotelID")].getSingleRooms()[Integer.valueOf(num)].setDateIsOccupied(start_d + i);
						HotelList[results.getInt("HotelID")].setRoomState("S", Integer.valueOf(num), start_d + i);
					}
					for (String num : DR.split(":")) {
						if (num.equals("")) {
							//System.out.println("D");
							break;
						}
						//HotelList[results.getInt("HotelID")].getDoubleRooms()[Integer.valueOf(num)].setDateIsOccupied(start_d + i);
						HotelList[results.getInt("HotelID")].setRoomState("D", Integer.valueOf(num), start_d + i);
					}
					
					for (String num : QR.split(":")) {
						if (num.equals("")) {
							//System.out.println("Q");
							break;
						}
						//HotelList[results.getInt("HotelID")].getQuadRooms()[Integer.valueOf(num)].setDateIsOccupied(start_d + i);
						HotelList[results.getInt("HotelID")].setRoomState("Q", Integer.valueOf(num), start_d + i);
					}
					
					i++;
				}
				
			} while(results.next());

			/*int j = 0;
			while (j < 25) {
				System.out.println(String.valueOf(5+j) + ":" + HotelList[0].getQuadRooms()[0].getDateIsOccupied()[j]);
				j++;
			}*/
		}
		catch (Exception e) {
			System.out.println(e);
			//System.out.println("cannot find the file.");
		}
	}
}
