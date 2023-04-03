package br.com.gstatonato.productms.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    @ApiModelProperty(value = "Id do produto")
    private Long id;

    @ApiModelProperty(value = "Nome do produto")
    private String name;

    @ApiModelProperty(value = "Descrição do produto")
    private String description;

    @ApiModelProperty(value = "Preço do produto")
    private BigDecimal price;
}
