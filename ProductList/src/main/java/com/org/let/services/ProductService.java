package com.org.let.services;

// ProductService.java
import com.org.let.models.ModelProduct;
import com.org.let.models.ModelProductType;
import com.org.let.models.ModelProductDetails;
import com.org.let.repositories.ProductDetailRepository;
import com.org.let.repositories.ProductRepository;
import com.org.let.repositories.ProductTypeRepository;
import com.org.let.request.productType.RequestProduct;
import com.org.let.request.productType.RequestProductType;
import com.org.let.response.ProductDetailsDTO;
import com.org.let.response.ProductResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
                ModelProductType  productType = (ModelProductType) obj[1];
                System.out.println(product1.getId());
                System.out.println(product1.getName());
                System.out.println(product1.getProductTypeId().getProductTypeId());
                System.out.println(product1.getProductTypeId().getProductTypeName());


            }
        }
        return null;
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
