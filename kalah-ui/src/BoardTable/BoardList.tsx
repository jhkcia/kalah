import styled from "styled-components";
import { IBoard } from './IBoard';

type BoardListProps = {
    items: IBoard[],
    onSelect?: (item: IBoard) => void,
    columns: string[];
}

const StyledTable = styled.table`
    border: none;
    border-collapse: collapse;
    td {
        padding: 5px 10px;
    }
    
    tbody tr {
        :nth-of-type(odd) {
            background-color: #efefef;
        }
        :hover {
            background-color: #91bded;
        }
    }
    thead > tr {
        background-color: #c2c2c2;
    }
    caption {
        font-size: 0.9em;
        padding: 5px;
        font-weight: bold;
    }

    th {
        padding: 15px; 
    }
    td {
        padding: 15px;
    }
`;
const StyledHead = styled.thead``;
const StyledRow = styled.tr``;
const StyledData = styled.td``;
const StyledHeader = styled.th``;
const StyledBody = styled.tbody``;
export function BoardList({ items, onSelect, columns }: BoardListProps): JSX.Element {

    return (
        <StyledTable>
            <StyledHead>
                <StyledRow>
                    {
                        columns.map((column, index) => {
                            return (<StyledHeader key={index}>{column}</StyledHeader>)
                        })
                    }
                </StyledRow>
            </StyledHead>

            <StyledBody>
                {
                    items.map((item) => {
                        return (<StyledRow key={item.id} onClick = {() => onSelect && onSelect(item)}> 
                            {
                                columns.map((column, index) => {
                                    return (<StyledData key={index}>
                                        {(item as any)[column]}
                                    </StyledData>);
                                })
                            }
                        </StyledRow>)
                    })
                }
            </StyledBody>

        </StyledTable>
    );
} 