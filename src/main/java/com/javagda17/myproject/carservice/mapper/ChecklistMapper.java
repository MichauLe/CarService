package com.javagda17.myproject.carservice.mapper;

import com.javagda17.myproject.carservice.model.Checklist;
import com.javagda17.myproject.carservice.model.dto.ChecklistDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ChecklistMapper {
    @Mappings(value = {
            @Mapping(target = "wpis", source = "wpisWpis"),
            @Mapping(target = "dataWpisu", source = "wpisDataWpisu"),
            @Mapping(target = "id", source = "wpisId"),
            @Mapping(target = "tagSet",source = "tagSet"),
    })
    Checklist wallDtoToWall(ChecklistDto dto);

    @Mappings(value = {
            @Mapping(source = "wpis", target = "wpisWpis"),
            @Mapping(source = "dataWpisu", target = "wpisDataWpisu"),
            @Mapping(source = "id", target = "wpisId"),
            @Mapping(source = "tagSet", target = "tagSet"),

    })
    ChecklistDto wallToWallDto(Checklist checklist);
}
