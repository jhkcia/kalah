package com.jhkcia.kalah.model.rules;

import com.jhkcia.kalah.model.Board;
import com.jhkcia.kalah.model.GameRule;
import com.jhkcia.kalah.model.Pit;
import com.jhkcia.kalah.model.SowAction;

public class EmptyHouseLandingRule implements GameRule {

    @Override
    public void apply(Board board, SowAction sowAction) {
        if (!board.isStore(sowAction.getLastPit()) && board.belongsToPlayer(sowAction.getLastPit(), sowAction.getUserId()) && sowAction.getLastPit().getStones() == 1) {
            Pit oppositePit = board.getOppositePit(sowAction.getLastPit());
            if (!oppositePit.isEmpty()) {
                board.getStorePit(sowAction.getUserId()).addStones(oppositePit.getStones() + 1);
                oppositePit.removeAllStones();
                sowAction.getLastPit().removeAllStones();
            }
        }
    }
}
