package com.org.let.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String type;
    private String description;
    private double price;
    private String imageUrl;
    private Long productTypeId;
    private String productName;
    private String productTypeImageUri;
    private Long isEnable;
    private String operation;
}
