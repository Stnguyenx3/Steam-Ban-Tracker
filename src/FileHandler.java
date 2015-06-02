
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Handles reading/writing of files.
 * @author Steven
 *
 */
public class FileHandler implements Serializable {
	
	//Write to file
//	File file = new File("C:\\Users\\Steven\\workspace\\Steam Ban Tracker\\dat\\data.txt");
	
	private static ArrayList<User> tracked = new ArrayList<User>();
	
	private static ArrayList<PlayerSummary.Player> trackedSummaries = new ArrayList<PlayerSummary.Player>();
	
	private static String savedString = "";
	
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
				tracked = (ArrayList<User>)oIS.readObject();
			} else if (objType.equalsIgnoreCase("ArrayList<PlayerSummary.Player>")) {
				trackedSummaries = (ArrayList<PlayerSummary.Player>) oIS.readObject();
			} else {
				System.out.println("Object type needs to be implemented!");
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
	
	public static String getSavedString() {
		return savedString;
	}
	
	public static void setSavedString(String str) {
		savedString = str;
	}
	
}
