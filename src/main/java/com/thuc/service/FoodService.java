package com.thuc.service;

import com.thuc.model.Category;
import com.thuc.model.Food;
import com.thuc.model.Restaurant;
import com.thuc.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);

    void deleteFood(Long foodId) throws Exception;

    public List<Food> getRestaurantFood(
            Long restaurantId,
            boolean isVegetarian,
            boolean isNonveg,
            boolean isSeasonal,
            String foodCategory);

    public List<Food> searchFood(String keyword);

    public Food findFoodById(Long foodId) throws Exception;

    public Food updateAvailibityStatus(Long foodId) throws Exception;

}
