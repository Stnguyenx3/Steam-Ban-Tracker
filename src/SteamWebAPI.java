import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Implements the methods that are provided by the Steam Web API. Steam profile
 * information is obtained through HTTP requests to the steam servers.
 */
public class SteamWebAPI {

	// URL components
	private static String baseURL = "http://api.steampowered.com/";
	private static String interfaceName = "ISteamUser";

	static StringBuilder userIDs = new StringBuilder();
	private static String URLToRequest = baseURL + interfaceName + "/" + "GetPlayerBans/v1/?key="
			+ FileHandler.getAPIKey() + "&steamids=";

	// String that contains JSON file.
	private static String line;
	private static StringBuilder result;

	// String that contains aliases in JSON format.
	private static StringBuilder aliases;

	// String that contains player summaries in JSON format.
	private static StringBuilder summaries;

	/**
	 * Get the profile information(if profile is set to public) of a player
	 * returned as JSON format.
	 * 
	 * @param ids
	 *            Array of Steam Community IDs.
	 * @return Profile information for the Array of users in JSON.
	 */
	public static String getInfo(String[] ids) {

		// Clear userIDs to be used again.
		userIDs.setLength(0);

		for (int i = 0; i < ids.length; i++) {
			if (ids[i] != null) {
				userIDs.append(ids[i] + ",");
			} else {
				break;
			}
		}

		// Remove extra comma at end of String.
		String uIDsString = userIDs.substring(0, userIDs.length() - 1);

		try {

			URL url = new URL(URLToRequest + uIDsString);

			// System.out.println(url);

			URLConnection conn = url.openConnection();
			InputStream is = conn.getInputStream();
			// Read information from HTTP request and append to a string.
			// Code taken from
			// http://stackoverflow.com/questions/9856195/how-to-read-an-http-input-stream
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			result = new StringBuilder();

			while ((line = reader.readLine()) != null) {
				result.append(line + "\n");
			}

		} catch (MalformedURLException ex) {

			System.out.println("EXCEPTION:Malformed URL!");
		} catch (IOException e1) {

			System.out.println("EXCEPTION:IOException");
		}

		if (result == null) {
			return null;
		} else {
			return result.toString();
		}

	}

	/**
	 * Get User profile names.
	 * @param communityID String containing the Steam Community ID of the player to get aliases for.
	 * @return String containing all User aliases and the time it was changed.
	 */
	public static String getUserAlias(String communityID) {
		// http://steamcommunity.com/profiles/{steam64BitId}/ajaxaliases

		try {
			URL url = new URL("http://steamcommunity.com/profiles/" + communityID + "/ajaxaliases");
			// System.out.println(url);
			URLConnection conn = url.openConnection();
			InputStream is = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			aliases = new StringBuilder();

			while ((line = reader.readLine()) != null) {
				aliases.append(line + "\n");
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Parse user alises and get most current one used.

		return aliases.toString();
	}

	/**
	 * Get the summary of players in JSON format.
	 * 
	 * @param communityID
	 *            Array of Steam Community ID of the players you want the
	 *            summary of.
	 * @return A string containing the player summary in JSON format.
	 */
	public static String getPlayerSummaries(ArrayList<User> communityID) {

		try {

			userIDs.delete(0, userIDs.length());

			for (int i = 0; i < communityID.size(); i++) {

				userIDs.append(communityID.get(i).getSteamId() + ",");

			}

			if (communityID.size() > 0) {

				// Remove extra comma at end of String.
				String uIDsString = userIDs.substring(0, userIDs.length() - 1);

				URL url = new URL(baseURL + interfaceName + "/GetPlayerSummaries/v0002/?key=" + FileHandler.getAPIKey()
						+ "&steamids=" + uIDsString);
				// System.out.println(url);
				URLConnection conn = url.openConnection();
				InputStream is = conn.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				summaries = new StringBuilder();

				while ((line = reader.readLine()) != null) {
					summaries.append(line + "\n");
				}

				return summaries.toString();

			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "No Summaries.";
	}
}
