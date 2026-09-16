package com.laplateforme.guildboard.application.controller;

import java.util.List;
import com.laplateforme.guildboard.application.dto.AssignmentAnswerDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/adventurers") //Define https path
public class AdventurerController {

    private final AdventurerService adventurerService;

    public AdventurerController(AdventurerService adventurerService) {
        this.adventurerService = adventurerService;
    }

    // Get all adventurers
    @GetMapping //Get method
    public ResponseEntity<List<AdventurerAnswerDTO>> getAllAdventurers() {
        return ResponseEntity.ok(adventurerService.getAllAdventurers());
    }

    // Get an adventurer by ID
    @GetMapping("/{id}")
    public ResponseEntity<AdventurerAnswerDTO> getAdventurerById(@PathVariable Long id) {
        return ResponseEntity.ok(adventurerService.getAdventurerById(id));
    }

    // Create a new adventurer
    @PostMapping
    public ResponseEntity<AdventurerAnswerDTO> createAdventurer(@Valid @RequestBody AdventurerRequestDTO request) {
        AdventurerAnswerDTO created=adventurerService.createAdventurer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Update an adventurer
    @PutMapping("/{id}")
    public ResponseEntity<AdventurerAnswerDTO> updateAdventurer(@PathVariable Long id,@Valid @RequestBody AdventurerRequestDTO request) {
        AdventurerAnswerDTO updated= adventurerService.updateAdventurer(id, request);
        return ResponseEntity.ok(updated);
    }

    // Delete an adventurer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdventurer(@PathVariable Long id) {
        adventurerService.deleteAdventurer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<AssignmentAnswerDTO>> getAdventurerHistory(@PathVariable Long id){
        return ResponseEntity.ok(adventurerService.getAdventurerHistory(id));
    }
}