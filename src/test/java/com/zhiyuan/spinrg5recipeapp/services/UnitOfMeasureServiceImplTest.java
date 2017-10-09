package com.zhiyuan.spinrg5recipeapp.services;

import com.zhiyuan.spinrg5recipeapp.commands.UnitOfMeasureCommand;
import com.zhiyuan.spinrg5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.zhiyuan.spinrg5recipeapp.domain.UnitOfMeasure;
import com.zhiyuan.spinrg5recipeapp.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UnitOfMeasureServiceImplTest {
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
    UnitOfMeasureService unitOfMeasureService;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository,unitOfMeasureToUnitOfMeasureCommand);
    }



    @Test
    public void listAllUom() throws Exception {
        //given
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();

        UnitOfMeasure unitOfMeasure1 = new UnitOfMeasure();
        unitOfMeasure1.setId(1L);

        UnitOfMeasure unitOfMeasure2 = new UnitOfMeasure();
        unitOfMeasure2.setId(2L);

        unitOfMeasures.add(unitOfMeasure1);
        unitOfMeasures.add(unitOfMeasure2);

        when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);

        //when
        Set<UnitOfMeasureCommand> unitOfMeasureCommands = unitOfMeasureService.listAllUom();

        //then
        assertEquals(unitOfMeasureCommands.size(),unitOfMeasures.size());
        verify(unitOfMeasureRepository,times(1)).findAll();
    }

}