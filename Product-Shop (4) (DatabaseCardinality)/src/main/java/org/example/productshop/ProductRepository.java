package org.example.productshop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Custome query = @Query(....)

    // SELECT *FROM product WHERE stock > 10
    List<Product> findAllByStockGreaterThan(int stock);
    // SELECT * FROM product name = ? AND category = ?
    List<Product> findAllByNameEqualsIgnoreCaseAndCategoryIgnoreCase(String name, String category);

    void deleteAllByCategoryIgnoreCase(String category);
    long countAllByCategoryEqualsIgnoreCase(String category);
}
