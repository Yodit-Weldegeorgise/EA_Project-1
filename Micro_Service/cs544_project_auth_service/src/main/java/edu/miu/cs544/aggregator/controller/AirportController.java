package edu.miu.cs544.aggregator.controller;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import edu.miu.cs544.service.request.AirportRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import edu.miu.cs544.aggregator.service.AirportService;
import edu.miu.cs544.service.aggregator.response.AirportResponse;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/airports")
public class AirportController {
	@Autowired
	private AirportService airportService;
	
	@GetMapping
	public List<AirportResponse> getAll() {
		return airportService.getAll();
	}
	
	@GetMapping(params = {"code"})
	public AirportResponse getByCode(@RequestParam String code) {
		return airportService.getByCode(code);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Collection<AirportResponse> saveAirports(@RequestBody Collection<AirportRequest> airports) {
		return airportService.saveAll(airports);
	}

	@PutMapping("/{code}")
	@PreAuthorize("hasRole('ADMIN')")
	public AirportResponse putAirport(@RequestBody AirportRequest airportRequest, @PathVariable String code) {
		return airportService.put(airportRequest, code);
	}

	@DeleteMapping("/{code}")
	@PreAuthorize("hasRole('ADMIN')")
	public AirportResponse deleteAirport(@PathVariable String code) {
		return airportService.deleteAirport(code);

	}
}
