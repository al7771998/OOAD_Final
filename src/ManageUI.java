import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
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

public class ManageUI extends JPanel {
	
	private UIMainFrame mUIMainFrame;
	private ManageController controller;
	
	private JLayeredPane layeredPane;
	private JLabel background = new JLabel();
	final int frameWidth = 1152, frameHeight = 720;
	
	// attribute of title
	private JPanel title = new JPanel();
	final private int titleWidth = 980, titleHeight = 60;
	final private Dimension titleCenter = new Dimension(frameWidth / 2, frameHeight / 20);
	private JLabel titleText = new JLabel("     Order List     ", JLabel.CENTER);
	
	// attribute of order list
	private JPanel orderlist = new JPanel();
	final private int orderlistWidth = 1000, orderlistHeight = 600;
	final private Dimension orderlistCenter = new Dimension(frameWidth / 2, frameHeight / 2);
	JTable orderlistTable = new JTable();

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
			}
		}
	};
	
	public ManageUI(UIMainFrame mUImainFrame) {
		this.mUIMainFrame = mUImainFrame;
		controller = new ManageController();
		initPanel();
		initTitle();
		orderlist.setLayout(new BorderLayout());
		orderlist.setOpaque(false);
		initLayerPane();
		logout.addMouseListener(ml);
	}
	
	public void update_data() {
		controller.UpdateController();
		revalidate();
		repaint();
		orderlist.setOpaque(false);
		showorderlist(controller.makeOrderList());
	}
	
	private void initPanel() {
		setLayout(new GridLayout(1, 1));
		setOpaque(false);
	}
	
	private void initTitle() {
		titleText.setFont(new Font("Brush Script MT", Font.BOLD, 64));
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
		
		this.orderlist.setBounds(orderlistCenter.width - (orderlistWidth / 2),
				orderlistCenter.height - (orderlistHeight / 2), orderlistWidth, orderlistHeight);
		showorderlist(controller.makeOrderList());
		layeredPane.add(orderlist, new Integer(3));
		this.add(layeredPane);
		
		orderlistTable.setPreferredScrollableViewportSize(new Dimension(1000,600));
	}
	
	public void showorderlist(DefaultTableModel tablemodel) {
		orderlistTable = new JTable(tablemodel) {
			public boolean isCellEditable(int row, int column) {
				if (column == 12) {
					return true;
				}
				return false;
			}

		};
		orderlistTable.setOpaque(false);
		JTableHeader head = orderlistTable.getTableHeader();
		head.setFont(new Font("Arial", Font.PLAIN, 10));

		// row height
		orderlistTable.setRowHeight(40);
		// column width
		orderlistTable.getColumnModel().getColumn(0).setMaxWidth(100); // "ID", 
		orderlistTable.getColumnModel().getColumn(1).setMaxWidth(100); // "UserID"
		orderlistTable.getColumnModel().getColumn(2).setMaxWidth(100); // "HotelID"
		orderlistTable.getColumnModel().getColumn(3).setMaxWidth(100); // "Reservations"
		orderlistTable.getColumnModel().getColumn(4).setMaxWidth(300);// "Email"
		orderlistTable.getColumnModel().getColumn(5).setMaxWidth(70); // "ContactName"
		orderlistTable.getColumnModel().getColumn(6).setMaxWidth(70); // "ContactPhone"
		orderlistTable.getColumnModel().getColumn(7).setMaxWidth(70); // "CheckInDate"
		orderlistTable.getColumnModel().getColumn(8).setMaxWidth(70); // "CheckOutDate"
		orderlistTable.getColumnModel().getColumn(9).setMaxWidth(70); // "SingleRoom Num"
		orderlistTable.getColumnModel().getColumn(10).setMaxWidth(70);// "DoubleRoom Num"
		orderlistTable.getColumnModel().getColumn(11).setMaxWidth(70);// "QuadRoom Num"
		orderlistTable.getColumnModel().getColumn(12).setMaxWidth(70);// "SumPrice" 
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
		for (int i = 0; i <= 12; i++) {
			orderlistTable.getColumn(controller.heading[i]).setCellRenderer(ter);
		}

		// build up Table
		JScrollPane orderlistJScrollPane = new JScrollPane(orderlistTable,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		//buttonsColumn = new ButtonColumn(orderlistTable, 8);

		// set 'back' and 'reserve' button
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 1));
		buttons.setOpaque(false);
		buttons.setBorder(new EmptyBorder(20, 40, 20, 40));
		logout.setFont(new Font("Dialog", Font.BOLD, 25));
		buttons.add(logout);

		orderlist.removeAll();
		//orderlist.add(choicepanel, BorderLayout.NORTH);
		orderlist.add(orderlistJScrollPane, BorderLayout.CENTER);
		orderlist.add(buttons, BorderLayout.SOUTH);
	}
	
}