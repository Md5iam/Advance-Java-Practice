package com.example.masterycasetracker.service;

import com.example.masterycasetracker.dto.CaseRequestDTO;
import com.example.masterycasetracker.dto.CaseResponseDTO;
import com.example.masterycasetracker.dto.DashboardStatsDTO;

import java.util.List;

public interface CaseService {

    CaseResponseDTO createCase(CaseRequestDTO requestDTO);

    List<CaseResponseDTO> getAllCases();

    CaseResponseDTO getCaseById(String id);

    CaseResponseDTO updateCase(String id, CaseRequestDTO requestDTO);

    void deleteCase(String id);

    List<CaseResponseDTO> searchCases(String query);

    DashboardStatsDTO getDashboardStats();
}
