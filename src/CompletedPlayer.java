import java.io.Serializable;

/**
 * Represents a player with both profile information and player summary.
 * @author Steven
 *
 */
public class CompletedPlayer implements Serializable {

	public User user;
	public PlayerSummary.Player summary;
	
	public CompletedPlayer(User u, PlayerSummary.Player s) {
		user = u;
		summary = s;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User u) {
		user = u;
	}
	
	public PlayerSummary.Player getSummary() {
		return summary;
	}
	
	public void setSummary(PlayerSummary.Player s) {
		summary = s;
	}
	
	@Override
	public String toString() {
		return summary.getPersonaName() + " [" + user.getSteamId() + "]";
	}
			
}
