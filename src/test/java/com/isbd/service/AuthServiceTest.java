package com.isbd.service;

import com.isbd.dao.PlayerDao;
import com.isbd.dto.LoginDto;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.exception.EntityNotSavedException;
import com.isbd.model.Player;
import com.isbd.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private PlayerDao playerDao;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AuthService underTest;

    @BeforeEach
    void setUp() {
        underTest = new AuthService(playerDao, jwtTokenProvider, bCryptPasswordEncoder);
    }

    @Test
    public void shouldSaveNewPlayer() {
        Player player = new Player(0, "username", "password", 0);
        when(playerDao.save(any())).thenReturn(1);

        underTest.save(player);

        verify(playerDao).save(player);
    }

    @Test
    public void shouldThrowExceptionWhenCanNotSaveNewPlayer() {
        Player player = new Player(0, "username", "password", 0);
        when(playerDao.save(any())).thenReturn(0);

        assertThatThrownBy(() -> underTest.save(player)).isInstanceOf(EntityNotSavedException.class);
    }

    @Test
    public void shouldLoginPlayer() {
        LoginDto loginDto = new LoginDto("username", "password");
        when(playerDao.getByUsername(eq(loginDto.getUsername()))).thenReturn(Optional.of(new Player(0, loginDto.getUsername(),
                loginDto.getPassword(), 0)));
        when(bCryptPasswordEncoder.matches(anyString(), anyString())).thenReturn(true);

        underTest.login(loginDto);

        verify(playerDao).getByUsername(loginDto.getUsername());
        verify(bCryptPasswordEncoder).matches(loginDto.getPassword(), loginDto.getPassword());
    }

    @Test
    public void shouldThrowExceptionWhenGotWrongUsername() {
        LoginDto loginDto = new LoginDto("username", "password");
        when(playerDao.getByUsername(eq(loginDto.getUsername()))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.login(loginDto)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void shouldThrowExceptionWhenGotWrongPassword() {
        LoginDto loginDto = new LoginDto("username", "password");
        when(playerDao.getByUsername(eq(loginDto.getUsername()))).thenReturn(Optional.of(new Player(0, loginDto.getUsername(),
                "anotherPassword", 0)));
        when(bCryptPasswordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThatThrownBy(() -> underTest.login(loginDto)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void shouldRegisterPlayer() {
        LoginDto loginDto = new LoginDto("username", "password");
        Player player = new Player(0, loginDto.getUsername(), loginDto.getPassword(), 0);
        when(playerDao.getByUsername(eq(loginDto.getUsername())))
                .thenReturn(Optional.empty()).thenReturn(Optional.of(player));
        when(playerDao.save(any())).thenReturn(1);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(loginDto.getPassword());

        underTest.register(loginDto);

        verify(playerDao, times(2)).getByUsername(loginDto.getUsername());
        verify(playerDao).save(player);
    }
}