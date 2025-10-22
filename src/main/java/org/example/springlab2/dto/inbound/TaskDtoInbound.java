package org.example.springlab2.dto.inbound;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

public record TaskDtoInbound(

        @NotBlank(message = "Title cannot be empty")
        @Size(max = 50)
        String title,

        @NotBlank(message = "Description cannot be empty")
        String description,

        @NotNull(message = "Date cannot be unselected")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate deadline,

        @Min(1) @Max(10)
        int priority
) {}