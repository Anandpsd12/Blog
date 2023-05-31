package com.blog.repositories;

import com.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {  // User import from blog.entities


      Optional<User> findByEmail(String email);  // When we call this method (UserRepository.findById Spring boot automatically write the SQL query & get the record from id)
      Optional<User> findByUsernameOrEmail(String username, String email);
      Optional<User> findByUsername(String username);  // it will search the record based on username

      Boolean existsByUsername(String username);
      Boolean existsByEmail(String email);
}
