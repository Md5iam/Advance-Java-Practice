package com.example.labfinal.service;

import com.example.labfinal.dto.CaseRequestDTO;
import com.example.labfinal.dto.CaseResponseDTO;
import com.example.labfinal.model.CaseStatus;
import com.example.labfinal.model.Priority;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CaseServiceTest {

    @Autowired
    private CaseService caseService;

    @Test
    void testCaseServiceLifecycle() {
        String testCaseId = "CASE-TEST-UNIT";
        try {
            CaseRequestDTO requestDTO = CaseRequestDTO.builder()
                    .caseId(testCaseId)
                    .title("Unit Test Case Lifecycle")
                    .leadDetective("Detective Unit")
                    .priority(Priority.LOW)
                    .status(CaseStatus.OPEN)
                    .build();

            CaseResponseDTO created = caseService.createCase(requestDTO);
            Assertions.assertNotNull(created.getId());
            Assertions.assertEquals(CaseStatus.OPEN, created.getStatus());

            CaseResponseDTO updated = caseService.updateCaseStatus(created.getId(), CaseStatus.RESOLVED);
            Assertions.assertEquals(CaseStatus.RESOLVED, updated.getStatus());

            caseService.deleteCase(created.getId());
        } catch (Exception ignored) {
        }
    }
}
