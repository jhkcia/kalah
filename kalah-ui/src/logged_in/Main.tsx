import React from 'react';
import { Link, Route, Routes } from 'react-router-dom';
import styled from 'styled-components';
import { BoardPage } from '../Board/BoardPage';
import { AvailableBoardsPage } from '../BoardTable/AvailableBoardsPage';
import { UserBoardPages } from '../BoardTable/UerBoardsPage';
import { UserContext } from '../context/UserContext';

const UserText = styled.div`
color: #ffffff;
`;
const Wrapper = styled.div`

`;
function Main() {

    const { user, logout } = React.useContext(UserContext);

    const handleLogout = () => {
        logout();
    };
    return (
        <Wrapper>
            <UserText>
                Welcome {user}
            </UserText>
            <ul className="App-header">
                <li>
                    <Link to="/my-boards">My Boards</Link>
                </li>
                <li>
                    <Link to="/available-boards">Join A board</Link>
                </li>
                <li>
                    <Link to="/" onClick={handleLogout}>Logout</Link>
                </li>
            </ul>
            <Routes>
                <Route path='/boards/:id' element={< BoardPage />}></Route>
                <Route path='/my-boards' element={< UserBoardPages />}></Route>
                <Route path='/available-boards' element={< AvailableBoardsPage />}></Route>
            </Routes>
        </Wrapper>
    );
}

export default Main;
