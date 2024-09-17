package dev.naman.productservicettsmorningdeb24.controllers;

import dev.naman.productservicettsmorningdeb24.dtos.CreateProductRequestDto;
import dev.naman.productservicettsmorningdeb24.dtos.ErrorDto;
import dev.naman.productservicettsmorningdeb24.dtos.UserDTO;
import dev.naman.productservicettsmorningdeb24.exceptions.ProductNotFoundException;
import dev.naman.productservicettsmorningdeb24.models.Product;
import dev.naman.productservicettsmorningdeb24.services.FakeStoreProductService;
import dev.naman.productservicettsmorningdeb24.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

//    private Map<Integer, Integer> mp = new TreeMap<>();
//    List<Integer> li = new ArrayList<>();

    private ProductService productService;
    private RestTemplate restTemplate;

//    private ProductService productService2 = new FakeStoreProductService();


    //To learn testcase, I have added the @Qualifier("fakeStoreProductService") here
    /*public ProductController( @Qualifier("fakeStoreProductService") ProductService productService, RestTemplate restTemplate) {
        this.productService =productService;
        this.restTemplate =restTemplate;
    }*/
    public ProductController(@Qualifier("selfProductService") ProductService productService,
                             RestTemplate restTemplate
    ) {
        this.productService = productService;
        this.restTemplate = restTemplate;
    }

// private ProductService productService;

    // POST /products
    // RequestBody
    // {
    //     title:
    //     description:
    //     price:
    //     category: "mobile"
    // }
    @PostMapping("/products")
    public Product createProduct(@RequestBody CreateProductRequestDto request) {
        return productService.createProduct(
                request.getTitle(),
                request.getDescription(),
                request.getCategory(),
                request.getPrice(),
                request.getImage()
        );
    }

    // GET /products/1
    // GET /products/201

    @GetMapping("/products/{id}")
    public Product getProductDetails(@PathVariable("id") Long productId) throws ProductNotFoundException {
        Product response = productService.getSingleProduct(productId);
        return response;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() throws ProductNotFoundException {

        //I want to call userService from this ProductService using restTemplate
        //Get the url of userService(Usually we use http://localhost:8181/users to call userService, if we want particular id, then we use http://localhost:8181/users/1
        //We don't want to hard code the same end point...

        //So, we do Client Side Load Balancing.
        //Hence, we did fetch.registry = true using eureka(See application.properties)
        //We have 3 instances/servers of userService. So,the url like localhost:UserService:8181, localhost:UserService:8182, localhost:UserService:8183
        //Let's say we want this userID = 2...Among any of the server of userService, we get userId = 2 users data

        //String url = "http://localhost:8181/users/1";
        String url = "http://userservice/users/2";
        //getForObject(endpoint, userDTO), we have use userService from ProductService, so use restTemplate and return userDTO
        //So, we have user object from the userService
        UserDTO userDTO = restTemplate.getForObject(url, UserDTO.class);


        List<Product> productList = productService.getProducts();

        /*
        //For learning test cases. I have added extraProduct here and add into productListSample...
        //I have just commented this whole now and back to earlier code what it is

        List<Product> productListSample = new ArrayList<>();

        Product extraProduct = new Product();
        extraProduct.setPrice(450);
        extraProduct.setTitle("Historical Books");
        extraProduct.setDescription("Buying a novels");

        //productList.add(extraProduct);
        productListSample.add(extraProduct);

//        throw new ProductNotFoundException("Bla bla bla");

        ResponseEntity<List<Product>> response = new ResponseEntity<>(productListSample, HttpStatus.OK);
        return response;*/


        ResponseEntity<List<Product>> response = new ResponseEntity<>(productList, HttpStatus.OK);
        return response;
    }

    public void updateProduct() {

    }

//    @ExceptionHandler(ProductNotFoundException.class)
//    public ResponseEntity<ErrorDto> handleProductNotFoundExeption(ProductNotFoundException exception) {
//
//        ErrorDto errorDto = new ErrorDto();
//        errorDto.setMessage(exception.getMessage());
//
//        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
////        return null;
//    }

    // Limited to only the exceptions thrown from this controller
    // Controller Advices: Global

    // if this controller ever ends up throwing ProductNotFoundException.class
    // for any reason, don't throw that exception as is.
    // Instead call this method and return what this method is returning
}

// allProducts - X
// all APIs should be structured around resources
// GET  /products