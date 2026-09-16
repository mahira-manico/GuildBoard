package com.laplateforme.guildboard.application.repository;
import com.laplateforme.guildboard.application.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//Interface Assignment inheriting of JpaRepository
public interface
AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByAdventurer_Id(Long adventurer_id); //Custom method to find an adventurer by his ID
    Optional<Assignment> findByQuestIdAndCompletedAtIsNull(Long quest_id); //Custom method to find by quest and check completedAt state

}

