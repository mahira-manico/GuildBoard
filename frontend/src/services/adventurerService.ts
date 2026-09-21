import axios from "axios";
import type { AdventurerAnswer } from "../types/adventurer";
import type { AssignmentAnswer } from "../types/assignment";
import type { CharacterClass } from "../types/characterClass";

const API_URL = "http://localhost:8080/api/adventurers";

// Get all adventurers
export async function getAllAdventurers(): Promise<AdventurerAnswer[]> {
  const response = await axios.get<AdventurerAnswer[]>(API_URL);
  return response.data;
}

// Get one adventurer by ID
export async function getAdventurerById(id: number): Promise<AdventurerAnswer> {
  const response = await axios.get<AdventurerAnswer>(`${API_URL}/${id}`);
  return response.data;
}

// Create an adventurer
export async function createAdventurer(data: {
  name: string;
  characterClass: CharacterClass | string;
}): Promise<AdventurerAnswer> {
  const response = await axios.post<AdventurerAnswer>(API_URL, data);
  return response.data;
}

// Update an adventurer
export async function updateAdventurer(
  id: number,
  data: { name: string; characterClass: CharacterClass | string }
): Promise<AdventurerAnswer> {
  const response = await axios.put<AdventurerAnswer>(`${API_URL}/${id}`, data);
  return response.data;
}

// Delete an adventurer
export async function deleteAdventurer(id: number): Promise<void> {
  await axios.delete(`${API_URL}/${id}`);
}

// Get adventurer history 
export async function getAdventurerHistory(id: number): Promise<AssignmentAnswer[]> {
  const response = await axios.get<AssignmentAnswer[]>(`${API_URL}/${id}/history`);
  return response.data;
}

export const adventurerService = {
  getAllAdventurers,
  getAdventurerById,
  createAdventurer,
  updateAdventurer,
  deleteAdventurer,
  getAdventurerHistory,
  seeHistory: getAdventurerHistory, 
};