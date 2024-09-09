package com.org.let.entities;

// Product.java

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "products_details")
@Data
public class ModelProductDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="PRODUCT_DETAIL_ID")
    private Long productDetailId;

    @Column(name = "RATING")
    private Long rating;

    @Column(name = "OPERATION")
    private String operation;

    @Column(name = "BUILT")
    private String built;

    @Column(name = "WARRANTY")
    private Long warranty;

    @Column(name = "IMAGE_URL")
    private String image;

    @Column(name = "PRICE")
    private Double price;


}
