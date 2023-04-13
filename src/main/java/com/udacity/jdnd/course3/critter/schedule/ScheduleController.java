package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.data.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    //
    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        //List<Long> employeeIds = schedule.getEmployee().stream().map(Employee::getId).collect(Collectors.toList());
        //List<Long> petIds = schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList());
        /**
         * Employee employee: This is the loop variable declaration. We declare a new variable called employee of type Employee.
         * This variable will take on the value of each element in the collection as we iterate over it.
         *
         * :: This separates the loop variable declaration from the collection we're iterating over.
         *
         * schedule.getEmployee(): This is the collection we're iterating over. In this case, it's the list of employees in the schedule object.
         */
        List<Long> employeeIds = new ArrayList<>();
        for (Employee employee : schedule.getEmployee()) {
            employeeIds.add(employee.getId());
        }

        List<Long> petIds = new ArrayList<>();
        for (Pet pet : schedule.getPets()) {
            petIds.add(pet.getId());
        }

        return new ScheduleDTO(schedule.getId(), employeeIds, petIds, schedule.getDate(), schedule.getActivities());
    };

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule(scheduleDTO.getDate(), scheduleDTO.getActivities());
        //scheduleService.createSchedule(schedule);

        ScheduleDTO convertedSchedule;
        convertedSchedule = convertScheduleToScheduleDTO(scheduleService.createSchedule(schedule, scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds()));

        return convertedSchedule;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();

        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for(Schedule schedule : schedules) {
            ScheduleDTO scheduleDTO = convertScheduleToScheduleDTO(schedule);
            scheduleDTOs.add(scheduleDTO);
        }

        return scheduleDTOs;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.getPetsSchedule(petId);

        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for(Schedule schedule : schedules) {
            ScheduleDTO scheduleDTO = convertScheduleToScheduleDTO(schedule);
            scheduleDTOs.add(scheduleDTO);
        }

        return scheduleDTOs;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.getEmployeeSchedule(employeeId);

        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for(Schedule schedule : schedules) {
            ScheduleDTO scheduleDTO = convertScheduleToScheduleDTO(schedule);
            scheduleDTOs.add(scheduleDTO);
        }

        return scheduleDTOs;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.getCustomerSchedule(customerId);

        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for(Schedule schedule : schedules) {
            ScheduleDTO scheduleDTO = convertScheduleToScheduleDTO(schedule);
            scheduleDTOs.add(scheduleDTO);
        }

        return scheduleDTOs;
    }
}
