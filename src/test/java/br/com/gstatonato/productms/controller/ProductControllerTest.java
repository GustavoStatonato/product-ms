package br.com.gstatonato.productms.controller;

import br.com.gstatonato.productms.domain.request.ProductRequest;
import br.com.gstatonato.productms.domain.response.ProductResponse;
import br.com.gstatonato.productms.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Test
    void addProduct() {
        ProductRequest productRequest = new ProductRequest("Lata", "Lata", new BigDecimal("5.5"));
        ProductResponse productResponse = new ProductResponse(1L, "Lata", "Lata", new BigDecimal("5.5"));

        when(productService.add(any())).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(productResponse));

        ResponseEntity<ProductResponse> responseEntity = productController.addProduct(productRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(productResponse, responseEntity.getBody());
    }

    @Test
    void putProduct() {
        ProductRequest productRequest = new ProductRequest("Lata", "Lata", new BigDecimal("5.5"));
        ProductResponse productResponse = new ProductResponse(1L, "Lata", "Lata", new BigDecimal("5.5"));

        when(productService.put(1L, productRequest)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(productResponse));

        ResponseEntity<ProductResponse> responseEntity = productController.putProduct(1L, productRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(productResponse, responseEntity.getBody());
    }

    @Test
    void getProduct() {
        ProductResponse productResponse = new ProductResponse(1L, "Lata", "Lata", new BigDecimal("5.5"));

        when(productService.get(1L)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(productResponse));

        ResponseEntity<ProductResponse> responseEntity = productController.getProduct(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(productResponse, responseEntity.getBody());
    }

    @Test
    void getProducts() {
        List<ProductResponse> list = Arrays.asList( new ProductResponse(1L, "Lata", "Lata", new BigDecimal("5.5")));

        when(productService.getList()).thenReturn(ResponseEntity.status(HttpStatus.OK).body(list));

        ResponseEntity<List<ProductResponse>> responseEntity = productController.getProducts();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(list, responseEntity.getBody());
    }

    @Test
    void searchProducts() {
        List<ProductResponse> productList = Arrays.asList(
                new ProductResponse(1L, "Lata 1", "Lata 1", new BigDecimal("5.5")),
                new ProductResponse(2L, "Lata 2", "Lata 2", new BigDecimal("10.5")),
                new ProductResponse(3L, "Lata 3", "Lata 3", new BigDecimal("15.5"))
        );
        when(productService.search(any(), any(), any())).thenReturn(ResponseEntity.status(HttpStatus.OK).body(productList));

        ResponseEntity<List<ProductResponse>> responseEntity = productController.searchProducts("Lata", new BigDecimal("5.5"), new BigDecimal("25.00"));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(3, responseEntity.getBody().size());
        assertEquals("Lata 1", responseEntity.getBody().get(0).getName());
    }

    @Test
    void deleteProduct() {
        productController.deleteProduct(1L);
        verify(productService, times(1)).delete(1L);
    }
}