package com.nnk.springboot.services;

import com.nnk.springboot.constant.Const;
import com.nnk.springboot.models.BidList;
import com.nnk.springboot.repositories.BidListRepository;
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
class BidListServiceTest {

    @Mock
    BidListRepository repository;

    @InjectMocks
    BidListService bidListService;

    @Test
    void fetchAll_DB_AccessOK() {
        //Arrange
        List<BidList> bidLists = new ArrayList<>();
        bidLists.add(Const.BIDLIST);
        when(repository.findAll()).thenReturn(bidLists);

        //Act
        List<BidList> result = bidListService.fetchAll();

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
        List<BidList> result = bidListService.fetchAll();

        //Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();

    }

    @Test
    void createAndSave_DB_AccessOK() {
        //Arrange & Act
        bidListService.createAndSave(Const.BIDLIST);

        //Assert
        verify(repository,times(1)).save(Const.BIDLIST);
    }

    @Test
    void createAndSave_DB_AccessFailure() {
        //Arrange
        when(repository.save(Const.BIDLIST)).thenThrow(Const.DATA_ACCESS_EXCEPTION);

        //Act
        bidListService.createAndSave(Const.BIDLIST);

        //Assert
        verify(repository, times(1)).save(Const.BIDLIST);
    }

    @Test
    void fetchById_DB_AccessOk_bidListExist() {
        //Arrange
        when(repository.findById(anyInt())).thenReturn(Optional.of(Const.BIDLIST));

        //Act
        Optional<BidList> result = bidListService.fetchById(anyInt());

        //Assert
        assertNotNull(result);
        assertSame(Const.BIDLIST, result.get());
    }

    @Test
    void fetchById_DB_AccessOk_bidListDontExist() {
        //Arrange
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        //Act
        Optional<BidList> result = bidListService.fetchById(anyInt());

        //Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void fetchById_DB_AccessFailure() {
        //Arrange
        when(repository.findById(anyInt())).thenThrow(Const.DATA_ACCESS_EXCEPTION);

        //Act
        Optional<BidList> result = bidListService.fetchById(anyInt());

        //Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void updateAndSave_DB_AccessOk() {
        //Arrange & Act
        bidListService.updateAndSave(Const.BIDLIST);

        //Assert
        verify(repository, times(1)).save(Const.BIDLIST);
    }

    @Test
    void updateAndSave_DB_AccessFailure() {
        //Arrange
        when(repository.save(Const.BIDLIST)).thenThrow(Const.DATA_ACCESS_EXCEPTION);

        //Act
        bidListService.updateAndSave(Const.BIDLIST);

        //Assert
        verify(repository, times(1)).save(Const.BIDLIST);
    }

    @Test
    void existsById() {
        //Arrange & Act
        bidListService.existsById(anyInt());

        //Assert
        verify(repository, times(1)).existsById(anyInt());
    }

    @Test
    void deleteById_DB_AccessOk() {
        //Arrange & Act
        bidListService.deleteById(anyInt());

        //Assert
        verify(repository, times(1)).deleteById(anyInt());
    }

    @Test
    void deleteById_DB_AccessFailure() {
        //Arrange
        doThrow(Const.DATA_ACCESS_EXCEPTION).when(repository).deleteById(anyInt());

        //Act
        bidListService.deleteById(anyInt());

        //Assert
        verify(repository, times(1)).deleteById(anyInt());
    }
}
