package org.bahmni_avni_integration.migrator.domain;

import org.bahmni_avni_integration.integration_data.domain.MappingType;

import java.util.List;
import java.util.Map;

public class StandardMappings {
    private List<Map<String, String>> list;

    public <T> StandardMappings(List<Map<String, String>> list) {
        this.list = list;
    }

    public Map<String, String> getLabMappingType() {
        return getForMappingType(MappingType.LabEncounterType);
    }

    public Map<String, String> getForMappingType(MappingType labEncounterType) {
        return list.stream().filter(stringStringMap -> stringStringMap.get("MappingType").equals(labEncounterType.name())).findFirst().orElse(null);
    }

    public Map<String, String> getDrugOrderMappingType() {
        return getForMappingType(MappingType.DrugOrderEncounterType);
    }

    public List<Map<String, String>> getList() {
        return list;
    }

    public Map<String, String> getDrugOrderConcept() {
        return getForMappingType(MappingType.DrugOrderConcept);
    }
}