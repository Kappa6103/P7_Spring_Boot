package com.nnk.springboot.services;

import com.nnk.springboot.constant.Const;
import com.nnk.springboot.models.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RuleNameServiceTest {

    @Mock
    RuleNameRepository repository;

    @InjectMocks
    RuleNameService ruleNameService;

    @Test
    void fetchAll_DB_AccessOK() {
        //Arrange
        List<RuleName> RuleNameList = new ArrayList<>();
        RuleNameList.add(Const.RULENAME);
        when(repository.findAll()).thenReturn(RuleNameList);

        //Act
        List<RuleName> result = ruleNameService.fetchAll();

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
        List<RuleName> result = ruleNameService.fetchAll();

        //Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();

    }

    @Test
    void createAndSave_DB_AccessOK() {
        //Arrange & Act
        ruleNameService.createAndSave(Const.RULENAME);

        //Assert
        verify(repository,times(1)).save(Const.RULENAME);
    }

    @Test
    void createAndSave_DB_AccessFailure() {
        //Arrange
        when(repository.save(Const.RULENAME)).thenThrow(Const.DATA_ACCESS_EXCEPTION);

        //Act
        ruleNameService.createAndSave(Const.RULENAME);

        //Assert
        verify(repository, times(1)).save(Const.RULENAME);
    }

    @Test
    void fetchById_DB_AccessOk_RuleNameExist() {
        //Arrange
        when(repository.findById(anyInt())).thenReturn(Optional.of(Const.RULENAME));

        //Act
        Optional<RuleName> result = ruleNameService.fetchById(anyInt());

        //Assert
        assertNotNull(result);
        assertSame(Const.RULENAME, result.get());
    }

    @Test
    void fetchById_DB_AccessOk_RuleNameDontExist() {
        //Arrange
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        //Act
        Optional<RuleName> result = ruleNameService.fetchById(anyInt());

        //Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void fetchById_DB_AccessFailure() {
        //Arrange
        when(repository.findById(anyInt())).thenThrow(Const.DATA_ACCESS_EXCEPTION);

        //Act
        Optional<RuleName> result = ruleNameService.fetchById(anyInt());

        //Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void updateAndSave_DB_AccessOk() {
        //Arrange & Act
        ruleNameService.updateAndSave(Const.RULENAME);

        //Assert
        verify(repository, times(1)).save(Const.RULENAME);
    }

    @Test
    void updateAndSave_DB_AccessFailure() {
        //Arrange
        when(repository.save(Const.RULENAME)).thenThrow(Const.DATA_ACCESS_EXCEPTION);

        //Act
        ruleNameService.updateAndSave(Const.RULENAME);

        //Assert
        verify(repository, times(1)).save(Const.RULENAME);
    }

    @Test
    void existsById() {
        //Arrange & Act
        ruleNameService.existsById(anyInt());

        //Assert
        verify(repository, times(1)).existsById(anyInt());
    }

    @Test
    void deleteById_DB_AccessOk() {
        //Arrange & Act
        ruleNameService.deleteById(anyInt());

        //Assert
        verify(repository, times(1)).deleteById(anyInt());
    }

    @Test
    void deleteById_DB_AccessFailure() {
        //Arrange
        doThrow(Const.DATA_ACCESS_EXCEPTION).when(repository).deleteById(anyInt());

        //Act
        ruleNameService.deleteById(anyInt());

        //Assert
        verify(repository, times(1)).deleteById(anyInt());
    }
}
