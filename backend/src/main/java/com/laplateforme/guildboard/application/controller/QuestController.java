package com.laplateforme.guildboard.application.controller;
import com.laplateforme.guildboard.application.dto.AssignmentAnswerDTO;
import com.laplateforme.guildboard.application.dto.AssignmentRequestDTO;
import com.laplateforme.guildboard.application.dto.QuestRequestDTO;
import com.laplateforme.guildboard.application.dto.QuestAnswerDTO;
import com.laplateforme.guildboard.application.entity.Difficulty;
import com.laplateforme.guildboard.application.entity.Status;
import com.laplateforme.guildboard.application.service.AssignmentService;
import com.laplateforme.guildboard.application.service.QuestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/quests")
public class QuestController {

    private final QuestService questService;
    private final AssignmentService assignmentService;

    public QuestController(QuestService questService, AssignmentService assignmentService) {
        this.questService = questService;
        this.assignmentService=assignmentService;
    }

    //See a quest
    @GetMapping("/{id}")
    public ResponseEntity<QuestAnswerDTO> seeQuest(@PathVariable Long id){
        QuestAnswerDTO quest=questService.seeQuest(id);
        return ResponseEntity.ok(quest);
    }

    //Create a quest
    @PostMapping
    public ResponseEntity<QuestAnswerDTO> createQuest(@Valid @RequestBody QuestRequestDTO request){
        QuestAnswerDTO quest=questService.createQuest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(quest);
    }

    //Update a quest
    @PutMapping("/{id}")
    public ResponseEntity<QuestAnswerDTO> updateQuest(@PathVariable Long id, @RequestBody QuestRequestDTO request){
        QuestAnswerDTO quest=questService.updateQuest(id, request);
        return ResponseEntity.ok(quest);
    }

    //Delete a quest
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuest(@PathVariable Long id){
        questService.deleteQuest(id);
        return ResponseEntity.noContent().build();
    }

    //Create assignment
    @PostMapping("/{id}/assignment")
    public ResponseEntity<AssignmentAnswerDTO> assignQuest(@PathVariable Long id, @ Valid @RequestBody AssignmentRequestDTO request){
        AssignmentAnswerDTO assignment=assignmentService.assignQuest(request.adventurer_id(), id);
        return ResponseEntity.status(HttpStatus.CREATED).body(assignment);

    }

    //See and filter all quests
    @GetMapping
    public ResponseEntity<List<QuestAnswerDTO>> seeAllQuests(@RequestParam(required = false)Status status, @RequestParam(required = false)Difficulty difficulty){
     return ResponseEntity.ok(questService.filterBy(status, difficulty));
    }

    //Complete a quest
    @PostMapping("{id}/completion")
    public  ResponseEntity<QuestAnswerDTO> completeQuest(@PathVariable Long id){
        QuestAnswerDTO quest=questService.completeQuest(id);
        return ResponseEntity.ok(quest);
    }


}
