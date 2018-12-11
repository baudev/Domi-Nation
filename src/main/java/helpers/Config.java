package helpers;

import java.io.*;
import java.util.Properties;

public class Config {

    private static Properties properties;

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

    public static void storeValue(String name, String value){
        if(properties == null){ // if properties is null, the it means that the configuration has not been read yet
            try {
                Config.loadConfigurationFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        properties.setProperty(name, value);
        try {
            Config.saveConfigurationFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load the properties from the configuration file
     * @throws IOException
     */
    private static void loadConfigurationFile() throws IOException {
        properties = new Properties();
        InputStream inputStream = new FileInputStream("src/main/resources/config.properties");
        properties.load(inputStream);
    }

    private static void saveConfigurationFile() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/config.properties");
        properties.store(fileOutputStream, "Configuration File");
    }

    /**
     *
     * GETTERS AND SETTERS
     *
     */

    public static Properties getProperties() {
        return properties;
    }

    public static void setProperties(Properties properties) {
        Config.properties = properties;
    }
}
