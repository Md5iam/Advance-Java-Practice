package com.example.labfinal.service;

import com.example.labfinal.dto.CaseRequestDTO;
import com.example.labfinal.dto.CaseResponseDTO;
import com.example.labfinal.model.CaseStatus;

import java.util.List;

public interface CaseService {
    CaseResponseDTO createCase(CaseRequestDTO requestDTO);
    List<CaseResponseDTO> getAllCases();
    CaseResponseDTO getCaseById(String id);
    CaseResponseDTO updateCaseStatus(String id, CaseStatus status);
    void deleteCase(String id);
    List<CaseResponseDTO> searchCases(String query);
}
