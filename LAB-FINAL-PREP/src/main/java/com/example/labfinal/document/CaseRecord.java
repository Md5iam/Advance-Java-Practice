package com.example.labfinal.document;

import com.example.labfinal.model.CaseStatus;
import com.example.labfinal.model.Priority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
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
    @Field("case_id")
    private String caseId;

    @Field("title")
    private String title;

    @Field("lead_detective")
    private String leadDetective;

    @Field("priority")
    private Priority priority;

    @Field("status")
    private CaseStatus status;

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;

    @Version
    private Long version;
}
