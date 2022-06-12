package com.jhkcia.kalah.model;

import com.jhkcia.kalah.exception.BoardIsFullException;
import com.jhkcia.kalah.exception.InvalidPitException;
import com.jhkcia.kalah.exception.InvalidSowException;
import com.jhkcia.kalah.model.rules.AdditionalMoveRule;
import com.jhkcia.kalah.model.rules.EmptyHouseLandingRule;
import com.jhkcia.kalah.model.rules.NoSeedRemainingRule;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private String player1;
    private String player2;
    private String currentTurnPlayer;
    private String winnerPlayer;
    private GameStatus status;
    @ElementCollection
    private List<Pit> pits;

    private static List<GameRule> rules;

    public static Integer PITS_COUNT = 14;

    private static final int INITIAL_STONE_COUNT = 4;
    private static final int STORE_COUNT = 6;

    private static int getFirstHouseId() {
        return STORE_COUNT;
    }

    private static int getSecondHouseId() {
        return STORE_COUNT * 2 + 1;
    }

    public Board() {

    }

    public Board(String player1) {
        this.player1 = player1;
        this.status = GameStatus.NotStart;
        initializePits();
    }

    private void initializePits() {
        this.pits = new ArrayList<>(PITS_COUNT);
        for (int i = 0; i < PITS_COUNT; i++) {
            Pit pit = new Pit(i);
            pits.add(pit);
            if (isHouse(pit)) {
                pit.setStones(INITIAL_STONE_COUNT);
            }
        }
    }

    public void join(String player) {
        if (StringUtils.hasText(this.player2)) {
            throw new BoardIsFullException(this.getId());
        }
        this.player2 = player;
        this.currentTurnPlayer = this.player1;
        this.status = GameStatus.Playing;
    }

    public Pit getPitByIndex(int index) {
        if (index < 0 || index >= PITS_COUNT) {
            throw new InvalidPitException(index);
        }
        return this.pits.get(index);
    }

    public long getId() {
        return id;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getWinnerPlayer() {
        return winnerPlayer;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCurrentTurnPlayer() {
        return currentTurnPlayer;
    }

    private int getCurrentTurnIndex() {
        if (currentTurnPlayer.equals(player1)) {
            return 0;
        } else if (currentTurnPlayer.equals(player2)) {
            return 1;
        }
        return -1;

    }

    private SowAction move(String user, Pit selectedPit, int playerIndex) {
        int nextIndex = selectedPit.getId();
        while (!selectedPit.isEmpty()) {
            nextIndex = ++nextIndex % PITS_COUNT;
            Pit nextPit = getPitByIndex(nextIndex);
            if (isStore(nextPit) && !belongsToPlayer(nextPit, playerIndex)) {
                continue;
            }
            selectedPit.removeStone();
            nextPit.addStone();
        }
        Pit lastPit = getPitByIndex(nextIndex);

        return new SowAction(user, selectedPit, lastPit, playerIndex);
    }

    private static List<GameRule> getRules() {
        if (rules == null) {
            rules = new ArrayList<>();
            rules.add(new EmptyHouseLandingRule());
            rules.add(new AdditionalMoveRule());
            rules.add(new NoSeedRemainingRule());
        }
        return rules;
    }

    public void sowSeeds(String player, int pitIndex) {
        if (status != GameStatus.Playing) {
            throw new InvalidSowException("Can not sow pit while game is not playing.");
        }
        if (!currentTurnPlayer.equals(player)) {
            throw new InvalidSowException("It is not your turn.");
        }
        Pit pit = getPitByIndex(pitIndex);
        if (isStore(pit)) {
            throw new InvalidSowException("Can not sow store pit.");
        }
        if (pit.isEmpty()) {
            throw new InvalidSowException("Can not sow empty pit.");
        }
        int playerIndex = getCurrentTurnIndex();
        if (!belongsToPlayer(pit, playerIndex)) {
            throw new InvalidSowException("You can only sow your pits.");
        }

        SowAction moveResult = move(player, pit, playerIndex);
        this.changeTurn();

        for (GameRule rule : getRules()) {
            rule.apply(this, moveResult);
        }

        checkGameFinished();
    }

    private void checkGameFinished() {
        boolean isFinished = getPlayerHouses(0).stream().allMatch(p -> p.isEmpty())
                && getPlayerHouses(1).stream().allMatch(p -> p.isEmpty());
        if (isFinished) {
            int p1Score = getStorePit(0).getStones();
            int p2Score = getStorePit(1).getStones();
            if (p1Score > p2Score) {
                this.winnerPlayer = player1;
            } else if (p1Score < p2Score) {
                this.winnerPlayer = player2;
            } else {
                this.winnerPlayer = null;
            }
            this.currentTurnPlayer = null;
            this.status = GameStatus.Finished;
        }
    }

    public int getCompetitorPlayerIndex(int playerIndex) {
        return 1 - playerIndex;
    }

    private void changeTurn() {
        if (currentTurnPlayer.equals(player1)) {
            currentTurnPlayer = player2;
        } else if (currentTurnPlayer.equals(player2)) {
            currentTurnPlayer = player1;
        }
    }

    public Pit getOppositePit(Pit pit) {
        return getPitByIndex(12 - pit.getId());
    }

    public Pit getStorePit(int playerIndex) {
        return getPitByIndex(getStoreIndex(playerIndex));
    }

    public void setCurrentTurnPlayer(String currentTurn) {
        this.currentTurnPlayer = currentTurn;
    }

    public List<Pit> getPlayerHouses(int playerIndex) {
        return pits.stream().filter(p -> belongsToPlayer(p, playerIndex) && isHouse(p)).collect(Collectors.toList());
    }

    public boolean isStore(Pit pit) {
        return pit.getId() == getFirstHouseId() || pit.getId() == getSecondHouseId();
    }

    public boolean isHouse(Pit pit) {
        return !isStore(pit);
    }

    public boolean belongsToPlayer(Pit pit, int playerIndex) {
        return (playerIndex == 0 && pit.getId() <= getFirstHouseId() && pit.getId() >= 0) ||
                (playerIndex == 1 && pit.getId() > getFirstHouseId() && pit.getId() <= getSecondHouseId());
    }

    public int getStoreIndex(int playerIndex) {
        if (playerIndex == 0) {
            return getFirstHouseId();
        } else if (playerIndex == 1) {
            return getSecondHouseId();
        }
        return -1;
    }

}
