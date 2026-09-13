package com.example.siam.service.impl;

import com.example.siam.model.Case;
import com.example.siam.model.Matrices;
import com.example.siam.repository.CaseRepository;
import com.example.siam.service.CaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CaseServiceImpl implements CaseService {

    private final CaseRepository caseRepository;

    public Case addCase(Case caseItem){
        caseRepository.insert(caseItem);
        return caseItem;
    }

    @Override
    public void createOperation(Case caseItem) {

    }

    @Override
    public List<Case> getAll() {
        return List.of();
    }

    @Override
    public Case getById(String id) {
        return null;
    }

    @Override
    public void update(String id, Case updateCase) {
        Case caseItem = caseRepository.getById(id);

    }

    @Override
    public void delete(String id) {
        caseRepository.deleteById(id);
    }

    @Override
    public Case search(String id) {
        return null;
    }

//    @Override
//    public Matrices getCnt() {
//        Matrices tmp = new Matrices();
//        int n = caseRepository.ge
//        for ( int i = 0 ; i < )
//    }

//    public Optional<Case> getCaseByid(String id){
//        return caseRepository.findById(id).orElse();
//    }
}
