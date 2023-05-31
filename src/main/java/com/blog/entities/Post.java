package com.blog.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data                // @Data from lombok automatically Getters & Setters made available/it reduces boiler-plate code
@AllArgsConstructor // Automatically creates constructors with Arguments
@NoArgsConstructor // Automatically creates constructors without Arguments
@Entity            // Defines that a class can be mapped to table
@Table(            // Denotes table in DB with which Entity is mapped
        name="posts" ,        // name attribute "posts"
        uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})} // To make the Title UNIQUE to avoid to blogs having the same title
)
public class Post {

    @Id                                                 // To make it primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title",nullable=false)
    private String title;

    @Column(name="description",nullable=false)
    private String description;

    @Column(name="content",nullable=false)
    private String content;



  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL , fetch = FetchType.LAZY, orphanRemoval = true )
    private Set<Comment> comments = new HashSet<>();

}
