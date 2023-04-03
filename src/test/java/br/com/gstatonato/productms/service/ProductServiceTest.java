package br.com.gstatonato.productms.service;

import br.com.gstatonato.productms.domain.mapper.ProductMapper;
import br.com.gstatonato.productms.domain.request.ProductRequest;
import br.com.gstatonato.productms.domain.response.ProductResponse;
import br.com.gstatonato.productms.entity.Product;
import br.com.gstatonato.productms.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Test
    void addTest() {
        ProductRequest request = new ProductRequest("Lata", "Lata", new BigDecimal("5.5"));
        Product product = new Product(1L, "Lata", "Lata", new BigDecimal("5.5"));
        ProductResponse productResponse = new ProductResponse(1L, "Lata", "Lata", new BigDecimal("5.5"));

        when(productRepository.save(any())).thenReturn(product);
        when(productMapper.toEntity(request)).thenReturn(product);
        when(productMapper.toResponse(product)).thenReturn(productResponse);
        ResponseEntity<ProductResponse> response = productService.add(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody().getId().longValue());
        assertEquals("Lata", response.getBody().getName());
        assertEquals("Lata", response.getBody().getDescription());
        assertEquals(new BigDecimal("5.5"), response.getBody().getPrice());
    }

    @Test
    void putTest() {
        ProductRequest request = new ProductRequest();
        request.setName("Teste");
        request.setDescription("Descrição do teste");
        request.setPrice(new BigDecimal("5.5"));

        Product product = new Product();
        product.setId(1L);
        product.setName("Lata");
        product.setDescription("Lata");
        product.setPrice(new BigDecimal("5.5"));

        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setDescription(product.getName());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toResponse(product)).thenReturn(productResponse);

        ResponseEntity<ProductResponse> responseEntity = productService.put(1L, request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ProductResponse response = responseEntity.getBody();
        assertEquals(1L, response.getId().longValue());
        assertEquals("Lata", response.getName());
        assertEquals("Lata", response.getDescription());
        assertEquals(new BigDecimal("5.5"), response.getPrice());
    }

    @Test
    void putProductNotFoundTest() {
        ProductRequest productRequest = new ProductRequest("Lata", "Lata", new BigDecimal("5.5"));

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            productService.put(1L, productRequest);
        } catch (ResponseStatusException ex) {
            assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        }
    }

    @Test
    void getTest() {
        Long id = 1L;
        Product product = new Product(id, "Lata", "Lata", new BigDecimal("5.5"));
        ProductResponse productResponse = new ProductResponse(1L, "Lata", "Lata", new BigDecimal("5.5"));

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productMapper.toResponse(product)).thenReturn(productResponse);
        ResponseEntity<ProductResponse> response = productService.get(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, response.getBody().getId());
        assertEquals(product.getName(), response.getBody().getName());
        assertEquals(product.getDescription(), response.getBody().getDescription());
        assertEquals(product.getPrice(), response.getBody().getPrice());
    }

    @Test
    void getProductNotFoundTest() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            productService.get(1L);
        } catch (ResponseStatusException ex) {
            assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        }
    }

    @Test
    void getListTest() {
        Product product1 = new Product(1L, "Lata", "Lata", new BigDecimal("5.5"));
        Product product2 = new Product(2L, "Lata", "Lata", new BigDecimal("5.5"));
        List<Product> productList = Arrays.asList(product1, product2);

        ProductResponse productResponse1 = new ProductResponse(1L, "Lata", "Lata", new BigDecimal("5.5"));
        ProductResponse productResponse2 = new ProductResponse(2L, "Lata", "Lata", new BigDecimal("5.5"));
        List<ProductResponse> listResponse = Arrays.asList(productResponse1, productResponse2);

        when(productRepository.findAll()).thenReturn(productList);
        when(productMapper.toResponse(productList)).thenReturn(listResponse);

        ResponseEntity<List<ProductResponse>> responseEntity = productService.getList();

        List<ProductResponse> productResponseList = responseEntity.getBody();
        assertNotNull(productResponseList);

        ProductResponse productResponse1Final = productResponseList.get(0);
        ProductResponse productResponse2Final = productResponseList.get(1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, productResponseList.size());
        assertEquals(product1.getId(), productResponse1Final.getId());
        assertEquals(product1.getName(), productResponse1Final.getName());
        assertEquals(product1.getDescription(), productResponse1Final.getDescription());
        assertEquals(product1.getPrice(), productResponse1Final.getPrice());
        assertEquals(product2.getId(), productResponse2Final.getId());
        assertEquals(product2.getName(), productResponse2Final.getName());
        assertEquals(product2.getDescription(), productResponse2Final.getDescription());
        assertEquals(product2.getPrice(), productResponse2Final.getPrice());
    }

    @Test
    void searchTest() {
        String q = "Lata";
        BigDecimal minPrice = new BigDecimal("5.00");
        BigDecimal maxPrice = new BigDecimal("10.00");

        Product product1 = new Product(1L, "Lata", "Lata", new BigDecimal("5.5"));
        Product product2 = new Product(2L, "Lata", "Lata", new BigDecimal("5.5"));
        List<Product> productList = Arrays.asList(product1, product2);

        ProductResponse productResponse1 = new ProductResponse(1L, "Lata", "Lata", new BigDecimal("5.5"));
        ProductResponse productResponse2 = new ProductResponse(2L, "Lata", "Lata", new BigDecimal("5.5"));
        List<ProductResponse> listResponse = Arrays.asList(productResponse1, productResponse2);

        when(productRepository.searchProducts(anyString(), any(), any())).thenReturn(productList);
        when(productMapper.toResponse(productList)).thenReturn(listResponse);

        ResponseEntity<List<ProductResponse>> response = productService.search(q, minPrice, maxPrice);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listResponse, response.getBody());
    }

    @Test
    void deleteTest() {
        productService.delete(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }
}