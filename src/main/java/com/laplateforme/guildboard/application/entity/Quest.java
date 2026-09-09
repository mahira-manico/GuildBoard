package com.laplateforme.guildboard.application.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Quest {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true, length = 500)
    @Size(min = 5, max=500)
    private String title;

    @Column(nullable = false, length = 500)
    @Size(min = 10, max = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Size(min = 1)
    private int requiredLevel;

    @Size(min=0)
    private int goldReward;

    @Size(min = 0)
    private int xpReward;

    @Enumerated(EnumType.STRING)
    private Status status;

    protected Quest(){}

    public Quest(String title, String description, Difficulty difficulty, int requiredLevel, int goldReward, int xpReward, Status status){
        this.title=title;
        this.description=description;
        this.difficulty=difficulty;
        this.requiredLevel=requiredLevel;
        this.goldReward=goldReward;
        this.xpReward=xpReward;
        this.status=status;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getGoldReward() {
        return goldReward;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public int getXpReward() {
        return xpReward;
    }

    public Status getStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setGoldReward(int goldReward) {
        this.goldReward = goldReward;
    }

    public void setRequiredLevel(int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setXpReward(int xpReward) {
        this.xpReward = xpReward;
    }
}
