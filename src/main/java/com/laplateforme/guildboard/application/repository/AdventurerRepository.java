package com.laplateforme.guildboard.application.repository;
import com.laplateforme.guildboard.application.entity.Adventurer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdventurerRepository extends JpaRepository<Adventurer, Long> {
}
