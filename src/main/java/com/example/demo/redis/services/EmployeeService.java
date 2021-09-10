package com.example.demo.redis.services;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.example.demo.redis.entities.Employee;

@Service
public class EmployeeService {

	@Autowired
    private RedisTemplate<String, Employee> userTemplate;

	private static final String REDIS_PREFIX_EMPLOYEES = "employees";

	private static final String REDIS_KEYS_SEPARATOR = ":";

	public List<Employee> findByPattern(final String pattern) {
		return getValueOperations().multiGet(userTemplate.keys(getRedisKey(pattern)));
	}

	public Employee findById(final String userId) {
		final Employee user = getValueOperations().get(getRedisKey(UUID.fromString(userId).toString()));
		if(user == null) {
			throw new NotFoundException("Employee does not exist in the DB");
		}
		return user;
	}

	public void save(final Employee emp) {
		emp.setId(UUID.randomUUID().toString());
		getValueOperations().set(getRedisKey(emp.getId()), emp);
	}

	public void update(final Employee emp) {
		findById(emp.getId());
		getValueOperations().set(getRedisKey(emp.getId()), emp);
	}

	public void delete(final String empId) {
		if(!userTemplate.delete(getRedisKey(UUID.fromString(empId).toString()))) {
			throw new NotFoundException("Employee does not exist in the DB");
		}
	}

	private String getRedisKey(final String empId) {
        return REDIS_PREFIX_EMPLOYEES + REDIS_KEYS_SEPARATOR + empId;
    }

	private ValueOperations<String, Employee> getValueOperations() {
		return userTemplate.opsForValue();
	}

}
