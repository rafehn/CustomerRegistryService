package com.business.hotelcustomer.repo;


import com.business.hotelcustomer.entity.Customer;
import org.springframework.data.repository.Repository;

public interface CustomerCrudRepository extends Repository<Customer, Long> {

    Customer save(Customer customer);

}
