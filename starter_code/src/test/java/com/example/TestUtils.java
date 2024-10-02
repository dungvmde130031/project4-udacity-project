package com.example;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {
    public static void injectObject(Object target, String fieldName, Object toInject){
        boolean wasPrivate = false;
        try {
            Field f = target.getClass().getDeclaredField(fieldName);
            if(!f.isAccessible()){
                f.setAccessible(true);
                wasPrivate = true;
            }
            f.set(target, toInject);
            if(wasPrivate){
                f.setAccessible(false);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static User CreateUser(){
        User user = new User();
        user.setId(1L);
        user.setUsername("trung");
        user.setPassword("testPassword");
        user.setCart(CreateCart(user));
        return user;
    }
    public static Cart CreateCart(User user){
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setId(1L);
        List<Item> items = CreateItems();
        cart.setItems(CreateItems());
        cart.setTotal(items.stream().map(Item::getPrice).reduce(BigDecimal::add).get());
        return cart;
    }

    public static List<Item> CreateItems() {
        List<Item> items = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            items.add(CreateItem(i));
        }
        return items;
    }

    public static Item CreateItem(long id){
        Item item = new Item();
        item.setId(id);
        item.setPrice(BigDecimal.valueOf(id * 5));
        item.setName("Item" + item.getId());
        item.setDescription("Here is Description");
        return item;
    }

    public static List<UserOrder> CreateOrders(){
        List<UserOrder> orders = new ArrayList<>();
        for(int i = 0; i <=2; i++){
            UserOrder userOrder = new UserOrder();
            Cart cart = CreateCart(CreateUser());
            userOrder.setUser(CreateUser());
            userOrder.setId(Long.valueOf(i));
            userOrder.setItems(cart.getItems());
            userOrder.setTotal(cart.getTotal());
            orders.add(userOrder);
        }
        return orders;
    }
}
