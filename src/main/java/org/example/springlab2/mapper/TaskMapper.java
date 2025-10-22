package org.example.springlab2.mapper;

import org.example.springlab2.dto.inbound.TaskDtoInbound;
import org.example.springlab2.dto.outbound.TaskDtoOutbound;
import org.example.springlab2.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDtoOutbound toDto(Task task);
    Task toEntity(TaskDtoInbound dtoInbound);
}
