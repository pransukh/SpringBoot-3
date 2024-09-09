package com.org.let.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="PRODUCT_TYPE")
@Data
@RequiredArgsConstructor
public class ModelProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="PRODUCT_TYPE_ID")
    Long productTypeId;

    @Column(name ="PRODUCT_TYPE_NAME")
    String productTypeName;

    @Column(name ="IS_ENABLED")
    Long isEnabled;

    @Column(name ="IMAGE_URL")
    String imageIconURI;

    @OneToMany(mappedBy = "productTypeId")
    @JsonManagedReference
    private List<ModelProduct> modalProducts;


}
