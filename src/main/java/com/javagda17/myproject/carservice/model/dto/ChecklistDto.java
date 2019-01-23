package com.javagda17.myproject.carservice.model.dto;

import com.javagda17.myproject.carservice.model.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChecklistDto {
    private Long wpisId;
    private String wpisWpis;
    private Set<Item> tagSet;

    @CreationTimestamp
    private LocalDateTime wpisDataWpisu;
}
