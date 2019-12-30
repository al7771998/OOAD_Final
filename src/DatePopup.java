import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.lang.*;

public class DatePopup extends JDialog implements java.awt.event.ActionListener {
    private JPanel mPanel1 = new JPanel();
    private JPanel mPanel2 = new JPanel();
    private JPanel mPanel3 = new JPanel();
    private JPanel mPanel4 = new JPanel();
    private JButton mNewButton;
    private JTextField mText;
    private int mDay;
    private int mMonth;
    private int mYear;
    private int mTheDate;
    private int i = 0;
    private int j = 0;

    public DatePopup(JTextField pText) {
        super(new JFrame(), "Select the Date", true);
        this.mText = pText;
        try {
            DatePopupInit();
            pack();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DatePopupInit() {

    	Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        mPanel1.setLayout(new BorderLayout());
        mPanel1.setBorder(new TitledBorder(BorderFactory.createLineBorder(
                Color.white, 1), "Select the Date"));

        Calendar nowCalendar = Calendar.getInstance();
        this.mTheDate = nowCalendar.get(Calendar.DAY_OF_WEEK);
        this.mDay = nowCalendar.get(Calendar.DAY_OF_MONTH);
        this.mMonth = nowCalendar.get(Calendar.MONTH);
        this.mYear = nowCalendar.get(Calendar.YEAR);


		JComboBox comboMonth = new JComboBox();
        comboMonth.setActionCommand("MonthSelect");
        for(i = 1; i <= 12; i++) {
            comboMonth.addItem(i + " ");
            if (i == (this.mMonth + 1)) {
                comboMonth.setSelectedItem(i + " ");
            }
        }
        comboMonth.addActionListener(this);
        JComboBox comboYear = new JComboBox();
        comboYear.setActionCommand("YearSelect");
        for (i = 2019; i <= 2200; i++) {
            comboYear.addItem(i);
            if (i == this.mYear) {
                comboYear.setSelectedItem(i);
            }
        }
        comboYear.addActionListener(this);
        mPanel2.setLayout(new FlowLayout());
        mPanel2.add(comboMonth);
        mPanel2.add(comboYear);
        mPanel1.add(mPanel2, BorderLayout.NORTH);


        mPanel3.setLayout(new GridLayout(7, 7));
        mPanel3.add(new JLabel("Sun.", SwingConstants.CENTER));
        mPanel3.add(new JLabel("Mon.", SwingConstants.CENTER));
        mPanel3.add(new JLabel("Tue.", SwingConstants.CENTER));
        mPanel3.add(new JLabel("Wed.", SwingConstants.CENTER));
        mPanel3.add(new JLabel("Thu.", SwingConstants.CENTER));
        mPanel3.add(new JLabel("Fri.", SwingConstants.CENTER));
        mPanel3.add(new JLabel("Sat.", SwingConstants.CENTER));
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(this.mYear, this.mMonth + 1, 1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(this.mYear, this.mMonth, 1);
        int daysOfMonth;
        if (this.mMonth == 11) {
            daysOfMonth = 31;
        } else {
            daysOfMonth = calendar1.get(Calendar.DAY_OF_YEAR)
                    - calendar2.get(Calendar.DAY_OF_YEAR);
        }
        int firstDay = calendar2.get(Calendar.DAY_OF_WEEK);
        for (i = 1; i < 43; i++) {
            if (((i == firstDay) || (j != 0)) && (j < daysOfMonth)) {
                j++;
                mNewButton = new JButton((new String()).valueOf(j));
                if (j == this.mDay) {
                    mNewButton.setSelected(true);
                    mNewButton.setEnabled(false);
                    mNewButton.setBackground(new Color(84, 215, 4));
                }
                mNewButton.addActionListener(this);
                mPanel3.add(mNewButton);
            } else {
                mPanel3.add(new JLabel());
            }
        }
        mPanel1.add(mPanel3, BorderLayout.CENTER);


        JButton confirmButton = new JButton("confirm");
        confirmButton.setActionCommand("confirm");
        confirmButton.addActionListener(this);
        JButton cancelButton = new JButton("cancel");
        cancelButton.setActionCommand("cancel");
        cancelButton.addActionListener(this);
        mPanel4.setLayout(new FlowLayout());
        mPanel4.add(confirmButton);
        mPanel4.add(cancelButton);

        contentPane.add(mPanel1, BorderLayout.CENTER);
        contentPane.add(mPanel4, BorderLayout.SOUTH);
    }

    
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        Object obj = e.getSource();
        String temp = "";
        if (cmd.equals("MonthSelect")) {
            JComboBox comMonth = (JComboBox) obj;
            temp = comMonth.getSelectedItem().toString();
            temp = temp.substring(0, temp.length() - 1);
            int tempMonth = Integer.parseInt(temp);
            this.mMonth = tempMonth - 1;
            mypaint();
        } else if (cmd.equals("YearSelect")) {
            JComboBox comYear = (JComboBox) obj;
            temp = comYear.getSelectedItem().toString();
            temp = temp.substring(0, 4);
            int tempYear = Integer.parseInt(temp);
            this.mYear = tempYear;
            mypaint();
        } else if (cmd.equals("confirm")) {
            String pFinalDate = "";
            if (this.mMonth < 9) {
                pFinalDate = this.mYear + "/" + 0 + (this.mMonth + 1);
            } else {
                pFinalDate = this.mYear + "/" + (this.mMonth + 1);
            }
            if (this.mDay < 10) {
                pFinalDate = pFinalDate + "/" + 0 + this.mDay;
            } else {
                pFinalDate = pFinalDate + "/" + this.mDay;
            }
            this.mText.setText(pFinalDate);
            this.dispose();
        } else if (cmd.equals("cancel")) {
            this.dispose();
        } else {
            int tempDay = Integer.parseInt(cmd);
            this.mDay = tempDay;
            mypaint();
            // System.out.println(cmd);
        }
    }

    public void mypaint() {
        this.invalidate();
        mPanel3.removeAll();
        mPanel3.setLayout(new GridLayout(7, 7));
        mPanel3.add(new JLabel("Sun.", SwingConstants.CENTER));
        mPanel3.add(new JLabel("Mon.", SwingConstants.CENTER));
        mPanel3.add(new JLabel("Tue.", SwingConstants.CENTER));
        mPanel3.add(new JLabel("Wed.", SwingConstants.CENTER));
        mPanel3.add(new JLabel("Thu.", SwingConstants.CENTER));
        mPanel3.add(new JLabel("Fri.", SwingConstants.CENTER));
        mPanel3.add(new JLabel("Sat.", SwingConstants.CENTER));
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(this.mYear, this.mMonth + 1, 1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(this.mYear, this.mMonth, 1);
        int daysOfMonth;
        if (this.mMonth == 11) {
            daysOfMonth = 31;
        } else {
            daysOfMonth = calendar1.get(Calendar.DAY_OF_YEAR)
                    - calendar2.get(Calendar.DAY_OF_YEAR);
        }
        int firstDay = calendar2.get(Calendar.DAY_OF_WEEK);
        j = 0;
        for (i = 1; i < 43; i++) {
            if (((i == firstDay) || (j != 0)) && (j < daysOfMonth)) {
                j++;
                mNewButton = new JButton((new String()).valueOf(j));
                if (j == this.mDay) {
                    mNewButton.setSelected(true);
                    mNewButton.setEnabled(false);
                    mNewButton.setBackground(new Color(84, 215, 4));
                }
                mNewButton.addActionListener(this);
                mPanel3.add(mNewButton);
            } else {
                mPanel3.add(new JLabel());
            }
        }
        mPanel1.add(mPanel3, BorderLayout.CENTER);
        this.validate();
    }
    
    public void showDialog(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int)(screenSize.getWidth()-getWidth())/2,(int)(screenSize.getHeight()-getHeight())/2);
        setVisible(true);
    }
}