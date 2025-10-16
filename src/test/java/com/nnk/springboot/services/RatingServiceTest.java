package com.nnk.springboot.services;

import com.nnk.springboot.constant.Const;
import com.nnk.springboot.models.Rating;
import com.nnk.springboot.repositories.RatingRepository;
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
class RatingServiceTest {

    @Mock
    RatingRepository repository;

    @InjectMocks
    RatingService ratingService;

    @Test
    void fetchAll_DB_AccessOK() {
        //Arrange
        List<Rating> RatingList = new ArrayList<>();
        RatingList.add(Const.RATING);
        when(repository.findAll()).thenReturn(RatingList);

        //Act
        List<Rating> result = ratingService.fetchAll();

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
        List<Rating> result = ratingService.fetchAll();

        //Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();

    }

    @Test
    void createAndSave_DB_AccessOK() {
        //Arrange & Act
        ratingService.createAndSave(Const.RATING);

        //Assert
        verify(repository,times(1)).save(Const.RATING);
    }

    @Test
    void createAndSave_DB_AccessFailure() {
        //Arrange
        when(repository.save(Const.RATING)).thenThrow(Const.DATA_ACCESS_EXCEPTION);

        //Act
        ratingService.createAndSave(Const.RATING);

        //Assert
        verify(repository, times(1)).save(Const.RATING);
    }

    @Test
    void fetchById_DB_AccessOk_RatingExist() {
        //Arrange
        when(repository.findById(anyInt())).thenReturn(Optional.of(Const.RATING));

        //Act
        Optional<Rating> result = ratingService.fetchById(anyInt());

        //Assert
        assertNotNull(result);
        assertSame(Const.RATING, result.get());
    }

    @Test
    void fetchById_DB_AccessOk_RatingDontExist() {
        //Arrange
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        //Act
        Optional<Rating> result = ratingService.fetchById(anyInt());

        //Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void fetchById_DB_AccessFailure() {
        //Arrange
        when(repository.findById(anyInt())).thenThrow(Const.DATA_ACCESS_EXCEPTION);

        //Act
        Optional<Rating> result = ratingService.fetchById(anyInt());

        //Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void updateAndSave_DB_AccessOk() {
        //Arrange & Act
        ratingService.updateAndSave(Const.RATING);

        //Assert
        verify(repository, times(1)).save(Const.RATING);
    }

    @Test
    void updateAndSave_DB_AccessFailure() {
        //Arrange
        when(repository.save(Const.RATING)).thenThrow(Const.DATA_ACCESS_EXCEPTION);

        //Act
        ratingService.updateAndSave(Const.RATING);

        //Assert
        verify(repository, times(1)).save(Const.RATING);
    }

    @Test
    void existsById() {
        //Arrange & Act
        ratingService.existsById(anyInt());

        //Assert
        verify(repository, times(1)).existsById(anyInt());
    }

    @Test
    void deleteById_DB_AccessOk() {
        //Arrange & Act
        ratingService.deleteById(anyInt());

        //Assert
        verify(repository, times(1)).deleteById(anyInt());
    }

    @Test
    void deleteById_DB_AccessFailure() {
        //Arrange
        doThrow(Const.DATA_ACCESS_EXCEPTION).when(repository).deleteById(anyInt());

        //Act
        ratingService.deleteById(anyInt());

        //Assert
        verify(repository, times(1)).deleteById(anyInt());
    }
}
