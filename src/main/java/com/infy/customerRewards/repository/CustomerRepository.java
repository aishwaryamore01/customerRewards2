package com.infy.customerRewards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infy.customerRewards.entity.Customer;


@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long>{

}
