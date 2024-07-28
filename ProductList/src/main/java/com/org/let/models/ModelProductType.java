package com.org.let.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="PRODUCT_TYPE")
@Data
@Getter
@Setter
@ToString
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

}
