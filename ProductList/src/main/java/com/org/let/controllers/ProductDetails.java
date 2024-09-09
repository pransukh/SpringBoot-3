package com.org.let.controllers;

import com.org.let.models.ModelProductDetails;
import com.org.let.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/product/detail")
public class ProductDetails {

    @Autowired
    private ProductService productService;


    @GetMapping("/get")
    public ModelProductDetails getDetails(){
        return null;
    }
}
