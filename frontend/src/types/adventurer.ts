import type { CharacterClass } from './characterClass';


export interface AdventurerAnswer {
    id: number;
    name: string;
    characterClass: CharacterClass;
    level: number;
    xp: number;
    gold: number;
}

export interface AdventurerRequest{
    name:string;
    characterClass: CharacterClass;
}