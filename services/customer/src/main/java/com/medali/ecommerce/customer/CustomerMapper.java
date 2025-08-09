package com.medali.ecommerce.customer;

import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {
    public Customer toCustomer(CustomerRequest request) {
        if(request !=null) {
            return Customer.builder()
                    .id(request.id())
                    .firstname(request.firstname())
                    .lastname(request.lastname())
                    .email(request.email())
                    .address(request.address())
                    .build();
        }
        return null;
    }

    public void updateCustomerFromRequest(@Valid CustomerRequest request, Customer customer) {

    }

    public CustomerResponse fromCustomer(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstname(),
                customer.getLastname(),
                customer.getEmail(),
                customer.getAddress()
        );
    }
}
