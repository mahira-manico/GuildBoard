package com.laplateforme.guildboard.application.service;
import java.util.ArrayList;
import java.util.List;
import com.laplateforme.guildboard.application.dto.AssignmentAnswerDTO;
import com.laplateforme.guildboard.application.exception.RessourceNotFoundErrors;
import com.laplateforme.guildboard.application.repository.AssignmentRepository;
import org.springframework.stereotype.Service;
import com.laplateforme.guildboard.application.dto.AdventurerAnswerDTO;
import com.laplateforme.guildboard.application.dto.AdventurerRequestDTO;
import com.laplateforme.guildboard.application.entity.Adventurer;
import com.laplateforme.guildboard.application.repository.AdventurerRepository;


@Service
public class AdventurerService {

    private final AdventurerRepository adventurerRepository;
    private final AssignmentRepository assignmentRepository;

    public AdventurerService(AdventurerRepository adventurerRepository, AssignmentRepository assignmentRepository) {
        this.adventurerRepository = adventurerRepository;
        this.assignmentRepository = assignmentRepository;
    }

    // Get all adventurers
    public List<AdventurerAnswerDTO> getAllAdventurers() {

        List<Adventurer> adventurers = adventurerRepository.findAll();

        List<AdventurerAnswerDTO> answers = new ArrayList<>();

        for (Adventurer adventurer : adventurers) {
            answers.add(new AdventurerAnswerDTO(
                    adventurer.getId(),
                    adventurer.getName(),
                    adventurer.getCharacterClass(),
                    adventurer.getLevel(),
                    adventurer.getXp(),
                    adventurer.getGold())
            );
        }

        return answers;
    }

    // Get an adventurer by ID
    public AdventurerAnswerDTO getAdventurerById(Long id) {

        Adventurer adventurer = adventurerRepository.findById(id)
                        .orElseThrow(()->new RessourceNotFoundErrors("ADVENTURER_NOT_FOUND","Aventurier non trouvable avec l'id : "+id));
        return new AdventurerAnswerDTO(
                adventurer.getId(),
                adventurer.getName(),
                adventurer.getCharacterClass(),
                adventurer.getLevel(),
                adventurer.getXp(),
                adventurer.getGold());
    }

    // Create a new adventurer
    public AdventurerAnswerDTO createAdventurer(AdventurerRequestDTO request) {

        Adventurer adventurer = new Adventurer(
                request.name(),
                request.characterClass()
        );

        Adventurer savedAdventurer = adventurerRepository.save(adventurer);

        return new AdventurerAnswerDTO(
                savedAdventurer.getId(),
                savedAdventurer.getName(),
                savedAdventurer.getCharacterClass(),
                savedAdventurer.getLevel(),
                savedAdventurer.getXp(),
                savedAdventurer.getGold());
    }

    // Update an adventurer
    public AdventurerAnswerDTO updateAdventurer(Long id, AdventurerRequestDTO request) {

        Adventurer adventurer = adventurerRepository.findById(id).orElseThrow(()->new RessourceNotFoundErrors("ADVENTURER_NOT_FOUND","Aventurier non trouvé à l'id"+id));

            adventurer.setName(request.name());
            adventurer.setCharacterClass(request.characterClass());

            Adventurer savedAdventurer = adventurerRepository.save(adventurer);

            return new AdventurerAnswerDTO(
                    savedAdventurer.getId(),
                    savedAdventurer.getName(),
                    savedAdventurer.getCharacterClass(),
                    savedAdventurer.getLevel(),
                    savedAdventurer.getXp(),
                    savedAdventurer.getGold());

    }

    // Delete an adventurer
    public void deleteAdventurer(Long id) {
        adventurerRepository.findById(id).orElseThrow(()->new RessourceNotFoundErrors("ADVENTURER_NOT_FOUND","Aventurier non trouvé à l'id : "+id));
        adventurerRepository.deleteById(id);
    }

    //See an adventurer history
    public List<AssignmentAnswerDTO> getAdventurerHistory(Long id) {
        return assignmentRepository.findByAdventurer_Id(id).stream()
                .map(assignment -> new AssignmentAnswerDTO(
                        assignment.getId(),
                        assignment.getAdventurer().getId(),
                        assignment.getQuest().getId(),
                        assignment.getAssignedAt(),
                        assignment.getCompletedAt())).toList();
    }
}