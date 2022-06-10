import styled from "styled-components";

const Wrapper = styled.section`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    height: 100%;
    width: 100%;
`;

const Header = styled.h1`
    font-size: 7vw;
    padding: 50px;
`;
const Summary = styled.p`
    padding: 50px;
    font-size: 3vw;
    text-align: center;
`;

const Link = styled.a`
    font-size: 1rem;
`;
export function HomePage(): JSX.Element {

    return (
        <Wrapper>
            <Header>
                Online Kalah Game
            </Header>
            <Summary>
                Kalaha or Mancala is a game in the mancala family invented in the United States by William Julius Champion, Jr. in 1940. This game is sometimes also called "Kalahari", possibly by false etymology from the Kalahari desert in Namibia.
            </Summary>
            <Link href="https://en.wikipedia.org/wiki/Kalah">
                Find the rules of the game on Wikipedia
            </Link>
            <Link href="https://github.com/jhkcia/kalah">
                Source Code
            </Link>
        </Wrapper>
    );
} 