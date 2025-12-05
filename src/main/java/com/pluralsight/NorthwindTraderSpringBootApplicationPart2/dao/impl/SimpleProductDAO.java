package com.pluralsight.NorthwindTraderSpringBootApplicationPart2.dao.impl;


import com.pluralsight.NorthwindTraderSpringBootApplicationPart2.dao.interfaces.IProductDAO;
import com.pluralsight.NorthwindTraderSpringBootApplicationPart2.models.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SimpleProductDAO implements IProductDAO {
    private List<Product> products;

    public SimpleProductDAO() {
        this.products = new ArrayList<>();
        // Add some initial products
        products.add(new Product(1,"Chips", 5, 23.20));
        products.add(new Product(2,"Milk", 4, 10.23));
        products.add(new Product(3,"Candy", 3, 24.20));
    }

    @Override
    public Product add(Product product) {
        int maxId = 0;
        for (Product t : products) {
            if (t.getProductId() > maxId) {
                maxId = t.getProductId();
            }
        }
        product.setProductId(maxId + 1); // Increment max ID by 1
        products.add(product);
        return product;
    }


    @Override
    public List<Product> getAllProducts() {
        return products;
    }

    @Override
    public Product getProductById(int productId) {
        for (Product product : products) {
            if (product.getProductId() == productId) {
                return product;
            }
        }
        return null;
    }

    @Override
    public void update(int productId, Product product) {
        int index = getProductIndex(productId);
        if (index != -1) {
            products.set(index, product);
        }
    }

    @Override
    public void delete(int productId) {
        int index = getProductIndex(productId);
        if (index != -1) {
            products.remove(index);
        }
    }

    private int getProductIndex(int productId) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId() == productId) {
                return i;
            }
        }
        return -1;
    }
}
