
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

public class SearchUI extends JPanel {
	
	private SearchController controller = new SearchController();
	private UIMainFrame mUIMainFrame;
	
	private JLayeredPane layeredPane;
	private JLabel background = new JLabel();
	final int frameWidth = 1152, frameHeight = 720;
	
	// attribute of title
	private JPanel title = new JPanel();
	final private int titleWidth = 930, titleHeight = 120;
	final private Dimension titleCenter = new Dimension(frameWidth / 2, frameHeight / 5);
	private JLabel titleText = new JLabel("   SEARCH   ", JLabel.CENTER);
	
	// attribute of welcome
	//private JPanel welcome = new JPanel();
	//final private int welcomeWidth = 930, welcomeHeight = 80;
	//final private Dimension welcomeCenter = new Dimension(frameWidth / 2, frameHeight / 5);
	//private JLabel welcomeText = new JLabel(" welcome " + DatabaseUtil.user, JLabel.CENTER);
	
	// attribute of entering Search Date, People, Rooms
	private JPanel EnterSearch = new JPanel();
	final private int entersearchWidth = 600, entersearchlistHeight = 400;
	final private Dimension entersearchCenter = new Dimension(frameWidth / 2, frameHeight / 2);
	private JLabel backentersearch = new JLabel("BACK", JLabel.CENTER);
	private JLabel searchentersearch = new JLabel("NEXT", JLabel.CENTER);
	private JComboBox<Object> locationOption;
	protected JTextField entercheckindateField = new JTextField(10);
	protected JTextField entercheckoutdateField = new JTextField(10);
	protected JTextField enterpeopleField = new JTextField(10);
	protected JTextField enterroomField = new JTextField(10);
	protected JTextField enterlocationField = new JTextField(10);
	
	// attribute of invalid date error
	private JPanel Invalid_date_error = new JPanel();
	final private int invaiddateerrorWidth = 300, invaliddateerrorHeight = 75;
	final private Dimension invaliddateerrorCenter = new Dimension(frameWidth / 2, frameHeight / 5);
	private JLabel invaliddateerrorText = new JLabel("INVALID DATE!", JLabel.CENTER);

	// attribute of no matched hotel error
	private JPanel No_matched_hotel_error = new JPanel();
	final private int nomatchedhotelerrorWidth = 500, nomatchedhotelerrorHeight = 150;
	final private Dimension nomatchedhotelerrorCenter = new Dimension(frameWidth / 2, frameHeight / 2);
	private JLabel nomatchedhotelerrorText = new JLabel("NO MATCHED HOTEL!", JLabel.CENTER);
	private JLabel backnomatchedhotelerror = new JLabel("BACK", JLabel.CENTER);
	
	public SearchUI(UIMainFrame mUIMainFrame) {
		this.mUIMainFrame = mUIMainFrame;
		initPanel();
		initTitle();
		initEnterSearch();
		initEnterinvaliddateerror();
		initNomatchedhotelerror();
		initLayerPane();
		// buttons in enter hotel list
		backentersearch.addMouseListener(ml);
		searchentersearch.addMouseListener(ml);
		backnomatchedhotelerror.addMouseListener(ml);
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
		Button button1 = new Button("1");
        Button button2 = new Button("2");
		
        title.setOpaque(false);
		title.setLayout(new GridBagLayout());
		//GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;

		title.add(titleText, c, 0);
		//title.add(button1, c);
		
		c.ipady = 30;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		title.add(welcomeText, c, 1);
		//title.add(button2, c);
		
	}
	
	private void initPanel() {
		setLayout(new GridLayout(1, 1));
		setOpaque(false);
	}
	
	private void initEnterSearch() {
		EnterSearch.setBorder(new MatteBorder(5, 5, 5, 5, Color.white));
		EnterSearch.setLayout(new GridLayout(6, 1));
		EnterSearch.setOpaque(false);
		// check in date panel
		JPanel checkinPanel = new JPanel();
		checkinPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		checkinPanel.setOpaque(false);
		checkinPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
		// enter check in date
		JLabel checkin = new JLabel("  CHECK IN DATE: ");
		checkin.setFont(new Font("Arial Black", Font.PLAIN, 20));
		// setting check in yyyy/mm/dd
		entercheckindateField.setHorizontalAlignment(SwingConstants.CENTER);
		entercheckindateField.setEditable(false);
		entercheckindateField.setFont(new Font("Serif", Font.BOLD, 23));
		entercheckindateField.setBackground(new Color(255, 255, 255));
		entercheckindateField.setText("SELECT A DATE");
		entercheckindateField.setOpaque(true);
		entercheckindateField.setBounds(267, 15, 105, 40);
		entercheckindateField.setColumns(10);
		entercheckindateField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				DatePopup DP = new DatePopup(entercheckindateField);
				DP.showDialog();
			}
		});
		// check in panel adding
		checkinPanel.add(checkin);
		checkinPanel.add(entercheckindateField);

		// check out date panel
		JPanel checkoutPanel = new JPanel();
		checkoutPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		checkoutPanel.setOpaque(false);
		checkoutPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
		JLabel checkout = new JLabel("  CHECK OUT DATE: ");
		checkout.setFont(new Font("Arial Black", Font.PLAIN, 20));
		// setting check in yyyy/mm/dd
		entercheckoutdateField.setHorizontalAlignment(SwingConstants.CENTER);
		entercheckoutdateField.setEditable(false);
		entercheckoutdateField.setFont(new Font("Serif", Font.BOLD, 23));
		entercheckoutdateField.setBackground(new Color(255, 255, 255));
		entercheckoutdateField.setText("SELECT A DATE");
		entercheckoutdateField.setOpaque(true);
		entercheckoutdateField.setBounds(267, 15, 105, 40);
		entercheckoutdateField.setColumns(10);
		entercheckoutdateField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				DatePopup DP = new DatePopup(entercheckoutdateField);
				DP.showDialog();
			}
		});
		// check out panel adding
		checkoutPanel.add(checkout);
		checkoutPanel.add(entercheckoutdateField);

		// people panel
		JPanel peoplePanel = new JPanel();
		peoplePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		peoplePanel.setOpaque(false);
		peoplePanel.setBorder(new EmptyBorder(20, 40, 20, 40));
		JLabel people = new JLabel("NUMBER OF PEOPLE: ");
		people.setFont(new Font("Arial Black", Font.PLAIN, 20));
		enterpeopleField.setHorizontalAlignment(SwingConstants.CENTER);
		enterpeopleField.setEditable(true);
		enterpeopleField.setFont(new Font("Serif", Font.BOLD, 23));
		enterpeopleField.addKeyListener(new KeyAdapter() {// can only enter number!
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (!(keyChar >= '0' && keyChar <= '9')) {
					e.consume();
				}
			}
		});
		// people panel adding
		peoplePanel.add(people);
		peoplePanel.add(enterpeopleField);

		// room panel
		JPanel roomPanel = new JPanel();
		roomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		roomPanel.setOpaque(false);
		roomPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
		JLabel room = new JLabel("NUMBER OF ROOMS: ");
		room.setFont(new Font("Arial Black", Font.PLAIN, 20));
		enterroomField.setHorizontalAlignment(SwingConstants.CENTER);
		enterroomField.setEditable(true);
		enterroomField.setFont(new Font("Serif", Font.BOLD, 23));
		enterroomField.addKeyListener(new KeyAdapter() {// can only enter number!
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (!(keyChar >= '0' && keyChar <= '9')) {
					e.consume();
				}
			}
		});
		// room panel adding
		roomPanel.add(room);
		roomPanel.add(enterroomField);
		
		// location panel
		JPanel locationPanel = new JPanel();
		locationPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		locationPanel.setOpaque(false);
		locationPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
		JLabel location = new JLabel("LOCATION: ");
		location.setFont(new Font("Arial Black", Font.PLAIN, 20));
		String[] option = {"台北","台中","高雄"};
		locationOption = new JComboBox<Object>(option);
		locationOption.setFont(new Font("新細明體", Font.PLAIN, 20));

		// room panel adding
		locationPanel.add(location);
		locationPanel.add(locationOption);
		
		// set 'back' and 'next' button
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 2));
		buttons.setOpaque(false);
		buttons.setBorder(new EmptyBorder(20, 40, 20, 40));
		backentersearch.setFont(new Font("Arial Black", Font.PLAIN, 20));
		searchentersearch.setFont(new Font("Arial Black", Font.PLAIN, 20));
		buttons.add(backentersearch);
		buttons.add(searchentersearch);
		
		// EnterHotellist adding
		EnterSearch.add(checkinPanel);
		EnterSearch.add(checkoutPanel);
		EnterSearch.add(peoplePanel);
		EnterSearch.add(roomPanel);
		EnterSearch.add(locationPanel);
		EnterSearch.add(buttons);
	}
	
	private void initLayerPane() {
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(frameWidth, frameHeight));

		this.background.setIcon(new ImageIcon("images/Menu/background.jpg"));
		this.background.setBounds(0, 0, frameWidth, frameHeight);
		layeredPane.add(background, new Integer(0));

		this.title.setBounds(titleCenter.width - (titleWidth / 2), titleCenter.height - (titleHeight / 2), titleWidth,
				titleHeight);
		layeredPane.add(title, new Integer(1));

		this.EnterSearch.setBounds(entersearchCenter.width - (entersearchWidth / 2),
				entersearchCenter.height - (entersearchlistHeight / 3), entersearchWidth, entersearchlistHeight);

		layeredPane.add(EnterSearch, new Integer(3));

		this.add(layeredPane);
		
		this.Invalid_date_error.setBounds(invaliddateerrorCenter.width - (invaiddateerrorWidth / 2),
				invaliddateerrorCenter.height - (invaliddateerrorHeight / 2), invaiddateerrorWidth,
				invaliddateerrorHeight);

		this.No_matched_hotel_error.setBounds(nomatchedhotelerrorCenter.width - (nomatchedhotelerrorWidth / 2),
				nomatchedhotelerrorCenter.height - (nomatchedhotelerrorHeight / 2), nomatchedhotelerrorWidth,
				nomatchedhotelerrorHeight);
	}
	
	/**
	 * Initialize invalid date Panel
	 */
	private void initEnterinvaliddateerror() {
		Invalid_date_error.setLayout(new GridLayout(1, 1, 0, 0));
		Invalid_date_error.setOpaque(false);
		invaliddateerrorText.setFont(new Font("Dialog", Font.BOLD, 30));
		invaliddateerrorText.setForeground(new Color(255, 0, 0));
		Invalid_date_error.add(invaliddateerrorText);
	}
	
	/**
	 * Initialize search hotel error (No matched Hotel) Panel
	 */
	private void initNomatchedhotelerror() {
		nomatchedhotelerrorText.setFont(new Font("Dialog", Font.BOLD, 28));
		nomatchedhotelerrorText.setForeground(new Color(255, 0, 0));
		nomatchedhotelerrorText.setBorder(new EmptyBorder(20, 40, 20, 40));
		backnomatchedhotelerror.setFont(new Font("Arial Black", Font.BOLD, 28));
		backnomatchedhotelerror.setBorder(new EmptyBorder(20, 40, 20, 40));
		No_matched_hotel_error.setLayout(new GridLayout(2, 1, 0, 0));
		No_matched_hotel_error.setOpaque(false);
		No_matched_hotel_error.setBorder(new MatteBorder(5, 5, 5, 5, Color.white));
		No_matched_hotel_error.add(nomatchedhotelerrorText);
		No_matched_hotel_error.add(backnomatchedhotelerror);
	}
	
	public void updateTitle() {
		Component toRemove = title.getComponent(1);
		JLabel welcomeText = new JLabel(" welcome, " + DatabaseUtil.user.getUserID(), JLabel.CENTER);
		welcomeText.setFont(new Font("Brush Script MT", Font.BOLD, 32));
		welcomeText.setForeground(new Color(65, 105, 225));
		welcomeText.setOpaque(false);
		GridBagConstraints c = new GridBagConstraints();
		c.ipady = 30;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		title.remove(toRemove);
		title.add(welcomeText, c, 1);
	}

	
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
			if(e.getSource() == backentersearch) {
				mUIMainFrame.changeUI(UIMainFrame.UIStage.MENU);
				backentersearch.setForeground(Color.black);
			} else if(e.getSource() == searchentersearch) {
				String s1 = entercheckindateField.getText();
				String s2 = entercheckoutdateField.getText();
				DatabaseUtil.ReadHotelList();
				controller.UpdateHotelList();
				if (controller.countDaysBetween(s1, s2) > 0) {
					String CID = entercheckindateField.getText();
					String COD = entercheckoutdateField.getText();
					int People = Integer.parseInt(enterpeopleField.getText());
					int Rooms = Integer.parseInt(enterroomField.getText());
					String LOC = locationOption.getSelectedItem().toString();
					ArrayList<AvailableHotelRoom> AHR = controller.SearchAvailableHotels(CID, COD, People, Rooms, LOC);
					if (AHR.size() > 0) {
						mUIMainFrame.changeUI(UIMainFrame.UIStage.SEARCH_RESULT, People, CID, COD, AHR);
					} else {
						// no matched hotel
						layeredPane.remove(EnterSearch);
						layeredPane.remove(Invalid_date_error);
						layeredPane.add(No_matched_hotel_error, new Integer(3));
						validate();
						repaint();
					}
				} else {// Invaid date
					layeredPane.add(Invalid_date_error, new Integer(3));
					entercheckindateField.setText("SELECT DATE");
					entercheckoutdateField.setText("SELECT DATE");
					enterpeopleField.setText(null);
					enterroomField.setText(null);
					validate();
					repaint();
				}
				searchentersearch.setForeground(Color.black);
			} else if(e.getSource()== backnomatchedhotelerror) {
				layeredPane.remove(No_matched_hotel_error);
				layeredPane.add(EnterSearch, new Integer(3));
				validate();
				repaint();
			}
		}
	};
}