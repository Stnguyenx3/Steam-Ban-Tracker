import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.swing.JTable;
import javax.swing.JTextField;

public class BanTracker extends JFrame {

	private JPanel contentPane;
	private JLabel lblDesc;
	private JTextPane textPane;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_5;
	private JTextPane textPane_1;
	private JTextPane textPane_2;
	private JTextPane textPane_3;
	private JScrollPane scrollPane_1;

	private JLabel lblBrowsePlayers;
	private JLabel lblBrowseBannedPlayers;
	private JLabel lblSettingsLink;
	private JLabel lblSettingsCont;
	private JTextField textFieldAPIKEY;

	private ArrayList<User> oldUsers = new ArrayList<User>();
	private ArrayList<User> newUsers = new ArrayList<User>();

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
							FileHandler.writeToFile("players.tmp", FileHandler.getCompletedPlayers());
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
		FileHandler.readFromFile("players.tmp", "ArrayList<CompletedPlayer>");

	}

	/**
	 * Create the frame.
	 */
	public BanTracker() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 800, 800);
		tabbedPane.setFocusable(false);
		contentPane.add(tabbedPane);

		JPanel panelHome = new JPanel();
		tabbedPane.addTab("Home", null, panelHome, null);
		panelHome.setLayout(null);

		panel_1 = new JPanel();
		tabbedPane.addTab("Add Players", null, panel_1, null);
		panel_1.setLayout(null);

		lblDesc = new JLabel();
		lblDesc.setVerticalAlignment(SwingConstants.TOP);
		lblDesc.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblDesc.setBounds(30, 11, 740, 450);
		lblDesc.setText("<html>This program allows you to track CSGO players for Overwatch and VAC bans. "
				+ "You may use this program to keep track of players from your competitive matchmaking games.<br><br>"
				+ " <font color='red'><b>NOTE:</b></font> You must provide a Steam API key in the \"Settings\""
				+ " tab before using this program.<br><br>"
				+ "<u>Instructions:</u><br><br> To add multiple players at once<br>1) Once all players have connected, "
				+ "from the CSGO game console*, use the command \"<font color='red'>status</font>\" to obtain player"
				+ " information for the current competitive game.<br> 2) Copy and paste the output from the console "
				+ "into the text field in the \"Add Players\" tab and click \"Process Information\"<br><br>"
				+ "To add a single player by their Steam profile URL<br>"
				+ "1) Copy and paste their profile URL into the text field and click \"Process Information\".<br><br>"
				+ "Note: To enable the developers console in CS:GO, go to options -> Game Settings and change the option to Yes."
				+ " You can then access the console using the tilde key `." + "<html>");
		panelHome.add(lblDesc);

		textPane = new JTextPane();
		JScrollPane scrollPane = new JScrollPane(textPane);
		scrollPane.setBounds(10, 40, 770, 345);
		panel_1.add(scrollPane);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JLabel lblPasteCsgoConsole_1 = new JLabel("Paste CS:GO console output or Steam profile URL here");
		lblPasteCsgoConsole_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPasteCsgoConsole_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblPasteCsgoConsole_1.setBounds(10, 11, 770, 18);
		panel_1.add(lblPasteCsgoConsole_1);

		JButton button_1 = new JButton("Process Information");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String userInput = textPane.getText() + " ";
				String[] uInput = null;
				String[] comIDs = new String[100];
				int comIDCounter = 0;
				ArrayList<CompletedPlayer> pSummaries = FileHandler.getCompletedPlayers();

				UserManager uMInstance = UserManager.getInstance();

				Document doc = textPane_1.getStyledDocument();

				textPane_1.setText("");

				if (userInput.contains("steamcommunity.com")) {

					int idCount = countSubstring(userInput, "steamcommunity.com/id/");
					int profileCount = countSubstring(userInput, "steamcommunity.com/profiles/");

					// Check for multiple URLs
					if (idCount > 1 || profileCount > 1) {
						textPane_1.setText("More than one URL was entered!");
					} else {

						String line;

						try {
							uInput = new String[] { userInput };
							URL profileURL = new URL(userInput.replaceAll("\\s+", "") + "?xml=1");
							URLConnection conn = profileURL.openConnection();
							InputStream is = conn.getInputStream();
							// Read information from URL and append to a
							// string.
							// Code taken from
							// http://stackoverflow.com/questions/9856195/how-to-read-an-http-input-stream
							BufferedReader reader = new BufferedReader(new InputStreamReader(is));

							StringBuilder xmlResponse = new StringBuilder();
							while ((line = reader.readLine()) != null) {
								xmlResponse.append(line + "\n");
							}

							SAXParserFactory pFactory = SAXParserFactory.newInstance();
							SAXParser parser = pFactory.newSAXParser();
							SAXHandler handler = new SAXHandler();
							parser.parse(new InputSource(new StringReader(xmlResponse.toString())), handler);

							String[] cID = new String[] { handler.p.steamID };

							String jsonData = SteamWebAPI.getInfo(cID);

							uMInstance.addPlayer(jsonData, cID, 1);

							if (uMInstance.getLastAdded().size() == 1) {

								for (int j = 0; j < pSummaries.size(); j++) {
									if (uMInstance.getLastAdded().get(0).getSteamId()
											.equalsIgnoreCase(pSummaries.get(j).getSummary().getSteamID())) {

										textPane_1.setText(pSummaries.get(j).getSummary().getPersonaName()
												+ " has been added.");

									}
								}

							}

							if (uMInstance.getCurrentlyTracked().size() == 1) {

								for (int j = 0; j < pSummaries.size(); j++) {
									if (uMInstance.getCurrentlyTracked().get(0).getSteamId()
											.equalsIgnoreCase(pSummaries.get(j).getSummary().getSteamID())) {

										textPane_1.setText(pSummaries.get(j).getSummary().getPersonaName()
												+ " is already being tracked and will be updated!");

									}
								}

							}

							// Reset lastAdded for future use.
							uMInstance.clearLastAdded();

							// Reset for future use.
							uMInstance.clearCurrentlyTracked();

						} catch (MalformedURLException e) {
							textPane_1.setText("Please enter a valid URL!");
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParserConfigurationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SAXException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				} else if (userInput.contains("#")) {
					
					// Write user input to text file to be kept as a log.
					try {
					FileWriter writer = new FileWriter("playerLog.txt", true);
					PrintWriter printer = new PrintWriter(writer);
					printer.printf("%s", userInput);
					printer.println();
					printer.close();
					} catch (IOException e) {
						System.err.println("IOException when writing string to file!");
					}


					uInput = userInput.split("#");
					String jsonData = "";

					textPane_1.setText("");

					if (uInput != null && uInput.length > 0) {

						if (uInput.length <= 10) {
							processPlayer(uInput, comIDs, jsonData, comIDCounter, UserManager.getInstance(),
									textPane_1.getStyledDocument());
						} else {
							processPlayerWithGames(uInput, comIDs, jsonData, comIDCounter, UserManager.getInstance(),
									textPane_1.getStyledDocument());
						}

						if (uMInstance.getCurrentlyTracked().size() > 0) {

							try {
								doc.insertString(doc.getLength(),
										"The following players are already being tracked and will be updated:\n", null);
							} catch (BadLocationException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							for (int i = 0; i < uMInstance.getCurrentlyTracked().size(); i++) {
								for (int j = 0; j < pSummaries.size(); j++) {
									try {
										if (uMInstance.getCurrentlyTracked().get(i).getSteamId()
												.equalsIgnoreCase(pSummaries.get(j).getSummary().getSteamID())) {

											doc.insertString(doc.getLength(), pSummaries.get(j).getSummary()
													.getPersonaName()
													+ " (ID="
													+ pSummaries.get(j).getSummary().getSteamID()
													+ ")"
													+ "\n", null);

										}

									} catch (BadLocationException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();

									}
								}
							}

						}

						// Reset for future use.
						uMInstance.clearCurrentlyTracked();

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
		button_1.setBounds(10, 396, 770, 50);
		button_1.setFocusPainted(false);
		panel_1.add(button_1);

		textPane_1 = new JTextPane();
		textPane_1.setEditable(false);
		scrollPane_1 = new JScrollPane(textPane_1);
		scrollPane_1.setBounds(10, 457, 770, 265);
		panel_1.add(scrollPane_1);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		panel_2 = new JPanel();
		tabbedPane.addTab("Browse Players", null, panel_2, null);
		panel_2.setLayout(null);

		TableModel model = new PlayerTableModel(FileHandler.getCompletedPlayers());
		JTable table = new JTable(model);
		table.getTableHeader().setReorderingAllowed(false);

		// Center cell values.
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumn("ID").setCellRenderer(renderer);
		table.getColumn("Date added").setCellRenderer(renderer);
		table.getColumn("Date updated").setCellRenderer(renderer);
		table.getColumn("VAC bans").setCellRenderer(renderer);
		table.getColumn("Game bans").setCellRenderer(renderer);
		table.getColumn("Last ban (days)").setCellRenderer(renderer);
		table.getColumn("64-Bit SteamID").setCellRenderer(renderer);

		// Center table header.
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);

		// Set the width of each column.
		setupTable(table);

		JScrollPane tableScrollPane = new JScrollPane(table);
		tableScrollPane.setBounds(10, 80, 780, 525);
		tableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel_2.add(tableScrollPane);

		JButton btnRefreshAllPlayers = new JButton("Refresh");
		btnRefreshAllPlayers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PlayerTableModel model = (PlayerTableModel) table.getModel();
				model.refresh(FileHandler.getCompletedPlayers());
				lblBrowsePlayers.setText("There are currently " + FileHandler.getCompletedPlayers().size()
						+ " players being tracked.");
			}
		});
		btnRefreshAllPlayers.setBounds(10, 11, 99, 58);
		panel_2.add(btnRefreshAllPlayers);

		lblBrowsePlayers = new JLabel("");
		lblBrowsePlayers.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblBrowsePlayers.setBounds(119, 26, 655, 25);
		panel_2.add(lblBrowsePlayers);
		lblBrowsePlayers.setText("There are currently " + FileHandler.getCompletedPlayers().size()
				+ " players being tracked.");

		panel_3 = new JPanel();
		tabbedPane.addTab("Check Bans", null, panel_3, null);
		panel_3.setLayout(null);

		UserManager uM = UserManager.getInstance();

		JButton button = new JButton("Check Bans");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				textPane_2.setText("");

				uM.updateUsers();

				oldUsers = uM.getTracked();
				newUsers = uM.getUpdatedUsers();

				Document doc = textPane_2.getDocument();

				try {

					for (int i = 0; i < oldUsers.size(); i++) {

						if (oldUsers.get(i).equals(newUsers.get(i))) {
							if (newUsers.get(i).getNumberOfBans() > oldUsers.get(i).getNumberOfBans()) {
								doc.insertString(doc.getLength(), newUsers.get(i).getSteamId()
										+ " has been VAC banned!\n", null);
							} else if (newUsers.get(i).getNumberOfGameBans() > oldUsers.get(i).getNumberOfGameBans()) {
								doc.insertString(doc.getLength(), newUsers.get(i).getSteamId()
										+ " has been Overwatch banned!\n", null);
							}

						}

					}

					doc.insertString(doc.getLength(), "Finished checking for bans.", null);

				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}

			}
		});
		button.setFocusPainted(false);
		button.setBounds(10, 11, 282, 23);
		panel_3.add(button);

		textPane_2 = new JTextPane();
		textPane_2.setBounds(65, 485, 314, 107);
		textPane_2.setEditable(false);
		JScrollPane scrollPane_2 = new JScrollPane(textPane_2);
		scrollPane_2.setBounds(10, 45, 770, 400);
		panel_3.add(scrollPane_2);
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		panel_4 = new JPanel();
		tabbedPane.addTab("Banned", null, panel_4, null);
		panel_4.setLayout(null);

		ArrayList<CompletedPlayer> bannedPlayers = new ArrayList<CompletedPlayer>();

		// Populate ArrayList with CompletedPlayers that are banned.
		for (int i = 0; i < FileHandler.getCompletedPlayers().size(); i++) {
			CompletedPlayer currentPlayer = FileHandler.getCompletedPlayers().get(i);
			if (currentPlayer.getUser().getVacBan() == true || currentPlayer.getUser().getNumberOfGameBans() > 1) {
				bannedPlayers.add(currentPlayer);
			}
		}

		TableModel modelBanned = new PlayerTableModel(bannedPlayers);
		JTable tableBanned = new JTable(modelBanned);
		tableBanned.getTableHeader().setReorderingAllowed(false);
		
		// Center cell values.
		DefaultTableCellRenderer bannedRenderer = new DefaultTableCellRenderer();
		bannedRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		tableBanned.getColumn("ID").setCellRenderer(bannedRenderer);
		tableBanned.getColumn("Date added").setCellRenderer(bannedRenderer);
		tableBanned.getColumn("Date updated").setCellRenderer(bannedRenderer);
		tableBanned.getColumn("VAC bans").setCellRenderer(bannedRenderer);
		tableBanned.getColumn("Game bans").setCellRenderer(bannedRenderer);
		tableBanned.getColumn("Last ban (days)").setCellRenderer(bannedRenderer);
		tableBanned.getColumn("64-Bit SteamID").setCellRenderer(bannedRenderer);

		// Center table header.
		((DefaultTableCellRenderer) tableBanned.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Set the width of each column
		setupTable(tableBanned);
		
		JScrollPane tableBannedScrollPane = new JScrollPane(tableBanned);
		tableBannedScrollPane.setBounds(10, 80, 780, 525);
		tableBannedScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel_4.add(tableBannedScrollPane);
		
		JButton btnRefreshBannedPlayers = new JButton("Refresh");
		btnRefreshBannedPlayers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				bannedPlayers.clear();
				
				// Populate ArrayList with CompletedPlayers that are banned.
				for (int i = 0; i < FileHandler.getCompletedPlayers().size(); i++) {
					CompletedPlayer currentPlayer = FileHandler.getCompletedPlayers().get(i);
					if (currentPlayer.getUser().getVacBan() == true || currentPlayer.getUser().getNumberOfGameBans() > 1) {
						bannedPlayers.add(currentPlayer);
					}
				}
				
				PlayerTableModel modelBanned = (PlayerTableModel) tableBanned.getModel();
				modelBanned.refresh(bannedPlayers);
			}
		});
		btnRefreshBannedPlayers.setBounds(10, 11, 99, 58);
		panel_4.add(btnRefreshBannedPlayers);
		
		lblBrowseBannedPlayers = new JLabel("");
		lblBrowseBannedPlayers.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblBrowseBannedPlayers.setBounds(119, 26, 655, 25);
		panel_4.add(lblBrowseBannedPlayers);
		lblBrowseBannedPlayers.setText("There are currently " + bannedPlayers.size()
				+ " players being tracked.");

		panel_5 = new JPanel();
		tabbedPane.addTab("Settings", null, panel_5, null);
		panel_5.setLayout(null);

		JLabel lblSettingsAPI = new JLabel();
		lblSettingsAPI
				.setText("<html>A Steam Web API key is required for this program to work. Steam limits an API key to"
						+ " 100,000 requests per day. Using your own unique API key wouold make it harder to reach"
						+ " this limit. In order to get a Steam Web API key, you must go to the following link and"
						+ " sign in with your Steam account.</html>");
		lblSettingsAPI.setVerticalAlignment(SwingConstants.TOP);
		lblSettingsAPI.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblSettingsAPI.setBounds(10, 11, 770, 70);
		panel_5.add(lblSettingsAPI);

		try {
			URI uri = new URI("https://steamcommunity.com/login/home/?goto=%2Fdev%2Fapikey");

			lblSettingsLink = new JLabel();
			lblSettingsLink.setHorizontalAlignment(SwingConstants.CENTER);
			lblSettingsLink.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {

					if (Desktop.isDesktopSupported()) {

						try {
							Desktop.getDesktop().browse(uri);
						} catch (IOException e) {
							e.printStackTrace();
						}

					} else {
						JOptionPane.showMessageDialog(panel_5, "Unable to open URL, please visit it manually.");
					}
				}
			});

		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}

		lblSettingsLink.setText("<html><a href=\"\">https://steamcommunity.com/login/home/?goto=%2Fdev%2Fapikey</a>");
		lblSettingsLink.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblSettingsLink.setBounds(10, 80, 770, 28);
		panel_5.add(lblSettingsLink);

		lblSettingsCont = new JLabel();
		lblSettingsCont.setHorizontalAlignment(SwingConstants.CENTER);
		lblSettingsCont.setText("<html>After getting an API key, enter it here and press save</html>");
		lblSettingsCont.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblSettingsCont.setBounds(10, 119, 770, 28);

		panel_5.add(lblSettingsCont);

		textFieldAPIKEY = new JTextField();

		// Only display api key if the file exists.
		Path path = Paths.get("apikey.txt");
		if (Files.exists(path)) {
			textFieldAPIKEY.setText(FileHandler.getAPIKey());
		}

		textFieldAPIKEY.setBounds(10, 158, 640, 28);
		panel_5.add(textFieldAPIKEY);
		textFieldAPIKEY.setColumns(10);

		JButton btnSaveAPIKEY = new JButton("Save");
		btnSaveAPIKEY.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileHandler.writeToFile("apikey.txt", textFieldAPIKEY.getText());
			}
		});
		btnSaveAPIKEY.setBounds(660, 158, 120, 28);
		panel_5.add(btnSaveAPIKEY);

	}

	/**
	 * Count the number of times a substring appears in a string.
	 * 
	 * @param str
	 *            The string to be checked.
	 * @param subStr
	 *            The substring to check for.
	 * @return An integer value representing the number of times the substring
	 *         occurs in the string.
	 */
	private int countSubstring(String str, String subStr) {

		int count = 0;

		for (int i = str.indexOf(subStr); i != -1; i = str.indexOf(subStr, i + subStr.length())) {
			count++;
		}

		return count;

	}

	/**
	 * Get the index of a User in the sorted ArrayList containing all Users.
	 * 
	 * @param u
	 *            Sorted ArrayList containing all Users.
	 * @param s
	 *            Unsorted ArrayList containing all User summaries.
	 * @param offset
	 *            Integer representing an offset from the last element. To get
	 *            the 10th element from the last, use offset of 10. Using offset
	 *            of 1 would return the index of the last element.
	 * @return Integer repressenting the index of a User in an ArrayList, -1 if
	 *         no user is found.
	 */
	private int getIndex(ArrayList<User> u, ArrayList<PlayerSummary.Player> s, int offset) {
		int index = s.size();
		for (int i = 0; i < u.size(); i++) {
			if (s.get(index - offset).getSteamID().equalsIgnoreCase(u.get(i).getSteamId())) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Display recent User information in button action listener.
	 * 
	 * @param u
	 *            Sorted ArrayList containing all Users.
	 * @param s
	 *            Unsorted ArrayList containing all User summaries.
	 * @param d
	 *            Document the text will be apended to.
	 * @param pI
	 *            Integer index returned from getIndex().
	 * @param sOffset
	 *            Integer representing an offset from the last element. To get
	 *            the 10th element from the last, use offset of 10. Using offset
	 *            of 1 would return the index of the last element.
	 */
	private void displayInfo(ArrayList<User> u, ArrayList<PlayerSummary.Player> s, Document d, int pI, int sOffset) {
		textPane_3.setText("");
		try {
			if (pI != -1) {
				d.insertString(d.getLength(), "Summary for " + s.get(s.size() - sOffset).getPersonaName() + "\n\n"
						+ "Profile: " + "steamcommunity.com/profiles/" + u.get(pI).getSteamId() + "\n" + "Date added: "
						+ u.get(pI).getDateAdded() + "\n" + "Last updated: " + u.get(pI).getDateUpdated() + "\n"
						+ "Number of VAC bans: " + u.get(pI).getNumberOfBans() + "\n" + "Days since last VAC ban: "
						+ u.get(pI).getDaysSinceLastBan() + "\n" + "Number of Game Bans: "
						+ u.get(pI).getNumberOfGameBans(), null);
			} else {
				d.insertString(d.getLength(), "No summary was found!", null);
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Processes the user input and adds players to the program.
	 * 
	 * @param uI
	 *            Array of Strings containing user input after using split().
	 * @param cIDs
	 *            Array of Strings that will hold Community ID's
	 * @param jD
	 *            String containing JSON data to be parsed.
	 * @param cIDCounter
	 *            Integer used to check if number of Community ID's exceed the
	 *            allowed amount.
	 * @param instance
	 *            An instance of UserManager.
	 * @param d
	 *            Document that information will be printed to.
	 */
	private void processPlayer(String[] uI, String[] cIDs, String jD, int cIDCounter, UserManager instance, Document d) {

		int oldCount = FileHandler.getCompletedPlayers().size();

		for (int i = 1; i < uI.length; i++) {

			if (!SteamDataParser.getSteamID(uI[i]).equals("Invalid_Steam_ID")) {

				cIDs[cIDCounter] = SteamDataParser.convertToCommunityID(SteamDataParser.getSteamID(uI[i]));
				cIDCounter++;

				if (cIDCounter >= 100) {
					textPane_1.setText("Only 100 players may be added at a time!");
				}

			}

		}

		jD = SteamWebAPI.getInfo(cIDs);

		if (jD == null) {
			JOptionPane
					.showMessageDialog(panel_1,
							"Error getting information from Steam servers. Servers may be down or you have an invalid API key.");
		}

		// Add user
		instance.addPlayer(jD, cIDs, cIDCounter);

		// Reset comIDs for future use.
		for (int i = 0; i < cIDs.length; i++) {
			cIDs[i] = null;
		}

		ArrayList<User> lA = instance.getLastAdded();

		if (lA.size() > 0) {
			textPane_1.setText("The following players have been added:\n");

			for (int i = 0; i < lA.size(); i++) {
				try {

					for (int j = 0; j < FileHandler.getCompletedPlayers().size(); j++) {
						if (lA.get(i).getSteamId()
								.equalsIgnoreCase(FileHandler.getCompletedPlayers().get(j).getUser().getSteamId())) {
							d.insertString(d.getLength(), i + 1 + ") "
									+ FileHandler.getCompletedPlayers().get(j).getSummary().getPersonaName() + " (ID="
									+ FileHandler.getCompletedPlayers().get(j).getUser().getSteamId() + ")" + "\n",
									null);
						}
					}

				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// Reset lastAdded for future use.
			instance.clearLastAdded();
		}

		int newCount = FileHandler.getCompletedPlayers().size();

		BanTracker.displayInfoBox((newCount - oldCount) + " players have been added.", "Players added");
	}

	/**
	 * Processes the user input and adds players to the program along with the
	 * game that they were added from.
	 * 
	 * @param uI
	 *            Array of Strings containing user input after using split().
	 * @param cIDs
	 *            Array of Strings that will hold Community ID's
	 * @param jD
	 *            String containing JSON data to be parsed.
	 * @param cIDCounter
	 *            Integer used to check if number of Community ID's exceed the
	 *            allowed amount.
	 * @param instance
	 *            An instance of UserManager.
	 * @param d
	 *            Document that information will be printed to.
	 */
	private void processPlayerWithGames(String[] uI, String[] cIDs, String jD, int cIDCounter, UserManager instance,
			Document d) {

		int oldCount = FileHandler.getCompletedPlayers().size();

		for (int i = 1; i < uI.length; i++) {

			if (!SteamDataParser.getSteamID(uI[i]).equals("Invalid_Steam_ID")) {

				cIDs[cIDCounter] = SteamDataParser.convertToCommunityID(SteamDataParser.getSteamID(uI[i]));
				cIDCounter++;

				if (cIDCounter % 10 == 0) {
					String[] game = Arrays.copyOfRange(uI, cIDCounter - 9, cIDCounter + 1);
					// FileHandler.getGames().add(game);

				}

				if (cIDCounter >= 100) {
					textPane_1.setText("Only 100 players may be added at a time!");
				}

			}

		}

		jD = SteamWebAPI.getInfo(cIDs);

		// Add user
		instance.addPlayer(jD, cIDs, cIDCounter);

		// Reset comIDs for future use.
		for (int i = 0; i < cIDs.length; i++) {
			cIDs[i] = null;
		}

		ArrayList<User> lA = instance.getLastAdded();

		if (lA.size() > 0) {
			textPane_1.setText("The following players have been added:\n");

			for (int i = 0; i < lA.size(); i++) {
				try {

					for (int j = 0; j < FileHandler.getCompletedPlayers().size(); j++) {
						if (lA.get(i).getSteamId()
								.equalsIgnoreCase(FileHandler.getCompletedPlayers().get(j).getUser().getSteamId())) {
							d.insertString(d.getLength(), i + 1 + ") "
									+ FileHandler.getCompletedPlayers().get(j).getSummary().getPersonaName() + " (ID="
									+ FileHandler.getCompletedPlayers().get(j).getUser().getSteamId() + ")" + "\n",
									null);
						}
					}

				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// Reset lastAdded for future use.
			instance.clearLastAdded();
		}

		int newCount = FileHandler.getCompletedPlayers().size();

		BanTracker.displayInfoBox((newCount - oldCount) + " players have been added.", "Players added");
	}

	public static void displayInfoBox(String msg, String title) {
		JOptionPane.showMessageDialog(getFrames()[0], msg, title, JOptionPane.INFORMATION_MESSAGE);
	}

	private void setupTable(JTable t) {
		t.getColumn("ID").setMinWidth(150);
		t.getColumn("ID").setMaxWidth(150);
		t.getColumn("Date added").setMinWidth(75);
		t.getColumn("Date added").setMaxWidth(75);
		t.getColumn("Date updated").setMinWidth(85);
		t.getColumn("Date updated").setMaxWidth(85);
		t.getColumn("VAC bans").setMinWidth(65);
		t.getColumn("VAC bans").setMaxWidth(65);
		t.getColumn("Game bans").setMinWidth(75);
		t.getColumn("Game bans").setMaxWidth(75);
		t.getColumn("Last ban (days)").setMinWidth(100);
		t.getColumn("Last ban (days)").setMaxWidth(100);

		t.setRowHeight(30);
	}
}
