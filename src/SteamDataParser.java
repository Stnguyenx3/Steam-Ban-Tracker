/**
 * Handles information obtained from game console. Strips away unnecessary
 * information, able to retrieve Steam ID from a string, convert Steam ID into
 * Steam Community ID.
 * 
 * @author Steven
 *
 */
public class SteamDataParser {

	// A Steam ID has the following format: STEAM_X:Y:Z
	// Y is part of the ID number for the account, Y is either 0 or 1.
	// Z is the unique ID number for the account.

	/**
	 * Retrieves a Steam ID from the output generated by the in game console
	 * using the command 'status' in CSGO.
	 * 
	 * @param info
	 *            Single line string obtained from steam console that contains
	 *            user information.
	 * @return A user's Steam ID contained in a string, returns
	 *         "Invalid_Steam_ID" if ID is not properly formatted.
	 */
	public static String getSteamID(String info) {

		// Get the index of the first character in the Steam ID.
		if (info.contains("STEAM_1:")) {
			int startIndex = info.indexOf("STEAM_1");

			// Get the index of the last character in the Steam ID,
			// a whitespace determines that the Steam ID has ended.
			int endIndex = info.indexOf(" ", startIndex);
			// Strip away unnecessary information that is provided by Steam.
			String steamID = info.substring(startIndex, endIndex);
			// System.out.println(steamID + " is a valid steam ID!");
			return steamID;
		} else {
			// System.out.println("Invalid Steam ID!");
			return "Invalid_Steam_ID";
		}
	}

	/**
	 * Retrieves the Y-value of a Steam ID. The Y-Value is used to convert a
	 * Steam ID into a Steam Community ID which can be used to access a user's
	 * profile on http://steamcommunity.com/profiles/STEAM_COMMUNITY_ID
	 * 
	 * @param sIDY
	 *            A Steam ID, can be obtained from getSteamID().
	 * @return The Y-Value of a Steam ID which can be a 0 or 1.
	 */
	public static int getY(String sIDY) {
		// Get the index of first semicolon, Y-Value comes right after this
		// index.
		int startIndex = sIDY.indexOf(":");
		String yValue = sIDY.substring(startIndex + 1, startIndex + 2);
		int y = Integer.valueOf(yValue);
		return y;
	}

	/**
	 * Retrieves the Z-Value of a Steam ID. The Z-Value is a unique user ID used
	 * to convert a Steam ID into a Steam Community ID which can be used to
	 * access a user's profile at
	 * http://steamcommunity.com/profiles/STEAM_COMMUNITY_ID
	 * 
	 * @param sIDZ
	 *            A Steam ID, can be obtained from getSteamID().
	 * @return The unique ID (Z-Value) of a user's Steam ID.
	 */
	public static long getZ(String sIDZ) {
		// The Z-Value begins at the 11th character in the string.
		String zValue = sIDZ.substring(10, sIDZ.length());
		long z = Long.valueOf(zValue);
		return z;
	}

	/**
	 * Converts a Steam ID with the format STEAM_X:Y:Z into a Steam Community
	 * ID. The Community ID can be used to access a user's profile at
	 * http://steamcommunity.com/profiles/STEAM_COMMUNITY_ID
	 * 
	 * @param idToConvert
	 *            Steam ID to be converted into Steam Community ID.
	 * @return Steam Community ID as a string.
	 */
	public static String convertToCommunityID(String idToConvert) {

		// Formula to convert a Steam ID to a Community ID is W=Z*2+V+Y,
		// where Z is the unique user ID, Y is either 0 or 1, and
		// V is 76561197960265728, and W is the Community ID.
		// The base Steam uses for their Steam ID system.
		long idBase = 76561197960265728L;

		long cID = getZ(idToConvert) * 2 + idBase + getY(idToConvert);

		// System.out.println("http://steamcommunity.com/profiles/" + cID);
		return (Long.toString(cID));

	}
}
