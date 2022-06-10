import React from 'react';
import { Link, Route, Routes } from 'react-router-dom';
import { ResponsiveHeader } from '../header/ResponsiveHeader';
import { HomePage } from './HomePage';
import { LoginPage } from './LoginPage';
function Main() {

    return (
        <div className="App">
            <ResponsiveHeader></ResponsiveHeader>
            <ul className="App-header">
                <li>
                    <Link to="/">HOME</Link>
                </li>
                <li>
                    <Link to="/login">Login</Link>
                </li>
            </ul>
            <Routes>
                <Route path='/' element={< HomePage />}></Route>
                <Route path='/login' element={< LoginPage />}></Route>
            </Routes>
        </div>
    );
}

export default Main;
