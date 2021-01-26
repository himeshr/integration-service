package org.bahmni_avni_integration.repository;

import org.bahmni_avni_integration.domain.MappingMetaData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MappingMetaDataRepositoryTest {
    @Autowired
    private MappingMetaDataRepository mappingMetaDataRepository;

    @Test
    public void loadAll() {
        Iterable<MappingMetaData> all = mappingMetaDataRepository.findAll();
        List<MappingMetaData> list = new ArrayList<>();
        all.forEach(list::add);
        assertEquals(true, list.size() > 0);
    }
}