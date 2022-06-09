import { BoardList } from "./BoardList";
import { useNavigate } from 'react-router-dom';

export function AvailableBoardsPage(): JSX.Element {
    const navigate = useNavigate();

    return (
        <>
            Available Boards Page

            <BoardList items={[
                {
                    id: 1,
                    player1: "User 1",
                    player2: "User 2",
                    currentTurn: "User 2",
                    winner: "",
                    status: "playing",

                }
            ]} columns={['id', 'player1']} onSelect={(item) => navigate(`/boards/${item.id}`)} />

        </>
    );
} 