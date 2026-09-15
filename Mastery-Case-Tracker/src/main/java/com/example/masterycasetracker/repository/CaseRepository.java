package com.example.masterycasetracker.repository;

import com.example.masterycasetracker.model.CaseRecord;
import com.example.masterycasetracker.model.CaseStatus;
import com.example.masterycasetracker.model.Priority;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CaseRepository extends MongoRepository<CaseRecord, String> {

    Optional<CaseRecord> findByCaseId(String caseId);

    boolean existsByCaseId(String caseId);

    void deleteByCaseId(String caseId);

    List<CaseRecord> findByCaseIdContainingIgnoreCaseOrTitleContainingIgnoreCaseOrLeadDetectiveContainingIgnoreCase(
            String caseId,
            String title,
            String leadDetective
    );

    List<CaseRecord> findByPriority(Priority priority);

    List<CaseRecord> findByStatus(CaseStatus status);

    long countByStatusIn(List<CaseStatus> statuses);

    long countByPriority(Priority priority);

    long countByStatus(CaseStatus status);
}
