import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 * Class used to handle data to be used with JTables. Converts ArrayList of User
 * and Player objects into a 2D Arrays to be inserted into a JTable.
 * 
 * @author Steven
 *
 */
public class PlayerTableModel extends AbstractTableModel {

	ArrayList<User> users = FileHandler.getAllPlayers();
	ArrayList<PlayerSummary.Player> summaries = FileHandler.getAllSummaries();

	@Override
	public int getColumnCount() {

		return 7;
	}

	@Override
	public int getRowCount() {

		return users.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		User user = users.get(rowIndex);

		switch (columnIndex) {
		case 0:
			if (user.getSteamId().equalsIgnoreCase(summaries.get(rowIndex).getSteamID())) {
				return summaries.get(rowIndex).getPersonaName();
			} else {
				return null;
			}
		case 1:
			return user.getDateAdded();
		case 2:
			return user.getDateUpdated();
		case 3:
			return user.getNumberOfBans();
		case 4:
			return user.getNumberOfGameBans();
		case 5:
			return user.getDaysSinceLastBan();
		case 6:
			return user.getSteamId();
		}

		return null;
	}

	public String getColumnName(int columnIndex) {

		switch (columnIndex) {
		case 0:
			return "ID";
		case 1:
			return "Date added";
		case 2:
			return "Date updated";
		case 3:
			return "VAC bans";
		case 4:
			return "Game bans";
		case 5:
			return "Last ban (days)";
		case 6:
			return "64-Bit SteamID";
		}
		return null;

	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}

}
