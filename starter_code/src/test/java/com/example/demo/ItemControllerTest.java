package com.example.demo;

import com.example.demo.controllers.ItemController;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
public class ItemControllerTest {
    @InjectMocks
    private ItemController itemController;

    @Mock
    private final ItemRepository items = mock(ItemRepository.class);

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getItemByIdTest(){
        when(items.findById(1L)).thenReturn(Optional.of(createItem()));

        ResponseEntity<Item> response = itemController.getItemById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK,response.getStatusCode());

        Item item = response.getBody();
        assertNotNull(item);
    }

    @Test
    public void getItemsTest(){
        ResponseEntity<List<Item>> response = itemController.getItems();

        List<Item> itemList = response.getBody();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(itemList);
    }

    @Test
    public void getItemByNameTest(){
        List<Item> items = new ArrayList<>();

        items.add(createItem());
        when(this.items.findByName("Created Item")).thenReturn(items);
        ResponseEntity<List<Item>> response = itemController.getItemsByName("Created Item");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(items, response.getBody());
    }

    public static Item createItem(){
        Item item = new Item();

        item.setId(1L);
        item.setName("Created Item");
        item.setDescription("This is a fake item.");
        item.setPrice(BigDecimal.valueOf(55.0));

        return item;
    }
}
