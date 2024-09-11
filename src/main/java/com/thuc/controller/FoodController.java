package com.thuc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thuc.model.Food;
import com.thuc.model.Restaurant;
import com.thuc.model.User;
import com.thuc.request.CreateFoodRequest;
import com.thuc.service.FoodService;
import com.thuc.service.RestaurantService;
import com.thuc.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(
            @RequestParam String name,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<Food> food = foodService.searchFood(name);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(
            @RequestParam(required = false) boolean vegetarian,
            @RequestParam(required = false) boolean nonveg,
            @RequestParam(required = false) boolean seasonal,
            @RequestParam(required = false) String food_category,
            @PathVariable Long restaurantId,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<Food> foods = foodService.getRestaurantFood(restaurantId, vegetarian, nonveg, seasonal, food_category);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

}
