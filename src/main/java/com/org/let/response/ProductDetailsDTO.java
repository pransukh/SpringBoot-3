package com.org.let.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDetailsDTO {
    private Long details_id;
    private Long rating;
    private String built;
    private String operation;
    private Long warranty;
    private String image;
    private Double price;
}
