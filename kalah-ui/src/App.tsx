import React, { Fragment, lazy, Suspense } from 'react';
import { BrowserRouter, Router } from 'react-router-dom';
import './App.css';
import { UserContext } from './context/UserContext';
function App() {

  const LoggedInComponent = lazy(() => import("./logged_in/Main"));
  const LoggedOutComponent = lazy(() => import("./logged_out/Main"));
  const { user } = React.useContext(UserContext);

  return (
    <div className="App">
      <BrowserRouter>
        <Suspense fallback={<Fragment />}>
          {user ? <LoggedInComponent /> : <LoggedOutComponent />}
        </Suspense>
      </BrowserRouter>

    </div>
  );
}

export default App;
