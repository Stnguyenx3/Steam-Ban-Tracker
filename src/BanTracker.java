import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
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
	private JPanel panel_1;
	private JTextPane textPane_2;
	private JPanel panel_2;
	private JPanel panel_4;
	private JPanel panel_5;
	private JPanel panel_3;
	private JPanel panel_6;
	private JTextPane textPane_1;
	private JScrollPane scrollPane_1;

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
					frame.setResizable(false);

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
							FileHandler.writeToFile("users.tmp", FileHandler.getAllPlayers());
							FileHandler.writeToFile("summaries.tmp", FileHandler.getAllSummaries());
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
		FileHandler.readFromFile("summaries.tmp", "ArrayList<PlayerSummary.Player>");

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
		tabbedPane.setBounds(0, 0, 640, 800);
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
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JLabel lblPasteCsgoConsole_1 = new JLabel("Paste CS:GO console output or Steam profile URL here");
		lblPasteCsgoConsole_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPasteCsgoConsole_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblPasteCsgoConsole_1.setBounds(10, 11, 599, 18);
		panel_1.add(lblPasteCsgoConsole_1);

		JButton button_1 = new JButton("Process Information");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String userInput = textPane.getText() + " ";
				String[] uInput = null;
				String[] comIDs = new String[50];
				int comIDCounter = 0;

				UserManager uMInstance = UserManager.getInstance();

				Document doc = textPane_1.getStyledDocument();

				textPane_1.setText("");

				if (userInput.contains("steamcommunity.com")) {

					// User provided a Steam profile URL
					if (userInput.contains("steamcommunity.com/id/")) {

					} else if (userInput.contains("steamcommunity.com/profiles/")) {

					} else {
						textPane_1.setText("An error has occured, please make sure the URL is valid!");
					}

				} else if (userInput.contains("#")) {

					uInput = userInput.split("#");
					String jsonData = "";

					textPane_1.setText("");

					if (uInput != null && uInput.length > 0) {

						for (int i = 1; i < uInput.length; i++) {

							if (!SteamDataParser.getSteamID(uInput[i]).equals("Invalid_Steam_ID")) {

								comIDs[comIDCounter] = SteamDataParser.convertToCommunityID(SteamDataParser
										.getSteamID(uInput[i]));
								comIDCounter++;

								if (comIDCounter >= 50) {
									textPane_1.setText("Only 50 players may be added at a time!");
								}

							}

						}

						jsonData = SteamWebAPI.getInfo(comIDs);

						// Add user
						uMInstance.addPlayer(jsonData, comIDs, comIDCounter);
						ArrayList<User> lA = uMInstance.getLastAdded();

						if (lA.size() > 0) {
							textPane_1.setText("The following users have been added:\n");
						}

						for (int i = 0; i < lA.size(); i++) {
							try {
								doc.insertString(doc.getLength(), lA.get(i).getSteamId() + "\n", null);
							} catch (BadLocationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						// Reset lastAdded for future use.
						uMInstance.clearLastAdded();

						// Reset comIDs for future use.
						for (int i = 0; i < comIDs.length; i++) {
							comIDs[i] = null;
						}

					}
				} else {

					try {

						textPane_1.setText("");
						doc.insertString(doc.getLength(), "Please provide a valid input!\n\n", null);
						doc.insertString(
								doc.getLength(),
								"Valid inputs include:\n"
										+ "    CS:GO console output using the command 'status' \n"
										+ "    A single Steam profile URL of the form 'http://steamcommunity.com/id/xxx'\n"
										+ "    A single Steam profile URL of the form 'http://steamcommunity.com/profiles/xxx'",
								null);

					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

		});
		button_1.setBounds(10, 396, 599, 50);
		button_1.setFocusPainted(false);
		panel_1.add(button_1);

		textPane_1 = new JTextPane();
		textPane_1.setEditable(false);
		textPane_1.setFocusable(false);
		textPane_1.setBounds(10, 457, 599, 265);
		scrollPane_1 = new JScrollPane(textPane_1);
		scrollPane_1.setBounds(10, 457, 599, 265);
		panel_1.add(scrollPane_1);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

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
							if (UserManager.getInstance().bannedSinceAdded(
									FileHandler.getAllPlayers().get(i).getSteamId())) {
								numBanned++;
								doc.insertString(doc.getLength(),
										FileHandler.getAllPlayers().get(i).getSteamId() + str, null);
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
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		panel_5 = new JPanel();
		tabbedPane.addTab("Statistics", null, panel_5, null);
		panel_5.setLayout(null);

		panel_6 = new JPanel();
		tabbedPane.addTab("Settings", null, panel_6, null);

	}
}
