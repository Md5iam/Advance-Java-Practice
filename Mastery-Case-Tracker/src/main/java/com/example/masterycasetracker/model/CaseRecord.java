package com.example.masterycasetracker.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "cases")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaseRecord {

    @Id
    private String id;

    @Indexed(unique = true)
    @NotBlank(message = "Case ID cannot be blank")
    @Field("case_id")
    private String caseId;

    @NotBlank(message = "Case title cannot be blank")
    @Size(min = 3, max = 150, message = "Case title must be between 3 and 150 characters")
    @Field("title")
    private String title;

    @NotBlank(message = "Lead detective cannot be blank")
    @Field("lead_detective")
    private String leadDetective;

    @NotNull(message = "Priority is required")
    @Field("priority")
    private Priority priority;

    @NotNull(message = "Status is required")
    @Field("status")
    private CaseStatus status;

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;
}
