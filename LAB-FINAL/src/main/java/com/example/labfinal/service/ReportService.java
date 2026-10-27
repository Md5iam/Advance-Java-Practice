package com.example.labfinal.service;

import com.example.labfinal.document.PortalBugReport;

import java.util.List;

public interface ReportService {
    List<PortalBugReport> findAll();

    void deleteById(String id);


}
