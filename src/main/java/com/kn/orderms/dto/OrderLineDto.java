package com.kn.orderms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;


@Data
@Builder
public class OrderLineDto {

    @NotBlank
    @JsonProperty("product_sku_code")
    private String productSkuCode;

    @Positive
    private Integer quantity;
}
