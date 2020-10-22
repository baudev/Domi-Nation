package helpers;

import java.io.*;
import java.util.Properties;

/**
 * Handles all the methods linked to the configuration file.
 * The configuration file must be located to the resources directory and name <code>config.properties</code>.
 * This class provides only read methods.
 */
public class Config {

    private static Properties properties;

    /**
     * Returns a value stored in the configuration file.
     * @param name  Key of the desired value.
     * @return  Value associated to the key passed as parameter.
     *          Null if the key is not found in the configuration file.
     */
    public static String getValue(String name){
        if(properties == null){ // if properties is null, the it means that the configuration has not been read yet
            try {
                Config.loadConfigurationFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties.getProperty(name);
    }

    /**
     * Loads the properties from the configuration file.
     * @throws IOException  Cannot load the properties stored in the configuration file.
     */
    private static void loadConfigurationFile() throws IOException {
        properties = new Properties();
        InputStream inputStream = new FileInputStream("src/main/resources/config.properties");
        properties.load(inputStream);
    }

    /*
     *
     * GETTERS AND SETTERS
     *
     */

    /**
     * Gets the {@link Properties} attribute loaded from the configuration file.
     * @return  All properties stored in the configuration file.
     *          Null if the configuration file has not been read.
     */
    public static Properties getProperties() {
        return properties;
    }

    /**
     * Sets the {@link Properties} attribute value.
     * Take care, using this method doesn't store any attribute in the configuration file.
     * @param properties    Value of the {@link Properties} attribute to be set.
     */
    public static void setProperties(Properties properties) {
        Config.properties = properties;
    }
}
