package com.org.let.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String type;
    private String description;
    private double price;
    private String imageUrl;
    private ProductDetailsDTO productDetailsDTO;
}
