import React from "react";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { useSubscription } from "react-stomp-hooks";
import styled from "styled-components";
import BoardApi from "../api/BoardApi";
import { UserContext } from "../context/UserContext";
import { Board } from "./Board";
import { IFullBoard } from "./IFullBoard";
import { IPit } from "./IPit";

const Wrapper = styled.div`
    background-color: #a64200;
    background-image: url("/wood-pattern.png");
    height: 100vh;
    padding: 100px 10px;
`;

export function BoardPage(): JSX.Element {

    const { id } = useParams();

    useSubscription(`/topic/boards/${id}/get`, (message) => {
        BoardApi.getInstance().get(Number(id)).then(result => {
            setBoard(result.data);
        });
    });

    const [board, setBoard] = useState<IFullBoard>();
    const { user } = React.useContext(UserContext);

    useEffect(() => {
        BoardApi.getInstance().get(Number(id)).then(result => {
            setBoard(result.data);
        });
    }, [id]);

    const handleSow = (pit: IPit) => {
        if (pit.active) {
            BoardApi.getInstance().sowSeeds(Number(id), pit.id).then(result => {
                setBoard(result.data);
            });
        }
    }

    return (
        <Wrapper>
            {
                board && <Board item={board}
                    player={user ?? "NOT FOUND"}
                    onSow={handleSow} />
            }
        </Wrapper>
    );
} 