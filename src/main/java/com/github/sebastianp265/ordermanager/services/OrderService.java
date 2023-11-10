package com.github.sebastianp265.ordermanager.services;

import com.github.sebastianp265.ordermanager.dtos.CreateOrderDto;
import com.github.sebastianp265.ordermanager.entities.Order;
import com.github.sebastianp265.ordermanager.entities.Product;
import com.github.sebastianp265.ordermanager.entities.ProductQuantity;
import com.github.sebastianp265.ordermanager.repositories.ClientRepository;
import com.github.sebastianp265.ordermanager.repositories.OrderRepository;
import com.github.sebastianp265.ordermanager.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public Order createOrder(CreateOrderDto createOrderDto) {
        Order order = new Order();
        order.setClient(clientRepository.findById(createOrderDto.getClientId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client with provided id doesn't exist")));
        order.setStatus(Order.Status.NEW);

        Set<ProductQuantity> productQuantities = new HashSet<>();
        Map<Long, Long> productToQuantityMap = createOrderDto.getProductToQuantityMap();

        for (Map.Entry<Long, Long> entry : productToQuantityMap.entrySet()) {
            Product product = productRepository.findById(entry.getKey()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with provided id doesn't exist"));

            if (product.getNumberAvailableInStock() < entry.getValue()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product with id: " + product.getId() + " doesn't have required quantity");
            }
            product.setNumberAvailableInStock(product.getNumberAvailableInStock() - entry.getValue());

            ProductQuantity productQuantity = ProductQuantity.builder()
                    .product(product)
                    .quantity(entry.getValue())
                    .build();

            productQuantities.add(productQuantity);
        }

        order.setProductQuantities(productQuantities);
        Order orderToReturn = orderRepository.save(order);

        productRepository.saveAll(productQuantities.stream().map(ProductQuantity::getProduct).toList());
        return orderToReturn;
    }

    public Order updateOrderStatus(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with provided id doesn't exist"));
        if (order.getStatus() == Order.Status.NEW) {
            order.setStatus(Order.Status.IN_PROGRESS);
        } else if (order.getStatus() == Order.Status.IN_PROGRESS) {
            order.setStatus(Order.Status.DELIVERED);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order with id: " + order.getId() + " cannot be updated");
        }
        return orderRepository.save(order);
    }

    public Order cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with provided id doesn't exist"));
        if (order.getStatus() != Order.Status.NEW) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order with id: " + order.getId() + " cannot be cancelled");
        }
        List<Product> products = new LinkedList<>();
        order.getProductQuantities().forEach(productQuantity -> {
            Product product = productQuantity.getProduct();
            product.setNumberAvailableInStock(product.getNumberAvailableInStock() + productQuantity.getQuantity());
            products.add(product);
        });

        productRepository.saveAll(products);

        order.setCancelled(true);
        return orderRepository.save(order);
    }

    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }

    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order findOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with provided id doesn't exist"));
    }

}
