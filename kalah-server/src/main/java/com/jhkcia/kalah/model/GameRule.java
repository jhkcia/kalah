package com.jhkcia.kalah.model;

public interface GameRule {
    void apply(Board board, SowAction sowAction);
}
