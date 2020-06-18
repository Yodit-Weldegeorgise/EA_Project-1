package edu.miu.cs544.aggregator.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import edu.miu.cs544.aggregator.service.FlightService;
import edu.miu.cs544.aggregator.service.ReservationDetailService;
import edu.miu.cs544.service.aggregator.response.FlightResponse;
import edu.miu.cs544.service.aggregator.response.ReservationDetailResponse;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/flights")
public class FlightController {
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private ReservationDetailService reservationDetailService;
	
	@GetMapping
	public List<FlightResponse> getAll() {
		return flightService.getAll();
	}
	
	@GetMapping(params = {"number"})
	public FlightResponse getByNumber(@RequestParam Integer number) {
		return flightService.getByNumber(number);
	}
	
	@GetMapping(params = {"reservation_code"})
	public List<FlightResponse> getAllByReservationCode(@RequestParam String reservation_code) {
		ReservationDetailResponse[] details = reservationDetailService.getAllByReservationCode(reservation_code);
		List<Integer> flightNumbers = Stream.of(details)
				.parallel().map(detail -> detail.getFlightNumber()).collect(Collectors.toList());
		return flightService.getAllByNumbers(flightNumbers);
	}

	@DeleteMapping("/{flightNumber}")
	public void deleteFlight(@PathVariable Integer flightNumber) {
		flightService.deleteFlight(flightNumber);
	}
}
