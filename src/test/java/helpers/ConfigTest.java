package helpers;

import org.junit.jupiter.api.Test;

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
}