package com.org.let.request.productType;

import com.org.let.models.ModelProduct;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestProduct {

    private String name;
    private String description;
    private String imageUrl;
    private String operation;
    private String built;
    private Long type;
    private Long rating;
    private Long warranty;
    private Double price;



}
