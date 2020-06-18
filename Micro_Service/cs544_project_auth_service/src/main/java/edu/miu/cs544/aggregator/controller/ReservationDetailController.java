package edu.miu.cs544.aggregator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.miu.cs544.aggregator.service.ReservationDetailService;
import edu.miu.cs544.domain.ERole;
import edu.miu.cs544.domain.User;
import edu.miu.cs544.service.UserService;
import edu.miu.cs544.service.aggregator.response.ReservationDetailResponse;

@RestController
@RequestMapping("/reservations/details")
public class ReservationDetailController {
	@Autowired
	private ReservationDetailService reservationDetailService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public List<ReservationDetailResponse> getAll() {
		return reservationDetailService.getAll();
	}
	
	@GetMapping(params = {"reservation_code"})
	public ReservationDetailResponse[] getAllByReservationCode(@RequestParam String reservation_code) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.getByUsername(auth.getName());
		
		if(user.getRole().getName() == ERole.ROLE_PASSENGER)
		{
			return reservationDetailService.getAllByReservationCodeAndPassengerId(reservation_code, user.getPassengerId());
		}
		if(user.getRole().getName() == ERole.ROLE_AGENT)
		{
			return reservationDetailService.getAllByReservationCodeAndUserEmail(reservation_code, user.getUsername());
		}
		return reservationDetailService.getAllByReservationCode(reservation_code);
	}
}
