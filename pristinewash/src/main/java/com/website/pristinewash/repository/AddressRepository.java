package com.website.pristinewash.repository;

import com.website.pristinewash.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Query("SELECT a FROM Address a WHERE a.user.user_id = :userId")
    List<Address> findByUserUser_id(@Param("userId") Integer userId);

    @Query("SELECT a FROM Address a WHERE a.user.username = :username")
    List<Address> findByUserUsername(@Param("username") String userId);

    //List<Address> findByUserUser_id(Integer userId);
}
