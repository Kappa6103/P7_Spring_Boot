package com.nnk.springboot.services;

import com.nnk.springboot.constant.Const;
import com.nnk.springboot.models.Trade;
import com.nnk.springboot.repositories.TradeRepository;
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
class TradeServiceTest {

    @Mock
    TradeRepository repository;

    @InjectMocks
    TradeService tradeService;

    @Test
    void fetchAll_DB_AccessOK() {
        //Arrange
        List<Trade> TradeList = new ArrayList<>();
        TradeList.add(Const.TRADE);
        when(repository.findAll()).thenReturn(TradeList);

        //Act
        List<Trade> result = tradeService.fetchAll();

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
        List<Trade> result = tradeService.fetchAll();

        //Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();

    }

    @Test
    void createAndSave_DB_AccessOK() {
        //Arrange & Act
        tradeService.createAndSave(Const.TRADE);

        //Assert
        verify(repository,times(1)).save(Const.TRADE);
    }

    @Test
    void createAndSave_DB_AccessFailure() {
        //Arrange
        when(repository.save(Const.TRADE)).thenThrow(Const.DATA_ACCESS_EXCEPTION);

        //Act
        tradeService.createAndSave(Const.TRADE);

        //Assert
        verify(repository, times(1)).save(Const.TRADE);
    }

    @Test
    void fetchById_DB_AccessOk_TradeExist() {
        //Arrange
        when(repository.findById(anyInt())).thenReturn(Optional.of(Const.TRADE));

        //Act
        Optional<Trade> result = tradeService.fetchById(anyInt());

        //Assert
        assertNotNull(result);
        assertSame(Const.TRADE, result.get());
    }

    @Test
    void fetchById_DB_AccessOk_TradeDontExist() {
        //Arrange
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        //Act
        Optional<Trade> result = tradeService.fetchById(anyInt());

        //Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void fetchById_DB_AccessFailure() {
        //Arrange
        when(repository.findById(anyInt())).thenThrow(Const.DATA_ACCESS_EXCEPTION);

        //Act
        Optional<Trade> result = tradeService.fetchById(anyInt());

        //Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void updateAndSave_DB_AccessOk() {
        //Arrange & Act
        tradeService.updateAndSave(Const.TRADE);

        //Assert
        verify(repository, times(1)).save(Const.TRADE);
    }

    @Test
    void updateAndSave_DB_AccessFailure() {
        //Arrange
        when(repository.save(Const.TRADE)).thenThrow(Const.DATA_ACCESS_EXCEPTION);

        //Act
        tradeService.updateAndSave(Const.TRADE);

        //Assert
        verify(repository, times(1)).save(Const.TRADE);
    }

    @Test
    void existsById() {
        //Arrange & Act
        tradeService.existsById(anyInt());

        //Assert
        verify(repository, times(1)).existsById(anyInt());
    }

    @Test
    void deleteById_DB_AccessOk() {
        //Arrange & Act
        tradeService.deleteById(anyInt());

        //Assert
        verify(repository, times(1)).deleteById(anyInt());
    }

    @Test
    void deleteById_DB_AccessFailure() {
        //Arrange
        doThrow(Const.DATA_ACCESS_EXCEPTION).when(repository).deleteById(anyInt());

        //Act
        tradeService.deleteById(anyInt());

        //Assert
        verify(repository, times(1)).deleteById(anyInt());
    }
}
