import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
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

	private static ArrayList<User> trackedPlayers = new ArrayList<User>();

	private static ArrayList<CompletedPlayer> completedPlayers = FileHandler.getCompletedPlayers();

	// ArrayList containing the current requests User objects, used for
	// displaying information to the user in .
	private static ArrayList<User> lastAdded = new ArrayList<User>();

	// For the CURRENT request, an ArrayList containing Users that are already
	// being tracked.
	private static ArrayList<User> alreadyTracked = new ArrayList<User>();

	// ArrayList containing updated user information used to check for bans.
	private static ArrayList<User> updatedUsers = new ArrayList<User>();

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
	public void addPlayer(String jData, String[] ids, int size) {

		// Initialize trackedPlayers using CompletedPlayer objects.
		trackedPlayers.clear();
		for (int i = 0; i < completedPlayers.size(); i++) {
			trackedPlayers.add(completedPlayers.get(i).getUser());
		}

		// Deserialize JSON data into SteamUsers Object.
		SteamUsers newUsr = gson.fromJson(jData, SteamUsers.class);

		if (newUsr != null) {
			// Make sure a user has been found before adding to list.
			if (!newUsr.isEmpty()) {

				// Initialize Date
				// DateFormat dateFormat = new
				// SimpleDateFormat("MM/dd/yy 'at' HH:mm:ss z");
				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
				Date date = new Date();
				String dateAdded = dateFormat.format(date);

				for (int i = 0; i < newUsr.getSize(); i++) {

					User currentUser = newUsr.getPlayer(i);
					User user = null;

					if (!trackedPlayers.contains(currentUser)) {
						user = new User(currentUser.getSteamId(), currentUser.getCommunityBanned(),
								currentUser.getVacBan(), currentUser.getNumberOfBans(),
								currentUser.getDaysSinceLastBan(), currentUser.getEconomyBanned(),
								currentUser.getNumberOfGameBans(), dateAdded);

						String playerSummaryJson = SteamWebAPI.getPlayerSummaries(user.getSteamId());
						PlayerSummary playerSummary = new PlayerSummary();
						if (!playerSummaryJson.equalsIgnoreCase("No summary!")) {
							playerSummary = gson.fromJson(playerSummaryJson, PlayerSummary.class);
						} else {
							System.out.println("No Summary could be found!");
						}

						if (playerSummary.response.size() > 0) {
							CompletedPlayer newPlayer = new CompletedPlayer(user, playerSummary.response.getPlayer(0));
							completedPlayers.add(newPlayer);
						} else {
							BanTracker.displayInfoBox("Unable to get summary for " + user.getSteamId(), "Error");
						}

					} else {
						int index = trackedPlayers.indexOf(currentUser);
						user = User.updateUser(trackedPlayers.get(index), dateAdded);
					}

					// Check if user is already being tracked
					if (!trackedPlayers.contains(user)) {
						trackedPlayers.add(user);
						lastAdded.add(user);

					} else {
						alreadyTracked.add(user);
						// Update user information
						updatedUsers.add(user);
					}
				}

				// Sort trackedPlayers, trackedSummaries, and updatedUsers so
				// the player and their summaries match.
				Collections.sort(trackedPlayers);
				Collections.sort(updatedUsers);

			} else {
				System.out.println("User(s) does not exist!");
			}
		}
	}

	/**
	 * 
	 * @return ArrayList of User objects representing all users being tracked.
	 */
	public ArrayList<User> getTracked() {
		trackedPlayers.clear();
		for (int i = 0; i < completedPlayers.size(); i++) {
			trackedPlayers.add(completedPlayers.get(i).getUser());
		}

		return trackedPlayers;
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

		ArrayList<CompletedPlayer> players = FileHandler.getCompletedPlayers();

		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getUser().getSteamId().equals(cID)) {
				return true;
			}
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
		for (int i = 0; i < FileHandler.getCompletedPlayers().size(); i++) {
			if (FileHandler.getCompletedPlayers().get(i).getUser().getDaysSinceLastBan() < num) {
				System.out.println(FileHandler.getCompletedPlayers().get(i).getUser().getSteamId()
						+ " has a recent VAC ban!");
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

	/**
	 * Send request to Steam Web API to update all Users being tracked.
	 */
	public void updateUsers() {

		// Clear updatedUsers so that it may be used again.
		updatedUsers.clear();

		trackedPlayers.clear();
		for (int i = 0; i < completedPlayers.size(); i++) {
			trackedPlayers.add(completedPlayers.get(i).getUser());
		}

		String[] ids = new String[trackedPlayers.size()];

		for (int i = 0; i < trackedPlayers.size(); i++) {
			ids[i] = trackedPlayers.get(i).getSteamId();
		}

		// Split ids Array to have 100 elements. The Steam Web API allows a
		// maximum of 100 players per request.

		List<String> allIDs = Arrays.asList(ids);
		List<List<String>> subIDs = Lists.partition(allIDs, 100);

		for (int i = 0; i < subIDs.size(); i++) {

			String[] subIDsArr = new String[subIDs.get(i).size()];
			subIDsArr = subIDs.get(i).toArray(subIDsArr);

			String jsonData = SteamWebAPI.getInfo(subIDsArr);

			addPlayer(jsonData, subIDsArr, subIDsArr.length);
		}

	}

	/**
	 * 
	 * @return ArrayList of updated User objects.
	 */
	public ArrayList<User> getUpdatedUsers() {
		return updatedUsers;
	}

}
