
package com.example.foodorder.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.foodorder.model.FoodOrder;
import com.example.foodorder.service.FoodOrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class FoodOrderController {

    private final FoodOrderService service;

    public FoodOrderController(FoodOrderService service) {
        this.service = service;
    }

    // GET: all orders
    @GetMapping
    public List<FoodOrder> getAllOrders() {
        return service.getAllOrders();
    }

    // GET: order by ID
    @GetMapping("/{id}")
    public FoodOrder getOrderById(@PathVariable Long id) {
        return service.getOrderById(id);
    }

    // POST: add order
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FoodOrder addOrder(@Valid @RequestBody FoodOrder order) {
        return service.addOrder(order);
    }

    // PUT: update complete order
    @PutMapping("/{id}")
    public FoodOrder updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody FoodOrder order) {
        return service.updateOrder(id, order);
    }

    // PATCH: update only quantity
    @PatchMapping("/{id}/quantity")
    public FoodOrder updateQuantity(
            @PathVariable Long id,
            @RequestParam @jakarta.validation.constraints.Min(1) int quantity) {
        FoodOrder order = service.getOrderById(id);
        order.setQuantity(quantity);
        return order;
    }

    // DELETE: remove order
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long id) {
        service.deleteOrder(id);
    }

    // SEARCH: partial food name
    @GetMapping("/search")
    public List<FoodOrder> searchFood(@RequestParam String keyword) {
        return service.searchFood(keyword);
    }

    // SEARCH: exact food name
    @GetMapping("/exact")
    public List<FoodOrder> exactFood(@RequestParam String name) {
        return service.findByExactFoodName(name);
    }

    // FILTER: price range
    @GetMapping("/price")
    public List<FoodOrder> filterByPrice(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        return service.filterByPrice(min, max);
    }

    // SORT: price low to high
    @GetMapping("/sort/asc")
    public List<FoodOrder> sortAscending() {
        return service.sortByPriceAscending();
    }

    // SORT: price high to low
    @GetMapping("/sort/desc")
    public List<FoodOrder> sortDescending() {
        return service.sortByPriceDescending();
    }

    // MAP: food names only
    @GetMapping("/food-names")
    public List<String> getFoodNames() {
        return service.getAllFoodNames();
    }

    // FILTER: available orders only
    @GetMapping("/available")
    public List<FoodOrder> getAvailableOrders() {
        return service.getAvailableOrders();
    }

    // CHECK: whether a food exists
    @GetMapping("/exists")
    public boolean foodExists(@RequestParam String name) {
        return service.foodExists(name);
    }

    // COMPARE: two order prices
    @GetMapping("/compare")
    public String comparePrices(
            @RequestParam Long firstId,
            @RequestParam Long secondId) {

        int result = service.comparePrices(firstId, secondId);

        if (result < 0) {
            return "First order price is lower";
        } else if (result > 0) {
            return "First order price is higher";
        }
        return "Both order prices are equal";
    }
}
