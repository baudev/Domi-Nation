package helpers;

import mockit.Mock;
import mockit.MockUp;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ConfigTest {

    @Test
    void getInexistantValueShouldReturnNull() {
        assertNull(Config.getValue("inexistant_key_config"));
    }

    @Test
    void getExistantValueShouldReturnString() {
        assertNotNull(Config.getValue("projectName"));
    }

    @Test
    void loadInexistantFileConfiguration() {
        new MockUp<Config>() {
            @Mock
            private void loadConfigurationFile() throws IOException {
                throw new IOException("File configuration not found");
            }
        };

        Config.setProperties(null);

        assertThrows(Exception.class,
                ()->{
                    Config.getValue("test");
                }, "Should throw an exception as the configuration file is not found");
    }
}