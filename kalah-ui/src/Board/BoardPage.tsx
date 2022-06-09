import { useEffect } from "react";
import { useParams } from "react-router-dom";
import { Board } from "./Board";

export function BoardPage(): JSX.Element {

    const { id } = useParams();

    useEffect(() => {
        console.log("fetching board " + id);
    }, [id]);

    return (
        <>
            Single Board Page for {id}
            <Board item={
                {
                    id: 1,
                    player1: "Player 1",
                    player2: "Player 2",
                    currentTurn: "Player 2",
                    winner: "",
                    status: "playing",
                    pits: [0, 0, 3, 4, 5, 6, 7, 0, 0, 10, 11, 12, 13, 14]
                }
            }
                player="Player 2"
            />
        </>
    );
} 