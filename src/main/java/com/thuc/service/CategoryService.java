package com.thuc.service;

import com.thuc.model.Category;

import java.util.List;

public interface CategoryService {

    public Category createCategory(String name, Long UserId) throws Exception;

    public List<Category> findCategoryByRestaurantId(Long id) throws Exception;

    public Category findCategoryById(Long id) throws Exception;

}
