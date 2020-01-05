import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.OverlayLayout;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;

public class SignInUI extends JPanel {

	private UIMainFrame mUIMainFrame;
	private JLayeredPane layeredPane;
	private JLabel background = new JLabel();
	final int frameWidth = 1152, frameHeight = 720;
	
	// attribute of title
	private JPanel title = new JPanel();
	final private int titleWidth = 930, titleHeight = 80;
	final private Dimension titleCenter = new Dimension(frameWidth / 2, frameHeight / 4);
	private JLabel titleText = new JLabel("     HOTEL     ", JLabel.CENTER);
	
	// attribute of sign in
	private JPanel Signin = new JPanel();
	final private int signinSetWidth = 600, signinSetHeight = 210;
	final private Dimension signinSetCenter = new Dimension(frameWidth / 2, 450);
	private JLabel signinlogin = new JLabel("LOGIN", JLabel.CENTER);
	private JLabel signinback = new JLabel("BACK", JLabel.CENTER);
	protected JTextField signinidField = new JTextField();
	protected JPasswordField signinpasswordField = new JPasswordField();
		
	// attribute of sign in error - UNKNOWN ID
	private JPanel Signinerror = new JPanel();
	final private int signinerrorWidth = 700, signinerrorHeight = 110;
	final private Dimension signinerrorCenter = new Dimension(frameWidth / 2, 500);
	private JLabel signinerrorText = new JLabel("UNKNOWN ID.", JLabel.CENTER);
	private JLabel backsigninerror = new JLabel("BACK", JLabel.CENTER);

	// attribute of sign in error - WRONG PASSWORD
	private JPanel Signinerror1 = new JPanel();
	final private int signinerror1Width = 700, signinerror1Height = 110;
	final private Dimension signinerror1Center = new Dimension(frameWidth / 2, 500);	
	private JLabel signinerror1Text = new JLabel("WRONG PASSWORD.", JLabel.CENTER);
	private JLabel backsigninerror1 = new JLabel("BACK", JLabel.CENTER);
	
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
			if (e.getSource() == signinlogin) {
				String UserID = signinidField.getText();
				String Password = new String(signinpasswordField.getPassword());
				int re = SignInCheck(UserID, Password);
				if (re == 1) {
					System.out.println(DatabaseUtil.user.getUserID() + " Login!");
					if(DatabaseUtil.user.isManager) {
						mUIMainFrame.changeUI(UIMainFrame.UIStage.MANAGE);
					} else {
						mUIMainFrame.changeUI(UIMainFrame.UIStage.MENU);
					}
				} else if (re == 0) {
					// UserID doesn't exist.
					layeredPane.remove(Signin);
					layeredPane.add(Signinerror, new Integer(2));
					validate();
					repaint();
				} else if (re == -1) {
					// Wrong Password.
					layeredPane.remove(Signin);
					layeredPane.add(Signinerror1, new Integer(2));
					validate();
					repaint();
				}
				signinidField.setText("");
				signinpasswordField.setText("");
				signinlogin.setForeground(Color.black);
			} else if(e.getSource() == signinback) {
				mUIMainFrame.changeUI(UIMainFrame.UIStage.LOGIN);
			} else if (e.getSource() == backsigninerror || e.getSource() == backsigninerror1) {
				layeredPane.remove(Signinerror);
				layeredPane.remove(Signinerror1);
				layeredPane.add(Signin);
				signinidField.setText(null);
				signinpasswordField.setText(null);
				revalidate();
				backsigninerror.setForeground(Color.black);
				backsigninerror1.setForeground(Color.black);
			}
		}
	};
	
	public SignInUI(UIMainFrame mUIMainFrame) {
		this.mUIMainFrame = mUIMainFrame;
		initPanel();
		initTitle();
		initSignIn();
		initSigninerror();
		initSigninerror1();
		initLayerPane();
		signinlogin.addMouseListener(ml);
		signinback.addMouseListener(ml);
		backsigninerror.addMouseListener(ml);
		backsigninerror1.addMouseListener(ml);
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
	 * Initialize the Sign in Panel
	 */
	private void initSignIn() {
		Signin.setLayout(new GridLayout(3, 1));
		Signin.setOpaque(false);

		// enter ID Panel setting
		JPanel IDPanel = new JPanel();
		IDPanel.setOpaque(false);
		IDPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		IDPanel.setBorder(new EmptyBorder(20, 40, 10, 40));
		// enter ID
		JLabel ID = new JLabel("ID : ");
		ID.setFont(new Font("Serif", Font.BOLD, 24));
		signinidField = new JTextField(10) {
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
		signinidField.setEditable(true);
		signinidField.setFont(new Font("Serif", Font.BOLD, 23));
		signinidField.setBackground(new Color(232, 232, 232, 120));
		// ID Panel adding
		IDPanel.add(ID);
		IDPanel.add(signinidField);

		// enter password Panel setting
		JPanel passwordPanel = new JPanel();
		passwordPanel.setOpaque(false);
		passwordPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		passwordPanel.setBorder(new EmptyBorder(10, 40, 20, 40));
		// enter password
		JLabel password = new JLabel("PASSWORD : ");
		password.setFont(new Font("Serif", Font.BOLD, 24));
		signinpasswordField = new JPasswordField(11) {
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
		signinpasswordField.setEditable(true);
		signinpasswordField.setFont(new Font("Serif", Font.BOLD, 23));
		signinpasswordField.setBackground(new Color(232, 232, 232, 120));
		// whether to show the password or not
		signinpasswordField.setAlignmentX(Component.RIGHT_ALIGNMENT);
		AbstractDocument doc = (AbstractDocument) signinpasswordField.getDocument();
		doc.setDocumentFilter(new DocumentFilter());
		AbstractButton b = new JToggleButton(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				AbstractButton c = (AbstractButton) e.getSource();
				Character ec = c.isSelected() ? 0 : (Character) UIManager.get("PasswordField.echoChar");
				signinpasswordField.setEchoChar(ec);
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
		panel.add(signinpasswordField);
		// password Panel adding
		passwordPanel.add(password);
		passwordPanel.add(panel);

		// set 'back' and 'login' button
		JPanel buttons = new JPanel();
		buttons.setOpaque(false);
		buttons.setLayout(new GridLayout(1, 2));
		buttons.setBorder(new EmptyBorder(20, 140, 20, 140));
		signinlogin.setFont(new Font("Serif", Font.BOLD, 22));
		signinback.setFont(new Font("Serif", Font.BOLD, 22));
		signinlogin.setOpaque(false);
		signinback.setOpaque(false);
		buttons.add(signinback);
		buttons.add(signinlogin);

		// sign in adding
		Signin.add(IDPanel);
		Signin.add(passwordPanel);
		Signin.add(buttons);
	}
	
	/**
	 * initialize Sign in error panel
	 */
	private void initSigninerror() {
		signinerrorText.setFont(new Font("Dialog", Font.BOLD, 28));
		signinerrorText.setForeground(new Color(255, 0, 0));
		backsigninerror.setFont(new Font("Arial Black", Font.BOLD, 28));
		Signinerror.setLayout(new GridLayout(2, 1, 0, 0));
		Signinerror.setOpaque(false);
		Signinerror.add(signinerrorText);
		Signinerror.add(backsigninerror);
	}

	/**
	 * initialize Sign in error1 panel
	 */
	private void initSigninerror1() {
		signinerror1Text.setFont(new Font("Dialog", Font.BOLD, 28));
		signinerror1Text.setForeground(new Color(255, 0, 0));
		backsigninerror1.setFont(new Font("Arial Black", Font.BOLD, 28));
		Signinerror1.setLayout(new GridLayout(2, 1, 0, 0));
		Signinerror1.setOpaque(false);
		Signinerror1.add(signinerror1Text);
		Signinerror1.add(backsigninerror1);
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
		
		this.Signin.setBounds(signinSetCenter.width - (signinSetWidth / 2),
				signinSetCenter.height - (signinSetHeight / 2), signinSetWidth, signinSetHeight);
		layeredPane.add(Signin, new Integer(2));
		
		this.add(layeredPane);
		
		this.Signinerror.setBounds(signinerrorCenter.width - (signinerrorWidth / 2),
				signinerrorCenter.height - (signinerrorHeight / 2), signinerrorWidth, signinerrorHeight);

		this.Signinerror1.setBounds(signinerror1Center.width - (signinerror1Width / 2),
				signinerror1Center.height - (signinerror1Height / 2), signinerror1Width, signinerror1Height);
	}
	
	/**
	 * This method checks whether the User can be signed in.
	 * 
	 * @param UserID the current user's ID
	 * @param Password the current user's password
	 * @return int 1 if the user can be signed, 0 if user's ID is unknown, -1 if the password is wrong
	 */
	public static int SignInCheck(String UserID, String Password) { 
		User user = DatabaseUtil.getUser(UserID);
		System.out.println(user);
		DatabaseUtil.user = user;
		if (user == null) return 0;
		else if (!Password.equals(user.getPassword())) return -1;
		return 1;
	}
	
}