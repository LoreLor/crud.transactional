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
@Table(name="books")
public class Book {

    @Id
    private String ibsn;

    private String title;

    @ManyToOne
    @JoinColumn(name="author_id")
    private Author author;
}
