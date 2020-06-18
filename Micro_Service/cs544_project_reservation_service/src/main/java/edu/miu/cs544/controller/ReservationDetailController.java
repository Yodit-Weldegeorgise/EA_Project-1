package edu.miu.cs544.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.miu.cs544.service.ReservationDetailService;
import edu.miu.cs544.service.response.ReservationDetailResponse;

@RestController
@RequestMapping("/reservations/details")
public class ReservationDetailController {
	@Autowired
	private ReservationDetailService reservationDetailService;
	
	@GetMapping
	public List<ReservationDetailResponse> getAll() {
		return reservationDetailService.getAll();
	}
	
	@GetMapping(params = "reservation_code")
	public List<ReservationDetailResponse> getAllByReservationCode(String reservation_code) {
		return reservationDetailService.getAllByReservationCode(reservation_code);
	}
	
	@GetMapping(params = {"reservation_code", "passenger_id"})
	public List<ReservationDetailResponse> getAllByReservationCodeAndPassengerId(String reservation_code, @RequestParam Integer passenger_id) {
		return reservationDetailService.getAllByReservationCodeAndPassengerId(reservation_code, passenger_id);
	}
	
	@GetMapping(params = {"reservation_code", "user_email"})
	public List<ReservationDetailResponse> getAllByReservationCodeAndUserEmail(String reservation_code, @RequestParam String user_email) {
		return reservationDetailService.getAllByReservationCodeAndUserEmail(reservation_code, user_email);
	}
	
}
