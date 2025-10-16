package com.nnk.springboot.services;

import com.nnk.springboot.constant.Const;
import com.nnk.springboot.models.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
class CurveServiceTest {

    @Mock
    CurvePointRepository repository;

    @InjectMocks
    CurveService curveService;

    @Test
    void fetchAll_DB_AccessOK() {
        //Arrange
        List<CurvePoint> curvePointList = new ArrayList<>();
        curvePointList.add(Const.CURVEPOINT);
        when(repository.findAll()).thenReturn(curvePointList);

        //Act
        List<CurvePoint> result = curveService.fetchAll();

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
        List<CurvePoint> result = curveService.fetchAll();

        //Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();

    }

    @Test
    void createAndSave_DB_AccessOK() {
        //Arrange & Act
        curveService.createAndSave(Const.CURVEPOINT);

        //Assert
        verify(repository,times(1)).save(Const.CURVEPOINT);
    }

    @Test
    void createAndSave_DB_AccessFailure() {
        //Arrange
        when(repository.save(Const.CURVEPOINT)).thenThrow(Const.DATA_ACCESS_EXCEPTION);

        //Act
        curveService.createAndSave(Const.CURVEPOINT);

        //Assert
        verify(repository, times(1)).save(Const.CURVEPOINT);
    }

    @Test
    void fetchById_DB_AccessOk_CurvePointExist() {
        //Arrange
        when(repository.findById(anyInt())).thenReturn(Optional.of(Const.CURVEPOINT));

        //Act
        Optional<CurvePoint> result = curveService.fetchById(anyInt());

        //Assert
        assertNotNull(result);
        assertSame(Const.CURVEPOINT, result.get());
    }

    @Test
    void fetchById_DB_AccessOk_CurvePointDontExist() {
        //Arrange
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        //Act
        Optional<CurvePoint> result = curveService.fetchById(anyInt());

        //Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void fetchById_DB_AccessFailure() {
        //Arrange
        when(repository.findById(anyInt())).thenThrow(Const.DATA_ACCESS_EXCEPTION);

        //Act
        Optional<CurvePoint> result = curveService.fetchById(anyInt());

        //Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void updateAndSave_DB_AccessOk() {
        //Arrange & Act
        curveService.updateAndSave(Const.CURVEPOINT);

        //Assert
        verify(repository, times(1)).save(Const.CURVEPOINT);
    }

    @Test
    void updateAndSave_DB_AccessFailure() {
        //Arrange
        when(repository.save(Const.CURVEPOINT)).thenThrow(Const.DATA_ACCESS_EXCEPTION);

        //Act
        curveService.updateAndSave(Const.CURVEPOINT);

        //Assert
        verify(repository, times(1)).save(Const.CURVEPOINT);
    }

    @Test
    void existsById() {
        //Arrange & Act
        curveService.existsById(anyInt());

        //Assert
        verify(repository, times(1)).existsById(anyInt());
    }

    @Test
    void deleteById_DB_AccessOk() {
        //Arrange & Act
        curveService.deleteById(anyInt());

        //Assert
        verify(repository, times(1)).deleteById(anyInt());
    }

    @Test
    void deleteById_DB_AccessFailure() {
        //Arrange
        doThrow(Const.DATA_ACCESS_EXCEPTION).when(repository).deleteById(anyInt());

        //Act
        curveService.deleteById(anyInt());

        //Assert
        verify(repository, times(1)).deleteById(anyInt());
    }
}
