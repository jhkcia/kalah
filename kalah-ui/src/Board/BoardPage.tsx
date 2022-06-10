import { useEffect } from "react";
import { useParams } from "react-router-dom";
import styled from "styled-components";
import { Board } from "./Board";

const Wrapper = styled.div`
    background-color: #a64200;
    background-image: url("/wood-pattern.png");
    height: 100vh;
    padding: 100px 10px;
`;

export function BoardPage(): JSX.Element {

    const { id } = useParams();

    useEffect(() => {
        console.log("fetching board " + id);
    }, [id]);

    return (
        <Wrapper>
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
        </Wrapper>
    );
} 