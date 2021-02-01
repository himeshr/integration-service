package org.bahmni_avni_integration.repository.openmrs;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.bahmni_avni_integration.contract.bahmni.OpenMRSEncounter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenMRSEncounterRepositoryTest {
    @Autowired
    OpenMRSEncounterRepository openMRSEncounterRepository;

    @Test
    public void getEncounterByObservation() {
        OpenMRSEncounter encounter = openMRSEncounterRepository.getEncounter("TRI01099902", "Departments", "Departments, General OPD");
        assertNull(encounter);
    }

    @Test
    public void getEncounterByUuid() {
        OpenMRSEncounter encounter = openMRSEncounterRepository.getEncounter("481917a7-5f19-4598-9cb3-503d3f0e2cce");
        assertNotNull(encounter);
        assertNotNull(encounter.get("uuid"));
    }
}