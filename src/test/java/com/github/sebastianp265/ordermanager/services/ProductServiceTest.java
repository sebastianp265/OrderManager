package com.github.sebastianp265.ordermanager.services;

import com.github.sebastianp265.ordermanager.entities.Product;
import com.github.sebastianp265.ordermanager.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void createProduct_shouldCallProductRepositorySave() {
        // arrange
        Product product = Product.builder()
                .id(1L)
                .name("Test product")
                .numberAvailableInStock(10L)
                .price(BigDecimal.valueOf(10.0))
                .build();
        // act
        productService.createProduct(product);
        // assert
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void deleteProductById_shouldCallProductRepositoryDeleteById() {
        // arrange
        Long id = 1L;
        // act
        productService.deleteProductById(id);
        // assert
        verify(productRepository, times(1)).deleteById(id);
    }

    @Test
    void updateProduct_shouldCallProductRepositorySave() {
        // arrange
        Product product = Product.builder()
                .id(1L)
                .name("Test product")
                .numberAvailableInStock(10L)
                .price(BigDecimal.valueOf(10.0))
                .build();
        // act
        productService.updateProduct(product);
        // assert
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void findProductById_shouldCallProductRepositoryFindById() {
        // arrange
        Long id = 1L;
        // act
        when(productRepository.findById(id)).thenReturn(Optional.of(Product.builder().build()));
        productService.findProductById(id);
        // assert
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void findProductById_shouldThrowResponseStatusExceptionWhenProductNotFound() {
        // arrange
        Long id = 1L;
        // act
        when(productRepository.findById(id)).thenReturn(Optional.empty());
        // then
        assertThrows(ResponseStatusException.class, () -> productService.findProductById(id));
    }
}