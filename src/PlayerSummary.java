import java.io.Serializable;
import java.util.ArrayList;


public class PlayerSummary {
	
	//Variable name must be "response" because GSON uses reflection lookup.
	public Response response = new Response();
	
	class Response {
		
		//Variable name must be "players" because GSON uses reflection lookup.
		//Used to store data from JSON data. Do not use this ArrayList to
		//store Players because it is overwritten on subsequent JSON parses.
		private ArrayList<Player> players = new ArrayList<Player>();
		
		/**
		 * Get the specific Player from the ArrayList that contain the
		 * Players that have been converted from JSON to Java.
		 * @param index Index of the Player you want to retrieve.
		 * @return The Player at the specified index.
		 */
		public Player getPlayer(int index) {
			return players.get(index);
		}
		
		public boolean isEmpty() {
			if (players.isEmpty()) {
				return true;
			} else {
				return false;
			}
		}
		
		public void addPlayer(Player player) {
			players.add(player);
		}
		
		@Override
		public String toString() {
			return (players.toString());
		}
		
	}
	
	class Player implements Serializable {
		private String steamid;
		private String communityvisibilitystate;
		private String profilestate;
		private String personaname;
		private String lastlogoff;
		private String profileurl;
		private String avatar;
		private String avatarmedium;
		private String avatarfull;
		private String personastate;
		private String commentpermission;
		
		private String realname;
		private String primaryclanid;
		private String timecreated;
		private String gameid;
		private String gameserverip;
		private String gameextrainfo;
		private String cityid;
		private String loccountrycode;
		private String locstatecode;
		private String loccityid;
		
		public String getSteamID() {
			return steamid;
		}
		
		public void setSteamID(String newSteamID) {
			steamid = newSteamID;
		}
		
		public String getPersonaName() {
			return personaname;
		}
		
		public void setPersonaName(String name) {
			personaname = name;
		}
		
		public String getProfileURL() {
			return profileurl;
		}
		
		public void setProfileURL(String url) {
			profileurl = url;
		}
		
		public String getAvatarMedium() {
			return avatarmedium;
		}
		
		public void setAvatarMedium(String url) {
			this.avatarmedium = url;
		}
		
		@Override
		public String toString() {
			return "<<" + "SteamID = " + steamid + "    " + 
						  "Name = " + personaname + "    " + 
						  "Public/Private = " + communityvisibilitystate + "    " + 
						  "Account created = " + timecreated + ">>";
		}
		
	}
	
    public String toString() {
    	return response.toString();
    }

}
