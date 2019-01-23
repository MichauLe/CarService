package com.javagda17.myproject.carservice.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Checklist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String wpis;

    @CreationTimestamp
    private LocalDateTime dataWpisu;

    @ManyToMany(mappedBy = "tweetSet", fetch = FetchType.EAGER)
    private Set<Item> tagSet;
}
