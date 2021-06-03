package com.hackerrank.eshopping.product.dashboard.product;

import com.hackerrank.eshopping.product.dashboard.model.Product;

import java.util.Arrays;
import java.util.List;

public class ProductUtil {

    public static Product mergeProduct(Product source, Product target) {
        return target.toBuilder()
                .discountedPrice(source.getDiscountedPrice())
                .availability(source.getAvailability())
                .retailPrice(source.getRetailPrice())
                .build();
    }
}
