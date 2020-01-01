import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class SearchResultUI extends JPanel {
	
	private UIMainFrame mUIMainFrame;
	private SearchResultController controller;
	
	private JLayeredPane layeredPane;
	private JLabel background = new JLabel();
	final int frameWidth = 1152, frameHeight = 720;
	
	// attribute of hotel list
	private JPanel Hotellist = new JPanel();
	final private int hotellistWidth = 820, hotellistHeight = 600;
	final private Dimension hotellistCenter = new Dimension(frameWidth / 2, frameHeight / 2);
	JTable HotellistTable = new JTable();
	String[] heading = new String[] { "ID", "Star", "City", "Address", "Single", "Double", "Quad", "Price", "Select" };
	private JLabel showallText = new JLabel("SHOW   ALL", JLabel.CENTER);
	private JLabel backhotellist = new JLabel("BACK", JLabel.CENTER);
	private JLabel reservehotellist = new JLabel("RESERVE", JLabel.CENTER);
	
	private JPanel star = new JPanel();
	private JLabel star5 = new JLabel("5-star", JLabel.CENTER);
	private JLabel star4 = new JLabel("4-star", JLabel.CENTER);
	private JLabel star3 = new JLabel("3-star", JLabel.CENTER);
	private JLabel star2 = new JLabel("2-star", JLabel.CENTER);
	private JLabel pricehighText = new JLabel("PRICE (HIGHEST FIRST)", JLabel.CENTER);
	private JLabel pricelowText = new JLabel("PRICE (LOWEST FIRST)", JLabel.CENTER);
	
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
			layeredPane.remove(Hotellist);
			if (e.getSource() == showallText) {
				DefaultTableModel dtm = controller.makeHotellist();
				showHotellist(dtm);
				layeredPane.add(Hotellist, new Integer(3));
				validate();
				repaint();
				showallText.setForeground(Color.black);
			} else if (e.getSource() == pricehighText) { // show price high first
				ArrayList<AvailableHotelRoom> nAHR = controller.SortByPrice(0);
				DefaultTableModel dtm = controller.makeHotellist(nAHR);
				showHotellist(dtm);

				layeredPane.add(Hotellist, new Integer(3));
				validate();
				repaint();
				pricehighText.setForeground(Color.black);
			} else if (e.getSource() == pricelowText) { // show price low first
				ArrayList<AvailableHotelRoom> nAHR = controller.SortByPrice(1);
				DefaultTableModel dtm = controller.makeHotellist(nAHR);
				showHotellist(dtm);

				layeredPane.add(Hotellist, new Integer(3));
				validate();
				repaint();
				pricelowText.setForeground(Color.black);
			} else if (e.getSource() == star5) { // show star 5 hotel
				ArrayList<AvailableHotelRoom> nAHR = controller.SearchByStar(5);
				DefaultTableModel dtm =controller. makeHotellist(nAHR);
				showHotellist(dtm);

				layeredPane.add(Hotellist, new Integer(3));
				validate();
				repaint();
				star5.setForeground(Color.black);
			} else if (e.getSource() == star4) { // show star 4 hotel
				ArrayList<AvailableHotelRoom> nAHR = controller.SearchByStar(4);
				DefaultTableModel dtm = controller.makeHotellist(nAHR);
				showHotellist(dtm);

				layeredPane.add(Hotellist, new Integer(3));
				validate();
				repaint();
				star4.setForeground(Color.black);
			} else if (e.getSource() == star3) { // show star 3 hotel
				ArrayList<AvailableHotelRoom> nAHR = controller.SearchByStar(3);
				DefaultTableModel dtm = controller.makeHotellist(nAHR);
				showHotellist(dtm);

				layeredPane.add(Hotellist, new Integer(3));
				validate();
				repaint();
				star3.setForeground(Color.black);
			} else if (e.getSource() == star2) { // show star 2 hotel
				ArrayList<AvailableHotelRoom> nAHR = controller.SearchByStar(2);
				DefaultTableModel dtm = controller.makeHotellist(nAHR);
				showHotellist(dtm);

				layeredPane.add(Hotellist, new Integer(2));
				validate();
				repaint();
				star2.setForeground(Color.black);
			} else if (e.getSource() == backhotellist) {
				mUIMainFrame.changeUI(UIMainFrame.UIStage.SEARCH);
				validate();
				repaint();
				backhotellist.setForeground(Color.black);
			} else if (e.getSource() == reservehotellist) {
				//if(checkHotel(String CID, String COD, int HotelID, int sn, int dn, int qn)) {
					
				//}
				//TODO mUIMainFrame.changeUI()
				validate();
				repaint();
				reservehotellist.setForeground(Color.black);
			}
		}
	};
	
	public SearchResultUI(UIMainFrame mUIMainFrame, ArrayList<AvailableHotelRoom> AHR) {
		this.mUIMainFrame = mUIMainFrame;
		this.controller = new SearchResultController(AHR);
		
		initSearchTag();
		initLayerPane();
		
		DefaultTableModel dtm = controller.makeHotellist();
		showHotellist(dtm);

		layeredPane.add(Hotellist, new Integer(3));
		validate();
		repaint();
		showallText.setForeground(Color.black);
		
		// buttons in search
		star5.addMouseListener(ml);
		star4.addMouseListener(ml);
		star3.addMouseListener(ml);
		star2.addMouseListener(ml);
		pricehighText.addMouseListener(ml);
		pricelowText.addMouseListener(ml);
		// buttons in hotel list
		showallText.addMouseListener(ml);
		backhotellist.addMouseListener(ml);
		reservehotellist.addMouseListener(ml);
		
	}
	
	private void initSearchTag() {
		// set font
		star5.setFont(new Font("Dialog", Font.BOLD, 28));
		star4.setFont(new Font("Dialog", Font.BOLD, 28));
		star3.setFont(new Font("Dialog", Font.BOLD, 28));
		star2.setFont(new Font("Dialog", Font.BOLD, 28));
		pricehighText.setFont(new Font("Dialog", Font.BOLD, 28));
		pricelowText.setFont(new Font("Dialog", Font.BOLD, 28));
		star.setLayout(new GridLayout(1, 4, 0, 0));
		star.setOpaque(false);
		star.add(star5);
		star.add(star4);
		star.add(star3);
		star.add(star2);		
	}
	
	private void initLayerPane() {
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(frameWidth, frameHeight));

		this.background.setIcon(new ImageIcon("images/Menu/background.jpg"));
		this.background.setBounds(0, 0, frameWidth, frameHeight);
		layeredPane.add(background, new Integer(0));
		this.add(layeredPane);
		
		this.Hotellist.setBounds(hotellistCenter.width - (hotellistWidth / 2),
				hotellistCenter.height - (hotellistHeight / 2), hotellistWidth, hotellistHeight);
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
}

class ButtonColumn extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
	JTable table;
	JButton renderButton;
	JButton editButton;
	String text;
	
	Object hid;
	Object sroom;
	Object droom;
	Object qroom;

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
		hid = table.getModel().getValueAt(table.getSelectedRow(), 0);
		sroom = table.getModel().getValueAt(table.getSelectedRow(), 4);
		droom = table.getModel().getValueAt(table.getSelectedRow(), 5);
		qroom = table.getModel().getValueAt(table.getSelectedRow(), 6);
//		Menu.reservehotelid.setSelectedIndex((int) hid);
//		Menu.reservesingleroomField.setText(sroom.toString());
//		Menu.reservedoubleroomField.setText(droom.toString());
//		Menu.reservequadroomField.setText(qroom.toString());
	}
}