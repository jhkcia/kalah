import { BoardList } from "./BoardList";
import { useNavigate } from 'react-router-dom';
import styled from "styled-components";
import { useEffect, useState } from "react";
import { IBoard } from "./IBoard";
import BoardApi from "../api/BoardApi";
import { BoardListHeader } from "./BoardListHeader";

const Wrapper = styled.div`
    display: flex;
    justify-content: center;
    flex-direction: column;
    align-items: center;
`;

const ContentWrapper = styled.div`
    max-width: 800px;
    width: 100%;
`;
export function AvailableBoardsPage(): JSX.Element {
    const navigate = useNavigate();

    const [items, setItems] = useState<IBoard[]>([])

    useEffect(() => {
        BoardApi.getInstance().getAvailableBoards().then(boards => {
            setItems(boards.data);
        });
    }, []);

    const handleJoinBoard = (board: IBoard) => {
        BoardApi.getInstance().joinBoard(board.id).then(() => {
            navigate(`/boards/${board.id}`)
        });
    }
    return (
        <Wrapper>

            <BoardListHeader title='My Boards' />

            <ContentWrapper>
                <BoardList items={items} columns={['id', 'player1']} onSelect={handleJoinBoard} />
            </ContentWrapper>

        </Wrapper>
    );
} 