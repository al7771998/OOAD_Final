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

public class BookUI extends JPanel {
	private JLayeredPane layeredPane;
	private JLabel background = new JLabel();
	final int frameWidth = 1152, frameHeight = 720;
	
	// attribute of Reserve
	private JPanel Reserve = new JPanel();
	final private int reserveWidth = 620, reserveHeight = 300;
	final private Dimension reserveCenter = new Dimension(frameWidth / 2, frameHeight / 2);
	private JPanel reservebuttons = new JPanel();
	private JLabel cancelreserve = new JLabel("CANCEL", JLabel.CENTER);
	private JLabel backreserve = new JLabel("BACK", JLabel.CENTER);
	private JLabel nextreserve = new JLabel("NEXT", JLabel.CENTER);
	protected static JComboBox<Object> reservehotelid = new JComboBox<Object>();
	protected JTextField reservecheckindateField = new JTextField(10);
	protected JTextField reservecheckoutdateField = new JTextField(10);
	protected static JTextField reservesingleroomField = new JTextField(2);
	protected static JTextField reservedoubleroomField = new JTextField(2);
	protected static JTextField reservequadroomField = new JTextField(2);
	protected static JTextField reserveusername = new JTextField(2);
	protected static JTextField reserveuphone = new JTextField(2);
	
	// attribute of reserve error (sold out)
	private JPanel Soldout = new JPanel();
	final private int soldoutWidth = 700, soldoutHeight = 150;
	final private Dimension soldoutCenter = new Dimension(frameWidth / 2, frameHeight / 2);
	private JLabel soldoutText = new JLabel("Sorry, NO VACANT SUITES!", JLabel.CENTER);
	private JLabel backsoldout = new JLabel("BACK", JLabel.CENTER);

	// attribute of reserve success
	private JPanel Reserve_success = new JPanel();
	final private int reservesuccessWidth = 600, reservesuccessHeight = 75;
	final private Dimension reservesuccessCenter = new Dimension(frameWidth / 2, frameHeight / 5);
	protected JTextField successreservenumberField = new JTextField(10);
	
	// Controller
	private Book mBookController = new Book();
	
	/**
	 * Initialize Menu(Panel) settings
	 */
	private void initPanel() {
		setLayout(new GridLayout(1, 1));
		setOpaque(false);
	}
	
	/**
	 * Initialize Reserve Panel
	 */
	private void initReserve() {
		Reserve.setBorder(new MatteBorder(5, 5, 5, 5, Color.white));
		Reserve.setLayout(new GridLayout(5, 1));
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
		reservecheckindateField.setText("SELECT DATE");
		reservecheckindateField.setOpaque(true);
		reservecheckindateField.setBounds(267, 15, 105, 40);
		reservecheckindateField.setColumns(10);
		reservecheckindateField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				DatePopup DP = new DatePopup(reservecheckindateField);
				DP.showDialog();
			}
		});
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
		reservecheckoutdateField.setText("SELECT DATE");
		reservecheckoutdateField.setOpaque(true);
		reservecheckoutdateField.setBounds(267, 15, 105, 40);
		reservecheckoutdateField.setColumns(10);
		reservecheckoutdateField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				DatePopup DP = new DatePopup(reservecheckoutdateField);
				DP.showDialog();
			}
		});
		// check out panel adding
		checkoutPanel.add(checkout);
		checkoutPanel.add(reservecheckoutdateField);

		// hotelID Panel
		JPanel hotelIDPanel = new JPanel();
		hotelIDPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		hotelIDPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
		hotelIDPanel.setOpaque(false);
		// select hotel ID
		JLabel hotelID = new JLabel("    HotelID     : ");
		hotelID.setFont(new Font("Arial Black", Font.PLAIN, 20));
		String[] option = new String[1500];
		for (Integer i = 0; i < 1500; i++) {
			option[i] = i.toString();
		}
		reservehotelid = new JComboBox<Object>(option);
		hotelIDPanel.add(hotelID);
		hotelIDPanel.add(reservehotelid);

		// number of room panel
		JPanel roomPanel = new JPanel();
		roomPanel.setLayout(new GridLayout(1, 6));
		roomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		roomPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
		roomPanel.setOpaque(false);
		// single room
		JLabel singleroom = new JLabel("Single: ");
		singleroom.setFont(new Font("Arial Black", Font.PLAIN, 20));
		reservesingleroomField.setHorizontalAlignment(SwingConstants.CENTER);
		reservesingleroomField.setEditable(true);
		reservesingleroomField.setFont(new Font("Serif", Font.BOLD, 23));
		reservesingleroomField.addKeyListener(new KeyAdapter() {// can only enter number!
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (!(keyChar >= '0' && keyChar <= '9')) {
					e.consume();
				}
			}
		});
		// double room
		JLabel doubleroom = new JLabel("Double: ");
		doubleroom.setFont(new Font("Arial Black", Font.PLAIN, 20));
		reservedoubleroomField.setHorizontalAlignment(SwingConstants.CENTER);
		reservedoubleroomField.setEditable(true);
		reservedoubleroomField.setFont(new Font("Serif", Font.BOLD, 23));
		reservedoubleroomField.addKeyListener(new KeyAdapter() {// can only enter number!
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (!(keyChar >= '0' && keyChar <= '9')) {
					e.consume();
				}
			}
		});
		// quad room
		JLabel quadroom = new JLabel("Quad: ");
		quadroom.setFont(new Font("Arial Black", Font.PLAIN, 20));
		reservequadroomField.setHorizontalAlignment(SwingConstants.CENTER);
		reservequadroomField.setEditable(true);
		reservequadroomField.setFont(new Font("Serif", Font.BOLD, 23));
		reservequadroomField.addKeyListener(new KeyAdapter() {// can only enter number!
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (!(keyChar >= '0' && keyChar <= '9')) {
					e.consume();
				}
			}
		});
		// room panel adding
		roomPanel.add(singleroom);
		roomPanel.add(reservesingleroomField);
		roomPanel.add(doubleroom);
		roomPanel.add(reservedoubleroomField);
		roomPanel.add(quadroom);
		roomPanel.add(reservequadroomField);

		// setting 'back' and 'next' buttons
		reservebuttons.setLayout(new GridLayout(1, 3));
		reservebuttons.setOpaque(false);
		cancelreserve.setFont(new Font("Arial Black", Font.PLAIN, 20));
		backreserve.setFont(new Font("Arial Black", Font.PLAIN, 20));
		nextreserve.setFont(new Font("Arial Black", Font.PLAIN, 20));
		reservebuttons.add(cancelreserve);
		reservebuttons.add(nextreserve);

		// Reserve adding Panel
		Reserve.add(checkinPanel);
		Reserve.add(checkoutPanel);
		Reserve.add(hotelIDPanel);
		Reserve.add(roomPanel);
		Reserve.add(reservebuttons);
	}
	
	/**
	 * default constructor of Menu
	 */
	public BookUI() {
		initPanel();
		initReserve();
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
				
			}  else if (e.getSource() == backreserve) {
				
			} else if (e.getSource() == nextreserve) {

			} else if (e.getSource() == backsoldout) {
				layeredPane.remove(Soldout);
			} 
		}
	};
}