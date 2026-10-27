
package com.example.foodorder.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.foodorder.exception.OrderNotFoundException;
import com.example.foodorder.model.FoodOrder;

@Service
public class FoodOrderService {

    private final List<FoodOrder> orders = new ArrayList<>();
    private long nextId = 1;

    // CREATE
    public FoodOrder addOrder(FoodOrder order) {
        order.setOrderId(nextId++);
        orders.add(order);
        return order;
    }

    // READ ALL
    public List<FoodOrder> getAllOrders() {
        return new ArrayList<>(orders);
    }

    // READ BY ID
    public FoodOrder getOrderById(Long id) {
        return orders.stream()
                .filter(order -> order.getOrderId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                    new OrderNotFoundException(
                        "Order not found with ID: " + id));
    }

    // UPDATE
    public FoodOrder updateOrder(Long id, FoodOrder updatedOrder) {
        FoodOrder existing = getOrderById(id);

        existing.setCustomerName(updatedOrder.getCustomerName());
        existing.setFoodItem(updatedOrder.getFoodItem());
        existing.setShopName(updatedOrder.getShopName());
        existing.setCustomerEmail(updatedOrder.getCustomerEmail());
        existing.setCustomerMobile(updatedOrder.getCustomerMobile());
        existing.setQuantity(updatedOrder.getQuantity());
        existing.setPrice(updatedOrder.getPrice());
        existing.setAvailable(updatedOrder.isAvailable());

        return existing;
    }

    // DELETE
    public void deleteOrder(Long id) {
        FoodOrder order = getOrderById(id);
        orders.remove(order);
    }

    // PARTIAL MATCHING: food name contains search text
    public List<FoodOrder> searchFood(String keyword) {
        return orders.stream()
                .filter(order -> order.getFoodItem() != null
                        && order.getFoodItem()
                                .toLowerCase()
                                .contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    // EXACT MATCHING: exact food name
    public List<FoodOrder> findByExactFoodName(String foodName) {
        return orders.stream()
                .filter(order -> order.getFoodItem() != null
                        && order.getFoodItem()
                                .equalsIgnoreCase(foodName))
                .collect(Collectors.toList());
    }

    // FILTER: orders within a price range
    public List<FoodOrder> filterByPrice(
            BigDecimal minPrice, BigDecimal maxPrice) {

        return orders.stream()
                .filter(order -> order.getPrice() != null
                        && order.getPrice().compareTo(minPrice) >= 0
                        && order.getPrice().compareTo(maxPrice) <= 0)
                .collect(Collectors.toList());
    }

    // SORT: lowest price first
    public List<FoodOrder> sortByPriceAscending() {
        return orders.stream()
                .sorted(Comparator.comparing(FoodOrder::getPrice))
                .collect(Collectors.toList());
    }

    // SORT: highest price first
    public List<FoodOrder> sortByPriceDescending() {
        return orders.stream()
                .sorted(Comparator.comparing(FoodOrder::getPrice).reversed())
                .collect(Collectors.toList());
    }

    // MAP: return only food names
    public List<String> getAllFoodNames() {
        return orders.stream()
                .map(FoodOrder::getFoodItem)
                .collect(Collectors.toList());
    }

    // FILTER: available orders
    public List<FoodOrder> getAvailableOrders() {
        return orders.stream()
                .filter(FoodOrder::isAvailable)
                .collect(Collectors.toList());
    }

    // ANY MATCH: check whether a food exists
    public boolean foodExists(String foodName) {
        return orders.stream()
                .anyMatch(order -> order.getFoodItem() != null
                        && order.getFoodItem()
                                .equalsIgnoreCase(foodName));
    }

    // CUSTOM COMPARISON using BigDecimal.compareTo()
    public int comparePrices(Long firstOrderId, Long secondOrderId) {
        BigDecimal firstPrice = getOrderById(firstOrderId).getPrice();
        BigDecimal secondPrice = getOrderById(secondOrderId).getPrice();

        return firstPrice.compareTo(secondPrice);
    }
}
