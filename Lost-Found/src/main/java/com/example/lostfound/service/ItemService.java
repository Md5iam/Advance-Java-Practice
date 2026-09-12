package com.example.lostfound.service;

import com.example.lostfound.dto.ItemRequestDto;
import com.example.lostfound.model.Item;
import com.example.lostfound.model.ItemType;
import com.example.lostfound.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final CloudinaryService cloudinaryService;

    public Item createItem(ItemRequestDto dto, MultipartFile image) throws IOException {
        Item item = new Item();
        item.setTitle(dto.getTitle());
        item.setType(dto.getType());
        item.setCategory(dto.getCategory());
        item.setDescription(dto.getDescription());
        item.setLocation(dto.getLocation());
        item.setContactInfo(dto.getContactInfo());
        item.setCreatedAt(LocalDateTime.now());

        if (image != null && !image.isEmpty()) {
            Map<String, Object> uploadResult = cloudinaryService.uploadImage(image);
            item.setImageUrl(uploadResult.get("secure_url").toString());
            item.setImagePublicId(uploadResult.get("public_id").toString());
        }

        return itemRepository.save(item);
    }

    public List<Item> getItems(ItemType type) {
        if (type != null) {
            return itemRepository.findByTypeOrderByCreatedAtDesc(type);
        }
        return itemRepository.findAllByOrderByCreatedAtDesc();
    }

    public Item getItemById(String id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with ID: " + id));
    }

    public Item updateItemType(String id, ItemType newType) {
        Item item = getItemById(id);
        item.setType(newType);
        return itemRepository.save(item);
    }

    public void deleteItem(String id) {
        Item item = getItemById(id);

        // 1. Delete image from Cloudinary using stored public ID
        cloudinaryService.deleteImage(item.getImagePublicId());

        // 2. Remove document from MongoDB
        itemRepository.deleteById(id);
    }
}