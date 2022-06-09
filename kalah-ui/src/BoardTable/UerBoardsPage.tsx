import { useNavigate } from 'react-router-dom';
import { BoardList } from "./BoardList";

export function UserBoardPages(): JSX.Element {
    const navigate = useNavigate();

    return (
        <>
            User Board Pages
            <BoardList items={[
                {
                    id: 1,
                    player1: "User 1",
                    player2: "User 2",
                    currentTurn: "User 2",
                    winner: "",
                    status: "playing",
                }
            ]} columns={['id', 'player1', 'player2', 'status', 'currentTurn', 'winner']} onSelect={(item) => navigate(`/boards/${item.id}`)} />

        </>
    );
} 