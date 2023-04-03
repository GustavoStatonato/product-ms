package br.com.gstatonato.productms.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank
    @ApiModelProperty(value = "Nome do produto")
    private String name;

    @NotBlank
    @ApiModelProperty(value = "Descrição do produto")
    private String description;

    @NotNull
    @Positive
    @ApiModelProperty(value = "Preço do produto")
    private BigDecimal price;
}
