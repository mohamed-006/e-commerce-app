package com.medali.ecommerce.customer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public String createCustomer(CustomerRequest request) {
        var customer = repository.save(mapper.toCustomer(request));
        return customer.getId();
    }

    public void updateCustomer(@Valid CustomerRequest request) {
        var customer = repository.findById(request.id())
                .orElseThrow(() -> new CustomerNotfoundException(
                        format("Cannot update customer:: %s, customer not found", request.id())
                ));
        mergeCustomer(request, customer);
        repository.save(customer);
    }

    private void mergeCustomer(@Valid CustomerRequest request, Customer customer) {
        if(StringUtils.isNotBlank(request.firstname())) {
            customer.setFirstname(request.firstname());
        }
        if(StringUtils.isNotBlank(request.lastname())) {
            customer.setLastname(request.lastname());
        }
        if(StringUtils.isNotBlank(request.email())) {
            customer.setEmail(request.email());
        }
        if(request.address() != null) {
            customer.setAddress(request.address());
        }
    }

    public List<CustomerResponse> findAllCustomers() {
        return this.repository.findAll()
                .stream()
                .map(this.mapper::fromCustomer)
                .collect(Collectors.toList());
    }

    public Boolean existsById(String customerId) {
        return repository.findById( customerId).isPresent();
    }

    public CustomerResponse findById(String customerId) {
        return repository.findById(customerId)
                .map(mapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotfoundException(
                        format("Customer with id:: %s not found", customerId)
                ));
    }

    public void deleteCustomer(String customerId) {
        if (!repository.existsById(customerId)) {
            throw new CustomerNotfoundException(
                    format("Cannot delete customer:: %s, customer not found", customerId)
            );
        }
        repository.deleteById(customerId);
    }
}
