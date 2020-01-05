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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class MenuUI extends JPanel {
	
	private UIMainFrame mUIMainFrame;
	
	private JLayeredPane layeredPane;
	private JLabel background = new JLabel();
	final int frameWidth = 1152, frameHeight = 720;
	
	// attribute of title
	private JPanel title = new JPanel();
	final private int titleWidth = 930, titleHeight = 120;
	final private Dimension titleCenter = new Dimension(frameWidth / 2, frameHeight / 4);
	private JLabel titleText = new JLabel("     Hotel     ", JLabel.CENTER);

	// attribute of sub menu
	private JPanel subMenu = new JPanel();
	final private int subMenuWidth = 1200, subMenuHeight = 700;
	final private Dimension subMenuCenter = new Dimension(frameWidth / 2, frameHeight / 10 * 6);
	private JLabel search = new JLabel("SEARCH", JLabel.CENTER);
	private JLabel browse = new JLabel("BROWSE", JLabel.CENTER);
	private JLabel logout = new JLabel("LOGOUT", JLabel.CENTER);
	
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
			if(e.getSource() == logout) {
				mUIMainFrame.changeUI(UIMainFrame.UIStage.LOGIN);
				logout.setForeground(Color.black);
			}
			if(e.getSource() == browse) {
				mUIMainFrame.changeUI(UIMainFrame.UIStage.BROWSE);
				browse.setForeground(Color.black);
			}
			if(e.getSource() == search) {
				mUIMainFrame.changeUI(UIMainFrame.UIStage.SEARCH);
				search.setForeground(Color.black);
			}
		}
	};
	
	public MenuUI(UIMainFrame mUImainFrame) {
		this.mUIMainFrame = mUImainFrame;
		initPanel();
		initTitle();
		initSubMenu();
		initLayerPane();
		logout.addMouseListener(ml);
		browse.addMouseListener(ml);
		search.addMouseListener(ml);
	}
	
	private void initPanel() {
		setLayout(new GridLayout(1, 1));
		setOpaque(false);
	}
	
	private void initTitle() {
		
        title.setOpaque(false);
		title.setLayout(new GridBagLayout());
		
		JLabel welcomeText = new JLabel(" welcome ", JLabel.CENTER);
		if (DatabaseUtil.user != null)
			welcomeText = new JLabel(" welcome, " + DatabaseUtil.user.getUserID(), JLabel.CENTER);
		
		titleText.setFont(new Font("Brush Script MT", Font.BOLD, 80));
		titleText.setForeground(new Color(65, 105, 225));
		titleText.setOpaque(false);
		welcomeText.setFont(new Font("Brush Script MT", Font.BOLD, 32));
		welcomeText.setForeground(new Color(65, 105, 225));
		welcomeText.setOpaque(false);
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		title.add(titleText, c, 0);

		c.ipady = 30;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		title.add(welcomeText, c, 1);

	}
	
	private void initSubMenu() {

		JLabel blank = new JLabel("      ", JLabel.CENTER);
		browse.setFont(new Font("Serif", Font.BOLD, 30));
		search.setFont(new Font("Serif", Font.BOLD, 30));
		blank.setFont(new Font("Serif", Font.BOLD, 30));
		logout.setFont(new Font("Serif", Font.BOLD, 22));
		logout.setBorder(new EmptyBorder(50, 0, 0, 0));
		
		subMenu.setLayout(new GridBagLayout());
		subMenu.setOpaque(false);
		subMenu.setBackground(null);
		boolean test = false;
		GridBagConstraints c = new GridBagConstraints();
		Button button1 = new Button("test1");
		Button button2 = new Button("test2");
		Button button3 = new Button("test3");
		Button button4 = new Button("test4");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		subMenu.add(test? button1:search, c, 0);
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 1;
		subMenu.add(test? button2:blank, c, 1);
		c.gridx = 3;
		c.gridy = 0;
		c.gridwidth = 2;
		subMenu.add(test? button3:browse, c, 2);
		c.ipady = 80;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 5;
		subMenu.add(test? button4:logout, c, 3);
		

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
	
}