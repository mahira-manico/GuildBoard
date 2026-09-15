package com.laplateforme.guildboard.application.controller;
import com.laplateforme.guildboard.application.dto.AssigmentResponse;
import com.laplateforme.guildboard.application.dto.AssignmentRequest;
import com.laplateforme.guildboard.application.dto.QuestRequest;
import com.laplateforme.guildboard.application.dto.QuestResponse;
import com.laplateforme.guildboard.application.entity.Difficulty;
import com.laplateforme.guildboard.application.entity.Status;
import com.laplateforme.guildboard.application.service.AssignmentService;
import com.laplateforme.guildboard.application.service.QuestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quest")
public class QuestController {

    private final QuestService questService;
    private final AssignmentService assignmentService;

    public QuestController(QuestService questService, AssignmentService assignmentService) {
        this.questService = questService;
        this.assignmentService=assignmentService;
    }

    //See a quest
    @GetMapping("/{id}")
    public ResponseEntity<QuestResponse> seeQuest(@PathVariable Long id){
        QuestResponse quest=questService.seeQuest(id);
        return ResponseEntity.ok(quest);
    }

    //Create a quest
    @PostMapping
    public ResponseEntity<QuestResponse> createQuest(@Valid @RequestBody QuestRequest request){
        QuestResponse quest=questService.createQuest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(quest);
    }

    //Update a quest
    @PutMapping("/{id}")
    public ResponseEntity<QuestResponse> updateQuest(@PathVariable Long id, @RequestBody QuestRequest request){
        QuestResponse quest=questService.updateQuest(id, request);
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
    public ResponseEntity<AssigmentResponse> assignQuest(@PathVariable Long id, @ Valid @RequestBody AssignmentRequest request){
        AssigmentResponse assignment=assignmentService.assignQuest(request.adventurer_id(), id);
        return ResponseEntity.status(HttpStatus.CREATED).body(assignment);

    }

    //See and filter all quests
    @GetMapping
    public ResponseEntity<List<QuestResponse>> seeAllQuests(@RequestParam(required = false)Status status, @RequestParam(required = false)Difficulty difficulty){
     return ResponseEntity.ok(questService.filterBy(status, difficulty));
    }

    //Complete a quest
    @PostMapping("{id}/completion")
    public  ResponseEntity<QuestResponse> completeQuest(@PathVariable Long id){
        QuestResponse quest=questService.completeQuest(id);
        return ResponseEntity.ok(quest);
    }


}
