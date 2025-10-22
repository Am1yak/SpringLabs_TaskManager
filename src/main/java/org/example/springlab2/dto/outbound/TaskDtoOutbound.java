package org.example.springlab2.dto.outbound;

import java.util.Date;

public record TaskDtoOutbound(
        String title,
        String description,
        boolean completed,
        Date deadline,
        int priority
){}
