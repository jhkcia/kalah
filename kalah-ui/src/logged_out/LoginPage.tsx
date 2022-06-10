import React, { useState } from "react";
import styled from "styled-components";
import { UserContext } from "../context/UserContext";
import { UserContextType } from "../context/UserContextType";

const Wrapper = styled.section`
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    width: 100%;
`;

const Form = styled.form`
    margin: 0 auto;
    width: 100%;
    max-width: 414px;
    padding: 1.3rem;
    display: flex;
    flex-direction: column;
    position: relative;
`;
const Input = styled.input`
    max-width: 100%;
    padding: 11px 13px;
    background: #f9f9fa;
    color: #f03d4e;
    margin-bottom: 0.9rem;
    border-radius: 4px;
    outline: 0;
    border: 1px solid rgba(245, 245, 245, 0.7);
    font-size: 14px;
`;

const Button = styled.button`
    max-width: 100%;
    padding: 11px 13px;
    color: rgb(253, 249, 243);
    font-weight: 600;
    background: #f03d4e;
    border: none;
    border-radius: 3px;
    outline: 0;
    cursor: pointer;
    margin-top: 0.6rem;
`;
const Title = styled.h2`
    font-weight: normal;
    color: #2a2a29;
    text-shadow: 0 1px 3px rgba(0, 0, 0, 0.1), 0 1px 2px rgba(0, 0, 0, 0.1);
    text-align: center;
`;


export function LoginPage(): JSX.Element {
    const [username, setUsername] = useState("");

    const { login } = React.useContext(UserContext);


    const handleSubmit = (e: any) => {
        e.preventDefault();
        login(username);
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
                        name="username"
                        value={username}
                        onChange={handleUsernameChange}
                    />
                    <Button>Login</Button>
                </Form>
            </Wrapper>
        </>
    );
} 