import React from 'react';
import { BrowserRouter as Router, Link, Route, Routes } from 'react-router-dom';
import { BoardPage } from './Board/BoardPage';
import { AvailableBoardsPage } from './BoardTable/AvailableBoardsPage';
import { UserBoardPages } from './BoardTable/UerBoardsPage';
import './App.css'
import styled from 'styled-components';
function App() {

  const UserText = styled.div`
  color: #ffffff;
  `;

  return (
    <Router>
      <div className="App">

        <UserText>
          You are logged in as Jalal
        </UserText>
        <ul className="App-header">
          <li>
            <Link to="/">Login</Link>
          </li>
          <li>
            <Link to="/my-boards">My Boards</Link>
          </li>
          <li>
            <Link to="/available-boards">Join A board</Link>
          </li>
          <li>
            <Link to="/">Logout</Link>
          </li>
        </ul>
        <Routes>
          <Route path='/boards/:id' element={< BoardPage />}></Route>
          <Route path='/my-boards' element={< UserBoardPages />}></Route>
          <Route path='/available-boards' element={< AvailableBoardsPage />}></Route>
        </Routes>
      </div>
    </Router>
  );
}

export default App;
