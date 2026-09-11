package com.example.hellorest.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Product findById(String id){
        return productRepository.findById(id).orElse(null);
    }

    public Product create(Product product){
        return productRepository.save(product);
    }

    public Product update (String id, Product product){
        Product existingProduct = findById(id);
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());

        return  productRepository.save(existingProduct);
    }

    public void delete(String id ){
        productRepository.deleteById(id);
    }
}
