package com.example.lostfound.dto;

import com.example.lostfound.model.ItemType;
import lombok.Data;

@Data
public class ItemRequestDto {
    private String title;
    private ItemType type;
    private String category;
    private String description;
    private String location;
    private String contactInfo;
}