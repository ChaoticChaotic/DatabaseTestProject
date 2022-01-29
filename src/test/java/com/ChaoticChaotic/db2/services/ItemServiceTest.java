package com.ChaoticChaotic.db2.services;

import com.ChaoticChaotic.db2.entity.Item;
import com.ChaoticChaotic.db2.exception.BadRequestException;
import com.ChaoticChaotic.db2.exception.IdNotFoundException;
import com.ChaoticChaotic.db2.repository.ItemRepository;
import com.ChaoticChaotic.db2.repository.ShippingRepository;
import com.ChaoticChaotic.db2.services.impl.ItemImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private ShippingRepository shippingRepository;
    @InjectMocks
    private ItemImpl underTest;

    @Test
    void canAddItem() {
        Item test = new Item(1L,"Oranges"
                ,1222L
        );
        underTest.addItem(test);
        ArgumentCaptor<Item> itemArgumentCaptor =
                ArgumentCaptor.forClass(Item.class);
        verify(itemRepository)
                .save(itemArgumentCaptor.capture());
        Item capturedShipping = itemArgumentCaptor.getValue();
        assertThat(capturedShipping).isEqualTo(test);
    }

    @Test
    void whenTryDeleteItemThenThrowExceptionLineNotExists(){
        Long deletionId = 1L;
        given(itemRepository.existsById(any()))
                .willReturn(false);
        assertThatThrownBy(()-> underTest.deleteItem(deletionId))
                .isInstanceOf(IdNotFoundException.class)
                .hasMessage("Line with id " + deletionId + " does not exists");
        verify(itemRepository,never()).deleteById(any());
    }

    @Test
    void whenTryDeleteItemThenThrowExceptionPrimaryKeyUsedAsForeignKey(){
        Long deletionId = 1L;
        given(itemRepository.existsById(any()))
                .willReturn(true);
        given(shippingRepository.existsById(any()))
                .willReturn(true);
        assertThatThrownBy(()-> underTest.deleteItem(deletionId))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Line with id " + deletionId + " is busy");
        verify(itemRepository,never()).deleteById(any());
    }

    @Test
    void canDeleteItem() {
        Long deletionId = 1L;
        given(itemRepository.existsById(any()))
                .willReturn(true);
        given(shippingRepository.existsById(any()))
                .willReturn(false);
        underTest.deleteItem(deletionId);
        verify(itemRepository).deleteById(any());
    }

    @Test
    void canShowItems() {
        underTest.showItems();
        verify(itemRepository).findAll();
    }

}