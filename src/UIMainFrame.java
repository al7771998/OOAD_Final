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
		LOGIN,SIGNUP,SIGNIN,ROOM,SEARCH
	}
	
	private JPanel loginUI;
	private JPanel signUpUI;
	private JPanel signInUI;
	private JPanel searchUI;

	// Program constructor
	public UIMainFrame() {
		initFrame();
		loginUI  = new LoginUI(this);
		signUpUI = new SignUpUI(this);
		signInUI = new SignInUI(this);
		searchUI = new SearchUI(this);
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
		//this.getContentPane().removeAll();
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
		}
	}
	
}
