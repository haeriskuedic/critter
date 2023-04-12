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
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Pet savePet(Pet pet, Long customerId) {
        //return petRepository.save(pet);
        Customer customer = customerRepository.getOne(customerId);

        // set customer to pet
        pet.setCustomer(customer);
        pet = petRepository.save(pet);

        // set pets to customer
        List<Pet> pets = new ArrayList<>();
        pets.add(pet);
        customer.setPets(pets);
        customerRepository.save(customer);

        return pet;
    }

    public List<Pet> getAllPets() {
        List<Pet> pets = petRepository.findAll();

        return pets;
    }

    public Pet getPetById(Long petId) {
        return petRepository.getOne(petId);
    }

    public List<Pet> getPetsByOwner(long ownerId) {
        //List<Pet> pets = new ArrayList<>();
        //pets = customerRepository.getOne(ownerId).getPets();

        List<Pet> pets = petRepository.findPetByCustomerId(ownerId);

        return pets;
    }
}
