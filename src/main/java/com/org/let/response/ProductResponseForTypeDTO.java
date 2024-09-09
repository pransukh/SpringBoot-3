package com.org.let.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseForTypeDTO {
    private Long productId;
    private String description;
    private String productName;
    ProductDetailsDTO productDetailsDTO;
}
