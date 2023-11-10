package com.github.sebastianp265.ordermanager.controllers;

import com.github.sebastianp265.ordermanager.dtos.CreateOrderDto;
import com.github.sebastianp265.ordermanager.entities.Order;
import com.github.sebastianp265.ordermanager.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @PostMapping()
    public Order createOrder(@RequestBody CreateOrderDto createOrderDto) {
        return orderService.createOrder(createOrderDto);
    }

    @PostMapping("/{orderId}/update-status")
    public Order updateOrderStatus(@PathVariable Long orderId) {
        return orderService.updateOrderStatus(orderId);
    }


    @PostMapping("/{orderId}/cancel")
    public Order cancelOrder(@PathVariable Long orderId) {
        return orderService.cancelOrder(orderId);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderById(@PathVariable Long id) {
        orderService.deleteOrderById(id);
    }

    @PutMapping
    public Order updateOrder(@RequestBody Order order) {
        return orderService.updateOrder(order);
    }

    @GetMapping("/{id}")
    public Order findOrderById(@PathVariable Long id) {
        return orderService.findOrderById(id);
    }
}

