import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import { UserContext } from "../context/UserContext";

const Wrapper = styled.section`
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    width: 100%;
`;

const Form = styled.form`
    width: 100%;
    max-width: 414px;
    padding: 1.3rem;
    display: flex;
    flex-direction: column;
`;
const Input = styled.input`
    max-width: 100%;
    padding: 11px 13px;
    margin-bottom: 0.9rem;
    border-radius: 4px;
    outline: 0;
    border: 1px solid rgba(10, 10, 10, 0.7);
    font-size: 20px;
`;

const Button = styled.button`
    max-width: 100%;
    padding: 11px 13px;
    background-color: #91bded;
    font-size: 20px;    
    border: none;
    border-radius: 3px;
    cursor: pointer;
`;
const Title = styled.h2`
    font-weight: normal;
    text-align: center;
    margin-bottom: 1rem;
`;


export function LoginPage(): JSX.Element {
    const [username, setUsername] = useState("");

    const { login } = React.useContext(UserContext);

    const navigate = useNavigate();

    const handleSubmit = (e: any) => {
        e.preventDefault();
        login(username);
        navigate("/my-boards");
    };

    const handleUsernameChange = (e: any) => {
        setUsername(e.target.value);
    };

    return (
        <>
            <Wrapper>
                <Form onSubmit={handleSubmit}>
                    <Title>Login</Title>
                    <Input
                        data-testid="usernameInput"
                        name="username"
                        value={username}
                        onChange={handleUsernameChange}
                    />
                    <Button  data-testid="usernameButton" disabled={username === ''}>Login</Button>
                </Form>
            </Wrapper>
        </>
    );
} 