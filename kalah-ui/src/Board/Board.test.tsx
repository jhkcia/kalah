import { render, screen } from '@testing-library/react';
import { Board } from './Board';

const mockPit = jest.fn();
jest.mock("./Pit", () => ({
    Pit: (props: any) => {
        mockPit(props);
        return <div />;
    },
}));
describe('<Board />', () => {

    const checkHousePitParams = (id: number, seeds: number, active: boolean, testId: string) => {
        expect(mockPit).toHaveBeenCalledWith(expect.objectContaining({
            "data-testid": testId,
            "item": {
                "id": id,
                "seeds": seeds,
                "active": active,
            }
        }));
    }
    const checkStorePitParams = (id: number, seeds: number, title: string, testId: string) => {
        expect(mockPit).toHaveBeenCalledWith(expect.objectContaining({
            "data-testid": testId,
            "item": {
                "id": id,
                "seeds": seeds,
                "active": false,
                "title": title
            }
        }));
    }

    describe('render Pits', () => {
        it('Render pits for player1', async () => {

            let item = {
                id: 1,
                player1: "Player 1",
                player2: "Player 2",
                currentTurn: "Player 1",
                winner: "",
                status: "playing",
                pits: [0, 0, 3, 4, 5, 6, 7, 0, 0, 10, 11, 12, 13, 14]
            }

            render(<Board item={item} player="Player 1" />);
            expect(mockPit.mock.calls.length).toBe(14);
            checkHousePitParams(0, 0, false, "down-pit-house-0");
            checkHousePitParams(1, 0, false, "down-pit-house-1");
            checkHousePitParams(2, 3, true, "down-pit-house-2");
            checkHousePitParams(3, 4, true, "down-pit-house-3");
            checkHousePitParams(4, 5, true, "down-pit-house-4");
            checkHousePitParams(5, 6, true, "down-pit-house-5");

            checkStorePitParams(6, 7, "Player 1", "right-pit-store");

            checkHousePitParams(7, 0, false, "up-pit-house-5");
            checkHousePitParams(8, 0, false, "up-pit-house-4");
            checkHousePitParams(9, 10, false, "up-pit-house-3");
            checkHousePitParams(10, 11, false, "up-pit-house-2");
            checkHousePitParams(11, 12, false, "up-pit-house-1");
            checkHousePitParams(12, 13, false, "up-pit-house-0");

            checkStorePitParams(13, 14, "Player 2", "left-pit-store");

        });

        it('Render pits for player2', async () => {

            let item = {
                id: 1,
                player1: "Player 1",
                player2: "Player 2",
                currentTurn: "Player 2",
                winner: "",
                status: "playing",
                pits: [0, 0, 3, 4, 5, 6, 7, 0, 0, 10, 11, 12, 13, 14]
            }

            render(<Board item={item} player="Player 2" />);
            expect(mockPit.mock.calls.length).toBe(14);

            checkHousePitParams(0, 0, false, "up-pit-house-5");
            checkHousePitParams(1, 0, false, "up-pit-house-4");
            checkHousePitParams(2, 3, false, "up-pit-house-3");
            checkHousePitParams(3, 4, false, "up-pit-house-2");
            checkHousePitParams(4, 5, false, "up-pit-house-1");
            checkHousePitParams(5, 6, false, "up-pit-house-0");

            checkStorePitParams(6, 7, "Player 1", "left-pit-store");

            checkHousePitParams(7, 0, false, "down-pit-house-0");
            checkHousePitParams(8, 0, false, "down-pit-house-1");
            checkHousePitParams(9, 10, true, "down-pit-house-2");
            checkHousePitParams(10, 11, true, "down-pit-house-3");
            checkHousePitParams(11, 12, true, "down-pit-house-4");
            checkHousePitParams(12, 13, true, "down-pit-house-5");

            checkStorePitParams(13, 14, "Player 2", "right-pit-store");

        });
        it('Render pits for Opponent player', async () => {

            let item = {
                id: 1,
                player1: "Player 1",
                player2: "Player 2",
                currentTurn: "Player 1",
                winner: "",
                status: "playing",
                pits: [0, 0, 3, 4, 5, 6, 7, 0, 0, 10, 11, 12, 13, 14]
            }

            render(<Board item={item} player="Player 2" />);
            expect(mockPit.mock.calls.length).toBe(14);

            checkHousePitParams(0, 0, false, "up-pit-house-5");
            checkHousePitParams(1, 0, false, "up-pit-house-4");
            checkHousePitParams(2, 3, false, "up-pit-house-3");
            checkHousePitParams(3, 4, false, "up-pit-house-2");
            checkHousePitParams(4, 5, false, "up-pit-house-1");
            checkHousePitParams(5, 6, false, "up-pit-house-0");

            checkStorePitParams(6, 7, "Player 1", "left-pit-store");

            checkHousePitParams(7, 0, false, "down-pit-house-0");
            checkHousePitParams(8, 0, false, "down-pit-house-1");
            checkHousePitParams(9, 10, false, "down-pit-house-2");
            checkHousePitParams(10, 11, false, "down-pit-house-3");
            checkHousePitParams(11, 12, false, "down-pit-house-4");
            checkHousePitParams(12, 13, false, "down-pit-house-5");

            checkStorePitParams(13, 14, "Player 2", "right-pit-store");

        });
    });

    describe('render Current turn', () => {

        it('Current player\'s turn', async () => {
            let item = {
                id: 1,
                player1: "Player 1",
                player2: "Player 2",
                currentTurn: "Player 1",
                winner: "",
                status: "playing",
                pits: [4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 0]
            }

            render(<Board item={item} player="Player 1" />);
            expect(screen.getByTestId('currentTurnText')).toHaveTextContent("Your Turn");
        });

        it('Opponents\'s turn', async () => {
            let item = {
                id: 1,
                player1: "Player 1",
                player2: "Player 2",
                currentTurn: "Player 1",
                winner: "",
                status: "playing",
                pits: [4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 0]
            }

            render(<Board item={item} player="Player 2" />);

            expect(screen.getByTestId('currentTurnText')).toHaveTextContent("Player 1's Turn");

        });
    });

    describe('render Game Status', () => {

        it('Render Not Started game', async () => {
            let item = {
                id: 1,
                player1: "Player 1",
                player2: "Player 2",
                currentTurn: null,
                winner: "Player 2",
                status: "NotStart",
                pits: [4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 0]
            }

            render(<Board item={item} player="Player 1" />);
            expect(screen.getByTestId('stateText')).toHaveTextContent("Wait for other player to join");
        });
        it('Render Finished game for winner', async () => {
            let item = {
                id: 1,
                player1: "Player 1",
                player2: "Player 2",
                currentTurn: null,
                winner: "Player 2",
                status: "Finished",
                pits: [4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 0]
            }

            render(<Board item={item} player="Player 2" />);
            expect(screen.getByTestId('stateText')).toHaveTextContent("You Won!");
        });

        it('Render Finished game for looser', async () => {
            let item = {
                id: 1,
                player1: "Player 1",
                player2: "Player 2",
                currentTurn: null,
                winner: "Player 2",
                status: "Finished",
                pits: [4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 0]
            }

            render(<Board item={item} player="Player 1" />);
            expect(screen.getByTestId('stateText')).toHaveTextContent("Player 2 Won!");
        });

        it('Render Draw game', async () => {
            let item = {
                id: 1,
                player1: "Player 1",
                player2: "Player 2",
                currentTurn: null,
                winner: null,
                status: "Finished",
                pits: [4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 0]
            }

            render(<Board item={item} player="Player 1" />);
            expect(screen.getByTestId('stateText')).toHaveTextContent("Draw!");
        });
    });
});