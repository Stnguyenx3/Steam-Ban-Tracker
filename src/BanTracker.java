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
	private JLabel lblPrompt;
	private JTextField textField;
	private JTextArea textArea;
	private JTextPane textPane;
	private JTextPane textPane_1;
	private JPanel panel_1;
	private JTextPane textPane_2;
	private JLabel lblProfileavatar;
	private JPanel panel_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		//Set look and feel to Nimbus
		
		UIManager.put("nimbusBase", new Color(57,105,138));
		UIManager.put("control", new Color(214,217,223));
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BanTracker frame = new BanTracker();
					frame.setVisible(true);
					
					//Code taken from http://stackoverflow.com/questions/6084039/create-custom-operation-for-setdefaultcloseoperation
					//Uncomment code for option before closing.
					WindowListener exitListener = new WindowAdapter() {
			            @Override
			            public void windowClosing(WindowEvent e) {
//			                int confirm = JOptionPane.showOptionDialog(null, "Are You Sure to Close Application?", "Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
//			                System.out.println("confirm ="+ confirm);
//			                if (confirm == JOptionPane.YES_OPTION) {
			                	FileHandler.writeToFile("users.tmp", FileHandler.getAllPlayers());
			                	FileHandler.writeToFile("summaries.tmp", FileHandler.getAllSummaries());
			        			System.out.println("Finished saving...");
//			                	System.exit(0);
//			                }
			            }
			        };
					
//					frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			        frame.addWindowListener(exitListener);
			        //Code taken from http://stackoverflow.com/questions/6084039/create-custom-operation-for-setdefaultcloseoperation
			        
			        
					
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
		tabbedPane.setBounds(0, 0, 624, 761);
		tabbedPane.setFocusable(false);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Home", null, panel, null);
		panel.setLayout(null);
		
		panel_2 = new JPanel();
		tabbedPane.addTab("Recently Added Users", null, panel_2, null);
		panel_2.setLayout(null);
		
				//Initialize button
				JButton btnNewButton = new JButton("Get Info!");
				btnNewButton.setBounds(145, 10, 282, 23);
				btnNewButton.setFocusPainted(false);
				panel.add(btnNewButton);
				
				lblPrompt = new JLabel("Enter a Steam64ID you want to track");
				lblPrompt.setBounds(10, 57, 275, 25);
				panel.add(lblPrompt);
				lblPrompt.setHorizontalAlignment(SwingConstants.CENTER);
				lblPrompt.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
				
				textField = new JTextField();
				textField.setBounds(10, 86, 282, 20);
				panel.add(textField);
				textField.setColumns(10);
				
				textArea = new JTextArea();
				textArea.setEditable(false);
				textArea.setBounds(200, 400, 200, 250);
				JScrollPane scroll = new JScrollPane(textArea);
				scroll.setBounds(10, 117, 599, 250);
				panel.add(scroll);
				scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
				
				
				
				JButton btnLoadString = new JButton("Load String");
				btnLoadString.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
//						FileHandler.readFromFile("string.tmp", "String");
//						System.out.println("SAVED STRING IS:" + FileHandler.getSavedString());
//						textArea.append(FileHandler.getSavedString());
						
					}
				});
				btnLoadString.setBounds(338, 59, 89, 23);
				panel.add(btnLoadString);
				
				JButton btnTest = new JButton("Test");
				btnTest.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						System.out.println(FileHandler.getAllSummaries());
						
					}
				});
				btnTest.setBounds(437, 59, 89, 23);
				panel.add(btnTest);
				
				btnNewButton.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						
						
						int numOfPlayers = FileHandler.getAllPlayers().size() - 1;
						
						try {
							
							URL url1 = new URL(FileHandler.getAllSummaries().get(numOfPlayers - 9).getAvatarMedium());
							Image img1 = ImageIO.read(url1);
							JLabel lblAvatar1 = new JLabel(new ImageIcon(img1));
							lblAvatar1.setBounds(10, 11, 64, 64);
							panel_2.add(lblAvatar1);
							lblAvatar1.addMouseListener(new MouseAdapter()  
							{  
							    public void mouseClicked(MouseEvent e)  
							    {  
							    	System.out.println("JLabel has been clicked!");
							    }  
							});
							
							
							URL url2 = new URL(FileHandler.getAllSummaries().get(numOfPlayers - 8).getAvatarMedium());
							Image img2 = ImageIO.read(url2);
							JLabel lblAvatar2 = new JLabel(new ImageIcon(img2));
							lblAvatar2.setBounds(10, 85, 64, 64);
							panel_2.add(lblAvatar2);

							URL url3 = new URL(FileHandler.getAllSummaries().get(numOfPlayers - 7).getAvatarMedium());
							Image img3 = ImageIO.read(url3);
							JLabel lblAvatar3 = new JLabel(new ImageIcon(img3));
							lblAvatar3.setBounds(10, 159, 64, 64);
							panel_2.add(lblAvatar3);
							
							URL url4 = new URL(FileHandler.getAllSummaries().get(numOfPlayers - 6).getAvatarMedium());
							Image img4 = ImageIO.read(url4);
							JLabel lblAvatar4 = new JLabel(new ImageIcon(img4));
							lblAvatar4.setBounds(10, 233, 64, 64);
							panel_2.add(lblAvatar4);
							
							URL url5 = new URL(FileHandler.getAllSummaries().get(numOfPlayers - 5).getAvatarMedium());
							Image img5 = ImageIO.read(url5);
							JLabel lblAvatar5 = new JLabel(new ImageIcon(img5));
							lblAvatar5.setBounds(10, 307, 64, 64);
							panel_2.add(lblAvatar5);
							
							URL url6 = new URL(FileHandler.getAllSummaries().get(numOfPlayers - 4).getAvatarMedium());
							Image img6 = ImageIO.read(url6);
							JLabel lblAvatar6 = new JLabel(new ImageIcon(img6));
							lblAvatar6.setBounds(310, 11, 64, 64);
							panel_2.add(lblAvatar6);
							
							URL url7 = new URL(FileHandler.getAllSummaries().get(numOfPlayers - 3).getAvatarMedium());
							Image img7 = ImageIO.read(url7);
							JLabel lblAvatar7 = new JLabel(new ImageIcon(img7));
							lblAvatar7.setBounds(310, 85, 64, 64);
							panel_2.add(lblAvatar7);
							
							URL url8 = new URL(FileHandler.getAllSummaries().get(numOfPlayers - 2).getAvatarMedium());
							Image img8 = ImageIO.read(url8);
							JLabel lblAvatar8 = new JLabel(new ImageIcon(img8));
							lblAvatar8.setBounds(310, 159, 64, 64);
							panel_2.add(lblAvatar8);
							
							URL url9 = new URL(FileHandler.getAllSummaries().get(numOfPlayers - 1).getAvatarMedium());
							Image img9 = ImageIO.read(url9);
							JLabel lblAvatar9 = new JLabel(new ImageIcon(img9));
							lblAvatar9.setBounds(310, 233, 64, 64);
							panel_2.add(lblAvatar9);
							
							URL url10 = new URL(FileHandler.getAllSummaries().get(numOfPlayers).getAvatarMedium());
							Image img10 = ImageIO.read(url10);
							JLabel lblAvatar10 = new JLabel(new ImageIcon(img10));
							lblAvatar10.setBounds(310, 307, 64, 64);
							panel_2.add(lblAvatar10);
							
				        } catch (IOException ioE) {
				        	ioE.printStackTrace();
				        }
						

						
					}
				});
		
			
					
			
//				int numOfPlayers = FileHandler.getAllPlayers().size() - 1;
//				
//				try {
//					
//					URL url1 = new URL(FileHandler.getAllSummaries().get(numOfPlayers - 9).getAvatarMedium());
//					Image img1 = ImageIO.read(url1);
//					JLabel lblAvatar1 = new JLabel(new ImageIcon(img1));
//					lblAvatar1.setBounds(10, 11, 64, 64);
//					panel_2.add(lblAvatar1);
//					lblAvatar1.addMouseListener(new MouseAdapter()  
//					{  
//					    public void mouseClicked(MouseEvent e)  
//					    {  
//					    	System.out.println("JLabel has been clicked!");
//					    }  
//					});
//					
//					
//					URL url2 = new URL(FileHandler.getAllSummaries().get(numOfPlayers - 8).getAvatarMedium());
//					Image img2 = ImageIO.read(url2);
//					JLabel lblAvatar2 = new JLabel(new ImageIcon(img2));
//					lblAvatar2.setBounds(10, 85, 64, 64);
//					panel_2.add(lblAvatar2);
//
//					URL url3 = new URL(FileHandler.getAllSummaries().get(numOfPlayers - 7).getAvatarMedium());
//					Image img3 = ImageIO.read(url3);
//					JLabel lblAvatar3 = new JLabel(new ImageIcon(img3));
//					lblAvatar3.setBounds(10, 159, 64, 64);
//					panel_2.add(lblAvatar3);
//					
//					URL url4 = new URL(FileHandler.getAllSummaries().get(numOfPlayers - 6).getAvatarMedium());
//					Image img4 = ImageIO.read(url4);
//					JLabel lblAvatar4 = new JLabel(new ImageIcon(img4));
//					lblAvatar4.setBounds(10, 233, 64, 64);
//					panel_2.add(lblAvatar4);
//					
//					URL url5 = new URL(FileHandler.getAllSummaries().get(numOfPlayers - 5).getAvatarMedium());
//					Image img5 = ImageIO.read(url5);
//					JLabel lblAvatar5 = new JLabel(new ImageIcon(img5));
//					lblAvatar5.setBounds(10, 307, 64, 64);
//					panel_2.add(lblAvatar5);
//					
//					URL url6 = new URL(FileHandler.getAllSummaries().get(numOfPlayers - 4).getAvatarMedium());
//					Image img6 = ImageIO.read(url6);
//					JLabel lblAvatar6 = new JLabel(new ImageIcon(img6));
//					lblAvatar6.setBounds(310, 11, 64, 64);
//					panel_2.add(lblAvatar6);
//					
//					URL url7 = new URL(FileHandler.getAllSummaries().get(numOfPlayers - 3).getAvatarMedium());
//					Image img7 = ImageIO.read(url7);
//					JLabel lblAvatar7 = new JLabel(new ImageIcon(img7));
//					lblAvatar7.setBounds(310, 85, 64, 64);
//					panel_2.add(lblAvatar7);
//					
//					URL url8 = new URL(FileHandler.getAllSummaries().get(numOfPlayers - 2).getAvatarMedium());
//					Image img8 = ImageIO.read(url8);
//					JLabel lblAvatar8 = new JLabel(new ImageIcon(img8));
//					lblAvatar8.setBounds(310, 159, 64, 64);
//					panel_2.add(lblAvatar8);
//					
//					URL url9 = new URL(FileHandler.getAllSummaries().get(numOfPlayers - 1).getAvatarMedium());
//					Image img9 = ImageIO.read(url9);
//					JLabel lblAvatar9 = new JLabel(new ImageIcon(img9));
//					lblAvatar9.setBounds(310, 233, 64, 64);
//					panel_2.add(lblAvatar9);
//					
//					URL url10 = new URL(FileHandler.getAllSummaries().get(numOfPlayers).getAvatarMedium());
//					Image img10 = ImageIO.read(url10);
//					JLabel lblAvatar10 = new JLabel(new ImageIcon(img10));
//					lblAvatar10.setBounds(310, 307, 64, 64);
//					panel_2.add(lblAvatar10);
//					
//		        } catch (IOException ioE) {
//		        	ioE.printStackTrace();
//		        }		
				
				
				
				
				
				
				
				
		panel_1 = new JPanel();
		tabbedPane.addTab("Add Users", null, panel_1, null);
		panel_1.setLayout(null);
		
		textPane = new JTextPane();
		JScrollPane scrollPane = new JScrollPane(textPane);
		scrollPane.setBounds(10, 40, 599, 345);
		panel_1.add(scrollPane);
		scrollPane.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		
		JLabel lblPasteCsgoConsole_1 = new JLabel("Paste CSGO console information here to add users");
		lblPasteCsgoConsole_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblPasteCsgoConsole_1.setBounds(10, 11, 323, 18);
		panel_1.add(lblPasteCsgoConsole_1);
		
		JButton button_1 = new JButton("Process Information");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String userInput = textPane.getText() + " ";
				String[] uInput = userInput.split("#");
				String comID = "";
				
				for (int i = 1; i < uInput.length; i++) {
					
					if (!SteamDataParser.getSteamID(uInput[i]).equals("Invalid_Steam_ID")) {
						comID = SteamDataParser.convertToCommunityID(SteamDataParser.getSteamID(uInput[i]));
						if (!UserManager.isTracked(comID)) {
							textArea.append("Adding " + comID + " to tracked players.");
							textArea.append("\r\n");
							String jsonData = SteamWebAPI.getInfo(comID);
							UserManager.addPlayer(jsonData, comID, uInput[i]);
						} else {
							textArea.append(comID +" is already being tracked!");
							textArea.append("\r\n");
						}
					}
				}
				
			}
		});
		button_1.setBounds(10, 396, 599, 50);
		button_1.setFocusPainted(false);
		panel_1.add(button_1);
		
//		panel_2 = new JPanel();
//		tabbedPane.addTab("Recently Added Users", null, panel_2, null);
//		panel_2.setLayout(null);
		
//		textPane_1 = new JTextPane();
//		textPane_1.setBounds(20, 404, 567, 47);
//		textPane_1.setEditable(false);
//		JScrollPane scrollPane_1 = new JScrollPane(textPane_1);
//		scrollPane_1.setBounds(10, 11, 599, 400);
//		panel_2.add(scrollPane_1);
//		scrollPane_1.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
//		
//		
//		if (panel_2.isFocusable()) {
//			StyledDocument doc = textPane_1.getStyledDocument();
//			try {
//				doc.insertString(doc.getLength(), FileHandler.getAllPlayers().toString(), null);
//			} catch (BadLocationException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		}
		
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Check Bans", null, panel_3, null);
		panel_3.setLayout(null);
		
		JButton button = new JButton("Check Bans");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					Document doc = textPane_2.getDocument();
					
					//Clear textpane for new information to be shown.
					textPane_2.setText("");
					
					int numBanned = 0;
					
					String str = " has been VAC banned!";

					for (int i = 0; i < FileHandler.getAllPlayers().size(); i++) {
						if (FileHandler.getAllPlayers().get(i).getVacBan() == true) {
							if (UserManager.bannedSinceAdded(FileHandler.getAllPlayers().get(i).getSteamId())) {
								numBanned++;
								doc.insertString(doc.getLength(), FileHandler.getAllPlayers().get(i).getSteamId() + str, null);
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
		panel_3.add(button);
		
		textPane_2 = new JTextPane();
		textPane_2.setBounds(65, 485, 314, 107);
		textPane_2.setEditable(false);
		JScrollPane scrollPane_2 = new JScrollPane(textPane_2);
		scrollPane_2.setBounds(10, 45, 599, 400);
		panel_3.add(scrollPane_2);
		scrollPane_2.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		
		
		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("Statistics", null, panel_4, null);
		panel_4.setLayout(null);
		
		JPanel panel_5 = new JPanel();
		tabbedPane.addTab("Find A User", null, panel_5, null);
		panel_5.setLayout(null);
		
		
		
	}
}	



//Open URL in browser
/*
 
  				try {
				     String url = "http://steamcommunity.com/profiles/" + comID ;
				     java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
				} catch (java.io.IOException e) {
				     System.out.println(e.getMessage());
				}
*/
