package br.com.gstatonato.productms.controller;

import br.com.gstatonato.productms.domain.request.ProductRequest;
import br.com.gstatonato.productms.domain.response.ProductResponse;
import br.com.gstatonato.productms.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> addProduct(@Valid @RequestBody ProductRequest request) {
        return productService.add(request);
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> putProduct(@PathVariable Long id,
                                             @Valid @RequestBody ProductRequest request) {
        return productService.put(id, request);
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        return productService.get(id);
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductResponse>> getProducts() {
        return productService.getList();
    }

    @GetMapping(
            value = "/search",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductResponse>> searchProducts(
            @RequestParam(required = false) String q,
            @RequestParam(required = false, name = "min_price") BigDecimal minPrice,
            @RequestParam(required = false, name = "max_price") BigDecimal maxPrice) {
        return productService.search(q, minPrice, maxPrice);
    }

    @DeleteMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteProduct(@PathVariable Long id) {
        productService.delete(id);
    }
}
