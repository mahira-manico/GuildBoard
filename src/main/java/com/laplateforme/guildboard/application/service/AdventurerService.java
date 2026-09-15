package com.laplateforme.guildboard.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.laplateforme.guildboard.application.dto.AdventurerAnswerDTO;
import com.laplateforme.guildboard.application.dto.AdventurerRequestDTO;
import com.laplateforme.guildboard.application.entity.Adventurer;
import com.laplateforme.guildboard.application.repository.AdventurerRepository;


@Service
public class AdventurerService {

    private final AdventurerRepository adventurerRepository;

    public AdventurerService(AdventurerRepository adventurerRepository) {
        this.adventurerRepository = adventurerRepository;
    }

    // Get all adventurers
    public List<AdventurerAnswerDTO> getAllAdventurers() {

        List<Adventurer> adventurers = adventurerRepository.findAll();

        List<AdventurerAnswerDTO> answers = new ArrayList<>();

        for (Adventurer adventurer : adventurers) {
            answers.add(toAnswerDTO(adventurer));
        }

        return answers;
    }

    // Get an adventurer by ID
    public Optional<AdventurerAnswerDTO> getAdventurerById(Long id) {

        Optional<Adventurer> optionalAdventurer =
                adventurerRepository.findById(id);

        if (optionalAdventurer.isPresent()) {

            Adventurer adventurer = optionalAdventurer.get();

            AdventurerAnswerDTO dto = toAnswerDTO(adventurer);

            return Optional.of(dto);
        }

        return Optional.empty();
    }

    // Create a new adventurer
    public AdventurerAnswerDTO createAdventurer(AdventurerRequestDTO request) {

        Adventurer adventurer = new Adventurer(
                request.getName(),
                request.getCharacterClass()
        );

        Adventurer savedAdventurer =
                adventurerRepository.save(adventurer);

        return toAnswerDTO(savedAdventurer);
    }

    // Update an adventurer
    public AdventurerAnswerDTO updateAdventurer(
            Long id,
            AdventurerRequestDTO request) {

        Optional<Adventurer> optionalAdventurer =
                adventurerRepository.findById(id);

        if (optionalAdventurer.isPresent()) {

            Adventurer adventurer = optionalAdventurer.get();

            adventurer.setName(request.getName());
            adventurer.setCharacterClass(request.getCharacterClass());

            Adventurer savedAdventurer =
                    adventurerRepository.save(adventurer);

            return toAnswerDTO(savedAdventurer);
        }

        return null;
    }

    // Delete an adventurer
    public void deleteAdventurer(Long id) {
        adventurerRepository.deleteById(id);
    }

    // Convert an adventurer to an answer DTO
    private AdventurerAnswerDTO toAnswerDTO(Adventurer adventurer) {

        AdventurerAnswerDTO dto = new AdventurerAnswerDTO();

        dto.setId(adventurer.getId());
        dto.setName(adventurer.getName());
        dto.setCharacterClass(adventurer.getCharacterClass());
        dto.setLevel(adventurer.getLevel());
        dto.setXp(adventurer.getXp());
        dto.setGold(adventurer.getGold());

        return dto;
    }
}