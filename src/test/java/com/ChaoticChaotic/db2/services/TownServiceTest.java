package com.ChaoticChaotic.db2.services;

import com.ChaoticChaotic.db2.entity.Town;
import com.ChaoticChaotic.db2.exception.IdNotFoundException;
import com.ChaoticChaotic.db2.repository.TownRepository;
import com.ChaoticChaotic.db2.services.impl.TownImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TownServiceTest {

    @Mock
    private TownRepository townRepository;
    @InjectMocks
    private TownImpl underTest;


    @Test
    void canAddTown() {
        Town test = new Town(1L, "Voronezh"
                ,1222L
        );
        underTest.addTown(test);
        ArgumentCaptor<Town> townArgumentCaptor =
                ArgumentCaptor.forClass(Town.class);
        verify(townRepository)
                .save(townArgumentCaptor.capture());
        Town capturedShipping = townArgumentCaptor.getValue();
        assertThat(capturedShipping).isEqualTo(test);
    }

    @Test
    void whenTryDeleteTownThenThrowException(){
        Long deletionId = 1L;
        given(townRepository.existsById(any()))
                .willReturn(false);
        assertThatThrownBy(()-> underTest.deleteTown(deletionId))
                .isInstanceOf(IdNotFoundException.class)
                .hasMessage("Line with id " + deletionId + " does not exists");
        verify(townRepository,never()).deleteById(any());
    }

    @Test
    void canDeleteTown() {
        Long deletionId = 1L;
        given(townRepository.existsById(any()))
                .willReturn(true);
        underTest.deleteTown(deletionId);
        verify(townRepository).deleteById(any());
    }

    @Test
    void canShowTowns() {
        underTest.showTowns();
        verify(townRepository).findAll();
    }
}