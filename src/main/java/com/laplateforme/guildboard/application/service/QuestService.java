package com.laplateforme.guildboard.application.service;
import com.laplateforme.guildboard.application.dto.QuestRequestDTO;
import com.laplateforme.guildboard.application.dto.QuestAnswerDTO;
import com.laplateforme.guildboard.application.entity.*;
import com.laplateforme.guildboard.application.exception.BusinessRuleException;
import com.laplateforme.guildboard.application.exception.RessourceNotFoundException;
import com.laplateforme.guildboard.application.repository.AdventurerRepository;
import com.laplateforme.guildboard.application.repository.AssignmentRepository;
import com.laplateforme.guildboard.application.repository.QuestRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuestService {
    private final QuestRepository questRepository;
    private final AssignmentRepository assignmentRepository;
    private final AdventurerRepository adventurerRepository;

    public QuestService(QuestRepository questRepository, AssignmentRepository assignmentRepository, AdventurerRepository adventurerRepository){
        this.questRepository=questRepository;
        this.assignmentRepository = assignmentRepository;
        this.adventurerRepository = adventurerRepository;
    }

    //Filter all quests by status
    public List<QuestAnswerDTO> findByStatus(Status status){
        return questRepository.findByStatus(status).stream().map(quest ->
                new QuestAnswerDTO(
                        quest.getId(),
                        quest.getTitle(),
                        quest.getDescription(),
                        quest.getDifficulty(),
                        quest.getRequiredLevel(),
                        quest.getGoldReward(),
                        quest.getXpReward(),
                        quest.getStatus())).toList();

    }

    //Filter all quests by difficulty
    public List<QuestAnswerDTO> findByDifficulty(Difficulty difficulty){
        return questRepository.findByDifficulty(difficulty).stream().map(quest ->
                new QuestAnswerDTO(
                        quest.getId(),
                        quest.getTitle(),
                        quest.getDescription(),
                        quest.getDifficulty(),
                        quest.getRequiredLevel(),
                        quest.getGoldReward(),
                        quest.getXpReward(),
                        quest.getStatus())).toList();
    }

    //See all quests
    public List<QuestAnswerDTO> seeAllQuests(){
       return questRepository.findAll().stream().map(quest ->
               new QuestAnswerDTO(
                       quest.getId(),
                       quest.getTitle(),
                       quest.getDescription(),
                       quest.getDifficulty(),
                       quest.getRequiredLevel(),
                       quest.getGoldReward(),
                       quest.getXpReward(),
                       quest.getStatus())).toList();

    }

    //Method to filter quests using the three methods above
    public List<QuestAnswerDTO> filterBy(Status status, Difficulty difficulty){
        if(status!=null && difficulty==null){
            return findByStatus(status);
        } else if (status==null && difficulty!=null) {
            return findByDifficulty(difficulty);
        }
        else return seeAllQuests();
    }

    //Method to see a quest using its ID
    public QuestAnswerDTO seeQuest(Long id){
        Quest quest=questRepository.findById(id)
                .orElseThrow(()->new RessourceNotFoundException("QUEST_NOT_FOUND","Quête non trouvé à l'id : "+id)); //Error Handling
        return new QuestAnswerDTO(
                quest.getId(),
                quest.getTitle(),
                quest.getDescription(),
                quest.getDifficulty(),
                quest.getRequiredLevel(),
                quest.getGoldReward(),
                quest.getGoldReward(),
                quest.getStatus());
    }

    @Transactional //Use of transactional for methods who modify a table for better protection
    public QuestAnswerDTO createQuest(QuestRequestDTO questRequestDTO){

        Quest createAQuest=new Quest(
                questRequestDTO.title(),
                questRequestDTO.description(),
                questRequestDTO.difficulty(),
                questRequestDTO.requiredLevel(),
                questRequestDTO.goldReward(),
                questRequestDTO.xpReward());

        createAQuest.setStatus(Status.AVAILABLE);

        Quest savedQuest=questRepository.save(createAQuest);

        return new QuestAnswerDTO(
                savedQuest.getId(),
                savedQuest.getTitle(),
                savedQuest.getDescription(),
                savedQuest.getDifficulty(),
                savedQuest.getRequiredLevel(),
                savedQuest.getGoldReward(),
                savedQuest.getXpReward(),
                savedQuest.getStatus());
    }

    //Method to delete a quest
    @Transactional
    public void deleteQuest(Long id){

        Quest quest=questRepository.findById(id)
                .orElseThrow(()->new RessourceNotFoundException("QUEST_NOT_FOUND","La quête est introuvable avec l'id : "+id));

        if(quest.getStatus()==Status.ON_GOING){
            throw new BusinessRuleException("QUEST_NOT_DONE","Une quête en cours ne peut pas être supprimé!");
        }
        questRepository.delete(quest);
    }


    //Method to update a quest
    @Transactional
    public QuestAnswerDTO updateQuest(Long id, QuestRequestDTO questRequestDTO){

        Quest currentQuest=questRepository.findById(id).orElseThrow(()->new RessourceNotFoundException("QUEST_NOT_FOUND","La quête est introuvable avec l'id : "+id));
        if(currentQuest.getStatus()!=Status.AVAILABLE){
            throw new BusinessRuleException("QUEST_ON_GOING","Une quête en cours ne peut être modifié!");
        }

        currentQuest.setTitle(questRequestDTO.title());
        currentQuest.setDescription(questRequestDTO.description());
        currentQuest.setDifficulty(questRequestDTO.difficulty());
        currentQuest.setRequiredLevel(questRequestDTO.requiredLevel());
        currentQuest.setGoldReward(questRequestDTO.goldReward());
        currentQuest.setStatus(questRequestDTO.status());

        questRepository.save(currentQuest);

        return new QuestAnswerDTO(
                currentQuest.getId(),
                currentQuest.getTitle(),
                currentQuest.getDescription(),
                currentQuest.getDifficulty(),
                currentQuest.getRequiredLevel(),
                currentQuest.getGoldReward(),
                currentQuest.getGoldReward(),
                currentQuest.getStatus());
    }

    //Method to complete a quest
    @Transactional
    public QuestAnswerDTO completeQuest(Long quest_id){

        Quest existingQuest=questRepository.findById(quest_id)
                .orElseThrow(()->new RessourceNotFoundException("QUEST_NOT_FOUND","Quête non trouvé avec l'id : "+quest_id)); //Check if ressources exists

        if(existingQuest.getStatus()!=Status.ON_GOING){
            throw new BusinessRuleException("QUEST_NOT_ON_GOING","La quête n'est pas en cours!");
        }

        Assignment assignment=assignmentRepository.findByQuestIdAndCompletedAtIsNull(quest_id)
                .orElseThrow(()->new RessourceNotFoundException("ASSIGNMENT_NOT_FOUND","l'assignation n'existe pas"));

        assignment.setCompletedAt(LocalDateTime.now());
        assignmentRepository.save(assignment);

        Adventurer adventurer=assignment.getAdventurer();
        adventurer.setGold(adventurer.getGold()+existingQuest.getGoldReward());

        int currentXp= adventurer.getXp()+existingQuest.getXpReward();
        int currentLevel=adventurer.getLevel();

        while (currentXp >= currentLevel * 100) {
            currentXp -= currentLevel * 100;
            currentLevel++;
        }
        adventurer.setXp(currentXp);
        adventurer.setLevel(currentLevel);
        adventurerRepository.save(adventurer);

        existingQuest.setStatus(Status.COMPLETED);

        Quest completedQuest=questRepository.save(existingQuest);

        return new QuestAnswerDTO(
                completedQuest.getId(),
                completedQuest.getTitle(),
                completedQuest.getDescription(),
                completedQuest.getDifficulty(),
                completedQuest.getRequiredLevel(),
                completedQuest.getGoldReward(),
                completedQuest.getXpReward(),
                completedQuest.getStatus()
        );


    }
}
