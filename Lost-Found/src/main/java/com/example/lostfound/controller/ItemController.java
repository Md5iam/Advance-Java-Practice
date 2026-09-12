package com.example.lostfound.controller;

import com.example.lostfound.dto.ItemRequestDto;
import com.example.lostfound.model.Item;
import com.example.lostfound.model.ItemType;
import com.example.lostfound.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
public class ItemController {

    private final ItemService itemService;
    
    // 1. Upload Product (POST multipart/form-data)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Item> createItem(
            @ModelAttribute ItemRequestDto dto,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        Item savedItem = itemService.createItem(dto, image);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    // 2. Fetch Items (GET /api/v1/items?type=LOST or ?type=FOUND)
    @GetMapping
    public ResponseEntity<List<Item>> getItems(@RequestParam(required = false) ItemType type) {
        return ResponseEntity.ok(itemService.getItems(type));
    }

    // 3. Update Type from LOST to FOUND (PATCH /api/v1/items/{id}/type?type=FOUND)
    @PatchMapping("/{id}/type")
    public ResponseEntity<Item> updateItemType(
            @PathVariable String id,
            @RequestParam ItemType type) {
        return ResponseEntity.ok(itemService.updateItemType(id, type));
    }

    // 4. Delete entire record & Cloudinary image (DELETE /api/v1/items/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable String id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}