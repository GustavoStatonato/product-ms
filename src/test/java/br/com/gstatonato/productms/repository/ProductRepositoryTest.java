package br.com.gstatonato.productms.repository;

import br.com.gstatonato.productms.entity.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @Mock
    private ProductRepository productRepository;

    @Test
    void searchProducts() {
        Product product1 = new Product(1L,"lata", "lati", new BigDecimal("5.5"));

        List<Product> productList = Arrays.asList(product1);
        when(productRepository.searchProducts(any(), any(), any())).thenReturn(productList);

        List<Product> filteredProducts = productRepository.searchProducts("lata", new BigDecimal("1"), new BigDecimal("10"));
        assertEquals(productList, filteredProducts);

    }
    
}