import React from 'react';
import { Route, Routes } from 'react-router-dom';
import { ResponsiveHeader } from '../header/ResponsiveHeader';
import { HomePage } from './HomePage';
import { LoginPage } from './LoginPage';
function Main() {

    return (
        <div className="App">
            <ResponsiveHeader title='KALAH' titleLink='/' items={[{
                title: 'Login',
                link: '/login',
            }]}></ResponsiveHeader>

            <Routes>
                <Route path='/' element={< HomePage />}></Route>
                <Route path='/login' element={< LoginPage />}></Route>
            </Routes>
        </div>
    );
}

export default Main;
