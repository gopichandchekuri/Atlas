package com.atlas.backend.service;

import com.atlas.backend.dto.*;

import java.util.List;

public interface ProductService {
     ProductResponse createProduct(ProductRequest request);
     List<ProductResponse> getAllProducts();
     ProductResponse getProductById(Long productId);
     ProductResponse updateProduct(Long productId,ProductRequest request);
     ProductResponse updateStatus (Long productId,ProductStatusRequest request);
     void deleteProductById(Long productId);
}
