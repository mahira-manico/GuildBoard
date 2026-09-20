export interface Quest {
    id: number;
    title: string;
    description: string;
    difficulty: string;
    requiredLevel: number;
    goldReward: number;
    xpReward: number;
    status: string;
}