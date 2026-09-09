package com.laplateforme.guildboard.application.repository;
import com.laplateforme.guildboard.application.entity.Quest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestRepository extends JpaRepository<Quest, Long> {
}
