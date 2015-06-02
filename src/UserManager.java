import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;

/**
 * Stores all of the user's being tracked, allows for the addition, retrieval, and removal of players being tracked.
 * @author Steven
 *
 */
public class UserManager {
	
	//gson Object used to parse json data.
	static Gson gson = new Gson();
	
	private static ArrayList<User> trackedPlayers = FileHandler.getAllPlayers();
	
	private static ArrayList<PlayerSummary.Player> trackedSummaries = FileHandler.getAllSummaries();
	
	public static void addPlayer(String jData, String id, String lineOfData) {

		//Deserialize JSON data into SteamUsers Object.
		SteamUsers newUsr = gson.fromJson(jData, SteamUsers.class);
		
		//Make sure a user has been found before adding to list.
		if (!newUsr.isEmpty()) {
			
			System.out.println("Adding " + newUsr.getPlayer(0).getSteamId() + " to the list!");
			
			//Initialize Date
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
			Date date = new Date();
			String dateAdded = dateFormat.format(date);
			
			User currentUser = newUsr.getPlayer(0);
			User user = new User(currentUser.getSteamId(),
				     currentUser.getCommunityBanned(),
				     currentUser.getVacBan(),
				     currentUser.getNumberOfBans(),
				     currentUser.getDaysSinceLastBan(),
				     currentUser.getEconomyBanned(),
				     dateAdded,
				     currentUser.getNumberOfBans());
			
			trackedPlayers.add(user);
			
			//Get Player Summaries and add to  ArrayList.
			parsePlayerSummary(id);
			
			
		} else {
			System.out.println("User with ID:" + id + " does not exist!");
		}
		
	}
	
	/**
	 * 
	 * @return An int representing the number of players being tracked.
	 */
	public static int getNumOfPlayers() {
		return trackedPlayers.size();
	}
	
	/**
	 * Get the player at specified index. 
	 * @param playerToGet Interger representing index of User you want returned.
	 * @return User at specified index.
	 */
	public static User getPlayer(int playerToGet) {
		return trackedPlayers.get(playerToGet);
	}
	
	/**
	 * Check whether a User is being tracked or not.
	 * @param cID Steam Community ID of User.
	 * @return Boolean value, true if player is tracked, false if not.
	 */
	public static boolean isTracked(String cID) {
		
		ArrayList<User> users = FileHandler.getAllPlayers();
		
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getSteamId().equals(cID) ) {
				return true;
			}
		}
		return false;
	}
    
    /**
     * Count the number of times a substring appears in a string.
     * @param str The string to be checked.
     * @param subStr The substring to check for.
     * @return An integer value representing the number of times the substring occurs in the string.
     */
    private static int countSubstring(String str, String subStr) {
    	
    	int count = 0;
    	
    	for (int i = str.indexOf(subStr); i != -1; i = str.indexOf(subStr, i + subStr.length())) {
    		count++;
    	}
    	
    	return count;
    	
    }
    
    public static void parsePlayerSummary(String communityID) {
    	String jsonData = SteamWebAPI.getPlayerSummaries(communityID);
    	PlayerSummary playerSummary = new PlayerSummary();
    	playerSummary = gson.fromJson(jsonData, PlayerSummary.class);
    	
    	System.out.println("Printing player summary...");
    	System.out.println(playerSummary);
    	
    	if (!playerSummary.response.isEmpty()) {
    		PlayerSummary.Player playerToAdd = playerSummary.response.getPlayer(0);
    		
    		trackedSummaries.add(playerToAdd);

    	}
    	
    }
    
    /**
     * Check whether or not a player has been VAC banned after being adding to the list.
     * @param cID
     * @return
     */
    public static boolean bannedSinceAdded(String cID) {
		
    	boolean found = false; 	
    	
    	for (int i = 0; i < FileHandler.getAllPlayers().size(); i++) {
    		
    		//Search for player in list.
    		if (cID.equals(FileHandler.getAllPlayers().get(i).getSteamId())) {
		
    			found = true;
    			
    			if (FileHandler.getAllPlayers().get(i).getNumberOfBans() > FileHandler.getAllPlayers().get(i).getInitVACBans()) {
    				System.out.println(FileHandler.getAllPlayers().get(i).getSteamId() + " (" +
    								   FileHandler.getAllSummaries().get(i).getPersonaName()+ ") has been VAC banned!");
    				return true;
    			}
    			
    		}
    		
    	}
    	
    	if (!found) {
    		System.out.println("User could not be found.");
    	}
    	
    	return false;

    	
    }
    
    /**
     * Check players for recent VAC bans.
     * @param num The number of past days you want to check. ie 30 checks the past month for VAC bans.
     */
    public void recentVACBans(int num) {
    	for (int i = 0; i < FileHandler.getAllPlayers().size(); i++) {
    		if (FileHandler.getAllPlayers().get(i).getDaysSinceLastBan() < num) {
    			System.out.println(FileHandler.getAllPlayers().get(i).getSteamId() + " has a recent VAC ban!");
    		}
    	}
    }
   
	
}
