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
        // Populate customer object with customerDto properties
        customer.setUsername(customerDto.getUsername());
        customer.setEmail(customerDto.getEmail());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setAddress(customerDto.getAddress());
        customer.setPhone(customerDto.getPhone());
        customer.setGender(customerDto.getGender());

        // Save the customer using customerCrudRepository
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
        customerDto.setAddress(foundCustomer.getAddress());
        customerDto.setPhone(foundCustomer.getPhone());
        customerDto.setGender(foundCustomer.getGender());

        GetCustomerResponse response = new GetCustomerResponse();
        response.setCustomer(customerDto);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "checkCustomerRequest")
    @ResponsePayload
    public CheckCustomerResponse checkCustomer(@RequestPayload CheckCustomerRequest request) {
        String email = request.getEmail();
        boolean exists = customerRepository.existsCustomerByEmail(email);

        CheckCustomerResponse response = new CheckCustomerResponse();
        response.setExists(exists);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateCustomerRequest")
    @ResponsePayload
    public UpdateCustomerResponse updateCustomer(@RequestPayload UpdateCustomerRequest request) {
        CustomerDto updatedCustomerDto = request.getCustomer();
        Customer existingCustomer = customerRepository.findCustomerByUsername(updatedCustomerDto.getUsername());

        if (existingCustomer != null) {
            existingCustomer.setEmail(updatedCustomerDto.getEmail());
            existingCustomer.setFirstName(updatedCustomerDto.getFirstName());
            existingCustomer.setLastName(updatedCustomerDto.getLastName());
            existingCustomer.setAddress(updatedCustomerDto.getAddress());
            existingCustomer.setPhone(updatedCustomerDto.getPhone());
            existingCustomer.setGender(updatedCustomerDto.getGender());

            // Save the updated customer using customerCrudRepository
            Customer savedCustomer = customerCrudRepository.save(existingCustomer);

            UpdateCustomerResponse response = new UpdateCustomerResponse();
            response.setSuccess(savedCustomer != null);
            return response;
        } else {
            // Handle case where customer with given username doesn't exist
            UpdateCustomerResponse response = new UpdateCustomerResponse();
            response.setSuccess(false);
            return response;
        }
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteCustomerRequest")
    @ResponsePayload
    public DeleteCustomerResponse deleteCustomer(@RequestPayload DeleteCustomerRequest request) {
        CustomerDto customerDto = request.getCustomer();
        boolean success = customerRepository.deleteCustomerByUsername(customerDto.getUsername());

        DeleteCustomerResponse response = new DeleteCustomerResponse();
        response.setSuccess(success);

        return response;
    }
}
