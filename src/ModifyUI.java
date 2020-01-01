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
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class ModifyUI extends JPanel {
	
	private ModifyController controller;
	private UIMainFrame mUIMainFrame;
	
	private JLayeredPane layeredPane;
	private JLabel background = new JLabel();
	final int frameWidth = 1152, frameHeight = 720;
	
	// attribute of title
	private JPanel title = new JPanel();
	final private int titleWidth = 980, titleHeight = 60;
	final private Dimension titleCenter = new Dimension(frameWidth / 2, frameHeight / 10);
	private JLabel titleText = new JLabel("     Revise     ", JLabel.CENTER);
	
	// attribute of reserve order (reservation record and modify and cancel
	// reservation)
	private JPanel Reserveorder = new JPanel();
	final private int reserveorderWidth = 750, reserveorderHeight = 460;
	final private Dimension reserveorderCenter = new Dimension(frameWidth / 2, frameHeight / 2);
	private JLabel backText = new JLabel("BACK", JLabel.CENTER);
	private JLabel cancelText = new JLabel("CANCEL ORDER", JLabel.CENTER);
	private JLabel changeText = new JLabel("CHANGE", JLabel.CENTER);
	protected JTextField reserveorderhotelIDField = new JTextField(15);
	protected JTextField reserveordersingleroomField = new JTextField(2);
	protected JTextField reserveorderdoubleroomField = new JTextField(2);
	protected JTextField reserveorderquadroomField = new JTextField(2);
	protected JTextField reserveordercheckindateField = new JTextField(10);
	protected JTextField reserveordercheckoutdateField = new JTextField(10);
	protected JTextField reserveorderstaynightField = new JTextField(2);
	protected JTextField reserveorderpriceField = new JTextField(5);
	protected JTextField newcheckindateField = new JTextField(10); // 新日期
	protected JTextField newcheckoutdateField = new JTextField(10);
	protected JTextField newsingleroomField = new JTextField(2); // 新房間數
	protected JTextField newdoubleroomField = new JTextField(2);
	protected JTextField newquadroomField = new JTextField(2);
	//buttons
	private JLabel calculateText = new JLabel("CALCULATE", JLabel.CENTER);
	JPanel reserveorderbuttons = new JPanel();
	
	// change room error
	private JPanel Changeroom_error = new JPanel();
	final private int changeroomerrorWidth = 800, changeroomerrorHeight = 75;
	final private Dimension changeroomerrorCenter = new Dimension(frameWidth / 2, frameHeight / 10 * 9);
	private JLabel changeroomerrorText = new JLabel("SORRY, ADDING ROOMS IS UNAVAILABLE!!", JLabel.CENTER);

	// revise date error
	private JPanel Revisedate_error = new JPanel();
	final private int revisedateerrorWidth = 800, revisedateerrorHeight = 75;
	final private Dimension revisedateerrorCenter = new Dimension(frameWidth / 2, frameHeight / 10 * 9);
	private JLabel revisedateerrorText = new JLabel("SORRY, EXTENDING LODGE DAYS IS UNAVAILABLE!", JLabel.CENTER);
	
	// attribute of reserve success
	private JPanel Reserve_success = new JPanel();
	final private int reservesuccessWidth = 600, reservesuccessHeight = 75;
	final private Dimension reservesuccessCenter = new Dimension(frameWidth / 2, frameHeight / 5);
	protected JTextField successreservenumberField = new JTextField(10);
	
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
			layeredPane.remove(Revisedate_error);
			layeredPane.remove(Changeroom_error);
			if (e.getSource() == backText) {
				//TODO back to where it was
				mUIMainFrame.changeUI(UIMainFrame.UIStage.SEARCH);
			} else if(e.getSource() == cancelText) {
				int orderID = Integer.parseInt(successreservenumberField.getText());
				controller.cancel(orderID);
			} else if(e.getSource() == changeText) {
				if(!checkDate()) {
					layeredPane.add(Revisedate_error, new Integer(3)); 
					newcheckindateField.setText(null);
					newcheckoutdateField.setText(null);
					validate();
					repaint();
					return;
				} else if(!checkRoom()) {
					layeredPane.add(Changeroom_error, new Integer(3));
					newsingleroomField.setText(null);
					newdoubleroomField.setText(null);
					newquadroomField.setText(null);
					return;
				} else {
					
				}
				changeText.setForeground(Color.black);
			}  else if(e.getSource() == calculateText) {
				if(!checkDate()) {
					reserveorderstaynightField.setText("-1");
				}
				int hotelID = Integer.parseInt(reserveorderhotelIDField.getText());
				int nsn = Integer.parseInt(!newsingleroomField.getText().equals("") ? newsingleroomField.getText():"0");
				int ndn = Integer.parseInt(!newdoubleroomField.getText().equals("") ? newdoubleroomField.getText():"0");
				int nqn = Integer.parseInt(!newquadroomField.getText().equals("") ? newquadroomField.getText(): "0");
				int price = controller.getSumPrice(hotelID,nsn,ndn,nqn);
				reserveorderpriceField.setText(String.valueOf(price));
			}
		}
	};
	
	private boolean checkDate() {
		String s1 = newcheckindateField.getText();
		String s2 = newcheckoutdateField.getText();
		if(!s1.equals("SELECT DATE") && !s2.equals("SELECT DATE")) {
			if (controller.CountDaysBetween(s1, s2) > 0) {
				int OrderID = Integer.parseInt(successreservenumberField.getText());
				String nCID = newcheckindateField.getText();
				String nCOD = newcheckoutdateField.getText();
				return (controller.CheckDateforReviseDate(OrderID, nCID, nCOD));
			}
		}
		if(s1.equals("SELECT DATE") && s2.equals("SELECT DATE")) {
			return true;
		}
		return false;
	}
	
	private boolean checkRoom() {
		int osn = Integer.parseInt(reserveordersingleroomField.getText());
		int odn = Integer.parseInt(reserveorderdoubleroomField.getText());
		int oqn = Integer.parseInt(reserveorderquadroomField.getText());
		// 取得修改後的房間數
		int nsn = Integer.parseInt(newsingleroomField.getText()!=null ? newsingleroomField.getText():"-1");
		int ndn = Integer.parseInt(newdoubleroomField.getText()!=null ? newdoubleroomField.getText():"-1");
		int nqn = Integer.parseInt(newquadroomField.getText()!=null ? newquadroomField.getText(): "-1");
		if(nsn+ndn+nqn == -3) {
			return true;
		}
		if (nsn > osn || ndn > odn || nqn > oqn) {
//			 change room error 修改房間數失敗 不可增加房間
			layeredPane.add(Changeroom_error, new Integer(3));
			newsingleroomField.setText(null);
			newdoubleroomField.setText(null);
			newquadroomField.setText(null);
			return false;
		}
		return true;
	}
	
	public ModifyUI(UIMainFrame uIMainFrame,int reservationID, String CID, String COD, int hotelID, int sn, int dn, int qn, int sumPrice) {
		mUIMainFrame = uIMainFrame;
		controller = new ModifyController();
		initPanel();
		initTitle();
		initChangeroomerror();
		initRevisedateerror();
		initReservation();
		initLayerPane();
		
		backText.addMouseListener(ml);
		cancelText.addMouseListener(ml);
		changeText.addMouseListener(ml);
		calculateText.addMouseListener(ml);
		
		successreservenumberField.setText(String.valueOf(reservationID));
		reserveordersingleroomField.setText(String.valueOf(sn));
		reserveorderdoubleroomField.setText(String.valueOf(dn));
		reserveorderquadroomField.setText(String.valueOf(qn));
		reserveorderhotelIDField.setText(String.valueOf(hotelID));
		reserveordercheckindateField.setText(CID);
		reserveordercheckoutdateField.setText(COD);
		reserveorderstaynightField.setText(String.valueOf(controller.CountDaysBetween(CID, COD)));
		reserveorderpriceField.setText(String.valueOf(sumPrice));
		
	}
	
	private void initPanel() {
		setLayout(new GridLayout(1, 1));
		setOpaque(false);
	}
	
	private void initTitle() {
		titleText.setFont(new Font("Brush Script MT", Font.BOLD, 60));
		title.setLayout(new GridLayout(1, 1, 0, 0));
		title.setOpaque(false);
		titleText.setForeground(new Color(65, 105, 225));
		titleText.setOpaque(false);
		titleText.setBorder(new EmptyBorder(5, 5, 5, 5));
		title.add(titleText);
	}
	
	/**
	 * initialize change room error panel
	 */
	private void initChangeroomerror() {
		Changeroom_error.setLayout(new GridLayout(1, 1, 0, 0));
		Changeroom_error.setOpaque(false);
		changeroomerrorText.setFont(new Font("Dialog", Font.BOLD, 30));
		changeroomerrorText.setForeground(new Color(255, 0, 0));
		Changeroom_error.add(changeroomerrorText);
	}

	/**
	 * Initialize revise date error
	 */
	private void initRevisedateerror() {
		Revisedate_error.setLayout(new GridLayout(1, 1, 0, 0));
		Revisedate_error.setOpaque(false);
		revisedateerrorText.setFont(new Font("Dialog", Font.BOLD, 30));
		revisedateerrorText.setForeground(new Color(255, 0, 0));
		Revisedate_error.add(revisedateerrorText);
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
		
		this.Reserveorder.setBounds(reserveorderCenter.width - (reserveorderWidth / 2),
				reserveorderCenter.height - (reserveorderHeight / 2), reserveorderWidth, reserveorderHeight);
		
		layeredPane.add(Reserveorder, new Integer(3));
		
		this.add(layeredPane);
		
		this.Revisedate_error.setBounds(revisedateerrorCenter.width - (revisedateerrorWidth / 2),
				revisedateerrorCenter.height - (revisedateerrorHeight / 2), revisedateerrorWidth,
				revisedateerrorHeight);
		
		this.Changeroom_error.setBounds(changeroomerrorCenter.width - (changeroomerrorWidth / 2),
				changeroomerrorCenter.height - (changeroomerrorHeight / 2), changeroomerrorWidth,
				changeroomerrorHeight);
	}
	
	/**
	 * Initialize reserve order Panel
	 */
	private void initReservation() {
		Reserveorder.setLayout(new GridLayout(8, 1, 0, 0));
		Reserveorder.setBorder(new MatteBorder(5, 5, 5, 5, Color.white));
		Reserveorder.setOpaque(false);

		// success reserve hotel ID
		JPanel reservenumberPanel = new JPanel();
		reservenumberPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		reservenumberPanel.setOpaque(false);
		reservenumberPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
		JLabel reservenumber = new JLabel("RESERVATION NUMBER: ");
		reservenumber.setFont(new Font("Arial Black", Font.PLAIN, 20));
		successreservenumberField.setHorizontalAlignment(SwingConstants.CENTER);
		successreservenumberField.setFont(new Font("Serif", Font.BOLD, 20));
		successreservenumberField.setEditable(false);
		successreservenumberField.setOpaque(false);
		reservenumberPanel.add(reservenumber);
		reservenumberPanel.add(successreservenumberField);

		// hotelID Panel
		JPanel hotelIDPanel = new JPanel();
		hotelIDPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		hotelIDPanel.setOpaque(false);
		hotelIDPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
		// enter hotel ID
		JLabel hotelID = new JLabel("    HotelID     : ");
		hotelID.setFont(new Font("Arial Black", Font.PLAIN, 20));
		reserveorderhotelIDField.setHorizontalAlignment(SwingConstants.CENTER);
		reserveorderhotelIDField.setFont(new Font("Serif", Font.BOLD, 20));
		reserveorderhotelIDField.setEditable(false);
		// hotel ID Panel adding
		hotelIDPanel.add(hotelID);
		hotelIDPanel.add(reserveorderhotelIDField);

		// number of room panel
		JPanel roomPanel = new JPanel();
		roomPanel.setLayout(new GridLayout(1, 7));
		roomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		roomPanel.setOpaque(false);
		roomPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
		// row name
		JLabel room = new JLabel("ROOM: ");
		room.setFont(new Font("Arial Black", Font.BOLD, 20));
		// single room
		JLabel singleroom = new JLabel("Single: ");
		singleroom.setFont(new Font("Arial Black", Font.PLAIN, 20));
		reserveordersingleroomField.setHorizontalAlignment(SwingConstants.CENTER);
		reserveordersingleroomField.setEditable(false);
		reserveordersingleroomField.setFont(new Font("Serif", Font.BOLD, 20));
		reserveordersingleroomField.addKeyListener(new KeyAdapter() {// can only enter number!
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
		reserveorderdoubleroomField.setHorizontalAlignment(SwingConstants.CENTER);
		reserveorderdoubleroomField.setEditable(false);
		reserveorderdoubleroomField.setFont(new Font("Serif", Font.BOLD, 20));
		reserveorderdoubleroomField.addKeyListener(new KeyAdapter() {// can only enter number!
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
		reserveorderquadroomField.setHorizontalAlignment(SwingConstants.CENTER);
		reserveorderquadroomField.setEditable(false);
		reserveorderquadroomField.setFont(new Font("Serif", Font.BOLD, 20));
		reserveorderquadroomField.addKeyListener(new KeyAdapter() {// can only enter number!
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (!(keyChar >= '0' && keyChar <= '9')) {
					e.consume();
				}
			}
		});
		// room panel adding
		roomPanel.add(room);
		roomPanel.add(singleroom);
		roomPanel.add(reserveordersingleroomField);
		roomPanel.add(doubleroom);
		roomPanel.add(reserveorderdoubleroomField);
		roomPanel.add(quadroom);
		roomPanel.add(reserveorderquadroomField);
		
		//new room
		JPanel newRoomPanel = new JPanel();
		newRoomPanel.setLayout(new GridLayout(1, 7));
		newRoomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		newRoomPanel.setOpaque(false);
		newRoomPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
		// row name
		JLabel change = new JLabel("CHANGE TO ");
		change.setFont(new Font("Arial Black", Font.BOLD, 20));
		//new single room
		JLabel singleroom1 = new JLabel("Single: ");
		singleroom1.setFont(new Font("Arial Black", Font.PLAIN, 20));
		newsingleroomField.setHorizontalAlignment(SwingConstants.CENTER);
		newsingleroomField.setEditable(true);
		newsingleroomField.setFont(new Font("Arial Black", Font.BOLD, 23));
		newsingleroomField.addKeyListener(new KeyAdapter() {// can only enter number!
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (!(keyChar >= '0' && keyChar <= '9')) {
					e.consume();
				}
			}
		});
		//new double room
		JLabel doubleroom1 = new JLabel("Double: ");
		doubleroom1.setFont(new Font("Arial Black", Font.PLAIN, 20));
		newdoubleroomField.setHorizontalAlignment(SwingConstants.CENTER);
		newdoubleroomField.setEditable(true);
		newdoubleroomField.setFont(new Font("Arial Black", Font.BOLD, 23));
		newdoubleroomField.addKeyListener(new KeyAdapter() {// can only enter number!
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (!(keyChar >= '0' && keyChar <= '9')) {
					e.consume();
				}
			}
		});
		//new quad room
		JLabel quadroom1 = new JLabel("Quad: ");
		quadroom1.setFont(new Font("Arial Black", Font.PLAIN, 20));
		newquadroomField.setHorizontalAlignment(SwingConstants.CENTER);
		newquadroomField.setEditable(true);
		newquadroomField.setFont(new Font("Arial Black", Font.BOLD, 23));
		newquadroomField.addKeyListener(new KeyAdapter() {// can only enter number!
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (!(keyChar >= '0' && keyChar <= '9')) {
					e.consume();
				}
			}
		});
		
		// new room panel adding
		newRoomPanel.add(change);
		newRoomPanel.add(singleroom1);
		newRoomPanel.add(newsingleroomField);
		newRoomPanel.add(doubleroom1);
		newRoomPanel.add(newdoubleroomField);
		newRoomPanel.add(quadroom1);
		newRoomPanel.add(newquadroomField);

		// lodging date panel
		JPanel lodgingPanel = new JPanel();
		lodgingPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		lodgingPanel.setOpaque(false);
		lodgingPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
		JLabel date = new JLabel("DATE ");
		date.setFont(new Font("Arial Black", Font.BOLD, 20));
		reserveordercheckindateField.setHorizontalAlignment(SwingConstants.CENTER);
		reserveordercheckindateField.setFont(new Font("Serif", Font.BOLD, 20));
		reserveordercheckindateField.setEditable(false);
		JLabel mark = new JLabel("~");
		mark.setFont(new Font("Arial Black", Font.PLAIN, 20));
		reserveordercheckoutdateField.setHorizontalAlignment(SwingConstants.CENTER);
		reserveordercheckoutdateField.setFont(new Font("Serif", Font.BOLD, 20));
		reserveordercheckoutdateField.setEditable(false);
		// lodgingPanel adding
		lodgingPanel.add(date);
		lodgingPanel.add(reserveordercheckindateField);
		lodgingPanel.add(mark);
		lodgingPanel.add(reserveordercheckoutdateField);
		
		//revise date
		JPanel reviseDatePanel = new JPanel();
		reviseDatePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		reviseDatePanel.setOpaque(false);
		reviseDatePanel.setBorder(new EmptyBorder(20, 40, 20, 40));
		JLabel reviseDate = new JLabel("CHANGE TO ");
		reviseDate.setFont(new Font("Arial Black", Font.BOLD, 20));
		// setting check in yyyy/mm/dd
		newcheckindateField.setHorizontalAlignment(SwingConstants.CENTER);
		newcheckindateField.setEditable(true);
		newcheckindateField.setFont(new Font("Serif", Font.BOLD, 20));
		newcheckindateField.setText("SELECT DATE");
		newcheckindateField.setBounds(267, 15, 105, 40);
		newcheckindateField.setColumns(10);
		newcheckindateField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				DatePopup DP = new DatePopup(newcheckindateField);
				DP.showDialog();
			}
		});
		// setting check out yyyy/mm/dd
		newcheckoutdateField.setHorizontalAlignment(SwingConstants.CENTER);
		newcheckoutdateField.setEditable(true);
		newcheckoutdateField.setFont(new Font("Serif", Font.BOLD, 20));
		newcheckoutdateField.setText("SELECT DATE");
		newcheckoutdateField.setBounds(267, 15, 105, 40);
		newcheckoutdateField.setColumns(10);
		newcheckoutdateField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				DatePopup DP = new DatePopup(newcheckoutdateField);
				DP.showDialog();
			}
		});
		reviseDatePanel.add(reviseDate);
		reviseDatePanel.add(newcheckindateField);
		reviseDatePanel.add(mark);
		reviseDatePanel.add(newcheckoutdateField);

		// 'total length of stay' and 'total price'
		JPanel staypricePanel = new JPanel();
		staypricePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		staypricePanel.setOpaque(false);
		staypricePanel.setBorder(new EmptyBorder(20, 40, 20, 40));
		JLabel stay = new JLabel("Total Nights of Stay:");
		stay.setFont(new Font("Arial Black", Font.PLAIN, 20));
		reserveorderstaynightField.setHorizontalAlignment(SwingConstants.CENTER);
		reserveorderstaynightField.setFont(new Font("Serif", Font.BOLD, 20));
		reserveorderstaynightField.setEditable(false);
		JLabel price = new JLabel("Total Price:");
		price.setFont(new Font("Arial Black", Font.PLAIN, 20));
		reserveorderpriceField.setHorizontalAlignment(SwingConstants.CENTER);
		reserveorderpriceField.setFont(new Font("Serif", Font.BOLD, 20));
		reserveorderpriceField.setEditable(false);
		// stay price Panel adding
		staypricePanel.add(stay);
		staypricePanel.add(reserveorderstaynightField);
		staypricePanel.add(price);
		staypricePanel.add(reserveorderpriceField);
		calculateText.setFont(new Font("Arial Black", Font.PLAIN, 20));
		calculateText.setOpaque(true);
		calculateText.setBackground(Color.pink);
		staypricePanel.add(calculateText);

		// set 'back' and 'next' button
		reserveorderbuttons.setLayout(new GridLayout(1, 3));
		reserveorderbuttons.setOpaque(false);
		reserveorderbuttons.setBorder(new EmptyBorder(20, 40, 20, 40));
		changeText.setFont(new Font("Arial Black", Font.PLAIN, 20));
		cancelText.setFont(new Font("Arial Black", Font.PLAIN, 20));
		backText.setFont(new Font("Arial Black", Font.PLAIN, 20));
		reserveorderbuttons.add(backText);
		reserveorderbuttons.add(cancelText);
		reserveorderbuttons.add(changeText);

		// MCR adding
		Reserveorder.add(reservenumberPanel);
		Reserveorder.add(hotelIDPanel);
		Reserveorder.add(lodgingPanel);
		Reserveorder.add(reviseDatePanel);
		Reserveorder.add(roomPanel);
		Reserveorder.add(newRoomPanel);
		Reserveorder.add(staypricePanel);
		Reserveorder.add(reserveorderbuttons);
	}
	
	public void showReserveorder(int oid, int hid, int sroom, int droom, int qroom, String chkindate, String chkoutdate,
			int night, int p) {
		successreservenumberField.setText(Integer.toString(oid));
		reserveorderhotelIDField.setText(Integer.toString(hid));
		reserveordersingleroomField.setText(Integer.toString(sroom));
		reserveorderdoubleroomField.setText(Integer.toString(droom));
		reserveorderquadroomField.setText(Integer.toString(qroom));
		reserveordercheckindateField.setText(chkindate);
		reserveordercheckoutdateField.setText(chkoutdate);
		reserveorderstaynightField.setText(Integer.toString(night));
		reserveorderpriceField.setText(Integer.toString(p));
	}
}