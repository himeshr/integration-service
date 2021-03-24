package org.bahmni_avni_integration.migrator;

import org.bahmni_avni_integration.migrator.config.BahmniConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ConfigTest {
    @Autowired
    private BahmniConfig config;

    @Test
    public void readCustomProperties() {
        assertNotNull(config.getOpenMrsMySqlUser());
    }
}