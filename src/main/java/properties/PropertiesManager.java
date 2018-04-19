package properties;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class PropertiesManager {
	
	private static PropertiesManager instance;
	private HashMap<String, Integer> propertiesMap;

	private PropertiesManager() {
		String propertiesFileLocation = System.getProperty("user.dir")
			+ "/src/main/resources/constants.cfg";
		propertiesFileLocation = propertiesFileLocation.replaceAll("/", File.separator);
		File propertiesFile = new File(propertiesFileLocation);
		propertiesMap = new HashMap();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(propertiesFile));
			String currentLine;
			while ((currentLine = reader.readLine()) != null) {
				String[] keyAndValue = currentLine.split(":");
				propertiesMap.put(keyAndValue[0], Integer.parseInt(keyAndValue[1]));
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("Properties File Not Found");
		}
		catch (IOException io) {
			System.out.println("Failed to Read a Property");
		}
		instance = this;
	}
	
	public static PropertiesManager getManager() {
		if (instance == null) {
			instance = new PropertiesManager();
		}
		return instance;
	}
	
	public int getProperty(String property) {
		return propertiesMap.get(property);
	}

}
