package com.example.masterycasetracker.controller;

import com.example.masterycasetracker.dto.CaseRequestDTO;
import com.example.masterycasetracker.dto.CaseResponseDTO;
import com.example.masterycasetracker.dto.DashboardStatsDTO;
import com.example.masterycasetracker.service.CaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cases")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CaseController {

    private final CaseService caseService;

    @PostMapping
    public ResponseEntity<CaseResponseDTO> createCase(@Valid @RequestBody CaseRequestDTO requestDTO) {
        CaseResponseDTO createdCase = caseService.createCase(requestDTO);
        return new ResponseEntity<>(createdCase, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CaseResponseDTO>> getAllCases() {
        List<CaseResponseDTO> cases = caseService.getAllCases();
        return ResponseEntity.ok(cases);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CaseResponseDTO> getCaseById(@PathVariable("id") String id) {
        CaseResponseDTO caseResponse = caseService.getCaseById(id);
        return ResponseEntity.ok(caseResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CaseResponseDTO> updateCase(
            @PathVariable("id") String id,
            @Valid @RequestBody CaseRequestDTO requestDTO
    ) {
        CaseResponseDTO updatedCase = caseService.updateCase(id, requestDTO);
        return ResponseEntity.ok(updatedCase);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCase(@PathVariable("id") String id) {
        caseService.deleteCase(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<CaseResponseDTO>> searchCases(
            @RequestParam(name = "query", required = false) String query
    ) {
        List<CaseResponseDTO> results = caseService.searchCases(query);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats() {
        DashboardStatsDTO stats = caseService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }
}
