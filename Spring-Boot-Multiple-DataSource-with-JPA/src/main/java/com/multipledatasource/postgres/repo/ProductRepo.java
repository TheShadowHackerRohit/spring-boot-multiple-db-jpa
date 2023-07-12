package com.multipledatasource.postgres.repo;

import com.multipledatasource.postgres.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product,Integer> {

    Product findByName(String name);

}
