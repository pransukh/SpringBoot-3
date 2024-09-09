package com.org.let.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ProductTypeResponseDTO {
    Long productTypeId;
    String productTypeName;
    Long isEnabled;
    String imageIconURI;
    List<ProductResponseForTypeDTO> productResponseForTypeDTOList;
}
