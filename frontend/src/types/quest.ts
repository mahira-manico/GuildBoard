import type {Difficulty} from './difficulty';
import type {Status} from './status';

export interface QuestAnswer{
    id: number;
    title: string;
    description: string;
    difficulty: Difficulty;
    requiredLevel:number;
    goldReward:number;
    xpReward:number;
    status: Status;
}

export interface QuestRequest{
    title: string;
    description: string;
    diffculty: Difficulty;
    requiredLevel:number;
    goldReward:number;
    xpReward:number;
    status: Status;
}