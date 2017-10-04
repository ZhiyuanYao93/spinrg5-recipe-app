package com.zhiyuan.spinrg5recipeapp.repositories;

import com.zhiyuan.spinrg5recipeapp.domain.Category;
import com.zhiyuan.spinrg5recipeapp.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure,Long> {
    Optional<UnitOfMeasure> findByDescription(String description);

}
