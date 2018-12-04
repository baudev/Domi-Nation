package helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    /**
     * Load the properties from the configuration file
     * @throws IOException
     */
    private static void loadConfigurationFile() throws IOException {
        properties = new Properties();
        InputStream inputStream = new FileInputStream("src/main/resources/config.properties");
        properties.load(inputStream);
    }

}