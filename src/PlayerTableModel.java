import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

/**
 * Class used to handle data to be used with JTables. Converts ArrayList of User
 * and Player objects into a 2D Array to be inserted into a JTable.
 * 
 * @author Steven
 *
 */
public class PlayerTableModel extends AbstractTableModel {

	private ArrayList<CompletedPlayer> players;
	
	public PlayerTableModel(ArrayList<CompletedPlayer> p) {
		this.players = new ArrayList<CompletedPlayer>(p);
	}

	@Override
	public int getColumnCount() {

		return 7;
	}

	@Override
	public int getRowCount() {

		return players.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		CompletedPlayer player = players.get(rowIndex);
		
		switch (columnIndex) {
		case 0:
			return player.getSummary().getPersonaName();
		case 1:
			return player.getUser().getDateAdded();
		case 2:
			return player.getUser().getDateUpdated();
		case 3:
			return player.getUser().getNumberOfBans();
		case 4:
			return player.getUser().getNumberOfGameBans();
		case 5:
			return player.getUser().getDaysSinceLastBan();
		case 6:
			return player.getUser().getSteamId();
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
	
	public void refresh(ArrayList<CompletedPlayer> list) {
		players = new ArrayList<CompletedPlayer>(list);
		fireTableDataChanged();
	}

}
