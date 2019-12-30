
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
	
	private JFrame frame;
	private JLayeredPane layeredPane;
	private JLabel background = new JLabel();
	final int frameWidth = 1152, frameHeight = 720;
	
	// attribute of entering Search Date, People, Rooms
	private JPanel EnterSearch = new JPanel();
	final private int entersearchWidth = 600, entersearchlistHeight = 300;
	final private Dimension entersearchCenter = new Dimension(frameWidth / 2, frameHeight / 2);
	private JLabel backentersearch = new JLabel("BACK", JLabel.CENTER);
	private JLabel nextentersearch = new JLabel("NEXT", JLabel.CENTER);
	protected JTextField entercheckindateField = new JTextField(10);
	protected JTextField entercheckoutdateField = new JTextField(10);
	protected JTextField enterpeopleField = new JTextField(10);
	protected JTextField enterroomField = new JTextField(10);
	
	// attribute of Search
	private JPanel Search = new JPanel();
	final private int searchWidth = 570, searchHeight = 240;
	final private Dimension searchCenter = new Dimension(frameWidth / 2, frameHeight / 2);
	JPanel star = new JPanel();
	private JLabel star5 = new JLabel("5-star", JLabel.CENTER);
	private JLabel star4 = new JLabel("4-star", JLabel.CENTER);
	private JLabel star3 = new JLabel("3-star", JLabel.CENTER);
	private JLabel star2 = new JLabel("2-star", JLabel.CENTER);
	private JLabel pricehighText = new JLabel("PRICE (HIGHEST FIRST)", JLabel.CENTER);
	private JLabel pricelowText = new JLabel("PRICE (LOWEST FIRST)", JLabel.CENTER);
	private JLabel backsearch = new JLabel("BACK", JLabel.CENTER);

	// attribute of hotel list
	private JPanel Hotellist = new JPanel();
	final private int hotellistWidth = 820, hotellistHeight = 500;
	final private Dimension hotellistCenter = new Dimension(frameWidth / 2, frameHeight / 2);
	JTable HotellistTable = new JTable();
	String[] heading = new String[] { "ID", "Star", "City", "Address", "Single", "Double", "Quad", "Price", "Select" };
	private JLabel showallText = new JLabel("SHOW   ALL", JLabel.CENTER);
	private JLabel backhotellist = new JLabel("BACK", JLabel.CENTER);
	private JLabel reservehotellist = new JLabel("RESERVE", JLabel.CENTER);

	private void initPanel() {
		setLayout(new GridLayout(1, 1));
		setOpaque(false);
	}
	
	private void initSearch() {
		// set font
		star5.setFont(new Font("Dialog", Font.BOLD, 28));
		star4.setFont(new Font("Dialog", Font.BOLD, 28));
		star3.setFont(new Font("Dialog", Font.BOLD, 28));
		star2.setFont(new Font("Dialog", Font.BOLD, 28));
		pricehighText.setFont(new Font("Dialog", Font.BOLD, 28));
		pricelowText.setFont(new Font("Dialog", Font.BOLD, 28));
		backsearch.setFont(new Font("Dialog", Font.BOLD, 28));
		Search.setLayout(new GridLayout(4, 1, 0, 0));
		Search.setOpaque(false);
		Search.setBorder(new MatteBorder(5, 5, 5, 5, Color.white));
		star.setLayout(new GridLayout(1, 4, 0, 0));
		star.setOpaque(false);
		star.add(star5);
		star.add(star4);
		star.add(star3);
		star.add(star2);
		Search.add(star);
		Search.add(pricehighText);
		Search.add(pricelowText);
		Search.add(backsearch);
	}
	
	private void initEnterSearch() {
		EnterSearch.setBorder(new MatteBorder(5, 5, 5, 5, Color.white));
		EnterSearch.setLayout(new GridLayout(5, 1));
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
		// set 'back' and 'next' button
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 2));
		buttons.setOpaque(false);
		buttons.setBorder(new EmptyBorder(20, 40, 20, 40));
		backentersearch.setFont(new Font("Arial Black", Font.PLAIN, 20));
		nextentersearch.setFont(new Font("Arial Black", Font.PLAIN, 20));
		buttons.add(backentersearch);
		buttons.add(nextentersearch);
		// EnterHotellist adding
		EnterSearch.add(checkinPanel);
		EnterSearch.add(checkoutPanel);
		EnterSearch.add(peoplePanel);
		EnterSearch.add(roomPanel);
		EnterSearch.add(buttons);
	}
	
	/**
	 * set up a default table model for the hotel list
	 */
	public DefaultTableModel makeHotellist(ArrayList<AvailableHotelRoom> _AHR) {
		DefaultTableModel tablemodel = new DefaultTableModel(heading, 0);
		// get data
		for (int i = 0; i < _AHR.size(); i++) {
			int id = _AHR.get(i).getHotelID(); // id
			int star = _AHR.get(i).getHotelStar(); // star
			String locality = _AHR.get(i).getLocality(); // locality
			String address = _AHR.get(i).getAddress(); // address
			int sroom = _AHR.get(i).getSingle(); // single room
			int droom = _AHR.get(i).getDouble(); // double room
			int qroom = _AHR.get(i).getQuad(); // quad room
			int price = controller.CountSumPrice(_AHR.get(i)); // price
			String go = "Select"; // select
			Object[] data = { id, star, locality, address, sroom, droom, qroom, price, go };
			tablemodel.addRow(data);
		}
		return tablemodel;
	}
	
	/**
	 * Setting the appearance of the default table model
	 * 
	 * @param tablemodel the default table model to be set the appearance
	 */
	public void showHotellist(DefaultTableModel tablemodel) {
		HotellistTable = new JTable(tablemodel) {
			public boolean isCellEditable(int row, int column) {
				if (column == 8) {
					return true;
				}
				return false;
			}

		};
		HotellistTable.setOpaque(false);
		JTableHeader head = HotellistTable.getTableHeader();
		head.setFont(new Font("Arial", Font.PLAIN, 20));

		// row height
		HotellistTable.setRowHeight(40);
		// column width
		HotellistTable.getColumnModel().getColumn(0).setMaxWidth(60); // id
		HotellistTable.getColumnModel().getColumn(1).setMaxWidth(50); // star
		HotellistTable.getColumnModel().getColumn(2).setMaxWidth(60); // locality
		HotellistTable.getColumnModel().getColumn(3).setMaxWidth(300); // address
		HotellistTable.getColumnModel().getColumn(4).setMaxWidth(70); // single room
		HotellistTable.getColumnModel().getColumn(5).setMaxWidth(70); // double room
		HotellistTable.getColumnModel().getColumn(6).setMaxWidth(70); // quad room
		HotellistTable.getColumnModel().getColumn(7).setMaxWidth(70); // price
		HotellistTable.getColumnModel().getColumn(8).setMaxWidth(70); // select
		// row color
		DefaultTableCellRenderer ter = new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				if (row % 2 == 0)
					setBackground(new Color(248, 248, 255, 100));
				else if (row % 2 == 1)
					setBackground(Color.white);
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			}
		};
		for (int i = 0; i <= 8; i++) {
			HotellistTable.getColumn(heading[i]).setCellRenderer(ter);
		}

		// build up Table
		JScrollPane HotellistJScrollPane = new JScrollPane(HotellistTable,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		ButtonColumn buttonsColumn = new ButtonColumn(HotellistTable, 8);

		// set 'back' and 'reserve' button
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 2));
		buttons.setOpaque(false);
		buttons.setBorder(new EmptyBorder(20, 40, 20, 40));
		backhotellist.setFont(new Font("Dialog", Font.BOLD, 25));
		reservehotellist.setFont(new Font("Dialog", Font.BOLD, 25));
		buttons.add(backhotellist);
		buttons.add(reservehotellist);

		star5.setFont(new Font("Dialog", Font.BOLD, 23));
		star4.setFont(new Font("Dialog", Font.BOLD, 23));
		star3.setFont(new Font("Dialog", Font.BOLD, 23));
		star2.setFont(new Font("Dialog", Font.BOLD, 23));
		pricehighText.setFont(new Font("Dialog", Font.BOLD, 23));
		pricelowText.setFont(new Font("Dialog", Font.BOLD, 23));
		showallText.setFont(new Font("Dialog", Font.BOLD, 23));

		JPanel choicepanel = new JPanel();
		choicepanel.setLayout(new GridLayout(2, 1));
		choicepanel.setOpaque(false);
		JPanel pricepanel = new JPanel();
		pricepanel.setLayout(new GridLayout(1, 3, 0, 0));
		pricepanel.setOpaque(false);
		pricepanel.add(pricehighText);
		pricepanel.add(pricelowText);
		pricepanel.add(showallText);
		choicepanel.add(star);
		choicepanel.add(pricepanel);

		Hotellist.removeAll();
		Hotellist.add(choicepanel, BorderLayout.NORTH);
		Hotellist.add(HotellistJScrollPane, BorderLayout.CENTER);
		Hotellist.add(buttons, BorderLayout.SOUTH);
	}
	
	private void initLayerPane() {
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(frameWidth, frameHeight));

		this.background.setIcon(new ImageIcon("images/Menu/background.jpg"));
		this.background.setBounds(0, 0, frameWidth, frameHeight);
		layeredPane.add(background, new Integer(0));

		this.add(layeredPane);

		this.EnterSearch.setBounds(entersearchCenter.width - (entersearchWidth / 2),
				entersearchCenter.height - (entersearchlistHeight / 2), entersearchWidth, entersearchlistHeight);

		this.Hotellist.setBounds(hotellistCenter.width - (hotellistWidth / 2),
				hotellistCenter.height - (hotellistHeight / 2), hotellistWidth, hotellistHeight);

		this.Search.setBounds(searchCenter.width - (searchWidth / 2), searchCenter.height - (searchHeight / 2),
				searchWidth, searchHeight);
	}
	public SearchUI(UIMainFrame mUIMainFrame) {
		this.mUIMainFrame = mUIMainFrame;
		initPanel();
		initEnterSearch();
		initSearch();
		initLayerPane();
		// buttons in enter hotel list
		backentersearch.addMouseListener(ml);
		nextentersearch.addMouseListener(ml);
		
		// buttons in search
		star5.addMouseListener(ml);
		star4.addMouseListener(ml);
		star3.addMouseListener(ml);
		star2.addMouseListener(ml);
		pricehighText.addMouseListener(ml);
		pricelowText.addMouseListener(ml);
		backsearch.addMouseListener(ml);
		// buttons in hotel list
		showallText.addMouseListener(ml);
		backhotellist.addMouseListener(ml);
		reservehotellist.addMouseListener(ml);
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
			if (e.getSource() == nextentersearch) {
				initSearch();
				
			} else if (e.getSource() == showallText) {
				layeredPane.remove(Search);
				layeredPane.remove(Hotellist);
				initSearch();

				String CID = entercheckindateField.getText();
				String COD = entercheckoutdateField.getText();
				int People = Integer.parseInt(enterpeopleField.getText());
				int Rooms = Integer.parseInt(enterroomField.getText());
				//ArrayList<AvailableHotelRoom> AHR = main.SearchAvailableHotels(CID, COD, People, Rooms);
				//DefaultTableModel dtm = makeHotellist(AHR);
				//showHotellist(dtm);

				//layeredPane.add(Hotellist, new Integer(3));
				//showallText.setForeground(Color.black);
			} else if (e.getSource() == pricehighText) { // show price high first
				layeredPane.remove(Search);
				layeredPane.remove(Hotellist);
				initSearch();

				String CID = entercheckindateField.getText();
				String COD = entercheckoutdateField.getText();
				int People = Integer.parseInt(enterpeopleField.getText());
				int Rooms = Integer.parseInt(enterroomField.getText());
			} else if (e.getSource() == pricelowText) { // show price low first
				layeredPane.remove(Search);
				layeredPane.remove(Hotellist);
				initSearch();

				String CID = entercheckindateField.getText();
				String COD = entercheckoutdateField.getText();
				int People = Integer.parseInt(enterpeopleField.getText());
				int Rooms = Integer.parseInt(enterroomField.getText());
			} else if (e.getSource() == star5) { // show star 5 hotel
				layeredPane.remove(Search);
				layeredPane.remove(Hotellist);
				initSearch();

				String CID = entercheckindateField.getText();
				String COD = entercheckoutdateField.getText();
				int People = Integer.parseInt(enterpeopleField.getText());
				int Rooms = Integer.parseInt(enterroomField.getText());
				
			} else if (e.getSource() == star4) { // show star 4 hotel
				layeredPane.remove(Search);
				layeredPane.remove(Hotellist);
				initSearch();

				String CID = entercheckindateField.getText();
				String COD = entercheckoutdateField.getText();
				int People = Integer.parseInt(enterpeopleField.getText());
				int Rooms = Integer.parseInt(enterroomField.getText());
				
			} else if (e.getSource() == star3) { // show star 3 hotel
				layeredPane.remove(Search);
				layeredPane.remove(Hotellist);
				initSearch();

				String CID = entercheckindateField.getText();
				String COD = entercheckoutdateField.getText();
				int People = Integer.parseInt(enterpeopleField.getText());
				int Rooms = Integer.parseInt(enterroomField.getText());
				
			} else if (e.getSource() == star2) { // show star 2 hotel
				layeredPane.remove(Search);
				layeredPane.remove(Hotellist);
				initSearch();

				String CID = entercheckindateField.getText();
				String COD = entercheckoutdateField.getText();
				int People = Integer.parseInt(enterpeopleField.getText());
				int Rooms = Integer.parseInt(enterroomField.getText());
				
			} else if (e.getSource() == backhotellist) {
				initSearch();
				layeredPane.remove(Hotellist);
				layeredPane.add(EnterSearch);
				validate();
				repaint();
				backhotellist.setForeground(Color.black);
			} else if (e.getSource() == reservehotellist) {
				//reservecheckindateField.setText(entercheckindateField.getText());
				//reservecheckoutdateField.setText(entercheckoutdateField.getText());
			} else if (e.getSource() == backsearch) {
				
			} else if (e.getSource() == backentersearch) {
				layeredPane.remove(EnterSearch);
			}
		}
	};
}


class ButtonColumn extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
	JTable table;
	JButton renderButton;
	JButton editButton;
	String text;

	public ButtonColumn(JTable table, int column) {
		super();
		this.table = table;
		renderButton = new JButton();
		editButton = new JButton();
		editButton.setFocusPainted(false);
		editButton.addActionListener(this);

		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(column).setCellRenderer(this);
		columnModel.getColumn(column).setCellEditor(this);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (hasFocus) {
			renderButton.setForeground(table.getForeground());
			renderButton.setBackground(UIManager.getColor("Button.background"));

		} else if (isSelected) {
			renderButton.setForeground(table.getSelectionForeground());
			renderButton.setBackground(table.getSelectionBackground());
		} else {
			renderButton.setForeground(table.getForeground());
			renderButton.setBackground(UIManager.getColor("Button.background"));
		}
		renderButton.setText((value == null) ? " " : value.toString());
		return renderButton;
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		text = (value == null) ? " " : value.toString();
		editButton.setText(text);
		return editButton;
	}

	public Object getCellEditorValue() {
		return text;
	}

	public void actionPerformed(ActionEvent e) {
		fireEditingStopped();
		Object hid = table.getModel().getValueAt(table.getSelectedRow(), 0);
		Object sroom = table.getModel().getValueAt(table.getSelectedRow(), 4);
		Object droom = table.getModel().getValueAt(table.getSelectedRow(), 5);
		Object qroom = table.getModel().getValueAt(table.getSelectedRow(), 6);
		BookUI.reservehotelid.setSelectedIndex((int) hid);
		BookUI.reservesingleroomField.setText(sroom.toString());
		BookUI.reservedoubleroomField.setText(droom.toString());
		BookUI.reservequadroomField.setText(qroom.toString());
	}
}









