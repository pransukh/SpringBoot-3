package com.org.let.controllers;

import com.org.let.entities.ModelProductType;
import com.org.let.request.productType.RequestProductType;
import com.org.let.response.AllProdsInType;
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

    @GetMapping("/productType/{typeId}")
    public AllProdsInType getAllProductsByType(@PathVariable ("typeId") Long typeId){
        return productService.getAllProductsWithType(typeId);

    }


    @PostMapping("/add")
    public RequestProductType addProductType(@RequestBody RequestProductType requestProductType){

        productService.addProductType(requestProductType);
        return requestProductType;
    }
}
