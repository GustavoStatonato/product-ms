package br.com.gstatonato.productms.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

    @ApiModelProperty(value = "Código HTTP")
    @JsonProperty("status_code")
    private Integer statusCode;

    @ApiModelProperty(value = "Mensagem de erro")
    private String message;
}
