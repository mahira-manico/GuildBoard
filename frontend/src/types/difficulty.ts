export const Difficulty={
    EASY:"EASY",
    MEDIUM:"MEDIUM",
    HARD:"HARD",
    EPIC:"EPIC",
} as const

export type Difficulty=(typeof Difficulty)[keyof typeof Difficulty]
