package com.example.labfinal.document;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortalBugReport {
    @Id
    private String id;

    @NotNull(message = "Page Name is required")
    private String pageName;
    @Length(min = 3, message = "Reporter Name must be at least 3 characters")
    private String reporterName;
    private Severity severity;
    private String reproSteps;

}
