package com.springboot.transactional.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="authors")
public class Author {

    @Id
    @SequenceGenerator(name="authors_generator", sequenceName="authors_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authors_generator")
    private Long id;

    private String name;

    private Integer age;

}
