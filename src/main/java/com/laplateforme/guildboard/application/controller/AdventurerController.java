package com.laplateforme.guildboard.application.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laplateforme.guildboard.application.dto.AdventurerAnswerDTO;
import com.laplateforme.guildboard.application.dto.AdventurerRequestDTO;

import com.laplateforme.guildboard.application.service.AdventurerService;

@RestController
@RequestMapping("/adventurers")
public class AdventurerController {

    private final AdventurerService adventurerService;

    public AdventurerController(AdventurerService adventurerService) {
        this.adventurerService = adventurerService;
    }

    // Get all adventurers
    @GetMapping
    public List<AdventurerAnswerDTO> getAllAdventurers() {
        return adventurerService.getAllAdventurers();
    }

    // Get an adventurer by ID
    @GetMapping("/{id}")
    public Optional<AdventurerAnswerDTO> getAdventurerById(@PathVariable Long id) {
        return adventurerService.getAdventurerById(id);
    }

    // Create a new adventurer
    @PostMapping
    public AdventurerAnswerDTO createAdventurer(
            @RequestBody AdventurerRequestDTO request) {

        return adventurerService.createAdventurer(request);
    }

    // Update an adventurer
    @PutMapping("/{id}")
    public AdventurerAnswerDTO updateAdventurer(
            @PathVariable Long id,
            @RequestBody AdventurerRequestDTO request) {

        return adventurerService.updateAdventurer(id, request);
    }

    // Delete an adventurer
    @DeleteMapping("/{id}")
    public void deleteAdventurer(@PathVariable Long id) {
        adventurerService.deleteAdventurer(id);
    }
}