package com.example.labfinal.service.impl;

import com.example.labfinal.document.PortalBugReport;
import com.example.labfinal.repository.PortalBugReportRepo;
import com.example.labfinal.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final PortalBugReportRepo portalBugReportRepo;


    @Override
    @Cacheable(value = "report")
    public List<PortalBugReport> findAll() {
        return portalBugReportRepo.findAll();
    }

    @Override
    @CacheEvict(value = "report")
    public void deleteById(String id) {
        portalBugReportRepo.deleteById(id);
    }

}
