package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.Customer;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetRepository petRepository;

    public Customer saveCustomer(Customer customer, List<Long> petIds) {
        List<Pet> customerPets = new ArrayList<>();

        if (petIds != null && !petIds.isEmpty()) {
            /*
            This code iterates over each petId in the petIds list and uses it to call the getOne method
            of the petRepository, which returns a Pet object.
            The Pet object is then added to the customerPets list using the add method.
             */
            for (Long petId : petIds) {
                customerPets.add(petRepository.getOne(petId));
            }
        }

        customer.setPets(customerPets);

        return customerRepository.save(customer);
    };

    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();

        return customers;
    }

    public Customer getCustomerByPet(Long petId){
        //return petRepository.getOne(petId).getCustomer();
        Customer customer = petRepository.getOne(petId).getCustomer();

        return customer;
    }

}
