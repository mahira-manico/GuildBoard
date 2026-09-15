package com.laplateforme.guildboard.application.repository;
import com.laplateforme.guildboard.application.entity.Difficulty;
import com.laplateforme.guildboard.application.entity.Quest;
import com.laplateforme.guildboard.application.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestRepository extends JpaRepository<Quest, Long> {
    List<Quest> findByStatus(Status status);
    List<Quest> findByDifficulty(Difficulty difficulty);
}
