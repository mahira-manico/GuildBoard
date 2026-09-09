package com.laplateforme.guildboard.application.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
public class Assignment{

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    @JoinColumn(name = adventurer_id)
    private Adventurer adventurer;

    @OneToMany
    @JoinColumn(name=quest_id)
    private Quest quest;

    private LocalDateTime assigned_at;
    private LocalDateTime completed_at;

    protected Assignment(){}

    public Assignment(Adventurer adventurer, Quest quest, LocalDateTime assigned_at, LocalDateTime completed_at){
        this.adventurer=adventurer;
        this.quest=quest;
        this.assigned_at=assigned_at;
        this.completed_at=completed_at;
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

    public LocalDateTime getAssigned_at() {
        return assigned_at;
    }

    public void setAssigned_at(LocalDateTime assigned_at) {
        this.assigned_at = assigned_at;
    }

    public LocalDateTime getCompleted_at() {
        return completed_at;
    }

    public void setCompleted_at(LocalDateTime completed_at) {
        this.completed_at = completed_at;
    }
}
