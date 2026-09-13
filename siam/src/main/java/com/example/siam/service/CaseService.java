package com.example.siam.service;

import com.example.siam.model.Case;
import com.example.siam.model.Matrices;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CaseService {
    public Case addCase(Case caseItem);

    public void createOperation (Case caseItem);
    public List<Case> getAll();
    public Case getById(String id );
    public void update(String id, Case updateCase);
    public void delete(String id);
    public Case search (String id);

    public Matrices getCnt();
}
