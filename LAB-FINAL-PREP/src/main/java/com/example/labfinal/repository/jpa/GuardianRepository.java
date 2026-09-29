package com.example.labfinal.repository.jpa;

import com.example.labfinal.entity.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuardianRepository extends JpaRepository<Guardian, Long> {
}
