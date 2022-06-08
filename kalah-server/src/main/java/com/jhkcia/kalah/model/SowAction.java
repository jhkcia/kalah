package com.jhkcia.kalah.model;

public class SowAction {
    private String user;
    private Pit selectedPit;
    private Pit lastPit;
    private int userId;

    public SowAction(String user, Pit selectedPit, Pit lastPit, int userId) {
        this.user = user;
        this.selectedPit = selectedPit;
        this.lastPit = lastPit;
        this.userId = userId;
    }

    public Pit getSelectedPit() {
        return selectedPit;
    }

    public void setSelectedPit(Pit selectedPit) {
        this.selectedPit = selectedPit;
    }

    public Pit getLastPit() {
        return lastPit;
    }

    public void setLastPit(Pit lastPit) {
        this.lastPit = lastPit;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
