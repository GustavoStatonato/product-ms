package br.com.gstatonato.productms.domain.mapper;

import br.com.gstatonato.productms.domain.request.ProductRequest;
import br.com.gstatonato.productms.domain.response.ProductResponse;
import br.com.gstatonato.productms.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse toResponse(Product entity);
    Product toEntity(ProductRequest request);
    List<ProductResponse> toResponse(List<Product> entity);
}
