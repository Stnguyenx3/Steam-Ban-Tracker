import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Handles reading/writing of files.
 * 
 * @author Steven
 *
 */
public class FileHandler implements Serializable {

	private static ArrayList<User> tracked = new ArrayList<User>();

	private static ArrayList<PlayerSummary.Player> trackedSummaries = new ArrayList<PlayerSummary.Player>();

	private static ArrayList<PlayerSummary.Player> trackedSummariesUnsorted = new ArrayList<PlayerSummary.Player>();

	private static ArrayList<String[]> games = new ArrayList<String[]>();

	private static String savedString = "";

	private static String APIKEY = "";

	public static void writeToFile(String fileName, Object objToWrite) {

		try {
			FileOutputStream fOS = new FileOutputStream(fileName);
			ObjectOutputStream oOS = new ObjectOutputStream(fOS);
			oOS.writeObject(objToWrite);
			oOS.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void readFromFile(String fileName, String objType) {
		FileInputStream fIS;
		try {

			fIS = new FileInputStream(fileName);
			ObjectInputStream oIS = new ObjectInputStream(fIS);

			if (objType.equalsIgnoreCase("ArrayList<User>")) {
				tracked = (ArrayList<User>) oIS.readObject();
			} else if (objType.equalsIgnoreCase("ArrayList<PlayerSummary.Player>")) {
				if (fileName.equalsIgnoreCase("summaries.tmp")) {
					trackedSummaries = (ArrayList<PlayerSummary.Player>) oIS.readObject();
				}
				if (fileName.equalsIgnoreCase("s_unsorted.tmp")) {
					trackedSummariesUnsorted = (ArrayList<PlayerSummary.Player>) oIS.readObject();
				}
			} else if (objType.equalsIgnoreCase("ArrayList<String[]>")) {
				games = (ArrayList<String[]>) oIS.readObject();
			} else {
				System.out.println("Object type " + objType + " needs to be implemented!");
			}

			oIS.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @return ArrayList of User objects currently being tracked.
	 */
	public static ArrayList<User> getAllPlayers() {
		return tracked;
	}

	public static ArrayList<PlayerSummary.Player> getAllSummaries() {
		return trackedSummaries;
	}

	public static ArrayList<PlayerSummary.Player> getAllSummariesUnsorted() {
		return trackedSummariesUnsorted;
	}

	public static ArrayList<String[]> getGames() {
		return games;
	}

	public static String getSavedString() {
		return savedString;
	}

	public static void setSavedString(String str) {
		savedString = str;
	}

	/**
	 * Get the Steam Web API key from file.
	 * 
	 * @return String containing Steam Web API key
	 */
	public static String getAPIKey() {

		try {

			BufferedReader br = new BufferedReader(new FileReader("apikey.txt"));
			APIKEY = br.readLine();

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return APIKEY;
	}

	public static void updateArrayList(ArrayList<User> a) {
		tracked = a;
	}

}
