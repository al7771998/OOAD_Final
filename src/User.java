
/**
 * This is the class of User.
 * 
 * @author B06505017, B06505032, B06505054, B06902023
 * @version 1.0
 * @since 2019-05-31
 */
public class User {
	private String UserID;
	private String Password;
	/**
	 * Default constructor
	 */
	public User() {
		UserID = "";
		Password = "";
	}
	/**
	 * Copy constructor
	 */
	public User(String _UserID, String _Password) {
		UserID = _UserID;
		Password = _Password;
	}
	public String getUserID() {
		return UserID;
	}
	public String getPassword() {
		return Password;
	}
	public boolean equals(String _UserID) {
		return UserID == _UserID;
	}
}
