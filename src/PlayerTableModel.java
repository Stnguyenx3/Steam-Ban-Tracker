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
			return (rowIndex+1) + ")";
		case 1:
			return user.getSteamId();
		case 2:
			return user.getDateAdded();
		case 3:
			return user.getDateUpdated();
		case 4:
			return user.getNumberOfBans();
		case 5:
			return user.getNumberOfGameBans();
		case 6:
			return user.getDaysSinceLastBan();
		}

		return null;
	}

	public String getColumnName(int columnIndex) {

		switch (columnIndex) {
		case 0: 
			return "#";
		case 1:
			return "ID";
		case 2:
			return "Date added";
		case 3:
			return "Date updated";
		case 4:
			return "VAC bans";
		case 5:
			return "Game bans";
		case 6:
			return "Last ban (days)";
		}
		return null;

	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}

}
