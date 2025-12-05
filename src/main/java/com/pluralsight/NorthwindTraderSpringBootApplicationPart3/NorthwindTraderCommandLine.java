package com.pluralsight.NorthwindTraderSpringBootApplicationPart3;

import com.pluralsight.NorthwindTraderSpringBootApplicationPart3.models.Product;
import com.pluralsight.NorthwindTraderSpringBootApplicationPart3.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class NorthwindTraderCommandLine implements CommandLineRunner {

    @Autowired
    private ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in); // Scanner for reading user input.
        int choice;
        do {
            // Displaying the menu options to the user.
            System.out.println("========== Ledger Application ==========");
            System.out.println("1. List Products");
            System.out.println("2. Add Product");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Search Product");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consumes the newline character after the integer input.

            // Handling user's choice with switch-case.
            switch (choice) {
                case 1:
                    listProducts(productService);
                    break;
                case 2:
                    addProduct(scanner, productService);
                    break;
                case 3:
                    updateProduct(scanner, productService);
                    break;
                case 4:
                    deleteProduct(scanner, productService);
                    break;
                case 5:
                    searchProduct(scanner, productService);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0); // Repeat until user chooses to exit.

        scanner.close(); // Closing the scanner resource.
    }

    private static void listProducts(ProductService productService) {
        // This method retrieves and displays a list of all products.
        System.out.println("========== List of Products ==========");
        List<Product> products = productService.getAllProducts();
        for (Product product : products) {
            System.out.println(product); // Prints each product.
        }
        System.out.println(); // Adds an empty line for better readability.
    }

    private static void addProduct(Scanner scanner, ProductService productService) {
        // This method adds a new product based on user input.
        System.out.print("Product name: ");
        String productName = scanner.nextLine();
        System.out.print("Enter the category ID: ");
        int categoryId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter unit price: ");
        double unitPrice = scanner.nextDouble();
        scanner.nextLine();

        Product product = new Product(productName, categoryId, unitPrice);
        Product newProduct = productService.addProduct(product); // Adds the product to the service.

        System.out.println("Product added successfully.\n");
        System.out.println(newProduct);
        System.out.println();
    }

    private static void updateProduct(Scanner scanner, ProductService productService) {
        // This method updates an existing product based on user input.
        System.out.print("Enter the product ID to update: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consumes the newline character.

        Product existingProduct = productService.getProductById(productId);
        if (existingProduct == null) {
            System.out.println("Product not found.\n");
            return;
        }

        System.out.print("Enter the product name: ");
        String productName = scanner.nextLine();
        scanner.nextLine(); // Consumes the newline character.
        System.out.print("Enter new vendor name: ");
        int categoryId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the new unit price: ");
        double unitPrice = scanner.nextDouble();
        scanner.nextLine();

        Product updatedProduct = new Product(productId, productName, categoryId, unitPrice);
        productService.updateProduct(productId, updatedProduct); // Updates the product.

        System.out.println("Product updated successfully.\n");
    }

    private static void deleteProduct(Scanner scanner, ProductService productService) {
        // This method deletes a product based on the product ID provided by the user.
        System.out.print("Enter the product ID to delete: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consumes the newline character.

        Product existingProduct = productService.getProductById(productId);
        if (existingProduct == null) {
            System.out.println("Product not found.\n");
            return;
        }

        productService.deleteProduct(productId); // Deletes the product.

        System.out.println("Product deleted successfully.\n");
    }

    private static void searchProduct(Scanner scanner, ProductService productService) {
        // This method searches for a product based on the product ID provided by the user.
        System.out.print("Enter the product ID to search: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consumes the newline character.

        Product product = productService.getProductById(productId);
        if (product == null) {
            System.out.println("Product not found.\n");
        } else {
            System.out.println("========== Product Details ==========");
            System.out.println(product); // Displays the details of the found product.
            System.out.println();
        }
    }

}
