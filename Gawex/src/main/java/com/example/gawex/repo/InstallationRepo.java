package com.example.gawex.repo;

import com.example.gawex.entity.Installation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstallationRepo extends JpaRepository<Installation, Long> {
}
