import React from 'react';
import { Link, Route, Routes } from 'react-router-dom';
import styled from 'styled-components';
import { BoardPage } from '../Board/BoardPage';
import { AvailableBoardsPage } from '../BoardTable/AvailableBoardsPage';
import { UserBoardPages } from '../BoardTable/UerBoardsPage';
import { UserContext } from '../context/UserContext';
import { ResponsiveHeader } from '../header/ResponsiveHeader';

const Wrapper = styled.div`

`;
function Main() {

    const { user, logout } = React.useContext(UserContext);

    const handleLogout = () => {
        logout();
    };
    return (
        <Wrapper>
            <ResponsiveHeader title={`Welcome ${user}`} items={[{
                title: 'My Boards',
                link: '/my-boards',
            },
            {
                title: 'Available Boards',
                link: '/available-boards',
            },
            {
                title: 'Logout',
                link: '/',
                onClick: handleLogout,
            }]}></ResponsiveHeader>

            <Routes>
                <Route path='/boards/:id' element={< BoardPage />}></Route>
                <Route path='/my-boards' element={< UserBoardPages />}></Route>
                <Route path='/available-boards' element={< AvailableBoardsPage />}></Route>
            </Routes>
        </Wrapper>
    );
}

export default Main;
