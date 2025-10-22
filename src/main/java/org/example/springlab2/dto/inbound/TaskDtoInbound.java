package org.example.springlab2.dto.inbound;

import java.util.Date;

public record TaskDtoInbound(
        String title,
        String description,
        boolean completed,
        Date deadline,
        int priority
) {}