package br.com.gstatonato.productms.domain.mapper;

import br.com.gstatonato.productms.domain.request.ProductRequest;
import br.com.gstatonato.productms.domain.response.ProductResponse;
import br.com.gstatonato.productms.entity.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
//@ContextConfiguration(classes = ProductMapperImpl.class)
class ProductMapperTest {

    @InjectMocks private ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Test
    void toResponseTest() {
        Product product =  new Product(1L, "Lata", "Lata", new BigDecimal("5.5"));
        ProductResponse productResponse = productMapper.toResponse(product);

        assertEquals(1L, productResponse.getId());
        assertEquals("Lata", productResponse.getName());
        assertEquals("Lata", productResponse.getDescription());
        assertEquals(new BigDecimal("5.5"), productResponse.getPrice());
    }

    @Test
    void toResponseNullTest() {
        ProductResponse productResponse = productMapper.toResponse((Product) null);

        assertNull(productResponse);
    }

    @Test
    void toEntityTest() {
        ProductRequest productRequest = new ProductRequest("Lata", "Lata", new BigDecimal("5.5"));

        Product product = productMapper.toEntity(productRequest);

        assertNull(product.getId());
        assertEquals("Lata", product.getName());
        assertEquals("Lata", product.getDescription());
        assertEquals(new BigDecimal("5.5"), product.getPrice());
    }
    @Test
    void toEntityNullTest() {
        Product product  = productMapper.toEntity( null);

        assertNull(product);
    }

    @Test
    void toResponseListTest() {
        List<Product> productList = Arrays.asList(
                new Product(1L, "Lata", "Lata", new BigDecimal("5.5")),
                new Product(2L, "Lata", "Lata", new BigDecimal("5.5"))
        );

        List<ProductResponse> productResponseList = productMapper.toResponse(productList);

        ProductResponse productResponse1 = productResponseList.get(0);

        assertNotNull(productResponse1);
        assertEquals(1L, productResponse1.getId());
        assertEquals("Lata", productResponse1.getName());
        assertEquals("Lata", productResponse1.getDescription());
        assertEquals(new BigDecimal("5.5"), productResponse1.getPrice());

        ProductResponse productResponse2 = productResponseList.get(1);

        assertNotNull(productResponse2);
        assertEquals(2L, productResponse2.getId());
        assertEquals("Lata", productResponse2.getName());
        assertEquals("Lata", productResponse2.getDescription());
        assertEquals(new BigDecimal("5.5"), productResponse2.getPrice());
        }

    @Test
    void toResponseListNullTest() {
        List<ProductResponse> productResponseList = productMapper.toResponse((List<Product>) null);

        assertNull(productResponseList);
    }
}