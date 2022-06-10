import styled from "styled-components";
import { IFullBoard } from './IFullBoard';
import { Pit } from './Pit';

type BoardCardProps = {
    item: IFullBoard,
    player: string;
}

const RootWrapper = styled.div`
    display: flex;
    flex-direction: column;
    padding: 10px;
    border-radius: 10px;
    `
const Message = styled.div`
    display: flex;
    justify-content: center;
`;
const BoardTable = styled.table`
    width: 100%;
    table-layout: fixed;
`;
const BoardRow = styled.tr`
`
const BoardBody = styled.tbody`
`
const BoardCell = styled.td`
`
const BoardFullCell = styled.td.attrs({
    rowSpan: 2
})`
`;
const Text = styled.h1``;

const PIT_COUNT = 6;
export function Board({ item, player }: BoardCardProps): JSX.Element {

    const getButtonPits = () => {
        let startIndex = player === item.player1 ? 0 : PIT_COUNT + 1;
        return item.pits.slice(startIndex, startIndex + PIT_COUNT).map((pit, index) => {
            return {
                id: startIndex + index,
                seeds: pit,
                active: pit > 0 && item.currentTurn === player
            }
        });
    }

    const getTopPits = () => {
        let startIndex = player === item.player1 ? PIT_COUNT + 1 : 0;
        return item.pits.slice(startIndex, startIndex + PIT_COUNT).map((pit, index) => {
            return {
                id: startIndex + index,
                seeds: pit,
                active: false
            }
        }).reverse();
    }

    const getLeftPitStore = () => {
        let index = player === item.player1 ? 2 * PIT_COUNT + 1 : PIT_COUNT;
        return {
            id: index,
            seeds: item.pits[index],
            active: false,
            title: player === item.player1 ? item.player2 : item.player1
        }
    };

    const getRightPitStore = () => {
        let index = player === item.player1 ? PIT_COUNT : 2 * PIT_COUNT + 1;
        return {
            id: index,
            seeds: item.pits[index],
            active: false,
            title: player === item.player1 ? item.player1 : item.player2
        }
    };

    return (
        <RootWrapper >
            {item.currentTurn &&
                <Message data-testid="currentTurnText" >
                    {item.currentTurn === player ? <Text>Your Turn</Text> : <Text>{item.currentTurn}'s Turn</Text>}
                </Message>
            }
            <Message data-testid="stateText" >
                {
                    item.status === 'Finished' ?
                        item.winner ? player === item.winner ?
                            <Text>You Won!</Text> :
                            <Text>{item.winner} Won!</Text>
                            : <Text>Draw!</Text>
                        : item.status === 'NotStart' ?
                            <Text>Wait for other player to join</Text> : <></>
                }
            </Message>

            <BoardTable >
                <BoardBody>
                    <BoardRow>
                        <BoardFullCell>
                            <Pit data-testid="left-pit-store" item={getLeftPitStore()} />
                        </BoardFullCell>
                        {
                            getTopPits().map((pit, index) => {
                                return (
                                    <BoardCell key={`up-pit-house-${index}`}>
                                        <Pit data-testid={`up-pit-house-${index}`}
                                            item={pit} />
                                    </BoardCell>
                                )
                            })
                        }
                        <BoardFullCell >
                            <Pit data-testid="right-pit-store" item={getRightPitStore()} />
                        </BoardFullCell>
                    </BoardRow>
                    <BoardRow>
                        {
                            getButtonPits().map((pit, index) => {
                                return (
                                    <BoardCell key={`down-pit-house-${index}`}>
                                        <Pit data-testid={`down-pit-house-${index}`}
                                            item={pit} />
                                    </BoardCell>
                                )
                            })
                        }
                    </BoardRow>
                </BoardBody>
            </BoardTable>
        </RootWrapper>
    )
} 