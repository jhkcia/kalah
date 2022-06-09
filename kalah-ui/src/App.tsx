import React from 'react';
import { Board } from './Board/Board';

function App() {


  return (
    <>
      <Board item={
        {
          id: 1,
          player1: "Player 1",
          player2: "Player 2",
          currentTurn: "Player 2",
          winner: "",
          status: "playing",
          pits: [0, 0, 3, 4, 5, 6, 7, 0, 0, 10, 11, 12, 13, 14]
        }
      } 
      player="Player 2"
      />
    </>
  );
}

export default App;
