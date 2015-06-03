import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class BanTracker extends JFrame {

    private JPanel contentPane;
    private JLabel lblDesc;
    private JTextPane textPane;
    private JTextPane textPane_1;
    private JPanel panel_1;
    private JTextPane textPane_2;
    private JLabel lblProfileavatar;
    private JPanel panel_2;
    private JPanel panel_4;
    private JPanel panel_5;
    private JPanel panel_3;
    private JPanel panel_6;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

	// Set look and feel to Nimbus
	UIManager.put("nimbusBase", new Color(57, 105, 138));
	UIManager.put("control", new Color(214, 217, 223));

	try {
	    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		if ("Nimbus".equals(info.getName())) {
		    UIManager.setLookAndFeel(info.getClassName());
		    break;
		}
	    }
	} catch (Exception e) {
	    // If Nimbus is not available, you can set the GUI to another look
	    // and feel.
	}

	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    BanTracker frame = new BanTracker();
		    frame.setVisible(true);

		    // Code taken from
		    // http://stackoverflow.com/questions/6084039/create-custom-operation-for-setdefaultcloseoperation
		    // Uncomment code for option before closing.
		    WindowListener exitListener = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
			    // int confirm = JOptionPane.showOptionDialog(null,
			    // "Are You Sure to Close Application?",
			    // "Exit Confirmation", JOptionPane.YES_NO_OPTION,
			    // JOptionPane.QUESTION_MESSAGE, null, null, null);
			    // System.out.println("confirm ="+ confirm);
			    // if (confirm == JOptionPane.YES_OPTION) {
			    FileHandler.writeToFile("users.tmp",
				    FileHandler.getAllPlayers());
			    FileHandler.writeToFile("summaries.tmp",
				    FileHandler.getAllSummaries());
			    System.out.println("Finished saving...");
			    // System.exit(0);
			    // }
			}
		    };

		    // frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		    frame.addWindowListener(exitListener);
		    // Code taken from
		    // http://stackoverflow.com/questions/6084039/create-custom-operation-for-setdefaultcloseoperation

		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});

	System.out.println("Loading program...");
	FileHandler.readFromFile("users.tmp", "ArrayList<User>");
	FileHandler.readFromFile("summaries.tmp",
		"ArrayList<PlayerSummary.Player>");

    }

    /**
     * Create the frame.
     */
    public BanTracker() {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 640, 800);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);

	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	tabbedPane.setBounds(0, 0, 624, 761);
	tabbedPane.setFocusable(false);
	contentPane.add(tabbedPane);

	JPanel panelHome = new JPanel();
	tabbedPane.addTab("Home", null, panelHome, null);
	panelHome.setLayout(null);

	panel_1 = new JPanel();
	tabbedPane.addTab("Add Users", null, panel_1, null);
	panel_1.setLayout(null);

	lblDesc = new JLabel();
	lblDesc.setVerticalAlignment(SwingConstants.TOP);
	lblDesc.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
	lblDesc.setBounds(30, 11, 559, 301);
	lblDesc.setText("<html>This program allows you to track CSGO players for Overwatch and VAC bans. "
		+ "You may use this program to keep track of players from your competitive matchmaking games.<br><br>"
		+ "<u>Instructions:</u><br><br> To add multiple players at once<br>1) Once all players have connected, "
		+ "from the CSGO game console*, use the command \"<font color='red'>status</font>\" to obtain player"
		+ " information for the current competitive game.<br> 2) Copy and paste the output from the console "
		+ "into the text field in the \"Add Users\" tab and click \"Process Information\"<br><br>"
		// + "To add a single player by their Steam profile URL<br>"
		// + "1)"
		+ "<html>");
	panelHome.add(lblDesc);

	textPane = new JTextPane();
	JScrollPane scrollPane = new JScrollPane(textPane);
	scrollPane.setBounds(10, 40, 599, 345);
	panel_1.add(scrollPane);
	scrollPane
		.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JLabel lblPasteCsgoConsole_1 = new JLabel(
		"Paste CSGO console information here to add users");
	lblPasteCsgoConsole_1
		.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
	lblPasteCsgoConsole_1.setBounds(10, 11, 323, 18);
	panel_1.add(lblPasteCsgoConsole_1);

	JButton button_1 = new JButton("Process Information");
	button_1.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {

		String userInput = textPane.getText() + " ";
		String[] uInput = userInput.split("#");
		String comID = "";

		for (int i = 1; i < uInput.length; i++) {

		    if (!SteamDataParser.getSteamID(uInput[i]).equals(
			    "Invalid_Steam_ID")) {
			comID = SteamDataParser
				.convertToCommunityID(SteamDataParser
					.getSteamID(uInput[i]));
			// TODO Inform user that players have been added here..
		    }
		}

	    }
	});
	button_1.setBounds(10, 396, 599, 50);
	button_1.setFocusPainted(false);
	panel_1.add(button_1);

	panel_2 = new JPanel();
	tabbedPane.addTab("Recently Added Users", null, panel_2, null);
	panel_2.setLayout(null);

	panel_3 = new JPanel();
	tabbedPane.addTab("Find A User", null, panel_3, null);
	panel_3.setLayout(null);

	panel_4 = new JPanel();
	tabbedPane.addTab("Check Bans", null, panel_4, null);
	panel_4.setLayout(null);

	JButton button = new JButton("Check Bans");
	button.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {

		try {

		    Document doc = textPane_2.getDocument();

		    // Clear textpane for new information to be shown.
		    textPane_2.setText("");

		    int numBanned = 0;

		    String str = " has been VAC banned!";

		    for (int i = 0; i < FileHandler.getAllPlayers().size(); i++) {
			if (FileHandler.getAllPlayers().get(i).getVacBan() == true) {
			    if (UserManager.bannedSinceAdded(FileHandler
				    .getAllPlayers().get(i).getSteamId())) {
				numBanned++;
				doc.insertString(doc.getLength(), FileHandler
					.getAllPlayers().get(i).getSteamId()
					+ str, null);
				doc.insertString(doc.getLength(), "\n", null);
			    }

			}
		    }

		    if (numBanned == 0) {
			textPane_2.setText("No users have been VAC banned!");
		    }

		} catch (BadLocationException ex) {
		    ex.printStackTrace();
		}

	    }
	});
	button.setFocusPainted(false);
	button.setBounds(10, 11, 282, 23);
	panel_4.add(button);

	textPane_2 = new JTextPane();
	textPane_2.setBounds(65, 485, 314, 107);
	textPane_2.setEditable(false);
	JScrollPane scrollPane_2 = new JScrollPane(textPane_2);
	scrollPane_2.setBounds(10, 45, 599, 400);
	panel_4.add(scrollPane_2);
	scrollPane_2
		.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	panel_5 = new JPanel();
	tabbedPane.addTab("Statistics", null, panel_5, null);
	panel_5.setLayout(null);

	panel_6 = new JPanel();
	tabbedPane.addTab("Settings", null, panel_6, null);

    }
}
