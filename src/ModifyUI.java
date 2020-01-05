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
	final private Dimension titleCenter = new Dimension(frameWidth / 2, frameHeight / 15);
	private JLabel titleText = new JLabel("     Revise     ", JLabel.CENTER);
	
	// attribute of reserve order (reservation record and modify and cancel
	// reservation)
	private JPanel Reserveorder = new JPanel();
	final private int reserveorderWidth = 880, reserveorderHeight = 580;
	final private Dimension reserveorderCenter = new Dimension(frameWidth / 2, frameHeight / 2);
	private JLabel backText = new JLabel("BACK", JLabel.CENTER);
	private JLabel backTextcancelsuccess = new JLabel("BACK", JLabel.CENTER);
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
	
	//verification part
	protected JTextField usercodeField = new JTextField(5);
	protected JLabel verifycodeField = new JLabel("");
	
	// change room error
	private JPanel Changeroom_error = new JPanel();
	final private int changeroomerrorWidth = 800, changeroomerrorHeight = 75;
	final private Dimension changeroomerrorCenter = new Dimension(frameWidth / 2, frameHeight / 15 * 14);
	private JLabel changeroomerrorText = new JLabel("SORRY, ADDING ROOMS IS UNAVAILABLE!!", JLabel.CENTER);
	
	// change room error
	private JPanel VerifyCode_error = new JPanel();
	final private int verifyCodeerrorWidth = 800, verifyCodeerrorHeight = 75;
	final private Dimension verifyCodeerrorCenter = new Dimension(frameWidth / 2, frameHeight / 15 * 14);
	private JLabel verifyCodeerrorText = new JLabel("SORRY, Verify Code is wrong!!", JLabel.CENTER);

	// cancel room error
	private JPanel Cancelroom_error = new JPanel();
	final private int cancelroomerrorWidth = 800, cancelroomerrorHeight = 75;
	final private Dimension cancelroomerrorCenter = new Dimension(frameWidth / 2, frameHeight / 15 * 14);
	private JLabel cancelroomerrorText = new JLabel("Verification code is not correct!!", JLabel.CENTER);

		
	// revise date error
	private JPanel Revisedate_error = new JPanel();
	final private int revisedateerrorWidth = 900, revisedateerrorHeight = 75;
	final private Dimension revisedateerrorCenter = new Dimension(frameWidth / 2, frameHeight / 15 * 14);
	private JLabel revisedateerrorText = new JLabel("SORRY, EXTENDING LODGE DAYS IS UNAVAILABLE!", JLabel.CENTER);
	
	// reservation change error
	private JPanel Reservation_error = new JPanel();
	final private int reservationerrorWidth = 800, reservationerrorHeight = 75;
	final private Dimension reservationerrorCenter = new Dimension(frameWidth / 2, frameHeight / 15 * 14);
	private JLabel reservationerrorText = new JLabel("SORRY, ADDING PEOPLE IS UNAVAILABLE!!", JLabel.CENTER);

	protected JTextField reservationNumberField = new JTextField(10);
	protected JTextField orderIDField = new JTextField(10);

	// attribute of revise success
	private JPanel Revise_success = new JPanel();
	final private int revisesuccessWidth = 700, revisesuccessHeight = 110;
	final private Dimension revisesuccessCenter = new Dimension(frameWidth / 2, 500);
	private JLabel revisesuccessText = new JLabel("Change Success!", JLabel.CENTER);
	private JLabel revisesuccessDone = new JLabel("DONE", JLabel.CENTER);
	// attribute of cancel success
	private JPanel Cancel_success = new JPanel();
	final private int cancelsuccessWidth = 600, cancelsuccessHeight = 75;
	final private Dimension cancelsuccessCenter = new Dimension(frameWidth / 2, frameHeight / 2);
	//protected JTextField successreservenumberField = new JTextField(10);

	private UIMainFrame.UIStage last;
	private int hotelID,orderID,sn,dn,qn,reservationNum;
	private String CID,COD;
	long sumPrice;
	
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
			layeredPane.remove(Reservation_error);
			layeredPane.remove(VerifyCode_error);
			if (e.getSource() == backText || e.getSource() == backTextcancelsuccess) {
				//TODO back to where it was
				mUIMainFrame.changeUI(last);
			} else if(e.getSource() == cancelText) {
				String UserCode = usercodeField.getText(); // user enter verify code
				String VerifyCode = verifycodeField.getText(); // random verify code

				if (UserCode.equals(VerifyCode)) {
					int orderID = Integer.parseInt(orderIDField.getText());
					controller.cancel(orderID);
					layeredPane.remove(Reserveorder);
					layeredPane.add(Cancel_success, new Integer(3)); 
					newcheckindateField.setText("");
					newcheckoutdateField.setText("");
					validate();
					repaint();
				} else {// Wrong verify code.
					layeredPane.add(Changeroom_error, new Integer(3));
					//newcheckindateField.setText("");
					//newcheckoutdateField.setText("");
					validate();
					repaint();
				}
			} else if(e.getSource() == changeText) {
				String UserCode = usercodeField.getText(); // user enter verify code
				String VerifyCode = verifycodeField.getText(); // random verify code

				if(!UserCode.equals(VerifyCode)) {// Wrong verify code.
					layeredPane.add(VerifyCode_error, new Integer(3));
					//newcheckindateField.setText("");
					//newcheckoutdateField.setText("");
					validate();
					repaint();
				} else if(!checkDate()) {
					layeredPane.add(Revisedate_error, new Integer(3)); 
					newcheckindateField.setText("SELECT DATE");
					newcheckoutdateField.setText("SELECT DATE");
					validate();
					repaint();
				} else if(!checkRoom()) {
					layeredPane.add(Changeroom_error, new Integer(3));
					newsingleroomField.setText("");
					newdoubleroomField.setText("");
					newquadroomField.setText("");
				} else if(!checkReservation()){
					reservationNumberField.setText(String.valueOf(reservationNum));
					layeredPane.add(Reservation_error, new Integer(3));
					}else {
					sn = Integer.parseInt(!newsingleroomField.getText().equals("") ? newsingleroomField.getText():String.valueOf(sn));
					dn = Integer.parseInt(!newdoubleroomField.getText().equals("") ? newdoubleroomField.getText():String.valueOf(dn));
					qn = Integer.parseInt(!newquadroomField.getText().equals("") ? newquadroomField.getText(): String.valueOf(qn));
					String s1 = newcheckindateField.getText();
					String s2 = newcheckoutdateField.getText();
					if(!s1.equals("SELECT DATE") && !s2.equals("SELECT DATE")) {
						CID = s1;
						COD = s2;
					}
					Order order = controller.modifyHotel(orderID,hotelID,sn,dn,qn,CID,COD);
					layeredPane.remove(Reserveorder);
					layeredPane.add(Revise_success,new Integer(1));
					validate();
					repaint();
				}
				reserveorderstaynightField.setText(String.valueOf(controller.CountDaysBetween(CID, COD)));
				reserveorderpriceField.setText(String.valueOf(sumPrice));
				changeText.setForeground(Color.black);
			}  else if(e.getSource() == calculateText) {
				String s1 = newcheckindateField.getText();
				String s2 = newcheckoutdateField.getText();
				if(s1.equals("SELECT DATE") || s2.equals("SELECT DATE")) {
					s1 = CID;
					s2 = COD;
				}
				if(!s1.equals("SELECT DATE") && !s2.equals("SELECT DATE")) {
					if (controller.CountDaysBetween(s1, s2) > 0) {
						reserveorderstaynightField.setText(String.valueOf(controller.CountDaysBetween(s1, s2)));
					} else {
						reserveorderstaynightField.setText("-1");
					}
				}
				int hotelID = Integer.parseInt(reserveorderhotelIDField.getText());
				int nsn = Integer.parseInt(!newsingleroomField.getText().equals("") ? newsingleroomField.getText():reserveordersingleroomField.getText());
				int ndn = Integer.parseInt(!newdoubleroomField.getText().equals("") ? newdoubleroomField.getText():reserveorderdoubleroomField.getText());
				int nqn = Integer.parseInt(!newquadroomField.getText().equals("") ? newquadroomField.getText(): reserveorderquadroomField.getText());
				long price = controller.getSumPrice(hotelID,nsn,ndn,nqn) * controller.CountDaysBetween(s1, s2);
				reserveorderpriceField.setText(String.valueOf(price));
			} else if (e.getSource() == revisesuccessDone) {
				mUIMainFrame.changeUI(last);
			}
		}
	};
	
	private boolean checkDate() {
		String s1 = newcheckindateField.getText();
		String s2 = newcheckoutdateField.getText();
		if(!s1.equals("SELECT DATE") && !s2.equals("SELECT DATE")) {
			if (controller.CountDaysBetween(s1, s2) > 0) {
				int OrderID = Integer.parseInt(orderIDField.getText());
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
		int nsn = Integer.parseInt(!newsingleroomField.getText().equals("") ? newsingleroomField.getText():"-1");
		int ndn = Integer.parseInt(!newdoubleroomField.getText().equals("") ? newdoubleroomField.getText():"-1");
		int nqn = Integer.parseInt(!newquadroomField.getText().equals("") ? newquadroomField.getText(): "-1");
		if(nsn+ndn+nqn == -3) {
			return true;
		}
		if (nsn > osn || ndn > odn || nqn > oqn) {
//			 change room error 修改房間數失敗 不可增加房間
			layeredPane.add(Changeroom_error, new Integer(3));
			newsingleroomField.setText("");
			newdoubleroomField.setText("");
			newquadroomField.setText("");
			return false;
		}
		return true;
	}
	
	private boolean checkReservation() {
		int nReservationNum = Integer.valueOf(reservationNumberField.getText());
		int oldReservationNum = reservationNum;
		if( nReservationNum > oldReservationNum) {
			return false;
		}
		return true;
	}
	
	public ModifyUI(UIMainFrame uIMainFrame, UIMainFrame.UIStage last, int orderID, String CID, String COD, int hotelID, int sn, int dn, int qn, long sumPrice) {
		mUIMainFrame = uIMainFrame;
		this.last = last;
		controller = new ModifyController();
		initPanel();
		initTitle();
		initChangeroomerror();
		initCancelordererror();
		initRevisedateerror();
		initReviseSuccess();
		initVerifyCodeerror();
		initReservationerror();
		initReservation();
		initCancelSuccess();
		initLayerPane();
		
		backText.addMouseListener(ml);
		backTextcancelsuccess.addMouseListener(ml);
		cancelText.addMouseListener(ml);
		changeText.addMouseListener(ml);
		calculateText.addMouseListener(ml);
		revisesuccessDone.addMouseListener(ml);
		
		this.orderID = orderID;
		this.reservationNum = DatabaseUtil.getOrderByOrderID(orderID).getReservations();
		this.sn = sn;
		this.dn = dn;
		this.qn = qn;
		this.hotelID = hotelID;
		this.CID = CID;
		this.COD = COD;
		this.sumPrice = sumPrice;
		orderIDField.setText(String.valueOf(orderID));
		reservationNumberField.setText(String.valueOf(reservationNum));
		reserveordersingleroomField.setText(String.valueOf(sn));
		reserveorderdoubleroomField.setText(String.valueOf(dn));
		reserveorderquadroomField.setText(String.valueOf(qn));
		reserveorderhotelIDField.setText(String.valueOf(hotelID));
		reserveordercheckindateField.setText(CID);
		reserveordercheckoutdateField.setText(COD);
		reserveorderstaynightField.setText(String.valueOf(controller.CountDaysBetween(CID, COD)));
		reserveorderpriceField.setText(String.valueOf(sumPrice));
		newsingleroomField.setText("");
		newdoubleroomField.setText("");
		newquadroomField.setText("");
		
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
	 * initialize change room error panel
	 */
	private void initVerifyCodeerror() {
		VerifyCode_error.setLayout(new GridLayout(1, 1, 0, 0));
		VerifyCode_error.setOpaque(false);
		verifyCodeerrorText.setFont(new Font("Dialog", Font.BOLD, 30));
		verifyCodeerrorText.setForeground(new Color(255, 0, 0));
		VerifyCode_error.add(verifyCodeerrorText);
	}
	
	private void initCancelordererror() {
		Cancelroom_error.setLayout(new GridLayout(2, 1, 0, 0));
		Cancelroom_error.setOpaque(false);
		cancelroomerrorText.setFont(new Font("Dialog", Font.BOLD, 30));
		cancelroomerrorText.setForeground(new Color(255, 0, 0));
		Cancelroom_error.add(cancelroomerrorText);
	}
	/**
	 * initialize change room error panel
	 */
	private void initCancelSuccess() {
		Cancel_success.setLayout(new GridLayout(1, 1, 0, 0));
		Cancel_success.setOpaque(false);
		JPanel reservenumberPanel = new JPanel();
		reservenumberPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		reservenumberPanel.setOpaque(false);
		reservenumberPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		JLabel reservesuccessText = new JLabel("Cancel Succeeed!");
		reservesuccessText.setFont(new Font("Dialog", Font.BOLD, 25));
		reservenumberPanel.add(reservesuccessText);
		Cancel_success.add(reservenumberPanel);
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(2, 1));
		buttons.setOpaque(false);
		buttons.setBorder(new EmptyBorder(20, 40, 20, 40));
		backTextcancelsuccess.setFont(new Font("Arial Black", Font.PLAIN, 20));
		buttons.add(backTextcancelsuccess);
		Cancel_success.add(buttons);
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
	
	/**
	 * Initialize reservation error
	 */
	private void initReservationerror() {
		Reservation_error.setLayout(new GridLayout(1, 1, 0, 0));
		Reservation_error.setOpaque(false);
		reservationerrorText.setFont(new Font("Dialog", Font.BOLD, 30));
		reservationerrorText.setForeground(new Color(255, 0, 0));
		Reservation_error.add(reservationerrorText);
	}
	
	/**
	 * initialize Revise success panel
	 */
	private void initReviseSuccess() {
		revisesuccessText.setFont(new Font("Dialog", Font.BOLD, 28));
		revisesuccessText.setForeground(Color.green);
		revisesuccessDone.setFont(new Font("Arial Black", Font.BOLD, 28));
		Revise_success.setLayout(new GridLayout(2, 1, 0, 0));
		Revise_success.setOpaque(false);
		Revise_success.add(revisesuccessText);
		Revise_success.add(revisesuccessDone);
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
		verifycodeField.setText(getRandomString(6));
		layeredPane.add(Reserveorder, new Integer(3));
		
		this.add(layeredPane);
		
		this.Revisedate_error.setBounds(revisedateerrorCenter.width - (revisedateerrorWidth / 2),
				revisedateerrorCenter.height - (revisedateerrorHeight / 2), revisedateerrorWidth,
				revisedateerrorHeight);
		
		this.Changeroom_error.setBounds(changeroomerrorCenter.width - (changeroomerrorWidth / 2),
				changeroomerrorCenter.height - (changeroomerrorHeight / 2), changeroomerrorWidth,
				changeroomerrorHeight);
		
		this.VerifyCode_error.setBounds(verifyCodeerrorCenter.width - (verifyCodeerrorWidth / 2),
				verifyCodeerrorCenter.height - (verifyCodeerrorHeight / 2), verifyCodeerrorWidth,
				verifyCodeerrorHeight);
		
		this.Reservation_error.setBounds(reservationerrorCenter.width - (reservationerrorWidth / 2),
				reservationerrorCenter.height - (reservationerrorHeight / 2), reservationerrorWidth,
				reservationerrorHeight);
		
		this.Revise_success.setBounds(revisesuccessCenter.width - (revisesuccessWidth / 2),
				revisesuccessCenter.height - (revisesuccessHeight / 2), revisesuccessWidth, revisesuccessHeight);

		this.Cancel_success.setBounds(cancelsuccessCenter.width - (cancelsuccessWidth / 2),
				cancelsuccessCenter.height - (cancelsuccessHeight / 2), cancelsuccessWidth,
				cancelsuccessHeight);
		this.Cancelroom_error.setBounds(cancelroomerrorCenter.width - (cancelroomerrorWidth / 2),
				cancelroomerrorCenter.height - (cancelroomerrorHeight / 2), cancelroomerrorWidth,
				cancelroomerrorHeight);
	}
	
	/**
	 * Initialize reserve order Panel
	 */
	private void initReservation() {
		Reserveorder.setLayout(new GridLayout(10, 1, 0, 0));
		Reserveorder.setBorder(new MatteBorder(5, 5, 5, 5, Color.white));
		Reserveorder.setOpaque(false);

		// success reserve hotel ID
		JPanel reservenumberPanel = new JPanel();
		reservenumberPanel.setLayout(new GridLayout(2,1));
		reservenumberPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		reservenumberPanel.setOpaque(false);
		reservenumberPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		JLabel reservenumber = new JLabel("RESERVATION NUMBER: ");
		reservenumber.setFont(new Font("Arial Black", Font.PLAIN, 20));
		JPanel orderIDPanel = new JPanel();
		orderIDPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		orderIDPanel.setOpaque(false);
		orderIDPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		JLabel orderIDLabel = new JLabel("Order ID: ");
		orderIDLabel.setFont(new Font("Arial Black", Font.PLAIN, 20));
		reservationNumberField.setHorizontalAlignment(SwingConstants.CENTER);
		reservationNumberField.setEditable(true);
		reservationNumberField.setFont(new Font("Serif", Font.BOLD, 20));
		reservationNumberField.addKeyListener(new KeyAdapter() {// can only enter number!
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (!(keyChar >= '0' && keyChar <= '9')) {
					e.consume();
				}
			}
		});
		orderIDField.setHorizontalAlignment(SwingConstants.CENTER);
		orderIDField.setFont(new Font("Serif", Font.BOLD, 20));
		orderIDField.setEditable(false);
		orderIDField.setOpaque(false);
		orderIDPanel.add(orderIDLabel);
		orderIDPanel.add(orderIDField);
		reservenumberPanel.add(reservenumber);
		reservenumberPanel.add(reservationNumberField);

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

		// verify code Panel
		JPanel verifycodePanel = new JPanel();
		verifycodePanel.setOpaque(false);
		verifycodePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		verifycodePanel.setBorder(new EmptyBorder(20, 40, 20, 40));
		// enter verify code
		JLabel verifycode = new JLabel("VERIFY CODE        ");
		verifycode.setFont(new Font("Arial Black", Font.PLAIN, 20));
		usercodeField.setEditable(true);
		usercodeField.setFont(new Font("Times New Roman", Font.BOLD, 23));
		usercodeField.addKeyListener(new KeyAdapter() {// can only enter number!
			public void keyTyped(KeyEvent e) {
				String s = usercodeField.getText();
				if (s.length() >= 6)
					e.consume();
			}
		});
		usercodeField.setBackground(new Color(232, 232, 232, 120));
		verifycodeField.setFont(new Font("Times New Roman", Font.BOLD, 20));
		// verify code panel adding
		verifycodePanel.add(verifycode);
		verifycodePanel.add(usercodeField);
		verifycodePanel.add(verifycodeField);
		
		// MCR adding
		Reserveorder.add(orderIDPanel);
		Reserveorder.add(reservenumberPanel);
		Reserveorder.add(hotelIDPanel);
		Reserveorder.add(lodgingPanel);
		Reserveorder.add(reviseDatePanel);
		Reserveorder.add(roomPanel);
		Reserveorder.add(newRoomPanel);
		Reserveorder.add(staypricePanel);
		Reserveorder.add(verifycodePanel);
		Reserveorder.add(reserveorderbuttons);
	}
	
	public void showReserveorder(int oid, int hid, int sroom, int droom, int qroom, String chkindate, String chkoutdate,
			int night, int p) {
		orderIDField.setText(Integer.toString(oid));
		reserveorderhotelIDField.setText(Integer.toString(hid));
		reserveordersingleroomField.setText(Integer.toString(sroom));
		reserveorderdoubleroomField.setText(Integer.toString(droom));
		reserveorderquadroomField.setText(Integer.toString(qroom));
		reserveordercheckindateField.setText(chkindate);
		reserveordercheckoutdateField.setText(chkoutdate);
		reserveorderstaynightField.setText(Integer.toString(night));
		reserveorderpriceField.setText(Integer.toString(p));
	}
	
	public static String getRandomString(int length) {
		String str = "abcdefghigklmnopkrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sf = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(62);// 0~61
			sf.append(str.charAt(number));
		}
		return sf.toString();
	}
}