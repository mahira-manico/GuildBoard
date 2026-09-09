package com.laplateforme.guildboard.application.repository;
import com.laplateforme.guildboard.application.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
}
