package com.example.lostfound.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "items")
public class Item {

    @Id
    private String id;
    private String title;
    private ItemType type; // LOST or FOUND
    private String category;
    private String description;
    private String location;
    private String contactInfo;

    // Cloudinary Fields
    private String imageUrl;
    private String imagePublicId;

    private LocalDateTime createdAt = LocalDateTime.now();
}