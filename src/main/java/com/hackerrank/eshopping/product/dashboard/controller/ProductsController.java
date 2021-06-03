package com.hackerrank.eshopping.product.dashboard.controller;

import com.hackerrank.eshopping.product.dashboard.model.Product;
import com.hackerrank.eshopping.product.dashboard.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/products")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class ProductsController {

    private final ProductService productService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addProduct(@RequestBody Product product) {
        if (productService.doesProductExist(product.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/{product_id}")
    public ResponseEntity<Void> updateProduct(@PathVariable(value = "product_id") Long productId, @RequestBody Product product) {
        if (!productService.doesProductExist(productId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        productService.updateProduct(productId, product);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{product_id}")
    public ResponseEntity<Product> findProductById(@PathVariable(value = "product_id") Long productId) {
        Optional<Product> maybeProduct = productService.findById(productId);

        return maybeProduct.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = "category")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> findProductsByCategory(@RequestParam String category) throws UnsupportedEncodingException {
        String decodedCategory = URLDecoder.decode(category, StandardCharsets.UTF_8.toString());
        return productService.findProductsByCategory(decodedCategory);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"category", "availability"})
    @ResponseStatus(HttpStatus.OK)
    public List<Product> findProductsByCategoryAndAvailability(@RequestParam String category,
                                                               @RequestParam Integer availability) throws UnsupportedEncodingException {
        String decodedCategory = URLDecoder.decode(category, StandardCharsets.UTF_8.toString());
        return productService.findByCategoryAndAvailability(decodedCategory, Integer.valueOf(1).equals(availability));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Product> findAll() {
        return productService.findAll();
    }


}
