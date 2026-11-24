package com.atlas.backend.service.impl;
import com.atlas.backend.dto.ProductRequest;
import com.atlas.backend.dto.ProductResponse;
import com.atlas.backend.dto.ProductStatusRequest;
import com.atlas.backend.entity.Category;
import com.atlas.backend.entity.Product;
import com.atlas.backend.exception.DuplicateResourceException;
import com.atlas.backend.exception.ResourceNotFoundException;
import com.atlas.backend.repository.CategoryRepository;
import com.atlas.backend.repository.ProductRepository;
import com.atlas.backend.service.ProductService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
   private final ProductRepository productRepository;
   private final CategoryRepository categoryRepository;

//   @Autowired
//   Product product;

   public ProductServiceImpl(ProductRepository productRepository,CategoryRepository categoryRepository ){
       this.productRepository=productRepository;
       this.categoryRepository=categoryRepository;
   }

   @Override
   @Transactional
   public ProductResponse createProduct(ProductRequest request){
       if(productRepository.existsBySku(request.sku())){
           throw new DuplicateResourceException("SKU already exists:" + request.sku());
       }
       Product product=new Product();
       copyRequestToProduct(product,request);
       product.setStatus("ACTIVE");
       Product savedProduct=productRepository.save(product);
       return toResponse(product);

   }

   @Override
   @Transactional(readOnly=true)
   public List<ProductResponse> getAllProducts(){
       return productRepository.findAll()
                               .stream()
                               .map(this::toResponse)
                               .toList();

   }

   @Override
   @Transactional(readOnly = true)
   public ProductResponse getProductById(Long productId){
       Product product=findProductOrThrow(productId);
       return toResponse(product);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long productId,ProductRequest request){
      Product product=findProductOrThrow(productId);
      if(productRepository.existsBySkuAndIdNot(request.sku(),productId )){
          throw new DuplicateResourceException("SKU already exists:" + request.sku());
      }
      copyRequestToProduct(product,request);
      return toResponse(product);

    }

    @Override
    @Transactional
    public ProductResponse updateStatus(Long productId, ProductStatusRequest request){
        Product product=findProductOrThrow(productId);
        product.setStatus(request.status());
        return toResponse(product);

    }

    @Override
    @Transactional
    public void deleteProductById(Long productId){
       Product product=findProductOrThrow(productId);
        product.setStatus("INACTIVE");

    }


   private Product findProductOrThrow(Long productId){
       return productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product not found: "+productId));

   }

   private void copyRequestToProduct(Product product,ProductRequest request ){
       Category category=categoryRepository.findById(request.categoryId())
                                           .orElseThrow(()->new ResourceNotFoundException("category not found:"+request.categoryId()));

       product.setSku(request.sku());
       product.setName(request.name());
       product.setDescription(request.description());
       product.setCategory(category);
       product.setPrice(request.price());

   }

   private ProductResponse toResponse(Product product){
       return new ProductResponse(
               product.getId(),
               product.getSku(),
               product.getName(),
               product.getDescription(),
               product.getCategory().getId(),
               product.getCategory().getName(),
               product.getPrice(),
               product.getStatus(),
               product.getCreatedAt(),
               product.getUpdatedAt());

   }


}
