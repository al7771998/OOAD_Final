import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import java.util.ArrayList;

public class BookUI extends JPanel {
	private UIMainFrame mUIMainFrame;
	private JLayeredPane layeredPane;
	private JLabel background = new JLabel();
	final int frameWidth = 1152, frameHeight = 720;
	
	// attribute of title
	private JPanel title = new JPanel();
	final private int titleWidth = 930, titleHeight = 120;
	final private Dimension titleCenter = new Dimension(frameWidth / 2, frameHeight / 20 * 3);
	private JLabel titleText = new JLabel("   BOOK  ", JLabel.CENTER);
	
	// attribute of Reserve
	private JPanel Reserve = new JPanel();
	final private int reserveWidth = 720, reserveHeight = 480;
	final private Dimension reserveCenter = new Dimension(frameWidth / 2, frameHeight / 20 * 12);
	private JPanel reservebuttons = new JPanel();
	private JLabel cancelreserve = new JLabel("CANCEL", JLabel.CENTER);
	private JLabel backreserve = new JLabel("BACK", JLabel.CENTER);
	private JLabel nextreserve = new JLabel("NEXT", JLabel.CENTER);
	protected static JComboBox<Object> reservehotelid = new JComboBox<Object>();
	protected JTextField reservecheckindateField = new JTextField(10);
	protected JTextField reservecheckoutdateField = new JTextField(10);
	protected JTextField reservehotelID = new JTextField(10);
	protected static JTextField reservesingleroomField = new JTextField(2);
	protected static JTextField reservedoubleroomField = new JTextField(2);
	protected static JTextField reservequadroomField = new JTextField(2);
	protected static JTextField reserveusername = new JTextField(20);
	protected static JTextField reservephone = new JTextField(20);
	protected static JTextField reserveEmailField = new JTextField(40);
	
	// attribute of reserve error (sold out)
	private JPanel Soldout = new JPanel();
	final private int soldoutWidth = 700, soldoutHeight = 150;
	final private Dimension soldoutCenter = new Dimension(frameWidth / 2, frameHeight / 2);
	private JLabel soldoutText = new JLabel("Sorry, NO VACANT SUITES!", JLabel.CENTER);
	private JLabel backsoldout = new JLabel("BACK", JLabel.CENTER);

	// attribute of reserve success
	private JPanel Reserve_success = new JPanel();
	final private int reservesuccessWidth = 600, reservesuccessHeight = 375;
	final private Dimension reservesuccessCenter = new Dimension(frameWidth / 2, frameHeight / 2);
	protected JTextField successreservenumberField = new JTextField(10);
	
	// Controller
	private BookController mBookController;
	
	private int people;
	
	/** for testing **/
	/*public static void main(String[] args) {
		new BookUI(null, null, null, null, null, null, null);
	}*/
	
	/**
	 * Initialize Menu(Panel) settings
	 */
	private void initPanel() {
		setLayout(new GridLayout(1, 1));
		setOpaque(true);
	}
	
	/**
	 * Initialize Reserve Panel
	 */
	private void initReserve(int hotel_ID, String start, String end, int sn, int dn, int qn) {
		Reserve.setBorder(new MatteBorder(5, 5, 5, 5, Color.white));
		Reserve.setLayout(new GridBagLayout());
		Reserve.setOpaque(false);

		// check in date panel
		JPanel checkinPanel = new JPanel();
		checkinPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		checkinPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
		checkinPanel.setOpaque(false);
		// enter check in date
		JLabel checkin = new JLabel("  CHECK IN DATE: ");
		checkin.setFont(new Font("Arial Black", Font.PLAIN, 20));
		// setting check in yyyy/mm/dd
		reservecheckindateField.setHorizontalAlignment(SwingConstants.CENTER);
		reservecheckindateField.setEditable(false);
		reservecheckindateField.setFont(new Font("Serif", Font.BOLD, 23));
		reservecheckindateField.setBackground(new Color(255, 255, 255));
		reservecheckindateField.setText(start);
		reservecheckindateField.setOpaque(true);
		reservecheckindateField.setBounds(267, 15, 105, 50);
		reservecheckindateField.setColumns(10);
		
		// check in panel adding
		checkinPanel.add(checkin);
		checkinPanel.add(reservecheckindateField);

		// check out date panel
		JPanel checkoutPanel = new JPanel();
		checkoutPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		checkoutPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
		checkoutPanel.setOpaque(false);
		// enter check out date
		JLabel checkout = new JLabel("  CHECK OUT DATE: ");
		checkout.setFont(new Font("Arial Black", Font.PLAIN, 20));
		// setting check in yyyy/mm/dd
		reservecheckoutdateField.setHorizontalAlignment(SwingConstants.CENTER);
		reservecheckoutdateField.setEditable(false);
		reservecheckoutdateField.setFont(new Font("Serif", Font.BOLD, 23));
		reservecheckoutdateField.setBackground(new Color(255, 255, 255));
		reservecheckoutdateField.setText(end);
		reservecheckoutdateField.setOpaque(true);
		reservecheckoutdateField.setBounds(267, 15, 105, 50);
		reservecheckoutdateField.setColumns(10);
		
		// check out panel adding
		checkoutPanel.add(checkout);
		checkoutPanel.add(reservecheckoutdateField);

		// hotelID Panel
		JPanel hotelIDPanel = new JPanel();
		hotelIDPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		hotelIDPanel.setBorder(new EmptyBorder(60, 40, 1, 40));
		hotelIDPanel.setOpaque(false);
		// select hotel ID
		JLabel hotelID = new JLabel("HotelID: " + Integer.toString(hotel_ID));
		hotelID.setFont(new Font("Arial Black", Font.PLAIN, 20));
		String[] option = new String[1500];
		for (Integer i = 0; i < 1500; i++) {
			option[i] = i.toString();
		}
		reservehotelID.setHorizontalAlignment(SwingConstants.CENTER);
		reservehotelID.setEditable(false);
		reservehotelID.setFont(new Font("Serif", Font.BOLD, 23));
		reservehotelID.setBackground(new Color(255, 255, 255));
		reservehotelID.setText(Integer.toString(hotel_ID));
		reservehotelID.setOpaque(true);
		reservehotelID.setBounds(267, 15, 105, 30);
		reservehotelID.setColumns(10);
		hotelIDPanel.add(hotelID);

		// number of room panel
		JPanel roomPanel = new JPanel();
		roomPanel.setLayout(new GridLayout(3, 2));
		//roomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		roomPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
		roomPanel.setOpaque(false);
		// single room
		JLabel singleroom = new JLabel("Single: ");
		singleroom.setFont(new Font("Arial Black", Font.PLAIN, 20));
		reservesingleroomField.setHorizontalAlignment(SwingConstants.CENTER);
		reservesingleroomField.setEditable(false);
		reservesingleroomField.setFont(new Font("Serif", Font.BOLD, 23));
		reservesingleroomField.setText(Integer.toString(sn));
		// double room
		JLabel doubleroom = new JLabel("Double: ");
		doubleroom.setFont(new Font("Arial Black", Font.PLAIN, 20));
		reservedoubleroomField.setHorizontalAlignment(SwingConstants.CENTER);
		reservedoubleroomField.setEditable(false);
		reservedoubleroomField.setFont(new Font("Serif", Font.BOLD, 23));
		reservedoubleroomField.setText(Integer.toString(dn));
		// quad room
		JLabel quadroom = new JLabel("Quad: ");
		quadroom.setFont(new Font("Arial Black", Font.PLAIN, 20));
		reservequadroomField.setHorizontalAlignment(SwingConstants.CENTER);
		reservequadroomField.setEditable(false);
		reservequadroomField.setFont(new Font("Serif", Font.BOLD, 23));
		reservequadroomField.setText(Integer.toString(qn));
		// room panel adding
		roomPanel.add(singleroom);
		roomPanel.add(reservesingleroomField);
		roomPanel.add(doubleroom);
		roomPanel.add(reservedoubleroomField);
		roomPanel.add(quadroom);
		roomPanel.add(reservequadroomField);

		// username panel
		JPanel usernamePanel = new JPanel();
		usernamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		usernamePanel.setBorder(new EmptyBorder(25, 45, 25, 45));
		usernamePanel.setOpaque(false);
		// enter username
		JLabel username = new JLabel("  Username: ");
		username.setFont(new Font("Arial Black", Font.PLAIN, 20));
		// setting username
		reserveusername.setHorizontalAlignment(SwingConstants.CENTER);
		reserveusername.setEditable(true);
		reserveusername.setFont(new Font("Serif", Font.BOLD, 23));
		reserveusername.setBackground(new Color(255, 255, 255));
		reserveusername.setOpaque(true);
		reserveusername.setBounds(267, 15, 105, 40);
		reserveusername.setColumns(10);
		reserveusername.addKeyListener(new KeyAdapter() {
			
		});
		
		// username panel adding
		usernamePanel.add(username);
		usernamePanel.add(reserveusername);
		
		// phone number panel
		JPanel phonePanel = new JPanel();
		phonePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		phonePanel.setBorder(new EmptyBorder(20, 40, 20, 40));
		phonePanel.setOpaque(false);
		// enter phone
		JLabel phone = new JLabel("  Phone Number: ");
		phone.setFont(new Font("Arial Black", Font.PLAIN, 20));
		// setting phone
		reservephone.setHorizontalAlignment(SwingConstants.CENTER);
		reservephone.setEditable(true);
		reservephone.setFont(new Font("Serif", Font.BOLD, 23));
		reservephone.setBackground(new Color(255, 255, 255));
		reservephone.setOpaque(true);
		reservephone.setBounds(267, 15, 105, 40);
		reservephone.setColumns(10);
		reservephone.addKeyListener(new KeyAdapter() {// can only enter number!
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (!(keyChar >= '0' && keyChar <= '9')) {
					e.consume();
				}
			}
		});
		
		// phone panel adding
		phonePanel.add(phone);
		phonePanel.add(reservephone);
		
		// Email number panel
		JPanel emailPanel = new JPanel();
		emailPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		emailPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
		emailPanel.setOpaque(false);
		// enter email
		JLabel email = new JLabel("  Email: ");
		email.setFont(new Font("Arial Black", Font.PLAIN, 20));
		// setting email
		reserveEmailField.setHorizontalAlignment(SwingConstants.CENTER);
		reserveEmailField.setEditable(true);
		reserveEmailField.setFont(new Font("Serif", Font.BOLD, 23));
		reserveEmailField.setBackground(new Color(255, 255, 255));
		reserveEmailField.setOpaque(true);
		reserveEmailField.setBounds(267, 15, 105, 40);
		reserveEmailField.setColumns(10);
		reserveEmailField.addKeyListener(new KeyAdapter() {// can only enter number!
			
		});
		
		// email panel adding
		emailPanel.add(email);
		emailPanel.add(reserveEmailField);
				
		// setting 'back' and 'next' buttons
		reservebuttons.setLayout(new GridLayout(1, 3));
		reservebuttons.setBorder(new EmptyBorder(20, 80, 20, 80));
		reservebuttons.setOpaque(false);
		cancelreserve.setFont(new Font("Arial Black", Font.PLAIN, 20));
		backreserve.setFont(new Font("Arial Black", Font.PLAIN, 20));
		nextreserve.setFont(new Font("Arial Black", Font.PLAIN, 20));
		reservebuttons.add(cancelreserve);
		reservebuttons.add(backreserve);
		reservebuttons.add(nextreserve);

		// Reserve adding Panel
		boolean test = false;
		GridBagConstraints c = new GridBagConstraints();
		Button button1 = new Button("test1");
		Button button2 = new Button("test2");
		Button button3 = new Button("test3");
		Button button4 = new Button("test4");
		Button button5 = new Button("test5");
		Button button6 = new Button("test6");
		Button button7 = new Button("test7");
		Button button8 = new Button("test8");
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 20;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		Reserve.add(test? button1:checkinPanel, c, 0);
		c.gridx = 1;
		c.gridy = 0;
		Reserve.add(test? button2:checkoutPanel, c, 1);
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 1;
		Reserve.add(test? button3:hotelIDPanel, c, 2);
		c.gridx = 0;
		c.gridy = 2;
		c.gridheight = 2;
		Reserve.add(test? button4:roomPanel, c, 3);
		c.gridx = 1;
		c.gridy = 1;
		c.gridheight = 1;
		Reserve.add(test? button5:usernamePanel, c, 4);
		c.gridx = 1;
		c.gridy = 2;
		Reserve.add(test? button6:phonePanel, c, 5);
		c.gridx = 1;
		c.gridy = 3;
		Reserve.add(test? button7:emailPanel, c, 6);
		c.ipady = 20;
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
		Reserve.add(test? button8:reservebuttons, c, 7);
		
	}
	
	private void initTitle() {
		JLabel welcomeText = new JLabel(" welcome ", JLabel.CENTER);
		if (DatabaseUtil.user != null)
			welcomeText = new JLabel(" welcome, " + DatabaseUtil.user.getUserID(), JLabel.CENTER);
		
		titleText.setFont(new Font("Brush Script MT", Font.BOLD, 80));
		titleText.setForeground(new Color(65, 105, 225));
		titleText.setOpaque(false);
		//titleText.setBorder(new EmptyBorder(5, 5, 5, 5));
		welcomeText.setFont(new Font("Brush Script MT", Font.BOLD, 32));
		welcomeText.setForeground(new Color(65, 105, 225));
		welcomeText.setOpaque(false);
		//welcomeText.setBorder(new EmptyBorder(5, 5, 5, 5));

        title.setOpaque(false);
		title.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		title.add(titleText, c, 0);

		c.ipady = 10;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		title.add(welcomeText, c, 1);
	}
	
	private void initReservesuccess() {
		Reserve_success.setLayout(new GridLayout(2, 1, 0, 0));
		Reserve_success.setOpaque(false);
		JPanel reservenumberPanel = new JPanel();
		reservenumberPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		reservenumberPanel.setOpaque(false);
		reservenumberPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		JLabel reservesuccessText = new JLabel("SUCCEED! THANKS FOR YOUR BOOKING!");
		reservesuccessText.setFont(new Font("Dialog", Font.BOLD, 25));
		reservenumberPanel.add(reservesuccessText);
		Reserve_success.add(reservenumberPanel);
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 3));
		buttons.setOpaque(false);
		buttons.setBorder(new EmptyBorder(20, 40, 20, 40));
		buttons.add(backreserve);
		Reserve_success.add(buttons);
	}
	/**
	 * Initialize sold out Panel
	 */
	private void initSoldout() {
		soldoutText.setFont(new Font("Dialog", Font.BOLD, 28));
		soldoutText.setForeground(new Color(255, 0, 0));
		soldoutText.setBorder(new EmptyBorder(20, 40, 20, 40));
		backsoldout.setFont(new Font("Arial Black", Font.BOLD, 28));
		backsoldout.setBorder(new EmptyBorder(20, 40, 20, 40));
		Soldout.setLayout(new GridLayout(2, 1, 0, 0));
		Soldout.setOpaque(false);
		Soldout.setBorder(new MatteBorder(5, 5, 5, 5, Color.white));
		Soldout.add(soldoutText);
		Soldout.add(backsoldout);
	}
	
	/**
	 * Initialize the LayeredPane, setting the bounds and dimension of all Panel
	 */
	private void initLayerPane() {
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(frameWidth, frameHeight));

		this.background.setIcon(new ImageIcon("images/Menu/background.jpg"));
		this.background.setBounds(0, 0, frameWidth, frameHeight);
		layeredPane.add(background, new Integer(0));

		this.title.setBounds(titleCenter.width - (titleWidth / 2), titleCenter.height - (titleHeight / 2), titleWidth,
				titleHeight);
		layeredPane.add(title, new Integer(1));
		
		

		this.Reserve.setBounds(reserveCenter.width - (reserveWidth / 2), reserveCenter.height - (reserveHeight / 2),
				reserveWidth, reserveHeight);
		layeredPane.add(Reserve, new Integer(2));
		
		this.add(layeredPane);
		
		this.Reserve_success.setBounds(reservesuccessCenter.width - (reservesuccessWidth / 2),
				reservesuccessCenter.height - (reservesuccessHeight / 2), reservesuccessWidth, reservesuccessHeight);

		this.Soldout.setBounds(soldoutCenter.width - (soldoutWidth / 2), soldoutCenter.height - (soldoutHeight / 2),
				soldoutWidth, soldoutHeight);
	}
	/**
	 * default constructor of Menu
	 */
	//UIManager UImanager
	public BookUI(UIMainFrame mUIMainFrame, int people, String start, String end, int hotel_ID, int sn, int dn, int qn) {
		//mUIManager = UImanager;
		this.mUIMainFrame = mUIMainFrame;
		this.people = people;
		initPanel();
		initTitle();
		initReserve(hotel_ID, start, end, sn ,dn, qn);
		initReservesuccess();
		initSoldout();
		initLayerPane();
		//for test
		/*
		frame = new JFrame("test");
		frame.setBounds(0,0,frameWidth, frameHeight);
		frame.setVisible(true);
		frame.setContentPane(Reserve);
		*/
		// buttons in reserve
		cancelreserve.addMouseListener(ml);
		backreserve.addMouseListener(ml);
		nextreserve.addMouseListener(ml);
	}
	/**
	 * control the mouse event
	 */
	MouseListener ml = new MouseAdapter() {
		public void mouseEntered(MouseEvent e) {
			JLabel l = (JLabel) e.getSource();
			l.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			l.setForeground(Color.red);
		}

		public void mouseExited(MouseEvent e) {
			JLabel l = (JLabel) e.getSource();
			l.setForeground(Color.black);
		}

		public void mouseClicked(MouseEvent e) {
			if ( e.getSource() == cancelreserve) {
				mUIMainFrame.changeUI(UIMainFrame.UIStage.SEARCH);
			}  else if (e.getSource() == backreserve) {
				mUIMainFrame.changeUI(UIMainFrame.UIStage.SEARCH_RESULT);
			} else if (e.getSource() == nextreserve) {
				String s1 = reservecheckindateField.getText();
				String s2 = reservecheckoutdateField.getText();
				String HotelID = reservehotelID.getText();
				int sn = Integer.parseInt(reservesingleroomField.getText());
				int dn = Integer.parseInt(reservedoubleroomField.getText());
				int qn = Integer.parseInt(reservequadroomField.getText());
				String user = reserveusername.getText();
				String phone = reservephone.getText();
				String email = reserveEmailField.getText();
				//Forward reserve request to Book Controller
				mBookController = new BookController(HotelID, s1, s2, people, user, phone, email, sn, dn, qn);
				Order order = mBookController.BookHotel();
				if (order != null) 
				{
					// 訂房成功
					layeredPane.remove(Reserve);
					layeredPane.add(Reserve_success, new Integer(3));
					reservecheckindateField.setText(null);
					reservecheckoutdateField.setText(null);
					reservebuttons.removeAll();
					//reservebuttons.add(backreserve);
					validate();
					repaint();
					nextreserve.setForeground(Color.black);
				}
				else 
				{
					layeredPane.remove(Reserve);
					layeredPane.add(Soldout, new Integer(3));
					reservebuttons.removeAll();
					//reservebuttons.add(backreserve);
					validate();
					repaint();
					nextreserve.setForeground(Color.black);
				}
			}  
		}
	};
}