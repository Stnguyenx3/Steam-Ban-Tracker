import java.awt.Color;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.swing.JTable;

public class BanTracker extends JFrame {

	private JPanel contentPane;
	private JLabel lblDesc;
	private JTextPane textPane;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_4;
	private JPanel panel_5;
	private JPanel panel_3;
	private JPanel panel_6;
	private JTextPane textPane_1;
	private JTextPane textPane_2;
	private JTextPane textPane_3;
	private JScrollPane scrollPane_1;

	JLabel lblAvatar1 = new JLabel();
	JLabel lblAvatar1Info = new JLabel();
	JLabel lblAvatar2 = new JLabel();
	JLabel lblAvatar2Info = new JLabel();
	JLabel lblAvatar3 = new JLabel();
	JLabel lblAvatar3Info = new JLabel();
	JLabel lblAvatar4 = new JLabel();
	JLabel lblAvatar4Info = new JLabel();
	JLabel lblAvatar5 = new JLabel();
	JLabel lblAvatar5Info = new JLabel();
	JLabel lblAvatar6 = new JLabel();
	JLabel lblAvatar6Info = new JLabel();
	JLabel lblAvatar7 = new JLabel();
	JLabel lblAvatar7Info = new JLabel();
	JLabel lblAvatar8 = new JLabel();
	JLabel lblAvatar8Info = new JLabel();
	JLabel lblAvatar9 = new JLabel();
	JLabel lblAvatar9Info = new JLabel();
	JLabel lblAvatar10 = new JLabel();
	JLabel lblAvatar10Info = new JLabel();

	private JTable table;

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
							FileHandler.writeToFile("s_unsorted.tmp", FileHandler.getAllSummariesUnsorted());
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
		FileHandler.readFromFile("s_unsorted.tmp", "ArrayList<PlayerSummary.Player>");
		FileHandler.readFromFile("summaries.tmp", "ArrayList<PlayerSummary.Player>");

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
		lblDesc.setBounds(30, 11, 559, 450);
		lblDesc.setText("<html>This program allows you to track CSGO players for Overwatch and VAC bans. "
				+ "You may use this program to keep track of players from your competitive matchmaking games.<br><br>"
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
				ArrayList<PlayerSummary.Player> summaries = FileHandler.getAllSummaries();

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

								for (int j = 0; j < summaries.size(); j++) {
									if (uMInstance.getLastAdded().get(0).getSteamId()
											.equalsIgnoreCase(summaries.get(j).getSteamID())) {

										textPane_1.setText(summaries.get(j).getPersonaName() + " has been added.");

									}
								}

							}

							if (uMInstance.getCurrentlyTracked().size() == 1) {

								for (int j = 0; j < summaries.size(); j++) {
									if (uMInstance.getCurrentlyTracked().get(0).getSteamId()
											.equalsIgnoreCase(summaries.get(j).getSteamID())) {

										textPane_1.setText(summaries.get(j).getPersonaName()
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

						// Reset comIDs for future use.
						for (int i = 0; i < comIDs.length; i++) {
							comIDs[i] = null;
						}

						ArrayList<User> lA = uMInstance.getLastAdded();

						if (lA.size() > 0) {
							textPane_1.setText("The following players have been added:\n");

							for (int i = 0; i < lA.size(); i++) {
								try {

									for (int j = 0; j < summaries.size(); j++) {
										if (lA.get(i).getSteamId().equalsIgnoreCase(summaries.get(j).getSteamID())) {
											doc.insertString(doc.getLength(), i + 1 + ") "
													+ summaries.get(j).getPersonaName() + " (ID="
													+ summaries.get(j).getSteamID() + ")" + "\n", null);
										}
									}

								} catch (BadLocationException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

							// Reset lastAdded for future use.
							uMInstance.clearLastAdded();

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
								for (int j = 0; j < summaries.size(); j++) {
									try {
										if (uMInstance.getCurrentlyTracked().get(i).getSteamId()
												.equalsIgnoreCase(summaries.get(j).getSteamID())) {

											doc.insertString(doc.getLength(), summaries.get(j).getPersonaName()
													+ " (ID=" + summaries.get(j).getSteamID() + ")" + "\n", null);

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
		button_1.setBounds(10, 396, 599, 50);
		button_1.setFocusPainted(false);
		panel_1.add(button_1);

		textPane_1 = new JTextPane();
		textPane_1.setEditable(false);
		textPane_1.setBounds(10, 457, 599, 265);
		scrollPane_1 = new JScrollPane(textPane_1);
		scrollPane_1.setBounds(10, 457, 599, 265);
		panel_1.add(scrollPane_1);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		panel_2 = new JPanel();
		tabbedPane.addTab("Recently Added", null, panel_2, null);
		panel_2.setLayout(null);

		JLabel lblInfo1 = new JLabel("Refreshing may take a while!");
		lblInfo1.setVerticalAlignment(SwingConstants.TOP);
		lblInfo1.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfo1.setBounds(10, 63, 603, 367);
		lblInfo1.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		panel_2.add(lblInfo1);

		textPane_3 = new JTextPane();
		textPane_3.setText("Click on a profile to get more information!");
		textPane_3.setEditable(false);
		textPane_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textPane_3.setBounds(10, 441, 615, 291);
		panel_2.add(textPane_3);

		Document doc = textPane_3.getDocument();
		ArrayList<PlayerSummary.Player> summaries = FileHandler.getAllSummariesUnsorted();
		// ArrayList<User> players = FileHandler.getAllPlayers();

		lblAvatar1.setBounds(10, 61, 64, 64);
		lblAvatar1Info.setBounds(80, 61, 225, 25);
		lblAvatar1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				ArrayList<User> players = FileHandler.getAllPlayers();
				int playerIndex = getIndex(players, summaries, 10);
				textPane_3.setText("");
				displayInfo(players, summaries, doc, playerIndex, 10);

			}
		});
		panel_2.add(lblAvatar1);
		panel_2.add(lblAvatar1Info);

		lblAvatar2.setBounds(10, 135, 64, 64);
		lblAvatar2Info.setBounds(80, 135, 225, 25);
		lblAvatar2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				ArrayList<User> players = FileHandler.getAllPlayers();
				int playerIndex = getIndex(players, summaries, 9);
				textPane_3.setText("");
				displayInfo(players, summaries, doc, playerIndex, 9);

			}
		});
		panel_2.add(lblAvatar2);
		panel_2.add(lblAvatar2Info);

		lblAvatar3.setBounds(10, 209, 64, 64);
		lblAvatar3Info.setBounds(80, 209, 225, 25);
		lblAvatar3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				ArrayList<User> players = FileHandler.getAllPlayers();
				int playerIndex = getIndex(players, summaries, 8);
				textPane_3.setText("");
				displayInfo(players, summaries, doc, playerIndex, 8);

			}
		});
		panel_2.add(lblAvatar3);
		panel_2.add(lblAvatar3Info);

		lblAvatar4.setBounds(10, 283, 64, 64);
		lblAvatar4Info.setBounds(80, 283, 225, 25);
		lblAvatar4.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				ArrayList<User> players = FileHandler.getAllPlayers();
				int playerIndex = getIndex(players, summaries, 7);
				textPane_3.setText("");
				displayInfo(players, summaries, doc, playerIndex, 7);

			}
		});
		panel_2.add(lblAvatar4);
		panel_2.add(lblAvatar4Info);

		lblAvatar5.setBounds(10, 357, 64, 64);
		lblAvatar5Info.setBounds(80, 357, 225, 25);
		lblAvatar5.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				ArrayList<User> players = FileHandler.getAllPlayers();
				int playerIndex = getIndex(players, summaries, 6);
				textPane_3.setText("");
				displayInfo(players, summaries, doc, playerIndex, 6);

			}
		});
		panel_2.add(lblAvatar5);
		panel_2.add(lblAvatar5Info);

		lblAvatar6.setBounds(310, 61, 64, 64);
		lblAvatar6Info.setBounds(380, 61, 225, 25);
		lblAvatar6.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				ArrayList<User> players = FileHandler.getAllPlayers();
				int playerIndex = getIndex(players, summaries, 5);
				textPane_3.setText("");
				displayInfo(players, summaries, doc, playerIndex, 5);

			}
		});
		panel_2.add(lblAvatar6);
		panel_2.add(lblAvatar6Info);

		lblAvatar7.setBounds(310, 135, 64, 64);
		lblAvatar7Info.setBounds(380, 135, 225, 25);
		lblAvatar7.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				ArrayList<User> players = FileHandler.getAllPlayers();
				int playerIndex = getIndex(players, summaries, 4);
				textPane_3.setText("");
				displayInfo(players, summaries, doc, playerIndex, 4);

			}
		});
		panel_2.add(lblAvatar7);
		panel_2.add(lblAvatar7Info);

		lblAvatar8.setBounds(310, 209, 64, 64);
		lblAvatar8Info.setBounds(380, 209, 225, 25);
		lblAvatar8.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				ArrayList<User> players = FileHandler.getAllPlayers();
				int playerIndex = getIndex(players, summaries, 3);
				textPane_3.setText("");
				displayInfo(players, summaries, doc, playerIndex, 3);

			}
		});
		panel_2.add(lblAvatar8);
		panel_2.add(lblAvatar8Info);

		lblAvatar9.setBounds(310, 283, 64, 64);
		lblAvatar9Info.setBounds(380, 283, 225, 25);
		lblAvatar9.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				ArrayList<User> players = FileHandler.getAllPlayers();
				int playerIndex = getIndex(players, summaries, 2);
				textPane_3.setText("");
				displayInfo(players, summaries, doc, playerIndex, 2);

			}
		});
		panel_2.add(lblAvatar9);
		panel_2.add(lblAvatar9Info);

		lblAvatar10.setBounds(310, 357, 64, 64);
		lblAvatar10Info.setBounds(380, 357, 225, 25);
		lblAvatar10.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				ArrayList<User> players = FileHandler.getAllPlayers();
				int playerIndex = getIndex(players, summaries, 1);
				textPane_3.setText("");
				displayInfo(players, summaries, doc, playerIndex, 1);

			}
		});
		panel_2.add(lblAvatar10);
		panel_2.add(lblAvatar10Info);

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				lblInfo1.setText("");

				int numOfPlayers = FileHandler.getAllSummariesUnsorted().size() - 1;

				if (numOfPlayers >= 9) {
					try {

						URL url1 = new URL(FileHandler.getAllSummariesUnsorted().get(numOfPlayers - 9)
								.getAvatarMedium());
						Image img1 = ImageIO.read(url1);
						lblAvatar1.setIcon(new ImageIcon(img1));
						lblAvatar1Info.setText(FileHandler.getAllSummariesUnsorted().get(numOfPlayers - 9)
								.getPersonaName());

						URL url2 = new URL(FileHandler.getAllSummariesUnsorted().get(numOfPlayers - 8)
								.getAvatarMedium());
						Image img2 = ImageIO.read(url2);
						lblAvatar2.setIcon(new ImageIcon(img2));

						lblAvatar2Info.setText(FileHandler.getAllSummariesUnsorted().get(numOfPlayers - 8)
								.getPersonaName());

						URL url3 = new URL(FileHandler.getAllSummariesUnsorted().get(numOfPlayers - 7)
								.getAvatarMedium());
						Image img3 = ImageIO.read(url3);
						lblAvatar3.setIcon(new ImageIcon(img3));
						lblAvatar3Info.setText(FileHandler.getAllSummariesUnsorted().get(numOfPlayers - 7)
								.getPersonaName());

						URL url4 = new URL(FileHandler.getAllSummariesUnsorted().get(numOfPlayers - 6)
								.getAvatarMedium());
						Image img4 = ImageIO.read(url4);
						lblAvatar4.setIcon(new ImageIcon(img4));
						lblAvatar4Info.setText(FileHandler.getAllSummariesUnsorted().get(numOfPlayers - 6)
								.getPersonaName());

						URL url5 = new URL(FileHandler.getAllSummariesUnsorted().get(numOfPlayers - 5)
								.getAvatarMedium());
						Image img5 = ImageIO.read(url5);
						lblAvatar5.setIcon(new ImageIcon(img5));
						lblAvatar5Info.setText(FileHandler.getAllSummariesUnsorted().get(numOfPlayers - 5)
								.getPersonaName());

						URL url6 = new URL(FileHandler.getAllSummariesUnsorted().get(numOfPlayers - 4)
								.getAvatarMedium());
						Image img6 = ImageIO.read(url6);
						lblAvatar6.setIcon(new ImageIcon(img6));
						lblAvatar6Info.setText(FileHandler.getAllSummariesUnsorted().get(numOfPlayers - 4)
								.getPersonaName());

						URL url7 = new URL(FileHandler.getAllSummariesUnsorted().get(numOfPlayers - 3)
								.getAvatarMedium());
						Image img7 = ImageIO.read(url7);
						lblAvatar7.setIcon(new ImageIcon(img7));
						lblAvatar7Info.setText(FileHandler.getAllSummariesUnsorted().get(numOfPlayers - 3)
								.getPersonaName());

						URL url8 = new URL(FileHandler.getAllSummariesUnsorted().get(numOfPlayers - 2)
								.getAvatarMedium());
						Image img8 = ImageIO.read(url8);
						lblAvatar8.setIcon(new ImageIcon(img8));
						lblAvatar8Info.setText(FileHandler.getAllSummariesUnsorted().get(numOfPlayers - 2)
								.getPersonaName());

						URL url9 = new URL(FileHandler.getAllSummariesUnsorted().get(numOfPlayers - 1)
								.getAvatarMedium());
						Image img9 = ImageIO.read(url9);
						lblAvatar9.setIcon(new ImageIcon(img9));
						lblAvatar9Info.setText(FileHandler.getAllSummariesUnsorted().get(numOfPlayers - 1)
								.getPersonaName());

						URL url10 = new URL(FileHandler.getAllSummariesUnsorted().get(numOfPlayers).getAvatarMedium());
						Image img10 = ImageIO.read(url10);
						lblAvatar10.setIcon(new ImageIcon(img10));
						lblAvatar10Info.setText(FileHandler.getAllSummariesUnsorted().get(numOfPlayers)
								.getPersonaName());

					} catch (IOException ioE) {
						ioE.printStackTrace();
					}

				} else {
					lblInfo1.setText("At least 10 players must be added!");
				}

			}
		});
		btnRefresh.setBounds(10, 11, 603, 40);
		panel_2.add(btnRefresh);

		panel_3 = new JPanel();
		tabbedPane.addTab("Browse Players", null, panel_3, null);
		panel_3.setLayout(null);
		table = new JTable(new PlayerTableModel());
		table.getTableHeader().setReorderingAllowed(false);
		JScrollPane tableScrollPane = new JScrollPane(table);
		tableScrollPane.setBounds(10, 11, 780, 722);
		tableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel_3.add(tableScrollPane);

		JPanel panelGames = new JPanel();
		tabbedPane.addTab("Games", null, panelGames, null);
		panelGames.setLayout(null);

		panel_4 = new JPanel();
		tabbedPane.addTab("Check Bans", null, panel_4, null);
		panel_4.setLayout(null);

		UserManager uM = UserManager.getInstance();

		JButton button = new JButton("Check Bans");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				textPane_2.setText("");

				uM.updateUsers();

				ArrayList<User> oldUsers = uM.getTracked();
				ArrayList<User> newUsers = uM.getUpdatedUsers();

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

					// Update Users
					FileHandler.updateArrayList(newUsers);

				} catch (BadLocationException e1) {
					e1.printStackTrace();
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
}
