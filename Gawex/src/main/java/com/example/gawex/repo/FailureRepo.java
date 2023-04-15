package com.example.gawex.repo;

import com.example.gawex.entity.Failure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FailureRepo extends JpaRepository<Failure, Long> {
}
