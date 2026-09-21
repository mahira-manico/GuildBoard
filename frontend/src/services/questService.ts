import axios from "axios";
import type { Quest } from "../types/quest";

// Get all quests
export async function getAllQuests(): Promise<Quest[]> {

    const response = await axios.get<Quest[]>(
        "http://localhost:8080/api/quest"
    );

    return response.data;
}

// Get all quests with filters
export async function getQuests(
    status?: string,
    difficulty?: string
): Promise<Quest[]> {

    const response = await axios.get<Quest[]>(
        "http://localhost:8080/api/quest",
        {
            params: {
                status: status,
                difficulty: difficulty
            }
        }
    );

    return response.data;
}

// Get one quest by ID
export async function getQuestById(
    id: number
): Promise<Quest> {

    const response = await axios.get<Quest>(
        `http://localhost:8080/api/quest/${id}`
    );

    return response.data;
}

// Create a quest
export async function createQuest(
    title: string,
    description: string,
    difficulty: string,
    requiredLevel: number,
    goldReward: number,
    xpReward: number,
    status: string
): Promise<Quest> {

    const response = await axios.post<Quest>(
        "http://localhost:8080/api/quest",
        {
            title: title,
            description: description,
            difficulty: difficulty,
            requiredLevel: requiredLevel,
            goldReward: goldReward,
            xpReward: xpReward,
            status: status
        }
    );

    return response.data;
}

// Update a quest
export async function updateQuest(
    id: number,
    title: string,
    description: string,
    difficulty: string,
    requiredLevel: number,
    goldReward: number,
    xpReward: number,
    status: string
): Promise<Quest> {

    const response = await axios.put<Quest>(
        `http://localhost:8080/api/quest/${id}`,
        {
            title: title,
            description: description,
            difficulty: difficulty,
            requiredLevel: requiredLevel,
            goldReward: goldReward,
            xpReward: xpReward,
            status: status
        }
    );

    return response.data;
}

// Delete a quest
export async function deleteQuest(
    id: number
): Promise<void> {

    await axios.delete(
        `http://localhost:8080/api/quest/${id}`
    );
}

// Assign a quest to an adventurer
export async function assignQuest(
    questId: number,
    adventurerId: number
): Promise<any> {

    const response = await axios.post<any>(
        `http://localhost:8080/api/quest/${questId}/assignment`,
        {
            adventurer_id: adventurerId
        }
    );

    return response.data;
}

// Complete a quest
export async function completeQuest(
    questId: number
): Promise<Quest> {

    const response = await axios.post<Quest>(
        `http://localhost:8080/api/quest/${questId}/completion`
    );

    return response.data;
}