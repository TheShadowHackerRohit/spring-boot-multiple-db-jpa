package com.multipledatasource.mysql.repo;

import com.multipledatasource.mysql.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {

    User findByEmail(String email);

}
