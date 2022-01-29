package com.ChaoticChaotic.db2.services;


import com.ChaoticChaotic.db2.entity.Item;
import com.ChaoticChaotic.db2.entity.Shipping;
import com.ChaoticChaotic.db2.entity.Town;
import com.ChaoticChaotic.db2.exception.BadRequestException;
import com.ChaoticChaotic.db2.exception.IdNotFoundException;
import com.ChaoticChaotic.db2.repository.ShippingRepository;
import com.ChaoticChaotic.db2.services.impl.ShippingImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class ShippingServiceTest {

    @Mock
    private ShippingRepository shippingRepository;
    @InjectMocks
    private ShippingImpl underTest;


    @Test
    void canAddShipping() {

        Shipping test = new Shipping(1L, LocalDate.of(2021,1,24)
                                ,LocalDate.of(2021,1,26)
                                , new Town(1L, "Voronezh",1222L)
                                , new Item(1L,"Oranges",1222L)
                        );
        underTest.addShipping(test);
        ArgumentCaptor<Shipping> shippingArgumentCaptor =
                ArgumentCaptor.forClass(Shipping.class);
        verify(shippingRepository)
                .save(shippingArgumentCaptor.capture());
        Shipping capturedShipping = shippingArgumentCaptor.getValue();
        assertThat(capturedShipping).isEqualTo(test);
    }

    @Test
    void whenEndDateIsBeforeStartThrowException() {
        Shipping test = new Shipping(1L, LocalDate.of(2021,1,24)
                ,LocalDate.of(2021,1,26)
                , new Town(1L, "Voronezh",1222L)
                , new Item(1L,"Oranges",1222L)
        );
        assertThatThrownBy(()-> underTest.addShipping(test))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Incorrect shipping dates!");
        verify(shippingRepository,never()).save(any());
    }

    @Test
    void whenTryDeleteItemThenThrowExceptionLineNotExists(){
        Long deletionId = 1L;
        given(shippingRepository.existsById(any()))
                .willReturn(false);
        assertThatThrownBy(()-> underTest.deleteShipping(deletionId))
                .isInstanceOf(IdNotFoundException.class)
                .hasMessage("Line with id " + deletionId + " does not exists");
        verify(shippingRepository,never()).deleteById(any());
    }

    @Test
    void canDeleteShipping() {
        Long deletionId = 1L;
        given(shippingRepository.existsById(any()))
                .willReturn(true);
        underTest.deleteShipping(deletionId);
        verify(shippingRepository).deleteById(any());
    }

    @Test
    void canShowShipping() {
        underTest.showShipping();
        verify(shippingRepository).findAll();
    }
}