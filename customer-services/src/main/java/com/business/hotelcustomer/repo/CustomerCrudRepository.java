package com.business.hotelcustomer.repo;

import com.business.hotelcustomer.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerCrudRepository extends CrudRepository<Customer, Long> {
}
