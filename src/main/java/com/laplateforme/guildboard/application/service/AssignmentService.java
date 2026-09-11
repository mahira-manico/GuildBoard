package com.laplateforme.guildboard.application.service;
import com.laplateforme.guildboard.application.entity.Adventurer;
import com.laplateforme.guildboard.application.entity.Assignment;
import com.laplateforme.guildboard.application.entity.Quest;
import com.laplateforme.guildboard.application.entity.Status;
import com.laplateforme.guildboard.application.repository.AdventurerRepository;
import com.laplateforme.guildboard.application.repository.AssignmentRepository;
import com.laplateforme.guildboard.application.repository.QuestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository; //add repositories dependencies
    private final AdventurerRepository adventurerRepository;
    private final QuestRepository questRepository;

    //Create constructor
    public AssignmentService(AssignmentRepository assignmentRepository, AdventurerRepository adventurerRepository, QuestRepository questRepository){
        this.assignmentRepository=assignmentRepository;
        this.adventurerRepository=adventurerRepository;
        this.questRepository=questRepository;
    }

    //Get created method from AssignmentRepository to get all assignments from selected adventurer
    public List<Assignment> getAssignmentByAdventurer(Long adventurer_id){
        return assignmentRepository.findByAdventurer_Id(adventurer_id);
    }

    //Method to create an assignation entity between a quest and an adventurer
    public Assignment assignQuest(Long adventurer_id, Long quest_id) {
        Adventurer adv = adventurerRepository.findById(adventurer_id).orElseThrow(() -> new RuntimeException("Adventurer not found!")); //get selected id and throw a message error
        Quest q = questRepository.findById(quest_id).orElseThrow(() -> new RuntimeException("Quest not found!"));
        List<Assignment> assignmentsForAdv = assignmentRepository.findByAdventurer_Id(adventurer_id);

        for (Assignment a : assignmentsForAdv) { //Take all assignment elements in list
            if (a.getCompleted_at() == null) { //Verify if none of the assignments completions is null
                throw new RuntimeException("A quest is already in progress! Can't add a new one!");
            }
        }

        if (q.getStatus() != Status.AVAILABLE) { //Verify the status of the quest
            throw new RuntimeException("Quest is not available!");
        }
        if (adv.getLevel() < q.getRequiredLevel()) { //Verify that the adventurer level correspond the required level of the quest
            throw new RuntimeException("Level too low for quest!");
        }
        Assignment addQuest = new Assignment(adv, q); //If all verification are ok create a new assignation
        addQuest.setAssigned_at(LocalDateTime.now()); //set datetime to now and completed at null
        addQuest.setCompleted_at(null);

        q.setStatus(Status.ON_GOING);
        return assignmentRepository.save(addQuest); //return saved new assignment
    }

    public List<Assignment> seeAllQuests(Long adventurer_id){
        Adventurer adv=adventurerRepository.findById(adventurer_id).orElseThrow(()->new RuntimeException("Adventurer not found!"));
        List<Assignment> allAssignments = assignmentRepository.findByAdventurer_Id(adventurer_id);
        return allAssignments;
    }


    
}
