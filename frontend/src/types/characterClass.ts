export const CharacterClass ={
    WARRIOR: "WARRIOR",
    MAGE: "MAGE",
    RANGER: "RANGER",
    CLERIC: "CLERIC",
} as const;

export type CharacterClass=(typeof CharacterClass)[keyof typeof CharacterClass];