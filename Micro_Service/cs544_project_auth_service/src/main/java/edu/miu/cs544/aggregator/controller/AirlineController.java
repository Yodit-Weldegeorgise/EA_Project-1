package edu.miu.cs544.aggregator.controller;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import edu.miu.cs544.service.request.AirlineRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import edu.miu.cs544.aggregator.service.AirlineService;
import edu.miu.cs544.service.aggregator.response.AirlineResponse;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/airlines")
public class AirlineController {
	@Autowired
	private AirlineService airlineService;
	
	@GetMapping
	public List<AirlineResponse> getAll() {
		return airlineService.getAll();
	}
	
	@GetMapping(params = {"code"})
	public AirlineResponse getByCode(@RequestParam String code) {
		return airlineService.getByCode(code);
	}
	
	@GetMapping(params = {"departure_airport_code"})
	public List<AirlineResponse> getByDepartureAirportCode(@RequestParam String departure_airport_code) {
		return airlineService.getAllByDepartureAirportCode(departure_airport_code);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Collection<AirlineResponse> saveAirlines(@RequestBody Collection<AirlineRequest> airlines) {
		return airlineService.saveAll(airlines);
	}

	@PutMapping("/{code}")
	@PreAuthorize("hasRole('ADMIN')")
	public AirlineResponse putAirline(@RequestBody AirlineRequest airlineRequest, @PathVariable String code) {
		return airlineService.put(airlineRequest, code);
	}

	@DeleteMapping("/{code}")
	@PreAuthorize("hasRole('ADMIN')")
	public AirlineResponse deleteAirline(@PathVariable String code) {
		return airlineService.deleteAirline(code);
	}
}
