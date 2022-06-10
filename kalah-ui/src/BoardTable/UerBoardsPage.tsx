import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { BoardList } from "./BoardList";

const Wrapper = styled.div`
    display: flex;
    justify-content: center;
    flex-direction: column;
    align-items: center;
`;
const Text = styled.h1`
`;
const ContentWrapper = styled.div`
    max-width: 800px;
    width: 100%;
`;
export function UserBoardPages(): JSX.Element {
    const navigate = useNavigate();

    return (
        <Wrapper>
            <Text>
                User Board Pages
            </Text>
            <ContentWrapper>

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
            </ContentWrapper>

        </Wrapper>
    );
} 