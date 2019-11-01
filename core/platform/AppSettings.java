import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Using Properties 
 * 
 * This example demonstrates how to use Properties to read default as well as 
 * user-defined application configuration/settings from file for instance 
 * the language in which the messages are shown. 
 * 
 * @author MaiL
 *
 */
public class AppSettings {

	private static final String TRANSLATION_DIR = "..\\resources\\i18n";
	private static final String TRANSLATION_BASE = "translation";
	private static final String APP_PROPERTIES_FILE = "..\\resources\\conf\\app.properties";

	/**
	 * Built-in settings with default values
	 * @author MaiL
	 *
	 */
	private enum Settings {
		LANGUAGE("language", "en"),
		COUNTRY ("country", "US");
		
		private String property;
		private String value;

		/**
		 * Constructor
		 * @param key
		 * @param value
		 */
		private Settings(String key, String value) {
			this.property = key;
			this.value = value;
		}

		/**
		 * Getter for property Key
		 * @return
		 */
		public String getKey() {
			return property;
		}

		/**
		 * Getter for property Value
		 * @return
		 */
		public String getValue() {
			return value;
		}
	}
	
	/**
	 * Set of properties
	 */
	private Properties properties;
	
	/**
	 * loads predefined properties with default values
	 */
	private void loadDefaultSettings() {
		if (properties == null) {
			properties = new Properties();
		}
		for (Settings s : Settings.values()) {
			properties.setProperty(s.getKey(), s.getValue());
		}
	}
	
	/**
	 * overrides default values with user settings from file
	 */
	private void loadUserSettings() {
		FileInputStream in;
		
		try {
			in = new FileInputStream(APP_PROPERTIES_FILE);
			properties.load(in);
		} catch (FileNotFoundException e) {
			System.out.println("No user configuration file found at " + APP_PROPERTIES_FILE);
		} catch (IOException e) {
			System.out.println("Failed to read user configuration file found at " + APP_PROPERTIES_FILE + 
							   " with error: " + e.getMessage());
		}
	}
	
	/**
	 * initializes settings
	 */
	public AppSettings() {
		loadDefaultSettings();
		loadUserSettings();
	}
	
	/**
	 * Main
	 * @param args
	 */
	public static void main(String[] args) {
		AppSettings app = new AppSettings();
		app.showProperties();
		
		try {
			app.testTranslation();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * shows messages in language set in configuration
	 * @throws MalformedURLException
	 */
	private void testTranslation() throws MalformedURLException {
        Locale locale;
        ResourceBundle messages;

        locale = new Locale(properties.getProperty(Settings.LANGUAGE.getKey()), 
        					properties.getProperty(Settings.COUNTRY.getKey()));
        
        File file = new File(TRANSLATION_DIR);
        URL[] urls = {file.toURI().toURL()};
        ClassLoader loader = new URLClassLoader(urls);
        messages = ResourceBundle.getBundle(TRANSLATION_BASE, locale, loader);
        
        System.out.println("\nTranslations in " + locale.toString());
        System.out.println(messages.getString("greetings"));
        System.out.println(messages.getString("inquiry"));
        System.out.println(messages.getString("farewell"));        
	}

	/**
	 * shows current properties (also properties not defined in default settings)
	 */
	private void showProperties() {
		System.out.println("\nCurrent properties:");
		for (Enumeration<Object> e = properties.keys(); e.hasMoreElements(); ) {
			String property = (String)e.nextElement();
			System.out.println(property + " : " + properties.getProperty(property));
		}
	}

}
