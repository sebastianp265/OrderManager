package com.github.sebastianp265.ordermanager.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sebastianp265.ordermanager.dtos.CreateOrderDto;
import com.github.sebastianp265.ordermanager.entities.Client;
import com.github.sebastianp265.ordermanager.entities.Order;
import com.github.sebastianp265.ordermanager.entities.Product;
import com.github.sebastianp265.ordermanager.entities.ProductQuantity;
import com.github.sebastianp265.ordermanager.repositories.ClientRepository;
import com.github.sebastianp265.ordermanager.repositories.OrderRepository;
import com.github.sebastianp265.ordermanager.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private OrderService orderService;

    private Client client;
    private Product product;
    private Product product2;

    @BeforeEach
    void setUp() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Client clientForRepository = objectMapper.readValue(getClass().getResourceAsStream("/client.json"), Client.class);
        Product productForRepository = objectMapper.readValue(getClass().getResourceAsStream("/product.json"), Product.class);
        Product product2ForRepository = objectMapper.readValue(getClass().getResourceAsStream("/product2.json"), Product.class);

        when(clientRepository.findById(1L)).thenReturn(java.util.Optional.of(clientForRepository));
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(productForRepository));
        when(productRepository.findById(2L)).thenReturn(java.util.Optional.of(product2ForRepository));

        client = objectMapper.readValue(getClass().getResourceAsStream("/client.json"), Client.class);
        product = objectMapper.readValue(getClass().getResourceAsStream("/product.json"), Product.class);
        product2 = objectMapper.readValue(getClass().getResourceAsStream("/product2.json"), Product.class);
    }

    @Test
    void createOrder_shouldReturnOrder_whenClientAndProductsExist() {
        // arrange
        CreateOrderDto createOrderDto = CreateOrderDto.builder()
                .clientId(1L)
                .productToQuantityMap(Map.of(1L, 1L, 2L, 2L))
                .build();

        when(orderRepository.save(any(Order.class))).thenAnswer(
                i -> {
                    Order order = i.getArgument(0);
                    order.setId(1L);
                    return order;
                }
        );
        when(productRepository.saveAll(anyList())).thenReturn(List.of(product, product2));
        // act
        Order order = orderService.createOrder(createOrderDto);
        // assert
        product.setNumberAvailableInStock(1L);
        ProductQuantity expectedQuantity = ProductQuantity.builder()
                .product(product)
                .quantity(1L)
                .build();
        product2.setNumberAvailableInStock(0L);
        ProductQuantity expectedQuantity2 = ProductQuantity.builder()
                .product(product2)
                .quantity(2L)
                .build();

        Order expectedOrder = Order.builder()
                .id(1L)
                .client(client)
                .status(Order.Status.NEW)
                .productQuantities(Set.of(expectedQuantity2, expectedQuantity))
                .build();

        assertEquals(expectedOrder, order);
    }

}