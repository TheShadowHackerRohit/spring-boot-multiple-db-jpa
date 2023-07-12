package com.multipledatasource;

import com.multipledatasource.mysql.entities.User;
import com.multipledatasource.mysql.repo.UserRepo;
import com.multipledatasource.postgres.entities.Product;
import com.multipledatasource.postgres.repo.ProductRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootMultipleDataSourceWithJpaApplicationTests {
//
//	@Test
//	void contextLoads() {
//	}

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private UserRepo userRepo;


	@Test
	public void dbTest(){
			User user =	User.builder()
						.firstName("Rohit")
						.lastName("Sahu")
						.email("rohitme0091@gmail.com")
						.build();

		Product product = Product.builder()
					.name("Laptop")
					.description("This is lenovo laptop")
					.price(54000)
					.available(true)
					.build();

		userRepo.save(user);
		productRepo.save(product);

		System.out.println("data saved!!!");
	}

	@Test
	public void  getData(){
		productRepo.findAll().forEach(product -> System.out.println(product.getName()));

		userRepo.findAll().forEach(user -> System.out.println(user.getFirstName()));

	}



}
