import styled from 'styled-components';

const Text = styled.h1`
`;
const Button = styled.button`
    max-width: 100%;
    padding: 11px 13px;
    background-color: #91bded;
    font-size: 20px;    
    border: none;
    border-radius: 3px;
    cursor: pointer;
    margin-bottom: 1rem;
    height: 50px;
`;
const HeaderContainer = styled.div`
    display: flex;
    flex-direction: row;
    width: 100%;
    max-width: 800px;
    justify-content: space-between;
    padding: 10px;
`;
type IBoardListActionProps = {
    title: string;
    onClick: () => void;
}

type IBoardListHeaderProps = {
    title: string;
    actions?: IBoardListActionProps[];
}

export function BoardListHeader({ title, actions }: IBoardListHeaderProps): JSX.Element {
    return (
        <HeaderContainer>
            <Text>
                {title}
            </Text>
            {
                actions && actions.map(action => {
                    return (<Button onClick={action.onClick}>{action.title} </Button>);
                })
            }
        </HeaderContainer>
    );
} 