import java.io.Serializable;

/**
 * Class used to structure the results of GetPlayerBans/v1.
 * 
 * @author Steven
 *
 */
public class User implements Serializable {

	// Variable names are set by the Steam Web API, (does not use camelCase).
	private String SteamId;
	private boolean CommunityBanned;
	private boolean VACBanned;
	private int NumberOfVACBans;
	private int DaysSinceLastBan;
	private String EconomyBan;

	private String dateAdded;
	private int initialVacBans;

	public User() {
		this.SteamId = "NULL";
		this.CommunityBanned = false;
		this.VACBanned = false;
		this.NumberOfVACBans = 0;
		this.DaysSinceLastBan = 0;
		this.EconomyBan = "none";
		this.dateAdded = "yyyy/mm/dd";
		this.initialVacBans = NumberOfVACBans;
	}

	public User(String sID, boolean cBan, boolean vBan, int numBans, int daysBan, String eBan, String date, int initVB) {
		this.SteamId = sID;
		this.CommunityBanned = cBan;
		this.VACBanned = vBan;
		this.NumberOfVACBans = numBans;
		this.DaysSinceLastBan = daysBan;
		this.EconomyBan = eBan;
		this.dateAdded = date;
		this.initialVacBans = initVB;

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

	public String getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(String newDate) {
		this.dateAdded = newDate;
	}

	public int getInitVACBans() {
		return initialVacBans;
	}

	public void setInitVACBans(int numBans) {
		this.initialVacBans = numBans;
	}

	@Override
	public String toString() {

		return ("\r\n\r\n{SteamId:" + SteamId + ",\r\n" + "  Vac Banned:" + VACBanned + ",\r\n"
				+ "  Number Of Vac Bans:" + NumberOfVACBans + ",\r\n" + "  Days Since Last Ban:" + DaysSinceLastBan
				+ ",\r\n" + "  Date Added:" + dateAdded + "}");

	}
}
