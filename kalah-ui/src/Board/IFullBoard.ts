export interface IFullBoard {
    id: number;
    player1: string;
    player2?: string | null;
    currentTurn?: string | null;
    winner: string | null;
    status: string;
    pits: number[];
}
