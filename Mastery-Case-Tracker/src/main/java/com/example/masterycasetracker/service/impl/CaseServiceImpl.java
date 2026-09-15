package com.example.masterycasetracker.service.impl;

import com.example.masterycasetracker.dto.CaseRequestDTO;
import com.example.masterycasetracker.dto.CaseResponseDTO;
import com.example.masterycasetracker.dto.DashboardStatsDTO;
import com.example.masterycasetracker.exception.DuplicateCaseIdException;
import com.example.masterycasetracker.exception.ResourceNotFoundException;
import com.example.masterycasetracker.model.CaseRecord;
import com.example.masterycasetracker.model.CaseStatus;
import com.example.masterycasetracker.model.Priority;
import com.example.masterycasetracker.repository.CaseRepository;
import com.example.masterycasetracker.service.CaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CaseServiceImpl implements CaseService {

    private final CaseRepository caseRepository;

    @Override
    public CaseResponseDTO createCase(CaseRequestDTO requestDTO) {
        if (caseRepository.existsByCaseId(requestDTO.getCaseId())) {
            throw new DuplicateCaseIdException("A case record already exists with Case ID: " + requestDTO.getCaseId());
        }

        CaseRecord record = mapToEntity(requestDTO);
        record.setCreatedAt(LocalDateTime.now());
        record.setUpdatedAt(LocalDateTime.now());

        CaseRecord savedRecord = caseRepository.save(record);
        return mapToResponseDTO(savedRecord);
    }

    @Override
    public List<CaseResponseDTO> getAllCases() {
        return caseRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    public CaseResponseDTO getCaseById(String id) {
        CaseRecord record = findCaseByIdOrCaseId(id);
        return mapToResponseDTO(record);
    }

    @Override
    public CaseResponseDTO updateCase(String id, CaseRequestDTO requestDTO) {
        CaseRecord existing = findCaseByIdOrCaseId(id);

        if (!existing.getCaseId().equalsIgnoreCase(requestDTO.getCaseId())
                && caseRepository.existsByCaseId(requestDTO.getCaseId())) {
            throw new DuplicateCaseIdException("Cannot update: Another case record already exists with Case ID: " + requestDTO.getCaseId());
        }

        existing.setCaseId(requestDTO.getCaseId());
        existing.setTitle(requestDTO.getTitle());
        existing.setLeadDetective(requestDTO.getLeadDetective());
        existing.setPriority(requestDTO.getPriority());
        existing.setStatus(requestDTO.getStatus());
        existing.setUpdatedAt(LocalDateTime.now());

        CaseRecord updatedRecord = caseRepository.save(existing);
        return mapToResponseDTO(updatedRecord);
    }

    @Override
    public void deleteCase(String id) {
        CaseRecord existing = findCaseByIdOrCaseId(id);
        caseRepository.delete(existing);
    }

    @Override
    public List<CaseResponseDTO> searchCases(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllCases();
        }

        String sanitizedQuery = query.trim();
        return caseRepository.findByCaseIdContainingIgnoreCaseOrTitleContainingIgnoreCaseOrLeadDetectiveContainingIgnoreCase(
                        sanitizedQuery, sanitizedQuery, sanitizedQuery
                )
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    public DashboardStatsDTO getDashboardStats() {
        long totalCrimeFiles = caseRepository.count();
        long openActiveCases = caseRepository.countByStatusIn(List.of(CaseStatus.OPEN, CaseStatus.IN_PROGRESS));
        long highPriorityCases = caseRepository.countByPriority(Priority.HIGH);

        return DashboardStatsDTO.builder()
                .totalCrimeFiles(totalCrimeFiles)
                .openActiveCases(openActiveCases)
                .highPriorityCases(highPriorityCases)
                .build();
    }

    private CaseRecord findCaseByIdOrCaseId(String identifier) {
        return caseRepository.findById(identifier)
                .or(() -> caseRepository.findByCaseId(identifier))
                .orElseThrow(() -> new ResourceNotFoundException("Crime case file not found with identifier: " + identifier));
    }

    private CaseRecord mapToEntity(CaseRequestDTO dto) {
        return CaseRecord.builder()
                .caseId(dto.getCaseId())
                .title(dto.getTitle())
                .leadDetective(dto.getLeadDetective())
                .priority(dto.getPriority())
                .status(dto.getStatus())
                .build();
    }

    private CaseResponseDTO mapToResponseDTO(CaseRecord record) {
        return CaseResponseDTO.builder()
                .id(record.getId())
                .caseId(record.getCaseId())
                .title(record.getTitle())
                .leadDetective(record.getLeadDetective())
                .priority(record.getPriority())
                .status(record.getStatus())
                .createdAt(record.getCreatedAt())
                .updatedAt(record.getUpdatedAt())
                .build();
    }
}
