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

public class SignUpUI extends JPanel {

	private UIMainFrame mUIMainFrame;
	private JLayeredPane layeredPane;
	private JLabel background = new JLabel();
	final int frameWidth = 1152, frameHeight = 720;
	
	// attribute of title
	private JPanel title = new JPanel();
	final private int titleWidth = 930, titleHeight = 80;
	final private Dimension titleCenter = new Dimension(frameWidth / 2, frameHeight / 4);
	private JLabel titleText = new JLabel("     HOTEL     ", JLabel.CENTER);

	//signup section
	private JPanel Signup = new JPanel();
	final private int signupSetWidth = 600, signupSetHeight = 270;
	final private Dimension signupSetCenter = new Dimension(frameWidth / 2, 450);
	private JLabel signuplogin = new JLabel("SIGN UP and LOGIN", JLabel.CENTER);
	private JLabel signupcancel = new JLabel("CANCEL", JLabel.CENTER);
	protected JTextField signupidField = new JTextField(13);
	protected JPasswordField signuppasswordField = new JPasswordField(13);
	protected JTextField usercodeField = new JTextField(5);
	protected JLabel verifycodeField = new JLabel("");
	
	// attribute of sign up error - USERID ALREADY EXISTS.
	private JPanel Signuperror = new JPanel();
	final private int signuperrorWidth = 500, signuperrorHeight = 110;
	final private Dimension signuperrorCenter = new Dimension(frameWidth / 2, 500);
	private JLabel signuperrorText = new JLabel("USER ID ALREADY EXISTS.", JLabel.CENTER);
	private JLabel backsignuperror = new JLabel("BACK", JLabel.CENTER);

	// attribute of sign up error - WRONG VERIFY CODE.
	private JPanel Signuperror1 = new JPanel();
	final private int signuperror1Width = 500, signuperror1Height = 110;
	final private Dimension signuperrorCenter1 = new Dimension(frameWidth / 2, 500);
	private JLabel signuperror1Text = new JLabel("WRONG VERIFY CODE.", JLabel.CENTER);
	private JLabel backsignuperror1 = new JLabel("BACK", JLabel.CENTER);
	
	
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
			if (e.getSource() == signuplogin) {
				String UserID = signupidField.getText();
				String Password = new String(signuppasswordField.getPassword());
				String UserCode = usercodeField.getText(); // user enter verify code
				String VerifyCode = verifycodeField.getText(); // random verify code
				if (SignUpCheck(UserID)) {

					if (UserCode.equals(VerifyCode)) {
						// Create a new User
						DatabaseUtil.user = new User(UserID, Password, false);
						DatabaseUtil.insertUser(DatabaseUtil.user);
						mUIMainFrame.changeUI(UIMainFrame.UIStage.SEARCH);
						signuplogin.setForeground(Color.black);
					} else {// Wrong verify code.
						layeredPane.remove(Signup);
						layeredPane.add(Signuperror1, new Integer(3));
						verifycodeField.setText(getRandomString(6));
						validate();
						repaint();
						signuplogin.setForeground(Color.black);
					}
				} else {// UserID already existed.
					layeredPane.remove(Signup);
					layeredPane.add(Signuperror, new Integer(3));
					signupidField.setText("");
					signuppasswordField.setText("");
					verifycodeField.setText(getRandomString(6));
					revalidate();
					signuplogin.setForeground(Color.black);
				}
			} else if(e.getSource() == signupcancel) {
				mUIMainFrame.changeUI(UIMainFrame.UIStage.LOGIN);
				signupcancel.setForeground(Color.black);
			} else if(e.getSource() == backsignuperror || e.getSource() == backsignuperror1) {
				layeredPane.remove(Signuperror);
				layeredPane.remove(Signuperror1);
				layeredPane.add(Signup);
				signupidField.setText(null);
				signuppasswordField.setText(null);
				usercodeField.setText(null);
				revalidate();
				backsignuperror.setForeground(Color.black);
				backsignuperror1.setForeground(Color.black);
			}
		}
	};
	
	/**
	 * This method checks whether the current user's ID doesn't exist.
	 * 
	 * @param UserID the current user's ID
	 * @return boolean true if user's ID doesn't exist.
	 */
	public static boolean SignUpCheck(String UserID) {
		return DatabaseUtil.getUser(UserID) == null;
	}
	
	public SignUpUI(UIMainFrame UIMainFrame) {
		this.mUIMainFrame = UIMainFrame;
		initPanel();
		initTitle();
		initSignUp();
		initSignuperror();
		initSignuperror1();
		initLayerPane();
		signuplogin.addMouseListener(ml);
		signupcancel.addMouseListener(ml);
		backsignuperror.addMouseListener(ml);
		backsignuperror1.addMouseListener(ml);
	}
	
	private void initPanel() {
		setLayout(new GridLayout(1, 1));
		setOpaque(false);
	}
	
	private void initTitle() {
		titleText.setFont(new Font("Brush Script MT", Font.BOLD, 96));
		title.setLayout(new GridLayout(1, 1, 0, 0));
		title.setOpaque(false);
		titleText.setForeground(new Color(65, 105, 225));
		titleText.setOpaque(false);
		titleText.setBorder(new EmptyBorder(5, 5, 5, 5));
		title.add(titleText);
	}
	
	/**
	 * Initialize Sign up Panel
	 */
	private void initSignUp() {
		Signup.setLayout(new GridLayout(4, 1));
		Signup.setOpaque(false);

		// ID Panel
		JPanel IDPanel = new JPanel();
		IDPanel.setOpaque(false);
		IDPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		IDPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
		JLabel ID = new JLabel("         ID          ");
		ID.setFont(new Font("Arial Black", Font.PLAIN, 20));
		signupidField = new JTextField(10) {
			@Override
			protected void paintComponent(Graphics g) {
				if (!isOpaque() && getBorder() instanceof RoundedCornerBorder) {
					Graphics2D g2 = (Graphics2D) g.create();
					g2.setPaint(getBackground());
					g2.fill(((RoundedCornerBorder) getBorder()).getBorderShape(0, 0, getWidth() - 1, getHeight() - 1));
					g2.dispose();
				}
				super.paintComponent(g);
			}

			@Override
			public void updateUI() {
				super.updateUI();
				setOpaque(false);
				setBorder(new RoundedCornerBorder());
			}
		};
		signupidField.setEditable(true);
		signupidField.setFont(new Font("Arial Black", Font.BOLD, 23));
		signupidField.setBackground(new Color(232, 232, 232, 120));
		IDPanel.add(ID);
		IDPanel.add(signupidField);

		// password panel
		JPanel passwordPanel = new JPanel();
		passwordPanel.setOpaque(false);
		passwordPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		passwordPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
		JLabel password = new JLabel("PASSWORD    ");
		password.setFont(new Font("Arial Black", Font.PLAIN, 20));
		signuppasswordField = new JPasswordField(10) {
			protected void paintComponent(Graphics g) {
				if (!isOpaque() && getBorder() instanceof RoundedCornerBorder) {
					Graphics2D g2 = (Graphics2D) g.create();
					g2.setPaint(getBackground());
					g2.fill(((RoundedCornerBorder) getBorder()).getBorderShape(0, 0, getWidth() - 1, getHeight() - 1));
					g2.dispose();
				}
				super.paintComponent(g);
			}

			public void updateUI() {
				super.updateUI();
				setOpaque(false);
				setBorder(new RoundedCornerBorder());
			}
		};
		signuppasswordField.setEditable(true);
		signuppasswordField.setFont(new Font("Arial Black", Font.BOLD, 23));
		signuppasswordField.setBackground(new Color(232, 232, 232, 120));
		// whether to show the password or not
		signuppasswordField.setAlignmentX(Component.RIGHT_ALIGNMENT);
		AbstractDocument doc = (AbstractDocument) signuppasswordField.getDocument();
		doc.setDocumentFilter(new DocumentFilter());
		AbstractButton b = new JToggleButton(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AbstractButton c = (AbstractButton) e.getSource();
				Character ec = c.isSelected() ? 0 : (Character) UIManager.get("PasswordField.echoChar");
				signuppasswordField.setEchoChar(ec);
			}
		});
		b.setFocusable(false);
		b.setOpaque(false);
		b.setContentAreaFilled(false);
		b.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 4));
		b.setAlignmentX(Component.RIGHT_ALIGNMENT);
		b.setAlignmentY(Component.CENTER_ALIGNMENT);
		b.setIcon(new ColorIcon(Color.GREEN));
		b.setRolloverIcon(new ColorIcon(Color.GRAY));
		b.setSelectedIcon(new ColorIcon(Color.RED));
		b.setRolloverSelectedIcon(new ColorIcon(Color.GRAY));
		JPanel panel = new JPanel() {
			public boolean isOptimizedDrawingEnabled() {
				return false;
			}
		};
		panel.setLayout(new OverlayLayout(panel));
		panel.setOpaque(false);
		panel.add(b);
		panel.add(signuppasswordField);
		// password panel adding
		passwordPanel.add(password);
		passwordPanel.add(panel);

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

		// set 'cancel' and 'sign up and login' button
		JPanel buttons = new JPanel();
		buttons.setOpaque(false);
		buttons.setLayout(new GridLayout(1, 2));
		buttons.setBorder(new EmptyBorder(20, 40, 20, 40));
		signuplogin.setFont(new Font("Arial Black", Font.PLAIN, 18));
		signupcancel.setFont(new Font("Arial Black", Font.PLAIN, 18));
		buttons.add(signupcancel);
		buttons.add(signuplogin);

		// sign up adding
		Signup.add(IDPanel);
		Signup.add(passwordPanel);
		Signup.add(verifycodePanel);
		Signup.add(buttons);

	}
	
	/**
	 * Initialize sign up error panel
	 */
	private void initSignuperror() {
		signuperrorText.setFont(new Font("Dialog", Font.BOLD, 28));
		signuperrorText.setForeground(new Color(255, 0, 0));
		backsignuperror.setFont(new Font("Arial Black", Font.BOLD, 28));
		Signuperror.setLayout(new GridLayout(2, 1, 0, 0));
		Signuperror.setOpaque(false);
		Signuperror.add(signuperrorText);
		Signuperror.add(backsignuperror);
	}

	private void initSignuperror1() {
		signuperror1Text.setFont(new Font("Dialog", Font.BOLD, 28));
		signuperror1Text.setForeground(new Color(255, 0, 0));
		backsignuperror1.setFont(new Font("Arial Black", Font.BOLD, 28));
		Signuperror1.setLayout(new GridLayout(2, 1, 0, 0));
		Signuperror1.setOpaque(false);
		Signuperror1.add(signuperror1Text);
		Signuperror1.add(backsignuperror1);
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
		
		
		this.Signup.setBounds(signupSetCenter.width - (signupSetWidth / 2),
				signupSetCenter.height - (signupSetHeight / 2), signupSetWidth, signupSetHeight);
		verifycodeField.setText(getRandomString(6));
		
		layeredPane.add(Signup, new Integer(2));
		
		this.add(layeredPane);
		
		this.Signuperror.setBounds(signuperrorCenter.width - (signuperrorWidth / 2),
				signuperrorCenter.height - (signuperrorHeight / 2), signuperrorWidth, signuperrorHeight);

		this.Signuperror1.setBounds(signuperrorCenter1.width - (signuperror1Width / 2),
				signuperrorCenter1.height - (signuperror1Height / 2), signuperror1Width, signuperror1Height);
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