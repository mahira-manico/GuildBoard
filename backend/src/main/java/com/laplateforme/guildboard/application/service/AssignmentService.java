package com.laplateforme.guildboard.application.service;
import com.laplateforme.guildboard.application.dto.AssignmentAnswerDTO;
import com.laplateforme.guildboard.application.entity.Adventurer;
import com.laplateforme.guildboard.application.entity.Assignment;
import com.laplateforme.guildboard.application.entity.Quest;
import com.laplateforme.guildboard.application.entity.Status;
import com.laplateforme.guildboard.application.exception.BusinessRuleException;
import com.laplateforme.guildboard.application.exception.RessourceNotFoundException;
import com.laplateforme.guildboard.application.repository.AdventurerRepository;
import com.laplateforme.guildboard.application.repository.AssignmentRepository;
import com.laplateforme.guildboard.application.repository.QuestRepository;
import jakarta.transaction.Transactional;
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

    //Method to create an assignation entity between a quest and an adventurer
    @Transactional
    public AssignmentAnswerDTO assignQuest(Long adventurer_id, Long quest_id) {

        Adventurer adv = adventurerRepository.findById(adventurer_id)
                .orElseThrow(() -> new RessourceNotFoundException("ADVENTURER_NOT_FOUND","Aventurier non trouvé avec l'id : "+adventurer_id)); //get selected id and throw a message error

        Quest q = questRepository.findById(quest_id)
                .orElseThrow(() -> new RessourceNotFoundException("QUEST_NOT_FOUND","Quête non trouvé avec l'id : "+quest_id));

        List<Assignment> assignmentsForAdv = assignmentRepository.findByAdventurer_Id(adventurer_id);

        for (Assignment a : assignmentsForAdv) { //Take all assignment elements in list
            if (a.getCompletedAt() == null) { //Verify if none of the assignments completions is null
                throw new BusinessRuleException("QUEST_IN_PROGRESS","Une quête est déja en cours! Finissez la d'abord");
            }
        }

        if (q.getStatus() != Status.AVAILABLE) { //Verify the status of the quest
            throw new BusinessRuleException("QUEST_NOT_AVAILABLE","La quête n'est pas disponible!");
        }
        if (adv.getLevel() < q.getRequiredLevel()) { //Verify that the adventurer level correspond the required level of the quest
            throw new BusinessRuleException("LEVEL_TOO_LOW","Niveau trop bas pour la quête!");
        }

        Assignment addQuest = new Assignment(adv, q); //If all verification are ok create a new assignation
        addQuest.setAssignedAt(LocalDateTime.now()); //set datetime to now and completed at null
        addQuest.setCompletedAt(null);
        q.setStatus(Status.ON_GOING);
        assignmentRepository.save(addQuest); //return saved new assignment
        questRepository.save(q);

        return new AssignmentAnswerDTO(
                addQuest.getId(),
                addQuest.getAdventurer().getId(),
                addQuest.getQuest(),
                addQuest.getAssignedAt(),
                addQuest.getCompletedAt()
        );
    }


    
}
