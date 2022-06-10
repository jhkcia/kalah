import { IPit } from './IPit';
import styled from "styled-components"

type PitProps = {
    item: IPit,
    onSelect?: (item: IPit) => void
}

type WrapperProps = {
    active?: boolean;
}

const PlayerTitle = styled.h2`
  font-size: 1.5em;
  text-align: center;
  color: black;
`;

const Title = styled.h1`
  font-size: 2.5em;
  text-align: center;
  color: #3f1502;
`;

const Wrapper = styled.a<WrapperProps>`
    padding: 10px;
    margin: 10px;
    height: 200px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    border-radius: 50px;
    box-shadow: inset 0px 0px 21px 19px rgba(0,0,0,0.3);
    border: 2px transparent;

    ${({ active }) => active && `
        cursor: pointer;
        border: 2px solid #3f1502;
        box-shadow: inset 0px 0px 21px 21px rgba(0.8,0.8,0.8,0.5) ;
        :hover {
            box-shadow: inset 0px 0px 21px 30px rgba(0,0,0,0.6) !important;
        }`
    }
    transition: box-shadow 0.5s ease-in-out;
`;

export function Pit({ item, onSelect }: PitProps): JSX.Element {

    return (
        <Wrapper onClick={() => onSelect && onSelect(item)} active={item.active}>
            {
                item.title && <PlayerTitle data-testid="pitCount">
                    {item.title}
                </PlayerTitle>
            }
            <Title data-testid="pitCount">
                {item.seeds}
            </Title>
        </Wrapper>
    )
} 