import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import com.google.gson.Gson;

/**
 * Stores all of the user's being tracked, allows for the addition, retrieval,
 * and removal of players being tracked.
 * 
 * @author Steven
 *
 */
public class UserManager {

	private static UserManager instance = new UserManager();

	private UserManager() {
	};

	public static UserManager getInstance() {
		return instance;
	}

	// gson Object used to parse json data.
	static Gson gson = new Gson();

	private static ArrayList<User> trackedPlayers = FileHandler.getAllPlayers();

	private static ArrayList<PlayerSummary.Player> trackedSummaries = FileHandler.getAllSummaries();

	// ArrayList containing the current requests User objects, used for
	// displaying information to the user in .
	private static ArrayList<User> lastAdded = new ArrayList<User>();

	// For the current request, an ArrayList containing Users that are already
	// being tracked.
	private static ArrayList<User> alreadyTracked = new ArrayList<User>();

	/**
	 * Add player(s) to be tracked.
	 * 
	 * @param jData
	 *            JSON data returned from Steam Web API.
	 * @param ids
	 *            Array of Steam Community ID of players to be added.
	 * @param size
	 *            The number of players currently in the Array.
	 */
	public void addPlayer(String jData, String[] ids, int size, String[] rawData) {

		// Deserialize JSON data into SteamUsers Object.
		SteamUsers newUsr = gson.fromJson(jData, SteamUsers.class);

		// Make sure a user has been found before adding to list.
		if (!newUsr.isEmpty()) {

			// Initialize Date
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
			Date date = new Date();
			String dateAdded = dateFormat.format(date);

			for (int i = 0; i < newUsr.getSize(); i++) {

				User currentUser = newUsr.getPlayer(i);
				User user = new User(currentUser.getSteamId(), currentUser.getCommunityBanned(),
						currentUser.getVacBan(), currentUser.getNumberOfBans(), currentUser.getDaysSinceLastBan(),
						currentUser.getEconomyBanned(), dateAdded, currentUser.getNumberOfBans());

				// Check if user is already being tracked
				if (!trackedPlayers.contains(user)) {
					trackedPlayers.add(user);
					lastAdded.add(user);

				} else {
					// System.out.println(user.getSteamId() +
					// " is already being tracked!");
					alreadyTracked.add(user);
				}

			}

			if (lastAdded.size() > 0) {
				// Get Player Summaries and add to ArrayList.
				parsePlayerSummary(lastAdded);
			}
			
			// Sort trackedPlayers and trackedSummaries so the player and their summaries match.
			Collections.sort(trackedPlayers);
			Collections.sort(trackedSummaries);

		} else {
			System.out.println("User(s) does not exist!");
		}

	}

	/**
	 * 
	 * @return An int representing the number of players being tracked.
	 */
	public int getNumOfPlayers() {
		return trackedPlayers.size();
	}

	/**
	 * Get the player at specified index.
	 * 
	 * @param playerToGet
	 *            Interger representing index of User you want returned.
	 * @return User at specified index.
	 */
	public User getPlayer(int playerToGet) {
		return trackedPlayers.get(playerToGet);
	}

	/**
	 * Check whether a User is being tracked or not.
	 * 
	 * @param cID
	 *            Steam Community ID of User.
	 * @return Boolean value, true if player is tracked, false if not.
	 */
	public boolean isTracked(String cID) {

		ArrayList<User> users = FileHandler.getAllPlayers();

		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getSteamId().equals(cID)) {
				return true;
			}
		}
		return false;
	}

	public void parsePlayerSummary(ArrayList<User> communityID) {

		String jsonData = SteamWebAPI.getPlayerSummaries(communityID);
		PlayerSummary playerSummary = new PlayerSummary();

		if (!jsonData.equalsIgnoreCase("No Summaries.")) {
			playerSummary = gson.fromJson(jsonData, PlayerSummary.class);
		}

		// System.out.println("Printing player summary...");
		// System.out.println(playerSummary);

		if (!playerSummary.response.isEmpty()) {

			for (int i = 0; i < playerSummary.response.size(); i++) {

				PlayerSummary.Player playerToAdd = playerSummary.response.getPlayer(i);

				trackedSummaries.add(playerToAdd);

			}

		}

	}

	/**
	 * Check whether or not a player has been VAC banned after being adding to
	 * the list.
	 * 
	 * @param cID
	 * @return
	 */
	public boolean bannedSinceAdded(String cID) {

		boolean found = false;

		for (int i = 0; i < FileHandler.getAllPlayers().size(); i++) {

			// Search for player in list.
			if (cID.equals(FileHandler.getAllPlayers().get(i).getSteamId())) {

				found = true;

				if (FileHandler.getAllPlayers().get(i).getNumberOfBans() > FileHandler.getAllPlayers().get(i)
						.getInitVACBans()) {
					System.out.println(FileHandler.getAllPlayers().get(i).getSteamId() + " ("
							+ FileHandler.getAllSummaries().get(i).getPersonaName() + ") has been VAC banned!");
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
	 * 
	 * @param num
	 *            The number of past days you want to check. ie 30 checks the
	 *            past month for VAC bans.
	 */
	public void recentVACBans(int num) {
		for (int i = 0; i < FileHandler.getAllPlayers().size(); i++) {
			if (FileHandler.getAllPlayers().get(i).getDaysSinceLastBan() < num) {
				System.out.println(FileHandler.getAllPlayers().get(i).getSteamId() + " has a recent VAC ban!");
			}
		}
	}

	/**
	 * 
	 * @return ArrayList of User objects that contain the last added Users
	 */
	public ArrayList<User> getLastAdded() {
		return lastAdded;
	}

	/**
	 * Clear the ArrayList lastAdded for future use.
	 */
	public void clearLastAdded() {
		lastAdded.clear();
	}

	/**
	 * 
	 * @return ArrayList containing User objects that are already being tracked.
	 */
	public ArrayList<User> getCurrentlyTracked() {
		return alreadyTracked;
	}

	/**
	 * Clear the ArrayList alreadyTracked for future use.
	 */
	public void clearCurrentlyTracked() {
		alreadyTracked.clear();
	}

}
