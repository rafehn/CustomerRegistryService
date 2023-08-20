package com.business.hotelcustomer.endpoint;

import com.baeldung.springsoap.gen.*;
import com.business.hotelcustomer.entity.Customer;
import com.business.hotelcustomer.repo.CustomerCrudRepository;
import com.business.hotelcustomer.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class CustomerEndpoint {

    private static final String NAMESPACE_URI = "http://www.baeldung.com/springsoap/gen";
    private final CustomerRepository customerRepository;
    private final CustomerCrudRepository customerCrudRepository;

    @Autowired
    public CustomerEndpoint(CustomerRepository customerRepository, CustomerCrudRepository customerCrudRepository) {
        this.customerRepository = customerRepository;
        this.customerCrudRepository = customerCrudRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "defineCustomerRequest")
    @ResponsePayload
    public DefineCustomerResponse defineCustomer(@RequestPayload DefineCustomerRequest request) {

        CustomerDto customerDto = request.getCustomer();
        Customer customer = new Customer();

        customer.setUsername(customerDto.getUsername());
        customer.setEmail(customerDto.getEmail());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        Customer savedCustomer = customerCrudRepository.save(customer);
        customerDto.setId(savedCustomer.getId());

        DefineCustomerResponse response = new DefineCustomerResponse();
        response.setCustomer(customerDto);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCustomerRequest")
    @ResponsePayload
    public GetCustomerResponse getCustomer(@RequestPayload GetCustomerRequest request) {

        String username = request.getUsername();

        Customer foundCustomer = customerRepository.findCustomerByUsername(username);

        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(foundCustomer.getId());
        customerDto.setUsername(foundCustomer.getUsername());
        customerDto.setEmail(foundCustomer.getEmail());
        customerDto.setFirstName(foundCustomer.getFirstName());
        customerDto.setLastName(foundCustomer.getLastName());

        GetCustomerResponse response = new GetCustomerResponse();
        response.setCustomer(customerDto);

        return response;
    }

}
