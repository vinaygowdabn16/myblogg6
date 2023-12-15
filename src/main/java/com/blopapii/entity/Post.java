package com.blopapii.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name="posts",uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
public class Post {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="title",nullable=false,unique = true)
    private String title;
    @Column(name="description",nullable=false,unique = true)
    private String description;
    @Column(name="content",nullable=false,unique = true)
    private String content;

    @OneToMany(cascade=CascadeType.ALL,  mappedBy= "post")
    private List<Comment> comment = new ArrayList<>();

}
