package com.website.pristinewash.repository;

import com.website.pristinewash.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query("SELECT a FROM Cart a WHERE a.user.user_id = :userId")
    Optional<Cart> findByUserUserId(Integer userId);
}
