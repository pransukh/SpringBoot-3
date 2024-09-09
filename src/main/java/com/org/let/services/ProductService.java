package com.org.let.services;

// ProductService.java
import com.org.let.entities.ModelProduct;
import com.org.let.entities.ModelProductType;
import com.org.let.entities.ModelProductDetails;
import com.org.let.repositories.ProductDetailRepository;
import com.org.let.repositories.ProductRepository;
import com.org.let.repositories.ProductTypeRepository;
import com.org.let.request.productType.RequestProduct;
import com.org.let.request.productType.RequestProductType;
import com.org.let.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    public List<ModelProduct> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductResponseDTO getProductByID(Long id) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        List<Object[]> product = productRepository.findProductsWithTypes(id);
        System.out.println(product);
        if(!product.isEmpty()){
            for(Object[] obj:product){
                ModelProduct product1 = (ModelProduct)obj[0];
                productResponseDTO.setProductName(product1.getName());
                productResponseDTO.setImageUrl(product1.getProductDetailId().getImage());
                productResponseDTO.setPrice(product1.getProductDetailId().getPrice());
                productResponseDTO.setId(product1.getId());
                productResponseDTO.setName(product1.getName());
                productResponseDTO.setDescription(product1.getDescription());
                productResponseDTO.setProductTypeId(product1.getProductTypeId().getProductTypeId());
                productResponseDTO.setIsEnable(product1.getProductTypeId().getIsEnabled());
                productResponseDTO.setProductTypeImageUri(product1.getProductTypeId().getImageIconURI());
                productResponseDTO.setType(product1.getProductTypeId().getProductTypeName());
                productResponseDTO.setOperation(product1.getProductDetailId().getOperation());



            }
        }
        return productResponseDTO;
    }

    public ProductTypeResponseDTO getProductByTypeID(Long id) {
        ProductTypeResponseDTO productTypeResponseDTO = new ProductTypeResponseDTO();

        List<ProductResponseForTypeDTO> productResponseForTypeDTOList = new ArrayList<>();
        List<Object[]> product = productRepository.findProductsWithTypeId(id);


        ModelProductType productType = (ModelProductType) product.get(0)[0];
        productTypeResponseDTO.setProductTypeId(productType.getProductTypeId());
        productTypeResponseDTO.setProductTypeName(productType.getProductTypeName());
        productTypeResponseDTO.setImageIconURI(productType.getImageIconURI());
        productTypeResponseDTO.setIsEnabled(productType.getIsEnabled());


        if(!product.isEmpty()){
            for(Object[] obj:product){
                ModelProduct product1 = (ModelProduct)obj[1];
                ProductResponseForTypeDTO productResponseForTypeDTO = new ProductResponseForTypeDTO();
                ProductDetailsDTO productDetailsDTO = new ProductDetailsDTO();

                productDetailsDTO.setDetails_id(product1.getProductDetailId().getProductDetailId());
                productDetailsDTO.setPrice(product1.getProductDetailId().getPrice());
                productDetailsDTO.setRating(product1.getProductDetailId().getRating());
                productDetailsDTO.setOperation(product1.getProductDetailId().getOperation());
                productDetailsDTO.setBuilt(product1.getProductDetailId().getBuilt());
                productDetailsDTO.setImage(product1.getProductDetailId().getImage());
                productDetailsDTO.setWarranty(product1.getProductDetailId().getWarranty());

                productResponseForTypeDTO.setProductDetailsDTO(productDetailsDTO);

                productResponseForTypeDTO.setProductId(product1.getId());
                productResponseForTypeDTO.setProductName(product1.getName());
                productResponseForTypeDTO.setDescription(product1.getDescription());
                productResponseForTypeDTOList.add(productResponseForTypeDTO);
            }
            productTypeResponseDTO.setProductResponseForTypeDTOList(productResponseForTypeDTOList);
        }
        return productTypeResponseDTO;
    }

    public Optional<ModelProduct> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    public Optional<ModelProduct> getProductByQuery(String name, int id){
       return productRepository.findByNameAndId(name,id);
    }



    public List<ModelProductType> getAllProductType(){
        return productTypeRepository.findAll();
    }

    public boolean addProductType(RequestProductType requestProductType){
        ModelProductType modelProductType = new ModelProductType();
        modelProductType.setProductTypeName(requestProductType.getTypeName());
        modelProductType.setImageIconURI(requestProductType.getImageUrl());
        modelProductType.setIsEnabled(Long.parseLong(requestProductType.getEnabled()));
        productTypeRepository.save(modelProductType);
        return true;
    }

    public AllProdsInType getAllProductsWithType(Long productType){

        ModelProductType returnData = productTypeRepository.findByProductTypeId(productType);
        AllProdsInType allProdsInType = new AllProdsInType();
        allProdsInType.setType(returnData.getProductTypeId());

        List<ModelProduct> modelProductList = new ArrayList<>();
        for(ModelProduct m: returnData.getModalProducts()){
            modelProductList.add(m);
        }
        allProdsInType.setModelProductList(modelProductList);
        return allProdsInType;
    }

    public boolean addProduct(RequestProduct requestProduct){
        ModelProduct product = new ModelProduct();
        ModelProductDetails productDetails = new ModelProductDetails();
        System.out.println(requestProduct.getType());
        ModelProductType productType= productTypeRepository.findById(requestProduct.getType()).orElseThrow(() -> new RuntimeException("Product Type not found: "+ requestProduct.getType()));


        productDetails.setRating(requestProduct.getRating());
        productDetails.setBuilt(requestProduct.getBuilt());
        productDetails.setPrice(requestProduct.getPrice());
        productDetails.setOperation(requestProduct.getOperation());
        productDetails.setImage(requestProduct.getImageUrl());
        productDetails.setWarranty(requestProduct.getWarranty());

       ModelProductDetails modelProductDetails = productDetailRepository.save(productDetails)        ;

        product.setName(requestProduct.getName());
        product.setDescription(requestProduct.getDescription());
        product.setProductTypeId(productType);
        product.setProductDetailId(modelProductDetails);
        productRepository.save(product);

        return true;
    }


}
