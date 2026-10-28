package com.atlas.backend.controller;

import com.atlas.backend.dto.ProductRequest;
import com.atlas.backend.dto.ProductResponse;
import com.atlas.backend.dto.ProductStatusRequest;
import com.atlas.backend.entity.Product;
import com.atlas.backend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

   public ProductController(ProductService productService){
       this.productService=productService;
   }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest request){
       return productService.createProduct(request);
    }

    @GetMapping
    public List<ProductResponse> getAllProducts(){
       return productService.getAllProducts();
    }

   @GetMapping("/{productId}")
   public ProductResponse getProductById(@PathVariable Long productId){
       return productService.getProductById(productId);
    }

    @PutMapping("/{productId}")
    public ProductResponse updateProduct(@PathVariable Long productId,@Valid @RequestBody ProductRequest request){
       return productService.updateProduct(productId,request);

    }

    @PatchMapping("/{productId}/status")
    public ProductResponse updateStatus(@PathVariable Long productId,@Valid @RequestBody ProductStatusRequest request){
       return productService.updateStatus(productId,request);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable Long productId){
       productService.deleteProductById(productId);
    }

}
