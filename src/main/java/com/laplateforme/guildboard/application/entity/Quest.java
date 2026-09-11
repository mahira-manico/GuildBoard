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
    private Integer requiredLevel;

    @Size(min=0)
    private Integer goldReward;

    @Size(min = 0)
    private Integer xpReward;

    @Enumerated(EnumType.STRING)
    private Status status;

    protected Quest(){}

    public Quest(String title, String description, Difficulty difficulty, Integer requiredLevel, Integer goldReward, Integer xpReward, Status status){
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

    public Integer getGoldReward() {
        return goldReward;
    }

    public Integer getRequiredLevel() {
        return requiredLevel;
    }

    public Integer getXpReward() {
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

    public void setGoldReward(Integer goldReward) {
        this.goldReward = goldReward;
    }

    public void setRequiredLevel(Integer requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setXpReward(Integer xpReward) {
        this.xpReward = xpReward;
    }
}
