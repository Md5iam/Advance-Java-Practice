package com.example.labfinal.controller;

import com.example.labfinal.dto.CaseRequestDTO;
import com.example.labfinal.dto.CaseResponseDTO;
import com.example.labfinal.model.CaseStatus;
import com.example.labfinal.service.CaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cases")
@RequiredArgsConstructor
public class CaseRestController {

    private final CaseService caseService;

    @PostMapping
    public ResponseEntity<CaseResponseDTO> createCase(@Valid @RequestBody CaseRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(caseService.createCase(dto));
    }

    @GetMapping
    public ResponseEntity<List<CaseResponseDTO>> getAllCases() {
        return ResponseEntity.ok(caseService.getAllCases());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CaseResponseDTO> getCaseById(@PathVariable String id) {
        return ResponseEntity.ok(caseService.getCaseById(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<CaseResponseDTO> updateStatus(
            @PathVariable String id,
            @RequestParam("status") CaseStatus status
    ) {
        return ResponseEntity.ok(caseService.updateCaseStatus(id, status));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCase(@PathVariable String id) {
        caseService.deleteCase(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<CaseResponseDTO>> searchCases(@RequestParam(name = "query", required = false) String query) {
        return ResponseEntity.ok(caseService.searchCases(query));
    }
}
