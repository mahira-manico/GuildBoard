import type {QuestAnswer} from './quest';

export interface AssignmentAnswer{
    id:number;
    adventurer_id: number;
    quest:QuestAnswer;
    assigned_at:string;
    completed_at:string|null;
}

export interface AssignmentRequest{
    adventurer_id:number;
}