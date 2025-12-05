package com.pluralsight.NorthwindTraderSpringBootApplicationPart3.services;

import com.pluralsight.NorthwindTraderSpringBootApplicationPart3.dao.interfaces.IProductDAO;
import com.pluralsight.NorthwindTraderSpringBootApplicationPart3.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ProductService provides a layer of abstraction between the controller and the data access object (DAO).
 * It contains the business logic to interact with products and uses the IProductDAO for database operations.
 */
@Component
// Indicates that this class is a Spring component. It will be automatically detected and instantiated by Spring.
public class ProductService {
    private IProductDAO ProductDAO; // Interface to data access object for products.

    /**
     * Autowired constructor for dependency injection.
     * Spring will inject an implementation of IProductDAO when ProductService is created.
     *
     * @param ProductDAO the DAO implementation to be used by this service.
     */
    @Autowired // Injects the ProductDAO implementation.
    public ProductService(IProductDAO ProductDAO) {
        this.ProductDAO = ProductDAO;
    }

    /**
     * Adds a new product.
     *
     * @param product the product to add.
     * @return the new product
     */
    public Product addProduct(Product product) {
        return ProductDAO.add(product);
    }

    /**
     * Retrieves all products.
     *
     * @return a list of all products.
     */
    public List<Product> getAllProducts() {
        return ProductDAO.getAllProducts();
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param productId the ID of the product.
     * @return the product with the specified ID, or null if not found.
     */
    public Product getProductById(int productId) {
        return ProductDAO.getProductById(productId);
    }

    /**
     * Updates an existing product.
     *
     * @param product the product with updated details.
     */
    public void updateProduct(int productId, Product product) {
        ProductDAO.update(productId, product);
    }

    /**
     * Deletes a product.
     *
     * @param productId The ID of the product to delete.
     */
    public void deleteProduct(int productId) {
        ProductDAO.delete(productId);
    }
}