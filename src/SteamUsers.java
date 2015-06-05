import java.util.ArrayList;

public class SteamUsers {

	// Variable name set by Steam Web API!
	// Requests are returned as an array of players...
	private ArrayList<User> players = new ArrayList<User>();

	public SteamUsers() {
	}

	@Override
	public String toString() {
		return (players.toString());
	}

	/**
	 * Get the user at a specific index in the ArrayList players.
	 * 
	 * @param index
	 *            The index of a user to be returned.
	 * @return The user at the specified index.
	 */
	public User getPlayer(int index) {
		return players.get(index);
	}

	/**
	 * Determine whether or not the 'players' ArrayList is empty.
	 * 
	 * @return Boolean value true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		if (players.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Get the size of the 'players' ArrayList.
	 * 
	 * @return Integer representing the size of the 'players' ArrayList.
	 */
	public int getSize() {
		return players.size();
	}

}
