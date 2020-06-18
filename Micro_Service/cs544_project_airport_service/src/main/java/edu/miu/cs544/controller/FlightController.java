package edu.miu.cs544.controller;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.miu.cs544.service.FlightService;
import edu.miu.cs544.service.request.FlightRequest;
import edu.miu.cs544.service.response.FlightResponse;

@RestController
@RequestMapping("/flights")
public class FlightController {
	@Autowired
	private FlightService flightService;
	
	@GetMapping
	public List<FlightResponse> getAll() {
		return flightService.getAll();
	}
	
	@GetMapping(params = {"number"})
	public FlightResponse getByNumber(@RequestParam Integer number) {
		return flightService.getByNumber(number);
	}
	
	@GetMapping(params = {"numbers"})
	public List<FlightResponse> getByNumber(@RequestParam List<Integer> numbers) {
		return flightService.getAllByNumbers(numbers);
	}
	
	@PostMapping
	public ResponseEntity<Collection<FlightResponse>> saveFlights(@RequestBody Collection<FlightRequest> flights) {
		try {
			return ResponseEntity.ok(flightService.saveAll(flights));
		} catch (IllegalArgumentException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}
	
	@PutMapping("/{flightNumber}")
	public ResponseEntity<FlightResponse> putFlights(@RequestBody FlightRequest flightRequest, @PathVariable Integer flightNumber) {
		try {
			return ResponseEntity.ok(flightService.put(flightRequest, flightNumber));
		} catch (IllegalArgumentException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}
	
	@DeleteMapping("/{flightNumber}")
	public ResponseEntity<FlightResponse> deleteFlight(@PathVariable Integer flightNumber) {
		try {
			return ResponseEntity.ok(flightService.deleteFlight(flightNumber));
		} catch (NoSuchElementException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}
	@GetMapping("/available")
	public List<FlightResponse> getAllflightBetweenDepartureAndDestinationForADate(@RequestParam Date departure_date,
																			  @RequestParam String departure_airport,
																			  @RequestParam String arrival_airport
	)
	{
		System.out.println(departure_date);
		return flightService.getDepartureAndDestination(departure_date,departure_airport,arrival_airport);

	}
	
}
