package com.pluralsight.NorthwindTraderSpringBootApplicationPart3.dao.interfaces;

import com.pluralsight.NorthwindTraderSpringBootApplicationPart3.models.Product;

import java.util.List;

/**
 * Interface for Data Access Object (DAO) operations related to the Transaction model.
 * This interface defines the standard operations to be performed on the Transaction data.
 */
public interface IProductDAO {

    /**
     * Adds a new Product to the data store.
     *
     * @param product The Transaction object to be added.
     * @return the new Product
     */
    Product add(Product product);

    /**
     * Retrieves all Products from the data store.
     *
     * @return A list of all Products.
     */
    List<Product> getAllProducts();

    /**
     * Retrieves a specific Product by its ID.
     *
     * @param ProductId The ID of the Product to retrieve.
     * @return The Transaction object if found, or null otherwise.
     */
    Product getProductById(int ProductId);

    /**
     * Updates an existing Product in the data store.
     *
     * @param ProductId The ID of the Product to update.
     * @param product The Transaction object with updated information.
     */
    void update(int ProductId, Product product);

    /**
     * Deletes a Product from the data store.
     *
     * @param ProductId The ID of the Product to delete.
     */
    void delete(int ProductId);
}