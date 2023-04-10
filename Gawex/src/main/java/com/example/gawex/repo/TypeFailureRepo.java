package com.example.gawex.repo;

import com.example.gawex.entity.TypeFailure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeFailureRepo extends JpaRepository<TypeFailure, Long> {
}
