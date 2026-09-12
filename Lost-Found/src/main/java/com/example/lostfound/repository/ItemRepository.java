package com.example.lostfound.repository;

import com.example.lostfound.model.Item;
import com.example.lostfound.model.ItemType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
    List<Item> findByTypeOrderByCreatedAtDesc(ItemType type);
    List<Item> findAllByOrderByCreatedAtDesc();
}