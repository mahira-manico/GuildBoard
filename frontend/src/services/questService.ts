import axios from "axios";
import type { QuestAnswer } from "../types/quest";
import type { AssignmentAnswer } from "../types/assignment";
import type { Status } from "../types/status";
import type { Difficulty } from "../types/difficulty";

const API_URL = "http://localhost:8080/api/quests";

// Get all quests 
export async function getQuests(
  status?: Status | string,
  difficulty?: Difficulty | string
): Promise<QuestAnswer[]> {
  const response = await axios.get<QuestAnswer[]>(API_URL, {
    params: { 
      status: status || undefined, 
      difficulty: difficulty || undefined 
    },
  });
  return response.data;
}

export async function getAllQuests(): Promise<QuestAnswer[]> {
  return getQuests();
}

// Get one quest by ID
export async function getQuestById(id: number): Promise<QuestAnswer> {
  const response = await axios.get<QuestAnswer>(`${API_URL}/${id}`);
  return response.data;
}

// Create a quest
export async function createQuest(questData: {
  title: string;
  description: string;
  difficulty: Difficulty | string;
  requiredLevel: number;
  goldReward: number;
  xpReward: number;
}): Promise<QuestAnswer> {
  const response = await axios.post<QuestAnswer>(API_URL, questData);
  return response.data;
}

// Update a quest
export async function updateQuest(
  id: number,
  questData: Partial<QuestAnswer>
): Promise<QuestAnswer> {
  const response = await axios.put<QuestAnswer>(`${API_URL}/${id}`, questData);
  return response.data;
}

// Delete a quest
export async function deleteQuest(id: number): Promise<void> {
  await axios.delete(`${API_URL}/${id}`);
}

// Assign a quest to an adventurer 
export async function assignQuest(
  questId: number,
  adventurerId: number
): Promise<AssignmentAnswer> {
  const response = await axios.post<AssignmentAnswer>(
    `${API_URL}/${questId}/assignment`,
    { adventurerId }
  );
  return response.data;
}

// Complete a quest 
export async function completeQuest(questId: number): Promise<QuestAnswer> {
  const response = await axios.post<QuestAnswer>(
    `${API_URL}/${questId}/completion`
  );
  return response.data;
}

export const questService = {
  getQuests,
  getAllQuests,
  getAllQuest: getQuests, 
  getQuestById,
  createQuest,
  updateQuest,
  deleteQuest,
  assignQuest,
  completeQuest,
};