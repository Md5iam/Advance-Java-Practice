package com.example.labfinal.repository;

import com.example.labfinal.document.PortalBugReport;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PortalBugReportRepo extends MongoRepository<PortalBugReport, String> {
}
