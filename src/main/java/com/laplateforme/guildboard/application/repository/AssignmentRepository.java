package com.laplateforme.guildboard.application.repository;
import com.laplateforme.guildboard.application.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface
AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByAdventurer_Id(Long adventurer_id);
    Optional<Assignment> findByQuestIdAndCompletedAtIsNull(Long quest_id);

}

