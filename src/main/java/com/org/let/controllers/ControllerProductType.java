package com.org.let.controllers;

import com.org.let.models.ModelProductType;
import com.org.let.request.productType.RequestProductType;
import com.org.let.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product/type")
public class ControllerProductType {

    @Autowired
    ProductService productService;

    @GetMapping("/all")
    public List<ModelProductType> getAllProductType(){
        return productService.getAllProductType();
    }

    @PostMapping("/add")
    public RequestProductType addProductType(@RequestBody RequestProductType requestProductType){

        productService.addProductType(requestProductType);
        return requestProductType;
    }
}
