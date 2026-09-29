package com.example.labfinal;

import com.example.labfinal.dto.AiPromptRequest;
import com.example.labfinal.dto.AiPromptResponse;
import com.example.labfinal.dto.CaseRequestDTO;
import com.example.labfinal.dto.CaseResponseDTO;
import com.example.labfinal.dto.StudentGpaRecord;
import com.example.labfinal.model.AutonomousVehicle;
import com.example.labfinal.model.CaseStatus;
import com.example.labfinal.model.ElectricVehicle;
import com.example.labfinal.model.Priority;
import com.example.labfinal.model.Vehicle;
import com.example.labfinal.security.jwt.JwtUtils;
import com.example.labfinal.service.AiService;
import com.example.labfinal.service.CaseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@SpringBootTest
class LabFinalPrepApplicationTests {

    @Autowired
    private CaseService caseService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AiService aiService;

    @Test
    void testStudentRecordNormalization() {
        StudentGpaRecord record = new StudentGpaRecord("2021-1-60-001", "john doe", 4.5);
        Assertions.assertEquals("JOHN DOE", record.name());
        Assertions.assertEquals(4.0, record.gpa());
    }

    @Test
    void testStudentRecordValidationThrowsOnBlank() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new StudentGpaRecord("2021", "  ", 3.0));
    }

    @Test
    void testSealedClassPolymorphism() {
        Vehicle ev = new ElectricVehicle("EV-1", "Tesla Model 3", "DHK-1234", 75.0);
        Vehicle av = new AutonomousVehicle("AV-1", "Waymo One", "DHK-5678", "v12.4");

        Assertions.assertEquals("ELECTRIC", ev.getVehicleType());
        Assertions.assertEquals("AUTONOMOUS", av.getVehicleType());
    }

    @Test
    void testJwtTokenGenerationAndValidation() {
        UserDetails userDetails = new User("tester", "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        String token = jwtUtils.generateToken(userDetails);

        Assertions.assertNotNull(token);
        Assertions.assertFalse(token.isBlank());
        Assertions.assertEquals("tester", jwtUtils.extractUsername(token));
        Assertions.assertTrue(jwtUtils.isTokenValid(token, userDetails));
    }

    @Test
    void testSpringAiPromptExecution() {
        AiPromptRequest request = AiPromptRequest.builder()
                .prompt("What is Spring AI?")
                .build();

        AiPromptResponse response = aiService.processPrompt(request);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getResponse());
        Assertions.assertFalse(response.getResponse().isBlank());
        Assertions.assertTrue(response.getResponse().contains("Spring AI"));
    }

    @Test
    void testCaseServiceLifecycle() {
        String testCaseId = "CASE-999";
        try {
            CaseRequestDTO requestDTO = CaseRequestDTO.builder()
                    .caseId(testCaseId)
                    .title("Unit Test Case")
                    .leadDetective("Detective Test")
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
