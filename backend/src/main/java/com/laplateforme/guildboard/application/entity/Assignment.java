package com.laplateforme.guildboard.application.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;


@Entity
public class Assignment{

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @ManyToOne //Precise the link between key and foreign key
    @JoinColumn(name = "adventurer_id", nullable = false)
    private Adventurer adventurer;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "quest_id", nullable = false)
    private Quest quest;

    private LocalDateTime assignedAt;
    private LocalDateTime completedAt;

    protected Assignment(){}

    public Assignment(Adventurer adventurer, Quest quest){
        this.adventurer=adventurer;
        this.quest=quest;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Adventurer getAdventurer() {
        return adventurer;
    }

    public void setAdventurer(Adventurer adventurer) {
        this.adventurer = adventurer;
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}
