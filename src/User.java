import java.io.Serializable;

/**
 * Class used to structure the results of GetPlayerBans/v1 from the Steam Web API.
 * 
 * @author Steven
 *
 */
public class User implements Serializable, Comparable<User> {

	// Variable names are set by the Steam Web API, (does not use camelCase).
	private String SteamId;
	private boolean CommunityBanned;
	private boolean VACBanned;
	private int NumberOfVACBans;
	private int DaysSinceLastBan;
	private String EconomyBan;
	private int NumberOfGameBans;

	private String dateAdded;
	private String dateUpdated;

	public User() {
		this.SteamId = "NULL";
		this.CommunityBanned = false;
		this.VACBanned = false;
		this.NumberOfVACBans = 0;
		this.DaysSinceLastBan = 0;
		this.EconomyBan = "none";
		this.NumberOfGameBans = 0;
		this.dateAdded = "yyyy/mm/dd";
	}

	public User(String sID, boolean cBan, boolean vBan, int numBans, int daysBan, String eBan, int gBan, String date) {
		this.SteamId = sID;
		this.CommunityBanned = cBan;
		this.VACBanned = vBan;
		this.NumberOfVACBans = numBans;
		this.DaysSinceLastBan = daysBan;
		this.EconomyBan = eBan;
		this.NumberOfGameBans = gBan;
		this.dateAdded = date;


	}

	public void setSteamId(String id) {
		this.SteamId = id;
	}

	public String getSteamId() {
		return SteamId;
	}

	public void setCommunityBanned(boolean cBan) {
		this.CommunityBanned = cBan;
	}

	public boolean getCommunityBanned() {
		return CommunityBanned;
	}

	public void setVacBan(boolean flag) {
		VACBanned = flag;
	}

	public boolean getVacBan() {
		return VACBanned;
	}

	public void setNumberOfBans(int num) {
		this.NumberOfVACBans = num;
	}

	public int getNumberOfBans() {
		return NumberOfVACBans;
	}

	public void setDaysSinceLastBan(int days) {
		this.DaysSinceLastBan = days;
	}

	public int getDaysSinceLastBan() {
		return DaysSinceLastBan;
	}

	public void setEconomyBanned(String eBan) {
		this.EconomyBan = eBan;
	}

	public String getEconomyBanned() {
		return EconomyBan;
	}
	
	public void setNumberOfGameBans(int num) {
		this.NumberOfGameBans = num;
	}
	
	public int getNumberOfGameBans() {
		return NumberOfGameBans;
	}

	public String getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(String newDate) {
		this.dateAdded = newDate;
	}
	
	public String getDateUpdated() {
		return dateUpdated;
	}
	
	public void setDateUpdated(String date) {
		dateUpdated = date;
	}
	
	public static User updateUser(User u, String date) {
		u.dateUpdated = date;
		return u;
	}

	@Override
	public boolean equals(Object o) {

		User u = (User) o;

		if (!(o instanceof User)) {
			return false;
		} else if (u.SteamId.equalsIgnoreCase(this.SteamId)) {
			return true;
		} else {
			return false;
		}

	};

	@Override
	public String toString() {

		return ("\r\n\r\n{SteamId:" + SteamId + ",\r\n" + "  Vac Banned:" + VACBanned + ",\r\n"
				+ "  Number Of Vac Bans:" + NumberOfVACBans + ",\r\n" + "  Days Since Last Ban:" + DaysSinceLastBan
				+ ",\r\n" + "  Date Added:" + dateAdded + "}");

	}

	@Override
	public int compareTo(User u) {
		if (Long.valueOf(this.getSteamId()).compareTo(Long.valueOf(u.getSteamId())) < 0) {
			return -1;
		} else if (Long.valueOf(this.getSteamId()).compareTo(Long.valueOf(u.getSteamId())) > 0) {
			return 1;
		} else {
			return 0;
		}
	}
}
