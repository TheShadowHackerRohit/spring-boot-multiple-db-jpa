package com.multipledatasource.postgres.controller;

import com.multipledatasource.mysql.entities.User;
import com.multipledatasource.postgres.entities.Product;
import com.multipledatasource.postgres.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {


    @Autowired
    ProductRepo productRepo;


    @PostMapping("/save-product")
    public ResponseEntity addProduct(@RequestParam String name, @RequestParam String description, @RequestParam boolean available, @RequestParam double price ){

        Product product = new Product();

        product.setName(name);
        product.setAvailable(available);
        product.setPrice(price);
        product.setDescription(description);

        Product savedProduct = productRepo.save(product);

        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);

    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity getByID(@PathVariable int id){
        Product product = productRepo.findById(id).get();

        return new ResponseEntity<>(product, HttpStatus.FOUND);
    }

    @GetMapping("/find-all-product")
    public ResponseEntity getAllProduct(){
        List<Product> productList = productRepo.findAll();
        return new ResponseEntity<>(productList, HttpStatus.FOUND);
    }

    @PutMapping("update-price/{id}")
    public ResponseEntity updateEmailById(@PathVariable int id, @RequestParam double newPrice){
       Product product = productRepo.findById(id).get();
       product.setPrice(newPrice);

       Product savedProduct = productRepo.save(product);

        return new ResponseEntity<>(savedProduct, HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id){
       productRepo.deleteById(id);
        return "Product has been deleted";

    }



}
