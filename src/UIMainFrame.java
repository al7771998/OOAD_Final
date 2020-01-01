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
		LOGIN,SIGNUP,SIGNIN,ROOM,SEARCH,SEARCH_RESULT,BOOK,MANAGE
	}
	
	private JPanel loginUI;
	private JPanel signUpUI;
	private JPanel signInUI;
	private JPanel searchUI;
	private JPanel manageUI;

	// Program constructor
	public UIMainFrame() {
		initFrame();
		loginUI  = new LoginUI(this);
		signUpUI = new SignUpUI(this);
		signInUI = new SignInUI(this);
		searchUI = new SearchUI(this);
		manageUI = new ManageUI(this);
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
				this.setContentPane(searchUI);
				this.revalidate();
				System.out.println("change to search");
				break;
			case MANAGE:
				this.setContentPane(manageUI);
				this.revalidate();
				System.out.println("change to manage");
				break;
		}
	}
	
	public void changeUI(UIStage next, String CID, String COD, ArrayList<AvailableHotelRoom> AHR) {
		if(next == UIStage.SEARCH_RESULT) {
			this.setContentPane(new SearchResultUI( this, CID, COD, AHR));
			this.revalidate();
			System.out.println("change to search result");
		}
	}
	
	public void changeUI(UIStage next, String CID, String COD, int HotelID, int sn, int dn, int qn) {
		if(next == UIStage.SEARCH_RESULT) {
			this.setContentPane(new BookUI( this, CID, COD, HotelID, sn, dn, qn));
			this.revalidate();
			System.out.println("change to book");
		}
	}
}
