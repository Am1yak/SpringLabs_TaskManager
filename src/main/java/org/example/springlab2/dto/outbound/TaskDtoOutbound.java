package org.example.springlab2.dto.outbound;

import java.time.LocalDate;
import java.util.UUID;

public record TaskDtoOutbound(
        UUID id,
        String title,
        String description,
        boolean completed,
        LocalDate deadline,
        int priority
){}
