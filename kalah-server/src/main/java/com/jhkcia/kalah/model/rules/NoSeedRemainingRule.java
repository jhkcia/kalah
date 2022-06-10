package com.jhkcia.kalah.model.rules;

import com.jhkcia.kalah.model.*;

public class NoSeedRemainingRule implements GameRule {
    @Override
    public void apply(Board board, SowAction sowAction) {

        boolean userNotHaveAnyStone = board.getPlayerHouses(sowAction.getUserId()).stream().allMatch(p -> p.isEmpty());

        if (userNotHaveAnyStone) {
            int competitorPlayerIndex = board.getCompetitorPlayerIndex(sowAction.getUserId());
            for (Pit currentPit : board.getPlayerHouses(competitorPlayerIndex)) {
                if (!currentPit.isEmpty()) {
                    board.getStorePit(competitorPlayerIndex).addStones(currentPit.getStones());
                    currentPit.removeAllStones();
                }
            }
        }
    }
}
