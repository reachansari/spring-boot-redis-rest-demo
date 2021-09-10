package com.example.demo.redis.rest;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.redis.entities.Employee;
import com.example.demo.redis.services.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<Employee>> getUsers() {
    	final List<Employee> employees = employeeService.findByPattern("*");
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<Employee> getEmployees(@PathVariable("id") final String empId) {
    	final Employee emp = employeeService.findById(empId);
		return new ResponseEntity<>(emp, HttpStatus.OK);
	}

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> createEmployee(@RequestBody final Employee emp) {
    	employeeService.save(emp);
    	return new ResponseEntity<>(HttpStatus.CREATED);
	}

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> updateEmployee(@PathVariable("id") final String empId, @RequestBody final Employee emp) {
    	emp.setId(empId);
    	employeeService.update(emp);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") final String empId) {
    	employeeService.delete(empId);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
