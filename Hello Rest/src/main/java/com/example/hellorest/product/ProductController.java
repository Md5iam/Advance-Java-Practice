package com.example.hellorest.product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @PostMapping("products")
    public Product save(@RequestBody Product product){
        return productService.create(product);
    }

    @GetMapping("products")
    public List<Product> getAll(){
        return productService.findAll();
    }

    @PutMapping("products/{id}")
    public Product update(@PathVariable String id , @RequestBody Product product){
        return productService.update(id , product);
    }

    @DeleteMapping("products/{id}")
    public void delete(@PathVariable String id ){
        productService.delete(id);
    }

}
