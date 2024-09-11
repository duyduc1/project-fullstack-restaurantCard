package com.thuc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thuc.model.IngredientCategory;

import java.util.List;

public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory, Long> {

    List<IngredientCategory> findByRestaurantId(Long id);
}
