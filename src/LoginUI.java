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

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 * @author b06505032, b06505017, b06505054, b06902023
 *
 */
public class LoginUI extends JPanel {
	
	private UIMainFrame mUImainFrame;
	private JLayeredPane layeredPane;
	private JLabel background = new JLabel();
	final int frameWidth = 1152, frameHeight = 720;
	
	// attribute of title
	private JPanel title = new JPanel();
	final private int titleWidth = 930, titleHeight = 80;
	final private Dimension titleCenter = new Dimension(frameWidth / 2, frameHeight / 4);
	private JLabel titleText = new JLabel("     HOTEL     ", JLabel.CENTER);

	// attribute of sub menu
	private JPanel subMenu = new JPanel();
	final private int subMenuWidth = 500, subMenuHeight = 70;
	final private Dimension subMenuCenter = new Dimension(frameWidth / 2, frameHeight / 4 * 3);
	private JLabel signinText = new JLabel("SIGN IN", JLabel.CENTER);
	private JLabel signupText = new JLabel("SIGN UP", JLabel.CENTER);
	
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
			if (e.getSource() == signupText) {
				mUImainFrame.changeUI(UIMainFrame.UIStage.SIGNUP);
			} else if(e.getSource() == signinText) {
				mUImainFrame.changeUI(UIMainFrame.UIStage.SIGNIN);
			}
		}
	};
	
	public LoginUI(UIMainFrame mUImainFrame) {
		this.mUImainFrame = mUImainFrame;
		initPanel();
		initTitle();
		initSubMenu();
		initLayerPane();
		// buttons in sub menu / sign in / sign up
		signinText.addMouseListener(ml);
		signupText.addMouseListener(ml);
	}
	
	private void initPanel() {
		setLayout(new GridLayout(1, 1));
		setOpaque(false);
	}
	
	private void initTitle() {
		titleText.setFont(new Font("Brush Script MT", Font.BOLD, 96));
		signinText.setFont(new Font("Arial Black", Font.BOLD, 30));
		signupText.setFont(new Font("Arial Black", Font.BOLD, 30));
		title.setLayout(new GridLayout(1, 1, 0, 0));
		title.setOpaque(false);
		titleText.setForeground(new Color(65, 105, 225));
		titleText.setOpaque(false);
		titleText.setBorder(new EmptyBorder(5, 5, 5, 5));
		title.add(titleText);
	}
	
	private void initSubMenu() {
		subMenu.setLayout(new GridLayout(1, 2, 0, 0));
		subMenu.setOpaque(false);
		subMenu.setBackground(null);
		subMenu.add(signinText);
		subMenu.add(signupText);
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

		this.subMenu.setBounds(subMenuCenter.width - (subMenuWidth / 2), subMenuCenter.height - (subMenuHeight / 2),
				subMenuWidth, subMenuHeight);
		layeredPane.add(subMenu, new Integer(2));

		this.add(layeredPane);
	}
}
