package com.laplateforme.guildboard.application.service;
import com.laplateforme.guildboard.application.dto.QuestResponse;
import com.laplateforme.guildboard.application.entity.Quest;
import com.laplateforme.guildboard.application.entity.Status;
import com.laplateforme.guildboard.application.repository.QuestRepository;
import org.springframework.stereotype.Service;

@Service
public class QuestService {
    private final QuestRepository questRepository;

    public QuestService(QuestRepository questRepository, QuestResponse questResponse){
        this.questRepository=questRepository;
    }

    public Quest seeQuest(Long id){
        return questRepository.findById(id).orElseThrow(()->new RuntimeException("Quest not found!"));
    }

    public QuestResponse createQuest(QuestResponse questResponse){
        if (questResponse.title()==null||questResponse.title().trim().isEmpty()){
            throw new RuntimeException("Quest title is required!");
        }
        if (questResponse.description()==null||questResponse.description().length()>500||questResponse.description().length()<10){
            throw new RuntimeException("Text length must be between 10 and 500 characters!");
        }

        if (questResponse.requiredLevel()<1){
            throw new RuntimeException("Level must be 1 minimum!");
        }

        if(questResponse.xpReward()<0){
            throw new RuntimeException("Xp gains must be positive!");
        }

        Quest createAQuest=new Quest(questResponse.title(), questResponse.description(), questResponse.difficulty(), questResponse.requiredLevel(), questResponse.goldReward(), questResponse.xpReward());
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

    public Quest updateQuest(Long id, QuestResponse questResponse){

        Quest currentQuest=questRepository.findById(id).orElseThrow(()->new RuntimeException("Quest not found!"));
        if(currentQuest.getStatus()!=Status.AVAILABLE){
            throw new RuntimeException("Quest cannot be modified if already chosen");
        }

        currentQuest.setTitle(questResponse.title());
        currentQuest.setDescription(questResponse.description());
        currentQuest.setDifficulty(questResponse.difficulty());
        currentQuest.setRequiredLevel(questResponse.requiredLevel());
        currentQuest.setGoldReward(questResponse.goldReward());
        currentQuest.setStatus(questResponse.status());
        return questRepository.save(currentQuest);






    }
}
