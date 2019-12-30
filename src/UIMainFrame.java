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
		LOGIN,SIGNUP,SIGNIN,ROOM
	}
	
	private JPanel loginUI;
	private JPanel signUpUI;

	// Program constructor
	public UIMainFrame() {
		initFrame();
		loginUI  = new LoginUI(this);
		signUpUI = new SignUpUI(this);
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
				break;
			case SIGNUP:
				this.setContentPane(signUpUI);
				this.revalidate();
				break;
			case SIGNIN:
				//this.setContentPane();
				System.out.print("123");
				break;			
		}
	}
	
}
