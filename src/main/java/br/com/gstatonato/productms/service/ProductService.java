package br.com.gstatonato.productms.service;

import br.com.gstatonato.productms.domain.mapper.ProductMapper;
import br.com.gstatonato.productms.domain.request.ProductRequest;
import br.com.gstatonato.productms.domain.response.ProductResponse;
import br.com.gstatonato.productms.entity.Product;
import br.com.gstatonato.productms.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    public ResponseEntity<ProductResponse> add(ProductRequest request) {
        Product save = productRepository.save(productMapper.toEntity(request));
        return new ResponseEntity<>(productMapper.toResponse(save), HttpStatus.CREATED);
    }


    public ResponseEntity<ProductResponse> put(Long id, ProductRequest request) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setDescription(request.getDescription());
            product.setName(request.getName());
            product.setPrice(request.getPrice());
            Product save = productRepository.save(product);
            return new ResponseEntity<>(productMapper.toResponse(save), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ProductResponse> get(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.map(product ->
                        new ResponseEntity<>(productMapper.toResponse(product), HttpStatus.OK))
                .orElseGet(() ->
                        new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<List<ProductResponse>> getList() {
        List<Product> allProducts = productRepository.findAll();
        return new ResponseEntity<>(productMapper.toResponse(allProducts), HttpStatus.OK);
    }

    public ResponseEntity<List<ProductResponse>> search(String q, BigDecimal minPrice, BigDecimal maxPrice) {
        List<Product> allProducts = productRepository.searchProducts(q, minPrice, maxPrice);
        return new ResponseEntity<>(productMapper.toResponse(allProducts), HttpStatus.OK);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
