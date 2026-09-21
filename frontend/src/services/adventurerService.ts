import axios from "axios";
import type { Adventurer } from "../types/adventurer";

// Get all adventurers
export async function getAllAdventurers(): Promise<Adventurer[]> {

    const response = await axios.get<Adventurer[]>(
        "http://localhost:8080/api/adventurers"
    );

    return response.data;
}

// Get one adventurer by ID
export async function getAdventurerById(
    id: number
): Promise<Adventurer> {

    const response = await axios.get<Adventurer>(
        `http://localhost:8080/api/adventurers/${id}`
    );

    return response.data;
}

// Create an adventurer
export async function createAdventurer(
    name: string,
    characterClass: string
): Promise<Adventurer> {

    const response = await axios.post<Adventurer>(
        "http://localhost:8080/api/adventurers",
        {
            name: name,
            characterClass: characterClass
        }
    );

    return response.data;
}

// Update an adventurer
export async function updateAdventurer(
    id: number,
    name: string,
    characterClass: string
): Promise<Adventurer> {

    const response = await axios.put<Adventurer>(
        `http://localhost:8080/api/adventurers/${id}`,
        {
            name: name,
            characterClass: characterClass
        }
    );

    return response.data;
}

// Delete an adventurer
export async function deleteAdventurer(
    id: number
): Promise<void> {

    await axios.delete(
        `http://localhost:8080/api/adventurers/${id}`
    );
}

// Get adventurer history
export async function getAdventurerHistory(
    id: number
): Promise<any[]> {

    const response = await axios.get<any[]>(
        `http://localhost:8080/api/adventurers/${id}/history`
    );

    return response.data;
}