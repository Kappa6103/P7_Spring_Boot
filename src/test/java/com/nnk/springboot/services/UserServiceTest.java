package com.nnk.springboot.services;

import com.nnk.springboot.constant.Const;
import com.nnk.springboot.models.User;
import com.nnk.springboot.models.config.Role;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserRepository repository;

    @InjectMocks
    UserService userService;
    
    User user;
    
    @BeforeEach
    void init() {
        user = new User();
        user.setPassword(Const.PWD);
    }

    @Test
    void fetchAll_DB_AccessOK() {
        //Arrange
        List<User> UserList = new ArrayList<>();
        UserList.add(user);
        when(repository.findAll()).thenReturn(UserList);

        //Act
        List<User> result = userService.fetchAll();

        //Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void fetchAll_DB_AccessFailure() {
        //Arrange
        when(repository.findAll()).thenThrow(Const.DATA_ACCESS_EXCEPTION);

        //Act
        List<User> result = userService.fetchAll();

        //Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();

    }

    @Test
    void createAndSave_DB_AccessOK() {
        //Arrange & Act
        userService.createAndSave(user);

        //Assert
        verify(repository,times(1)).save(user);
        verify(passwordEncoder, times(1)).encode(anyString());
    }

    @Test
    void createAndSave_DB_AccessFailure() {
        //Arrange
        when(repository.save(user)).thenThrow(Const.DATA_ACCESS_EXCEPTION);

        //Act
        userService.createAndSave(user);

        //Assert
        verify(repository, times(1)).save(user);
        verify(passwordEncoder, times(1)).encode(anyString());
    }

    @Test
    void fetchById_DB_AccessOk_UserExist() {
        //Arrange
        when(repository.findById(anyInt())).thenReturn(Optional.of(user));

        //Act
        Optional<User> result = userService.fetchById(anyInt());

        //Assert
        assertNotNull(result);
        assertSame(user, result.get());
    }

    @Test
    void fetchById_DB_AccessOk_UserDontExist() {
        //Arrange
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        //Act
        Optional<User> result = userService.fetchById(anyInt());

        //Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void fetchById_DB_AccessFailure() {
        //Arrange
        when(repository.findById(anyInt())).thenThrow(Const.DATA_ACCESS_EXCEPTION);

        //Act
        Optional<User> result = userService.fetchById(anyInt());

        //Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void updateAndSave_DB_AccessOk() {
        //Arrange & Act
        userService.updateAndSave(user);

        //Assert
        verify(repository, times(1)).save(user);
        verify(passwordEncoder, times(1)).encode(anyString());
    }

    @Test
    void updateAndSave_DB_AccessFailure() {
        //Arrange
        when(repository.save(user)).thenThrow(Const.DATA_ACCESS_EXCEPTION);

        //Act
        userService.updateAndSave(user);

        //Assert
        verify(repository, times(1)).save(user);
        verify(passwordEncoder, times(1)).encode(anyString());
    }

    @Test
    void existsById() {
        //Arrange & Act
        userService.existsById(anyInt());

        //Assert
        verify(repository, times(1)).existsById(anyInt());
    }

    @Test
    void deleteById_DB_AccessOk() {
        //Arrange & Act
        userService.deleteById(anyInt());

        //Assert
        verify(repository, times(1)).deleteById(anyInt());
    }

    @Test
    void deleteById_DB_AccessFailure() {
        //Arrange
        doThrow(Const.DATA_ACCESS_EXCEPTION).when(repository).deleteById(anyInt());

        //Act
        userService.deleteById(anyInt());

        //Assert
        verify(repository, times(1)).deleteById(anyInt());
    }

    @Test
    void loadUserByUsername_passedArgIsNull() {
        //Act & Assert
        assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername(null));
    }

    @Test
    void loadUserByUsername_passedArgIsBlank() {
        //Act & Assert
        assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("  \n"));
    }

    @Test
    void loadUserByUserName_DB_accessFailure_orUsernameNotFound() {
        //Arrange
        when(repository.findByUsername(anyString())).thenThrow(Const.DATA_ACCESS_EXCEPTION);

        //Act & Assert
        assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername(Const.USERNAME));
    }

    @Test
    void loadUserByUserName_allGood() {
        //Arrange
        user.setUsername(Const.USERNAME);
        user.setPassword(Const.PWD);
        user.setRole(Role.valueOf(Const.ROLE_USER));
        when(repository.findByUsername(anyString())).thenReturn(Optional.of(user));

        //Act
        UserDetails result = userService.loadUserByUsername(Const.USERNAME);

        //Assert
        assertNotNull(result);
        assertEquals(Const.USERNAME, result.getUsername());
        assertEquals(Const.PWD, result.getPassword());
        assertEquals("[ROLE_USER]", result.getAuthorities().toString());

    }
}
