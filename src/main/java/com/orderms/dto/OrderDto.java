package com.orderms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class OrderDto {

    @NotBlank
    @JsonProperty("customer_reg_code")
    private String customerRegCode;

    @NotBlank
    @JsonProperty("date")
    private String dateStr;

    @NotNull
    @JsonProperty("order_lines")
    private List<OrderLineDto> orderLineDtos;
}
