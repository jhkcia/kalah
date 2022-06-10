import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import BoardApi from '../api/BoardApi';
import { BoardList } from "./BoardList";
import { BoardListHeader } from './BoardListHeader';
import { IBoard } from './IBoard';

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

export function UserBoardPages(): JSX.Element {
    const navigate = useNavigate();

    const [items, setItems] = useState<IBoard[]>([])

    useEffect(() => {
        BoardApi.getInstance().getUserBoards().then(boards => {
            setItems(boards.data);
        });
    }, []);
    const handleAddBoard = () => {
        BoardApi.getInstance().addBoard().then(board => {
            setItems([...items, board.data]);
        });
    };
    return (
        <Wrapper>
            <BoardListHeader title='My Boards' actions={[
                {
                    title: 'Add Board',
                    onClick: handleAddBoard
                }]} />

            <ContentWrapper>
                <BoardList items={items} columns={['id', 'player1', 'player2', 'status', 'currentTurn', 'winner']} onSelect={(item) => item.status !== "NotStart" && navigate(`/boards/${item.id}`)} />
            </ContentWrapper>
        </Wrapper>
    );
} 