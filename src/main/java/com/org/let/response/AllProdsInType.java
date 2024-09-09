package com.org.let.response;

import com.org.let.entities.ModelProduct;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AllProdsInType {
    Long type;
    List<ModelProduct> modelProductList;
}
