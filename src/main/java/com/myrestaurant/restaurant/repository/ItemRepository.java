package com.myrestaurant.restaurant.repository;

import com.myrestaurant.restaurant.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository  extends JpaRepository<Item, Long> {

    List<Item> findByItemId(String itemId);
}
