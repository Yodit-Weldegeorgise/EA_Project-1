package edu.miu.cs544.controller;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import edu.miu.cs544.service.AirlineService;
import edu.miu.cs544.service.request.AirlineRequest;
import edu.miu.cs544.service.response.AirlineResponse;

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
	public List<AirlineResponse> getAllByDepartureAirportCode(@RequestParam String departure_airport_code) {
		return airlineService.getAllByDepartureAirportCode(departure_airport_code);
	}
	
	@PostMapping
	public Collection<AirlineResponse> saveAirlines(@RequestBody Collection<AirlineRequest> airlines) {
		return airlineService.saveAll(airlines);
	}
	
	@PutMapping("/{code}")
	public AirlineResponse  putAirline(@RequestBody AirlineRequest airlineRequest, @PathVariable String code) {
		try {
			return airlineService.put(airlineRequest, code);
		} catch (IllegalArgumentException ex)  {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}

	}
	
	@DeleteMapping("/{code}")
	public AirlineResponse deleteAirline(@PathVariable String code) {
		try {
			return airlineService.deleteAirline(code);
		} catch (NoSuchElementException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		} catch (IllegalArgumentException ex) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
		}
	}
	
}
