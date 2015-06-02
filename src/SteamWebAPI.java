import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *Implements the methods that are provided by the Steam Web API. Steam profile information is obtained through
 *HTTP requests to the steam servers.
 */
public class SteamWebAPI {
	
	//TODO GetPlayerBans returns Community, VAC, and Economy ban statuses for given players.
	//TODO GetSchemaForGame returns gamename, gameversion and availablegamestats(achievements and stats).
	//Steam64ID: 76561198012483594,76561198000020858,76561198056862084
	
	//Steam API key
	private final static String APIKEY = "DF0D7C47A867F2A50A9C9A201AFCABF9";
	
	//URL components
	private static String baseURL = "http://api.steampowered.com/";
	private static String interfaceName = "ISteamUser";

	private static String userID = "";
	private static String URLToRequest = baseURL + interfaceName + "/" + "GetPlayerBans/v1/?key=" + APIKEY + "&steamids=";
	
	//String that contains JSON file.
	private static String line;
	private static StringBuilder result;
	
	//String that contains aliases in JSON format.
	private static StringBuilder aliases;
	
	//String that contains player summaries in JSON format.
	private static StringBuilder summaries;

	/**
	 * Get the profile information(if profile is set to public) of a player returned as JSON format.
	 * @param id Community ID of the user.
	 * @return Profile information for the user.
	 */
	public static String getInfo(String id) {
		
		userID = id;
		
		try {
			
			URL url = new URL(URLToRequest + userID);
			
			System.out.println(url);
			
			URLConnection conn = url.openConnection();
			InputStream is = conn.getInputStream();
			//Read information from HTTP request and append to a string.
			//Code taken from http://stackoverflow.com/questions/9856195/how-to-read-an-http-input-stream
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			result = new StringBuilder();
			
			while ((line = reader.readLine()) != null) {
				result.append(line + "\n");
			}
			
			//System.out.println(result.toString());
		} catch (MalformedURLException ex) {
			
			System.out.println("EXCEPTION:Malformed URL!");
		} catch (IOException e1) {
			
			System.out.println("EXCEPTION:IOException");
		}
		
		//Clear userIDs to be used again.
//		userID = "";
		
		return result.toString();
		
	}
	
	public static String getUserAlias(String communityID) {
		//http://steamcommunity.com/profiles/{steam64BitId}/ajaxaliases
		
		try {
			URL url = new URL("http://steamcommunity.com/profiles/" + communityID + "/ajaxaliases");
			//System.out.println(url);
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
		
		//Parse user alises and get most current one used.
		
		return aliases.toString();
	}
	
	/**
	 * Get the summary of a player in JSON format.
	 * @param communityID Steam Community ID of the player you want the summary of.
	 * @return A string containing the player summary in JSON format.
	 */
	public static String getPlayerSummaries(String communityID) {
		
		try {
			URL url = new URL(baseURL + interfaceName + "/GetPlayerSummaries/v0002/?key=" + APIKEY + "&steamids=" + communityID);
			//System.out.println(url);
			URLConnection conn = url.openConnection();
			InputStream is = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			summaries = new StringBuilder();
			
			while ((line = reader.readLine()) != null) {
				summaries.append(line + "\n");
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return summaries.toString();
	}
	
}
