package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.data.Customer;
import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    CustomerService customerService;

    @Autowired
    EmployeeService employeeService;

    // Convert Entities to DTOs
    private CustomerDTO convertCustomerToCustomerDTO(Customer customer) {
        /**
         * It gets a list of all the pets owned by the customer using customer.getPets().
         * It converts this list of pets to a stream of pets using stream().
         * It then maps each pet in the stream to its id property using map(Pet::getId). This means it creates a new stream with only the id properties of the pets.
         * It finally collects these mapped id properties into a new list of Long using collect(Collectors.toList()). This creates a list of pet IDs owned by the customer, which is assigned to the petIds variable.
         *
         * So, in summary, the line of code creates a new list of the IDs of all the pets owned by a customer.
         */
        List<Long> petIds = customer.getPets().stream().map(Pet::getId).collect(Collectors.toList());
        /**
         * Alternative way with loop instead of stream:
         */
        //List<Long> petIds = new ArrayList<>();
        //for (Pet pet : customer.getPets()) {
        //   petIds.add(pet.getId());
        //}

        return new CustomerDTO(customer.getId(), customer.getName(), customer.getPhoneNumber(), customer.getNotes(), petIds);
    }

    private EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        return new EmployeeDTO(employee.getId(), employee.getName(), employee.getSkills(), employee.getDaysAvailable());
    }


    // HTTP methods
    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = new Customer(customerDTO.getId(), customerDTO.getName(), customerDTO.getPhoneNumber(), customerDTO.getNotes());

        // save customer and convert customer to customerDTO
        //customerService.saveCustomer(customer);
        CustomerDTO convertedCustomer;
        convertedCustomer = convertCustomerToCustomerDTO(customerService.saveCustomer(customer));

        return convertedCustomer;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();

        /**
         * It takes a list of Customer objects called customers.
         * It converts this list of Customer objects to a stream of Customer objects using customers.stream().
         * It maps each Customer object in the stream to its corresponding CustomerDTO object using the convertCustomerToCustomerDTO() method. This means it creates a new stream with only CustomerDTO objects.
         * It finally collects these mapped CustomerDTO objects into a new list using collect(Collectors.toList()). This creates a list of CustomerDTO objects, which is returned by the method.
         */
        //return customers.stream().map(this::convertCustomerToCustomerDTO).collect(Collectors.toList());
        /**
         * Alternative way with loop instead of stream:
         */
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        for (Customer customer : customers) {
            CustomerDTO customerDTO = convertCustomerToCustomerDTO(customer);
            customerDTOs.add(customerDTO);
        }

        return customerDTOs;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        Customer customer = customerService.getCustomerByPet(petId);

        return convertCustomerToCustomerDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee(employeeDTO.getId(), employeeDTO.getName(), employeeDTO.getSkills(), employeeDTO.getDaysAvailable());

        // save employee and convert employee to employeeDTO
        EmployeeDTO convertedEmployee;
        convertedEmployee = convertEmployeeToEmployeeDTO(employeeService.saveEmployee(employee));

        return convertedEmployee;
    }

    //@PostMapping("/employee/{employeeId}")
    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);

        return convertEmployeeToEmployeeDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setEmployeeAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        // TODO: findEmployeesForService / UserController


        throw new UnsupportedOperationException();
    }

}
