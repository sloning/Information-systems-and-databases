package com.isbd.dao;

import com.isbd.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerDaoTest {
    @Mock
    private JdbcTemplate jdbcTemplate;
    private PlayerDao underTest;

    @BeforeEach
    void setUp() {
        underTest = new PlayerDao(jdbcTemplate);
    }

    @Test
    void shouldReturnByUsernameWhenGotListWithMultipleValues() {
        List<Player> players = List.of(
                new Player(1, "username", "password", 0),
                new Player(2, "username", "password", 0)
        );
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<Player>>any(), anyString()))
                .thenReturn(players);

        Optional<Player> value = underTest.getByUsername("username");

        assertThat(value).isPresent();
        assertThat(value.get()).isEqualTo(players.get(0));
    }

    @Test
    void shouldReturnByUsernameWhenGotEmptyList() {
        List<Player> players = new ArrayList<>();
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<Player>>any(), anyString()))
                .thenReturn(players);

        Optional<Player> value = underTest.getByUsername("username");

        assertThat(value).isEmpty();
    }
}