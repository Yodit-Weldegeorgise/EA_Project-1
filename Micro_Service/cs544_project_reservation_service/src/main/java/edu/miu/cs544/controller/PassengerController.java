package edu.miu.cs544.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.miu.cs544.exception.InvalidIdException;
import edu.miu.cs544.service.PassengerService;
import edu.miu.cs544.service.request.PassengerRequest;
import edu.miu.cs544.service.response.PassengerResponse;

@RestController
@RequestMapping("passengers")
public class PassengerController {
	@Autowired
	private PassengerService passengerService;
	
	@GetMapping
	public List<PassengerResponse> getAll() {
		return passengerService.getAll();
	} 
	
	@GetMapping("/{id}")
	public PassengerResponse getById(@PathVariable Integer id) {
		try {
			return passengerService.getById(id);
		} catch (IllegalArgumentException ex) {
			throw new InvalidIdException("Passenger id does not exist", ex);
		}		
	}
	
	@PostMapping("/new")
	public PassengerResponse newPassenger(@RequestBody PassengerRequest passengerRequest) {
		return passengerService.save(passengerRequest);
	}
	
	@PutMapping("/{id}")
	public PassengerResponse putPassenger(@RequestBody PassengerRequest passengerRequest, @PathVariable Integer id) {
		return passengerService.putPassenger(passengerRequest, id);		
	}
	
	@DeleteMapping("/{id}")
	public PassengerResponse deletePassenger(@PathVariable Integer id) {
		try {
			return passengerService.deleteById(id);
		} catch (IllegalArgumentException ex) {
			throw new InvalidIdException("Passenger id does not exist", ex);
		}
		
	}
	
}
