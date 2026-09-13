package com.example.siam.controller;

import com.example.siam.model.Case;
import com.example.siam.model.Matrices;
import com.example.siam.service.CaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/start")
@RequiredArgsConstructor
public class CaseController {

    private final CaseService caseService;

    @PostMapping("/add")
    public Case addCaseItem(@Valid @RequestBody Case caseItem){
        return caseService.addCase(caseItem);
    }

    @GetMapping("/get")
    public List<Case> getCaseItem(){
        return caseService.getAll();
    }

    @GetMapping("/get/{id}")
    public Case getCaseItemById(@PathVariable String id){
        return caseService.getById(id);
    }

    @PutMapping("/update/{id}")
    public void updateCase(@PathVariable String id, @RequestBody Case caseUpdate){
         caseService.update(id, caseUpdate);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCase(@PathVariable String id){
         caseService.delete(id);
    }

//    @GetMapping("/count")
//    public Matrices getMatrices(){
//
//    }
}
