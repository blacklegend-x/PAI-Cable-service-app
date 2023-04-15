package com.example.gawex.repo;

import com.example.gawex.entity.TypeInstallation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeInstallationRepo extends JpaRepository<TypeInstallation, Long> {
}
