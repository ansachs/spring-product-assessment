package com.hackerrank.eshopping.product.dashboard.product;

import com.hackerrank.eshopping.product.dashboard.model.Product;
import com.hackerrank.eshopping.product.dashboard.respository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public boolean doesProductExist(Long productId) {
        return findById(productId)
                .isPresent();
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public void updateProduct(Long productId, Product product) {
        productRepository.findById(productId)
                .map(t -> ProductUtil.mergeProduct(product, t))
                .ifPresent(productRepository::save);
    }

    public Optional<Product> findById(Long productId) {
        return productRepository.findById(productId);
    }

    public List<Product> findAll() {
        return productRepository.findAll().stream()
                .sorted(Comparator.comparingLong(Product::getId))
                .collect(Collectors.toList());
    }

    public List<Product> findProductsByCategory(String category) {
        return productRepository.findByCategory(category).stream()
                .sorted(Comparator.comparing(Product::getAvailability, Comparator.comparingInt(c -> Boolean.compare(c, true)))
                        .reversed()
                        .thenComparingDouble(Product::getDiscountedPrice)
                        .thenComparingLong(Product::getId)
                ).collect(Collectors.toList());

    }

    public List<Product> findByCategoryAndAvailability(String category, boolean availability) {
        return productRepository.findByCategoryAndAvailability(category, availability).stream()
                .sorted(Comparator.comparingInt(Product::getDiscountPercentage)
                        .reversed()
                        .thenComparingDouble(Product::getDiscountedPrice)
                        .thenComparingLong(Product::getId)
                ).collect(Collectors.toList());
    }
}
