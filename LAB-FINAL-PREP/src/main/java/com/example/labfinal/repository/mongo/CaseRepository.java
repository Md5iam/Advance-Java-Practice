package com.example.labfinal.repository.mongo;

import com.example.labfinal.document.CaseRecord;
import com.example.labfinal.model.CaseStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CaseRepository extends MongoRepository<CaseRecord, String> {

    Optional<CaseRecord> findByCaseId(String caseId);

    boolean existsByCaseId(String caseId);

    long countByStatus(CaseStatus status);

    @Query("{ 'status' : { $ne: ?0 } }")
    List<CaseRecord> findByStatusNot(CaseStatus status);

    @Query("{ $or: [ { 'title' : { $regex: ?0, $options: 'i' } }, { 'leadDetective' : { $regex: ?0, $options: 'i' } } ] }")
    List<CaseRecord> searchCases(String query);
}
