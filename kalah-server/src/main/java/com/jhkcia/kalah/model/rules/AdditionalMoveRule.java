package com.jhkcia.kalah.model.rules;

import com.jhkcia.kalah.model.Board;
import com.jhkcia.kalah.model.GameRule;
import com.jhkcia.kalah.model.SowAction;

public class AdditionalMoveRule implements GameRule {
    @Override
    public void apply(Board board, SowAction sowAction) {
        if (board.isStore(sowAction.getLastPit()) && board.belongsToPlayer(sowAction.getLastPit(), sowAction.getUserId())) {
            board.setCurrentTurn(sowAction.getUser());
        }
    }
}
