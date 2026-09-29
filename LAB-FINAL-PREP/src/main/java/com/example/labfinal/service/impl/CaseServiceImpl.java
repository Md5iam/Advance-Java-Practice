package com.example.labfinal.service.impl;

import com.example.labfinal.document.CaseRecord;
import com.example.labfinal.dto.CaseRequestDTO;
import com.example.labfinal.dto.CaseResponseDTO;
import com.example.labfinal.exception.DuplicateResourceException;
import com.example.labfinal.exception.ResourceNotFoundException;
import com.example.labfinal.model.CaseStatus;
import com.example.labfinal.repository.mongo.CaseRepository;
import com.example.labfinal.service.CaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CaseServiceImpl implements CaseService {

    private final CaseRepository caseRepository;

    @Override
    public CaseResponseDTO createCase(CaseRequestDTO requestDTO) {
        if (caseRepository.existsByCaseId(requestDTO.getCaseId())) {
            throw new DuplicateResourceException("Case ID already exists: " + requestDTO.getCaseId());
        }

        CaseRecord record = CaseRecord.builder()
                .caseId(requestDTO.getCaseId())
                .title(requestDTO.getTitle())
                .leadDetective(requestDTO.getLeadDetective())
                .priority(requestDTO.getPriority())
                .status(requestDTO.getStatus())
                .build();

        CaseRecord saved = caseRepository.save(record);
        return mapToResponseDTO(saved);
    }

    @Override
    public List<CaseResponseDTO> getAllCases() {
        return caseRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CaseResponseDTO getCaseById(String id) {
        CaseRecord record = caseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Case not found with ID: " + id));
        return mapToResponseDTO(record);
    }

    @Override
    public CaseResponseDTO updateCaseStatus(String id, CaseStatus status) {
        CaseRecord record = caseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Case not found with ID: " + id));

        record.setStatus(status);
        CaseRecord updated = caseRepository.save(record);
        return mapToResponseDTO(updated);
    }

    @Override
    public void deleteCase(String id) {
        if (!caseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Case not found with ID: " + id);
        }
        caseRepository.deleteById(id);
    }

    @Override
    public List<CaseResponseDTO> searchCases(String query) {
        if (query == null || query.isBlank()) {
            return getAllCases();
        }
        return caseRepository.searchCases(query.trim()).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
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
                .version(record.getVersion())
                .build();
    }
}
