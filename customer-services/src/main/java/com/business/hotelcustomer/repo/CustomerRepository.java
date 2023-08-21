package com.business.hotelcustomer.repo;

import com.business.hotelcustomer.entity.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

    List<Customer> findAll();

    Customer findCustomerByUsername(String username);

    List<Customer> findCustomerByFirstNameAndLastName(String firstName, String lastName);

    boolean existsCustomerByEmail(String email);

    boolean deleteCustomerByUsername(String username);
}
