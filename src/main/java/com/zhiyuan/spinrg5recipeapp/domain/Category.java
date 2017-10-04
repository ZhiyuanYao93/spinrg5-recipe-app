package com.zhiyuan.spinrg5recipeapp.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"recipes"})
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToMany(mappedBy = "categories")
//    @ManyToMany
//    @JoinTable(
//            name="category_recipe",
//            joinColumns = @JoinColumn(name="category_id"),
//            inverseJoinColumns = @JoinColumn(name="recipe_id")
//    )
    private Set<Recipe> recipes;

}
