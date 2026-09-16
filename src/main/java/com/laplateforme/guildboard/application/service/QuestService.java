package com.laplateforme.guildboard.application.service;
import com.laplateforme.guildboard.application.dto.QuestRequestDTO;
import com.laplateforme.guildboard.application.dto.QuestAnswerDTO;
import com.laplateforme.guildboard.application.entity.*;
import com.laplateforme.guildboard.application.exception.BusinessRuleErrors;
import com.laplateforme.guildboard.application.exception.RessourceNotFoundErrors;
import com.laplateforme.guildboard.application.repository.AdventurerRepository;
import com.laplateforme.guildboard.application.repository.AssignmentRepository;
import com.laplateforme.guildboard.application.repository.QuestRepository;
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

    public List<QuestAnswerDTO> filterBy(Status status, Difficulty difficulty){
        if(status!=null && difficulty==null){
            return findByStatus(status);
        } else if (status==null && difficulty!=null) {
            return findByDifficulty(difficulty);
        }
        else return seeAllQuests();
    }


    public QuestAnswerDTO seeQuest(Long id){
        Quest quest=questRepository.findById(id).orElseThrow(()->new RessourceNotFoundErrors("QUEST_NOT_FOUND","Quête non trouvé à l'id : "+id));
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

    public void deleteQuest(Long id){

        Quest quest=questRepository.findById(id).orElseThrow(()->new RessourceNotFoundErrors("QUEST_NOT_FOUND","La quête est introuvable avec l'id : "+id));

        if(quest.getStatus()==Status.ON_GOING){
            throw new BusinessRuleErrors("QUEST_NOT_DONE","Une quête en cours ne peut pas être supprimé!");
        }
        questRepository.delete(quest);
    }

    public QuestAnswerDTO updateQuest(Long id, QuestRequestDTO questRequestDTO){

        Quest currentQuest=questRepository.findById(id).orElseThrow(()->new RessourceNotFoundErrors("QUEST_NOT_FOUND","La quête est introuvable avec l'id : "+id));
        if(currentQuest.getStatus()!=Status.AVAILABLE){
            throw new BusinessRuleErrors("QUEST_ON_GOING","Une quête en cours ne peut être modifié!");
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

    public QuestAnswerDTO completeQuest(Long quest_id){
        Optional<Assignment> assignment=assignmentRepository.findByQuestIdAndCompletedAtIsNull(quest_id);
        assignment.get().setCompletedAt(LocalDateTime.now());
        assignmentRepository.save(assignment.get());

       Adventurer adventurer=assignment.get().getAdventurer();
       Quest quest=assignment.get().getQuest();
       adventurer.setGold(adventurer.getGold()+quest.getGoldReward());
       int currentXp= adventurer.getXp()+quest.getXpReward();
       int currentLevel=adventurer.getLevel();

        while (currentXp >= currentLevel * 100) {
            currentXp -= currentLevel * 100;
            currentLevel++;
        }
        adventurer.setXp(currentXp);
        adventurer.setLevel(currentLevel);
        adventurerRepository.save(adventurer);

        quest.setStatus(Status.COMPLETED);
        Quest completedQuest=questRepository.save(quest);
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
