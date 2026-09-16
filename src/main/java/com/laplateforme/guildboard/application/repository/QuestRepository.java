package com.laplateforme.guildboard.application.repository;
import com.laplateforme.guildboard.application.entity.Difficulty;
import com.laplateforme.guildboard.application.entity.Quest;
import com.laplateforme.guildboard.application.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//Interface Quest inheriting of JpaRepository
public interface QuestRepository extends JpaRepository<Quest, Long> {
    List<Quest> findByStatus(Status status); //Custom method to find a list of quest by status
    List<Quest> findByDifficulty(Difficulty difficulty); //Custom method to find a list of quest by his difficulty
    List<Quest> findByStatusAndDifficulty(Status status, Difficulty difficulty);
    boolean existsByTitle(String title);
}
