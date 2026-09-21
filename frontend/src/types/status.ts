export const Status={
    AVAILABLE:"AVAILABLE",
    ON_GOING:"ON_GOING",
    COMPLETED:"COMPLETED",
} as const

export type Status=(typeof Status)[keyof typeof Status]