package org.example.productshop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public void saveProduct(Product product){
        if ( product.getId() ==404){
            System.out.println("Product not found");
            return;
        }
        productRepository.save(product);
    }

    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    public Product getById(int id ){
        return productRepository.findById(id).orElse(null);
    }

    public void deleteById(int id ){
        productRepository.deleteById(id);
    }

}
