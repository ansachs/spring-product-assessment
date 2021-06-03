package com.hackerrank.eshopping.product.dashboard.respository;

import com.hackerrank.eshopping.product.dashboard.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findByCategoryAndAvailability(String category, Boolean availability);
    List<Product> findByCategory(String category);

    @Override
    List<Product> findAll();
}
