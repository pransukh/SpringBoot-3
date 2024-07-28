package com.org.let.models;

// Product.java

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "products")
@Data
@Getter
@Setter
public class ModelProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;



    @ManyToOne
    @JoinColumn(name = "PRODUCT_TYPE_ID")
    private ModelProductType productTypeId;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_DETAIL_ID")
    private ModelProductDetails productDetailId;
}
