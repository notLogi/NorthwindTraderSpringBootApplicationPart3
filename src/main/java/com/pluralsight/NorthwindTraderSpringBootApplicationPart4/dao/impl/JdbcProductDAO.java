package com.pluralsight.NorthwindTraderSpringBootApplicationPart4.dao.impl;


import com.pluralsight.NorthwindTraderSpringBootApplicationPart4.dao.interfaces.IProductDAO;
import com.pluralsight.NorthwindTraderSpringBootApplicationPart4.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcProductDAO implements IProductDAO {

    private DataSource dataSource;

    @Autowired
    public JdbcProductDAO(DataSource dataSource) {
        this.dataSource = dataSource;
        initialize(); // Initialize database tables and data on startup.
    }

    private void initialize() {
        // This method sets up the database table and populates it with initial data if necessary.
        try (Connection connection = dataSource.getConnection()) {
            // SQL statement to create a products table if it does not exist.
            String createTableQuery = "CREATE TABLE IF NOT EXISTS products (" +
                    "product_id INT PRIMARY KEY AUTO_INCREMENT," +
                    "amount DECIMAL(10, 2) NOT NULL," +
                    "vendor VARCHAR(255) NOT NULL" +
                    ")";
            try (PreparedStatement createTableStatement = connection.prepareStatement(createTableQuery)) {
                createTableStatement.execute(); // Execute the table creation query.
            }

            // Check if the table has any data already.
            String countQuery = "SELECT COUNT(*) AS rowCount FROM products";
            try (PreparedStatement countStatement = connection.prepareStatement(countQuery);
                 ResultSet resultSet = countStatement.executeQuery()) {
                if (resultSet.next() && resultSet.getInt("rowCount") == 0) {
                    // Insert initial data if the table is empty.
                    String insertDataQuery = "INSERT INTO products (ProductName, CategoryID, UnitPrice) VALUES (?, ?, ?)";
                    try (PreparedStatement insertDataStatement = connection.prepareStatement(insertDataQuery)) {
                        // Insert first product.
                        insertDataStatement.setString(1, "Chips");
                        insertDataStatement.setInt(2, 4);
                        insertDataStatement.setDouble(2, 2.50);
                        insertDataStatement.executeUpdate();

                        // Insert second product.
                        insertDataStatement.setString(1, "Toys");
                        insertDataStatement.setInt(2, 3);
                        insertDataStatement.setDouble(2, 32.50);
                        insertDataStatement.executeUpdate();

                        // Insert third product.
                        insertDataStatement.setString(1, "Candy");
                        insertDataStatement.setInt(2, 5);
                        insertDataStatement.setDouble(2, 4.50);
                        insertDataStatement.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle the SQL exception.
        }
    }

    @Override
    public Product add(Product product) {
        // This method adds a new product to the database.
        String insertDataQuery = "INSERT INTO products (ProductName, CategoryID, UnitPrice) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(insertDataQuery, Statement.RETURN_GENERATED_KEYS)) {
            // Setting parameters for the insert query.
            insertStatement.setString(1, product.getProductName());
            insertStatement.setInt(2, product.getCategoryId());
            insertStatement.setDouble(3, product.getUnitPrice());
            int affectedRows = insertStatement.executeUpdate(); // Execute the insert query.

            if (affectedRows == 0) {
                throw new SQLException("Creating product failed, no rows affected.");
            }

            try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    product.setProductId(generatedId);
                } else {
                    throw new SQLException("Creating product failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle the SQL exception.
        }

        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        // This method retrieves all products from the database.
        List<Product> products = new ArrayList<>();
        String getAllQuery = "SELECT * FROM products";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(getAllQuery);
             ResultSet resultSet = selectStatement.executeQuery()) {
            while (resultSet.next()) {
                // Extract data from each row in the result set.
                int productId = resultSet.getInt("ProductID");
                String productName = resultSet.getString("ProductName");
                int categoryId = resultSet.getInt("CategoryID");
                double unitPrice = resultSet.getDouble("UnitPrice");
                // Create a Product object and add it to the list.
                products.add(new Product(productId, productName, categoryId, unitPrice));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle the SQL exception.
        }
        return products; // Return the list of products.
    }

    @Override
    public Product getProductById(int productId) {
        // This method retrieves a specific product by its ID.
        Product product = null;
        String getByIdQuery = "SELECT * FROM products WHERE ProductID = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(getByIdQuery)) {
            selectStatement.setInt(1, productId); // Set the ID parameter in the query.
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Extract data from the result set.
                    int productIdFromDb = resultSet.getInt("ProductID");
                    String productName = resultSet.getString("ProductName");
                    int categoryId = resultSet.getInt("CategoryID");
                    double UnitPrice = resultSet.getDouble("UnitPrice");
                    // Create a Product object.
                    product = new Product(productIdFromDb, productName, categoryId, UnitPrice);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle the SQL exception.
        }
        return product; // Return the found product or null.
    }

    @Override
    public void update(int productId, Product product) {
        // This method updates an existing product in the database.
        String updateDataQuery = "UPDATE products SET ProductName = ?, CategoryID = ?, UnitPrice = ? WHERE ProductID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(updateDataQuery)) {
            // Setting parameters for the update query.
            updateStatement.setString(1, product.getProductName());
            updateStatement.setInt(2, product.getCategoryId());
            updateStatement.setDouble(3, product.getUnitPrice());
            updateStatement.setInt(4, productId);
            updateStatement.executeUpdate(); // Execute the update query.
        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle the SQL exception.
        }
    }

    @Override
    public void delete(int productId) {
        // This method deletes a product from the database.
        String deleteDataQuery = "DELETE FROM products WHERE ProductID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(deleteDataQuery)) {
            deleteStatement.setInt(1, productId); // Set the ID parameter in the delete query.
            deleteStatement.executeUpdate(); // Execute the delete query.
        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle the SQL exception.
        }
    }
}