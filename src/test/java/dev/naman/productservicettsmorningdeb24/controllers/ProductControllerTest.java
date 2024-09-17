package dev.naman.productservicettsmorningdeb24.controllers;

import dev.naman.productservicettsmorningdeb24.exceptions.ProductNotFoundException;
import dev.naman.productservicettsmorningdeb24.models.Product;
import dev.naman.productservicettsmorningdeb24.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {
    @Autowired
    private ProductController productController;
    //Dependency injection, so applied @AutoWired

    @MockBean
    @Qualifier("fakeStoreProductService")
    private ProductService productService;

    @Test
    void createProduct() {

        productService.getProducts();
    }

    @Test
    void getProductDetailsForPositive() throws ProductNotFoundException {
        //Arrange: arrange the input params
        Product product = new Product();//expectedResponse
        product.setTitle("Laptop");
        product.setPrice(100000);
        product.setDescription("For my dream job preparation");

        //getSingleProduct accepts some id say, my id number is 12
        when(
                productService.getSingleProduct(12L)
        ).thenReturn(product);


        when(
                productService.getSingleProduct(120L)
        ).thenReturn(new Product());


        when(
                productService.getSingleProduct(anyLong())
        ).thenReturn(product);


        //Act: Call the method
        // Get the actual response from productController
        Product actualResponse = productController.getProductDetails(120L);


        //Assert: Now, actualResponse should match it with the expectedResponse
        //If Response fail, send the message also...assertEquals(Expected output, actual output, message)
        assertEquals(product, actualResponse,
                "Product details from productService does not match with what we are getting from the ProductController");
    }

    @Test
    void getProductDetailsForNegative() throws ProductNotFoundException {
        when(
                productService.getSingleProduct(10000L)
        ).thenThrow(ProductNotFoundException.class);

        //assertThrows(expected response, actual response)
        //If I pass 10000, then I will get exception because there is no product id for 10000 in fackStoreProduct
        assertThrows(ProductNotFoundException.class, () -> productController.getProductDetails(10000L));
    }

    @Test
    void getAllProducts() throws ProductNotFoundException {
//        we need mocked productService to get list of products. So, hard coded the value to get the product list...
//        here we didn't call actual productService...instead of this, we do mocked productService and create hard coded list of products
        Product p1 = new Product();
        p1.setTitle("iPhone");
        p1.setPrice(40000);
        p1.setDescription("Branded phone");

        Product p2 = new Product();
        p2.setTitle("Harry Potter");
        p1.setPrice(2000);
        p2.setDescription("Available all version of Book");

        Product p3 = new Product();
        p3.setTitle("iPad");
        p2.setPrice(100000);
        p3.setDescription("Branded pad to write");

        List<Product> productList = new ArrayList<>();//productList is expectedResponse
        productList.add(p1);
        productList.add(p2);
        productList.add(p3);

        when(
                productService.getProducts()
        ).thenReturn(productList);

//        In Actual ProductController, the product is returned in ResponseEntity<List<Product>>.
//        In ProductController test, we are going to return the actual product and we hard-coded the value here in productList,
//        so, we have to return this here as
//        Get the actual response from productController
        ResponseEntity<List<Product>> responseEntity = productController.getAllProducts();
        List<Product> actualResponse = responseEntity.getBody();

        //assertEquals(Expected output, actual output)
        assertEquals(productList.size(), actualResponse.size());
        assertEquals(productList, actualResponse);
    }

    @Test
    void updateProduct() {
    }
}

