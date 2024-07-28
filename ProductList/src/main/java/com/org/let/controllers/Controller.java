package com.org.let.controllers;

import com.org.let.models.ModelProduct;
import com.org.let.request.productType.RequestProduct;
import com.org.let.response.ProductResponseDTO;
import com.org.let.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/prod/")
public class Controller {

    String fileSavePath="";
    @Autowired
    private ProductService productService;

    @GetMapping("all")
    public List<ModelProduct> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("search/id/{id}")
    public ProductResponseDTO getAllProducts(@PathVariable("id") Long id) {
        ProductResponseDTO productResponseDTO = productService.getProductByID(id);
        return productResponseDTO;
    }

    @GetMapping("search/name/{name}")
    public ModelProduct getProductByName(@PathVariable("name") String name) {
        Optional<ModelProduct> product = productService.getProductByName(name);
        if(product.isPresent()){
            return product.get();
        }else{
            return null;
        }
    }
    @GetMapping("search/query")
    public ModelProduct getProductByQuery(@RequestParam("name") String name,
                                          @RequestParam(value = "id", required = false) int id) {
        Optional<ModelProduct> product = productService.getProductByQuery(name,id);
        if(product.isPresent()){
            return product.get();
        }else{
            return null;
        }
    }

    @PostMapping("add/product")
    public RequestProduct AddProduct( @RequestParam("file") MultipartFile file,
                               @RequestParam("name") String name,
                               @RequestParam("type") Long type,
                               @RequestParam("description") String desc,
                               @RequestParam("operation") String operation,
                                      @RequestParam("build") String built,
                                      @RequestParam("rating") Long rating,
                                      @RequestParam("price") Double price,
                                      @RequestParam("warranty") Long warranty) {
        String uploadDir="";
        String fileDownloadUri="";
        if(!file.isEmpty()){
            uploadDir="C:\\DEV\\SPRING_CLI\\UploadedFiles\\products\\"+ File.separator+file.getOriginalFilename();
            fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/prod/download/products/")
                    .path(file.getOriginalFilename())
                    .toUriString();
            System.out.println("Callling other");
            ResponseEntity<String> responseEntity= uploadFile(file);
            System.out.println(responseEntity.getStatusCode());
        }else{
            System.out.println("File Empty....");
        }

        RequestProduct requestProduct;
        requestProduct = new RequestProduct(name,desc,fileDownloadUri,operation,built,type,rating,warranty,price);


        productService.addProduct(requestProduct);
        return requestProduct;
    }


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file to upload");
        }
        String uploadDir="C:\\DEV\\SPRING_CLI\\UploadedFiles\\products\\";
        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDir + File.separator + Objects.requireNonNull(file.getOriginalFilename()));
            Files.write(path, bytes);

            // Generate file download URL
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/prod/download/")
                    .path(file.getOriginalFilename())
                    .toUriString();

            return ResponseEntity.ok().body("File uploaded successfully. Download URL: " + fileDownloadUri);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
        }
    }

    @GetMapping("/download/{category}/{fileName:.+}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("category") String dirName, @PathVariable String fileName) {
        String uploadDir="C:\\DEV\\SPRING_CLI\\UploadedFiles\\"+dirName+"\\";
        try {
            Path path = Paths.get(uploadDir + File.separator + fileName);
            byte[] fileBytes = Files.readAllBytes(path);

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                    .body(fileBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/print/{fileName:.+}")
    public ResponseEntity<Resource> print(@PathVariable String fileName) {
        String uploadDir="C:\\DEV\\SPRING_CLI\\";
        try {
            Path path = Paths.get(uploadDir + File.separator + fileName);
            byte[] fileBytes = Files.readAllBytes(path);
            ByteArrayResource resource = new ByteArrayResource(fileBytes);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(fileBytes.length)
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
