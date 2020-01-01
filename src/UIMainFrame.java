import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.MatteBorder;

public class UIMainFrame extends JFrame {
	final public int frameWidth = 1152, frameHeight = 720;
	
	//panel code
	public enum UIStage{
		LOGIN,SIGNUP,SIGNIN,SEARCH,SEARCH_RESULT,BOOK,MANAGE,BROWSE,MENU,MODIFY
	}
	
	private JPanel loginUI;
	private JPanel signUpUI;
	private JPanel signInUI;
	private JPanel searchUI;
	private JPanel manageUI;
	private JPanel browseUI;
	private JPanel searchResultUI;
	private JPanel menuUI;

	// Program constructor
	public UIMainFrame() {
		initFrame();
		loginUI  = new LoginUI(this);
		signUpUI = new SignUpUI(this);
		signInUI = new SignInUI(this);
		searchUI = new SearchUI(this);
		manageUI = new ManageUI(this);
		browseUI = new BrowseUI(this);
		menuUI = new MenuUI(this);
		this.setContentPane(loginUI);
        this.setVisible(true);
	}

	// Initialize the frame
	private void initFrame() {
		this.setTitle("HOTEL");
		this.setSize(frameWidth, frameHeight);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
	}

	public static void main(String[] args) {
		new UIMainFrame();
	}
	
	public void changeUI(UIStage next) {
		switch(next) {
			case LOGIN:
				this.setContentPane(loginUI);
				this.revalidate();
				System.out.println("change to login");
				break;
			case SIGNUP:
				this.setContentPane(signUpUI);
				this.revalidate();
				System.out.println("change to signup");
				break;
			case SIGNIN:
				this.setContentPane(signInUI);
				this.revalidate();
				System.out.println("change to signin");
				break;
			case SEARCH:
				((SearchUI)searchUI).updateTitle();
				this.setContentPane(searchUI);
				this.revalidate();
				System.out.println("change to search");
				break;
			case MANAGE:
				this.setContentPane(manageUI);
				this.revalidate();
				System.out.println("change to manage");
				break;
			case SEARCH_RESULT:
				this.setContentPane(searchResultUI);
				this.revalidate();
				System.out.println("change to search result");
				break;
			case BROWSE:
				((BrowseUI)browseUI).update_login_data();
				this.setContentPane(browseUI);
				this.revalidate();
				System.out.println("change to browse");
				break;
			case MENU:
				this.setContentPane(menuUI);
				this.revalidate();
				System.out.println("change to menu");
				break;
		}
	}
	
	public void changeUI(UIStage next, int people, String CID, String COD, ArrayList<AvailableHotelRoom> AHR) {
		if(next == UIStage.SEARCH_RESULT) {
			searchResultUI = new SearchResultUI( this, people, CID, COD, AHR);
			this.setContentPane(searchResultUI);
			this.revalidate();
			System.out.println("change to search result");
		}
	}
	
	public void changeUI(UIStage next, int people, String CID, String COD, int HotelID, int sn, int dn, int qn) {
		if(next == UIStage.BOOK) {
			this.setContentPane(new BookUI( this, people, CID, COD, HotelID, sn, dn, qn));
			this.revalidate();
			System.out.println("change to book");
		}
	}
	
	public void changeUI(UIStage next, int reservationID, String CID, String COD, int HotelID, int sn, int dn, int qn, int sumPrice) {
		if(next == UIStage.MODIFY) {
			this.setContentPane(new ModifyUI(this, reservationID, CID, COD, HotelID, sn, dn, qn, sumPrice));
			this.revalidate();
			System.out.println("change to book");
		}
	}
}
