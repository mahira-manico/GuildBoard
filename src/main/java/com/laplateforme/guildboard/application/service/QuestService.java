package com.laplateforme.guildboard.application.service;
import com.laplateforme.guildboard.application.dto.QuestRequest;
import com.laplateforme.guildboard.application.dto.QuestResponse;
import com.laplateforme.guildboard.application.entity.*;
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

    public List<QuestResponse> findByStatus(Status status){
        return questRepository.findByStatus(status).stream().map(quest ->
                new QuestResponse(
                        quest.getId(),
                        quest.getTitle(),
                        quest.getDescription(),
                        quest.getDifficulty(),
                        quest.getRequiredLevel(),
                        quest.getGoldReward(),
                        quest.getXpReward(),
                        quest.getStatus())).toList();

    }

    public List<QuestResponse> findByDifficulty(Difficulty difficulty){
        return questRepository.findByDifficulty(difficulty).stream().map(quest ->
                new QuestResponse(
                        quest.getId(),
                        quest.getTitle(),
                        quest.getDescription(),
                        quest.getDifficulty(),
                        quest.getRequiredLevel(),
                        quest.getGoldReward(),
                        quest.getXpReward(),
                        quest.getStatus())).toList();
    }

    public List<QuestResponse> seeAllQuests(){
       return questRepository.findAll().stream().map(quest ->
               new QuestResponse(
                       quest.getId(),
                       quest.getTitle(),
                       quest.getDescription(),
                       quest.getDifficulty(),
                       quest.getRequiredLevel(),
                       quest.getGoldReward(),
                       quest.getXpReward(),
                       quest.getStatus())).toList();

    }

    public List<QuestResponse> filterBy(Status status, Difficulty difficulty){
        if(status!=null && difficulty==null){
            return findByStatus(status);
        } else if (status==null && difficulty!=null) {
            return findByDifficulty(difficulty);
        }
        else return seeAllQuests();
    }


    public QuestResponse seeQuest(Long id){
        Quest quest=questRepository.findById(id).orElseThrow(()->new RuntimeException("Quest not found!"));
        return new QuestResponse(
                quest.getId(),
                quest.getTitle(),
                quest.getDescription(),
                quest.getDifficulty(),
                quest.getRequiredLevel(),
                quest.getGoldReward(),
                quest.getGoldReward(),
                quest.getStatus());
    }

    public QuestResponse createQuest(QuestRequest questRequest){
        if (questRequest.title()==null||questRequest.title().trim().isEmpty()){
            throw new RuntimeException("Quest title is required!");
        }
        if (questRequest.description()==null||questRequest.description().length()>500||questRequest.description().length()<10){
            throw new RuntimeException("Text length must be between 10 and 500 characters!");
        }

        if (questRequest.requiredLevel()<1){
            throw new RuntimeException("Level must be 1 minimum!");
        }

        if(questRequest.xpReward()<0){
            throw new RuntimeException("Xp gains must be positive!");
        }

        Quest createAQuest=new Quest(questRequest.title(), questRequest.description(), questRequest.difficulty(), questRequest.requiredLevel(), questRequest.goldReward(), questRequest.xpReward());
        createAQuest.setStatus(Status.AVAILABLE);
        Quest savedQuest=questRepository.save(createAQuest);
        return new QuestResponse(savedQuest.getId(), savedQuest.getTitle(), savedQuest.getDescription(), savedQuest.getDifficulty(), savedQuest.getRequiredLevel(), savedQuest.getGoldReward(), savedQuest.getXpReward(), savedQuest.getStatus());
    }

    public void deleteQuest(Long id){

        Quest quest=questRepository.findById(id).orElseThrow(()->new RuntimeException("Quest not found"));
        if(quest.getStatus()==Status.ON_GOING){
            throw new RuntimeException("Quest cannot be deleted while on_going!");
        }
        questRepository.delete(quest);
    }

    public QuestResponse updateQuest(Long id, QuestRequest questRequest){

        Quest currentQuest=questRepository.findById(id).orElseThrow(()->new RuntimeException("Quest not found!"));
        if(currentQuest.getStatus()!=Status.AVAILABLE){
            throw new RuntimeException("Quest cannot be modified if already chosen");
        }

        currentQuest.setTitle(questRequest.title());
        currentQuest.setDescription(questRequest.description());
        currentQuest.setDifficulty(questRequest.difficulty());
        currentQuest.setRequiredLevel(questRequest.requiredLevel());
        currentQuest.setGoldReward(questRequest.goldReward());
        currentQuest.setStatus(questRequest.status());

        questRepository.save(currentQuest);

        return new QuestResponse(
                currentQuest.getId(),
                currentQuest.getTitle(),
                currentQuest.getDescription(),
                currentQuest.getDifficulty(),
                currentQuest.getRequiredLevel(),
                currentQuest.getGoldReward(),
                currentQuest.getGoldReward(),
                currentQuest.getStatus());
    }

    public QuestResponse completeQuest(Long quest_id){
        Optional<Assignment> assignment=assignmentRepository.findByQuest_IdAndCompleted_atIsNull(quest_id);
        assignment.get(). setCompleted_at(LocalDateTime.now());
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
        return new QuestResponse(
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
