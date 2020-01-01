import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

public class ManageUI extends JPanel {
	
	private UIMainFrame mUIMainFrame;
	//private ManageController controller;
	
	private JLayeredPane layeredPane;
	private JLabel background = new JLabel();
	final int frameWidth = 1152, frameHeight = 720;
	
	// attribute of title
	private JPanel title = new JPanel();
	final private int titleWidth = 930, titleHeight = 80;
	final private Dimension titleCenter = new Dimension(frameWidth / 2, frameHeight / 4);
	private JLabel titleText = new JLabel("     MANAGE     ", JLabel.CENTER);
	
	// attribute of order list
	private JPanel orderlist = new JPanel();
	final private int orderlistWidth = 820, orderlistHeight = 600;
	final private Dimension orderlistCenter = new Dimension(frameWidth / 2, frameHeight / 2);
	JTable orderlistTable = new JTable();
	String[] heading = new String[] { "ID","UserID", "HotelID",
	"Reservations",
	"Email",
	"ContactName",
	"ContactPhone",
	"CheckInDate",
	"CheckOutDate",
	"SumPrice" };

	private JLabel backhotellist = new JLabel("BACK", JLabel.CENTER);
	private JLabel reservehotellist = new JLabel("RESERVE", JLabel.CENTER);
	
	public ManageUI(UIMainFrame mUImainFrame) {
		this.mUIMainFrame = mUImainFrame;
		initPanel();
		initTitle();
		initLayerPane();

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
	
	private void initLayerPane() {
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(frameWidth, frameHeight));

		this.background.setIcon(new ImageIcon("images/Menu/background.jpg"));
		this.background.setBounds(0, 0, frameWidth, frameHeight);
		layeredPane.add(background, new Integer(0));

		this.title.setBounds(titleCenter.width - (titleWidth / 2), titleCenter.height - (titleHeight / 2), titleWidth,
				titleHeight);
		layeredPane.add(title, new Integer(1));
		this.add(layeredPane);
	}
	
}