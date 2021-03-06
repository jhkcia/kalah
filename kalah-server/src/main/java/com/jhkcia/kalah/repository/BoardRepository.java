package com.jhkcia.kalah.repository;

import com.jhkcia.kalah.model.Board;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @EntityGraph(attributePaths = {"pits"})
    Optional<Board> findById(Long id);

    List<Board> findAllByPlayer1OrPlayer2(String player2Username, String player1Username);

    List<Board> findAllByPlayer2IsNullAndPlayer1Not(String username);
}
